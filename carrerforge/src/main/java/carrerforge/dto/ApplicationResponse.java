package carrerforge.dto;
import carrerforge.entity.ApplicationStatus;
import carrerforge.entity.JobApplication;
import java.time.LocalDateTime;
public record ApplicationResponse(Long id,Long jobId,String jobTitle,String company,String location,String fullName,String email,String phone,String resumeFileName,LocalDateTime appliedAt,ApplicationStatus status){public static ApplicationResponse from(JobApplication a){return new ApplicationResponse(a.getId(),a.getJob().getId(),a.getJob().getTitle(),a.getJob().getCompany(),a.getJob().getLocation(),a.getFullName(),a.getEmail(),a.getPhone(),a.getResumeFileName(),a.getAppliedAt(),a.getStatus());}}
