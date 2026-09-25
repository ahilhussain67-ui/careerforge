package carrerforge.dto;

import carrerforge.entity.Job;
import java.time.LocalDate;

public record JobSummaryResponse(
        Long id,
        String title,
        String company,
        String location,
        String jobType,
        String experienceLevel,
        String salaryRange,
        String applicationUrl,
        String skills,
        LocalDate postedDate,
        boolean remoteAvailable) {

    public static JobSummaryResponse from(Job job) {
        return new JobSummaryResponse(job.getId(), job.getTitle(), job.getCompany(), job.getLocation(),
                job.getJobType(), job.getExperienceLevel(), job.getSalaryRange(), job.getApplicationUrl(),
                job.getSkills(), job.getPostedDate(), job.isRemoteAvailable());
    }
}