package carrerforge.repository;
import carrerforge.entity.JobApplication;
import carrerforge.dto.ApplicationResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.*;
public interface JobApplicationRepository extends JpaRepository<JobApplication,Long>{
 boolean existsByJobIdAndUserId(Long jobId,Long userId);
 @Query("select new carrerforge.dto.ApplicationResponse(a.id,j.id,j.title,j.company,j.location,a.fullName,a.email,a.phone,a.resumeFileName,a.appliedAt,a.status) " +
	 "from JobApplication a join a.job j where a.id = :id")
 Optional<ApplicationResponse> findResponseById(@Param("id") Long id);
 @Query("select new carrerforge.dto.ApplicationResponse(a.id,j.id,j.title,j.company,j.location,a.fullName,a.email,a.phone,a.resumeFileName,a.appliedAt,a.status) " +
	 "from JobApplication a join a.job j where a.user.id = :userId order by a.appliedAt desc")
 List<ApplicationResponse> findResponsesByUserId(@Param("userId") Long userId);
 @Query("select new carrerforge.dto.ApplicationResponse(a.id,j.id,j.title,j.company,j.location,a.fullName,a.email,a.phone,a.resumeFileName,a.appliedAt,a.status) " +
	 "from JobApplication a join a.job j where a.job.id = :jobId order by a.appliedAt desc")
 List<ApplicationResponse> findResponsesByJobId(@Param("jobId") Long jobId);
 @Query("select new carrerforge.dto.ApplicationResponse(a.id,j.id,j.title,j.company,j.location,a.fullName,a.email,a.phone,a.resumeFileName,a.appliedAt,a.status) " +
	 "from JobApplication a join a.job j order by a.appliedAt desc")
 List<ApplicationResponse> findAllResponses();
}
