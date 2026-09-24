package carrerforge.service;

import java.util.List;

import carrerforge.dto.LoginRequest;
import carrerforge.dto.RegisterRequest;
import carrerforge.dto.UserResponse;
import carrerforge.entity.User;
import carrerforge.exception.EmailAlreadyExistsException;
import carrerforge.exception.InvalidCredentialsException;
import carrerforge.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public User save(User user) {
        return repository.save(user);
    }

    public UserResponse register(RegisterRequest request) {

        // Duplicate email check
        if (repository.findByEmail(request.email()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already registered");
        }

        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));

        return UserResponse.from(repository.save(user));
    }

    public UserResponse login(LoginRequest request) {

        User user = repository.findByEmail(request.email()).orElseThrow(InvalidCredentialsException::new);
        boolean matches = passwordEncoder.matches(request.password(), user.getPassword())
                || user.getPassword().equals(request.password());
        if (!matches) throw new InvalidCredentialsException();
        if (!user.getPassword().startsWith("$2")) {
            user.setPassword(passwordEncoder.encode(request.password()));
            repository.save(user);
        }

        return UserResponse.from(user);
    }
}
