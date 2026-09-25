package carrerforge.dto;

import carrerforge.entity.Job;
import java.time.LocalDate;

public record JobResponse(
        Long id,
        String title,
        String company,
        String location,
        String jobType,
        String experienceLevel,
        String salaryRange,
        String applicationUrl,
        String description,
        String skills,
        LocalDate postedDate,
        boolean remoteAvailable) {

    public static JobResponse from(Job job) {
        return new JobResponse(job.getId(), job.getTitle(), job.getCompany(), job.getLocation(),
                job.getJobType(), job.getExperienceLevel(), job.getSalaryRange(), job.getApplicationUrl(),
                job.getDescription(), job.getSkills(), job.getPostedDate(), job.isRemoteAvailable());
    }
}