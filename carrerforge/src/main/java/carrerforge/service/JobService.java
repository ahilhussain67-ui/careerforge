package carrerforge.service;
import carrerforge.entity.Job;
import carrerforge.repository.JobRepository;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class JobService {
 private final JobRepository repository; public JobService(JobRepository repository){this.repository=repository;}
 public List<Job> search(String keyword,String location,String type,String level,Boolean remote){return repository.findAll().stream().filter(j->keyword==null||keyword.isBlank()||(j.getTitle()+j.getCompany()+j.getSkills()).toLowerCase().contains(keyword.toLowerCase())).filter(j->location==null||location.isBlank()||j.getLocation().toLowerCase().contains(location.toLowerCase())).filter(j->type==null||type.isBlank()||j.getJobType().equalsIgnoreCase(type)).filter(j->level==null||level.isBlank()||j.getExperienceLevel().equalsIgnoreCase(level)).filter(j->remote==null||!remote||j.isRemoteAvailable()).toList();}
 public Job get(Long id){return repository.findById(id).orElseThrow(()->new NoSuchElementException("Job not found"));} public Job save(Job job){return repository.save(job);}
}
