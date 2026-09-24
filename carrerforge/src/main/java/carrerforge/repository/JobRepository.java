package carrerforge.repository;
import carrerforge.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface JobRepository extends JpaRepository<Job,Long>{ Optional<Job> findByTitleAndCompany(String title,String company); }
