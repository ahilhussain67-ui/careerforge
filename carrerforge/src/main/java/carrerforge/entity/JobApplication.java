package carrerforge.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"job_id","user_id"}))
public class JobApplication {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) @JoinColumn(name="job_id") private Job job;
 @ManyToOne(optional=false) @JoinColumn(name="user_id") private User user;
 private String fullName,email,phone,resumeFileName;
 @Column(columnDefinition="TEXT") private String coverLetter;
 private LocalDateTime appliedAt;
 @Enumerated(EnumType.STRING) private ApplicationStatus status;
 public Long getId(){return id;} public Job getJob(){return job;} public void setJob(Job v){job=v;} public User getUser(){return user;} public void setUser(User v){user=v;} public String getFullName(){return fullName;} public void setFullName(String v){fullName=v;} public String getEmail(){return email;} public void setEmail(String v){email=v;} public String getPhone(){return phone;} public void setPhone(String v){phone=v;} public String getResumeFileName(){return resumeFileName;} public void setResumeFileName(String v){resumeFileName=v;} public String getCoverLetter(){return coverLetter;} public void setCoverLetter(String v){coverLetter=v;} public LocalDateTime getAppliedAt(){return appliedAt;} public void setAppliedAt(LocalDateTime v){appliedAt=v;} public ApplicationStatus getStatus(){return status;} public void setStatus(ApplicationStatus v){status=v;}
}
