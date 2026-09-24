package carrerforge.repository;
import carrerforge.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface JobApplicationRepository extends JpaRepository<JobApplication,Long>{boolean existsByJobIdAndUserId(Long jobId,Long userId);List<JobApplication> findByUserIdOrderByAppliedAtDesc(Long userId);List<JobApplication> findByJobIdOrderByAppliedAtDesc(Long jobId);}
