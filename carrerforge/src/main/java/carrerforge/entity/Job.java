package carrerforge.entity;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity
public class Job {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private String title, company, location, jobType, experienceLevel, salaryRange, applicationUrl;
 @Column(columnDefinition="TEXT") private String description;
 private String skills;
 private LocalDate postedDate;
 private boolean remoteAvailable;
 public Long getId(){return id;} public void setId(Long v){id=v;} public String getTitle(){return title;} public void setTitle(String v){title=v;} public String getCompany(){return company;} public void setCompany(String v){company=v;} public String getLocation(){return location;} public void setLocation(String v){location=v;} public String getJobType(){return jobType;} public void setJobType(String v){jobType=v;} public String getExperienceLevel(){return experienceLevel;} public void setExperienceLevel(String v){experienceLevel=v;} public String getSalaryRange(){return salaryRange;} public void setSalaryRange(String v){salaryRange=v;} public String getApplicationUrl(){return applicationUrl;} public void setApplicationUrl(String v){applicationUrl=v;} public String getDescription(){return description;} public void setDescription(String v){description=v;} public String getSkills(){return skills;} public void setSkills(String v){skills=v;} public LocalDate getPostedDate(){return postedDate;} public void setPostedDate(LocalDate v){postedDate=v;} public boolean isRemoteAvailable(){return remoteAvailable;} public void setRemoteAvailable(boolean v){remoteAvailable=v;}
}
