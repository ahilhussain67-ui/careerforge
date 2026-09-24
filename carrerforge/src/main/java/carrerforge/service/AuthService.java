package carrerforge.service;

import carrerforge.dto.LoginRequest;
import carrerforge.dto.RegisterRequest;
import carrerforge.dto.UserResponse;
import carrerforge.entity.User;
import carrerforge.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository) { this.userRepository = userRepository; }

    public UserResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("Email is already registered");
        }
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        return UserResponse.from(userRepository.save(user));
    }

    public UserResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .filter(candidate -> passwordEncoder.matches(request.password(), candidate.getPassword())
                        || candidate.getPassword().equals(request.password()))
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        if (!user.getPassword().startsWith("$2")) {
            user.setPassword(passwordEncoder.encode(request.password()));
            userRepository.save(user);
        }
        return UserResponse.from(user);
    }
}
