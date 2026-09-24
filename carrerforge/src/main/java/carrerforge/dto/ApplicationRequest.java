package carrerforge.dto;
import jakarta.validation.constraints.*;
public record ApplicationRequest(@NotNull Long jobId,@NotNull Long userId,@NotBlank String fullName,@NotBlank @Email String email,@NotBlank String phone,String resumeFileName,@NotBlank @Size(min=20,message="Cover letter must be at least 20 characters") String coverLetter) {}
