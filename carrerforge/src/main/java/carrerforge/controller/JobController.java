package carrerforge.controller;
import carrerforge.entity.Job;
import carrerforge.service.JobService;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@CrossOrigin(origins="${app.cors.allowed-origin:http://localhost:5173}") @RestController @RequestMapping("/api/jobs")
public class JobController {
 private final JobService service; public JobController(JobService service){this.service=service;}
 @GetMapping public List<Job> list(@RequestParam(required=false) String keyword,@RequestParam(required=false) String location,@RequestParam(required=false) String type,@RequestParam(required=false) String level,@RequestParam(required=false) Boolean remote){return service.search(keyword,location,type,level,remote);}
 @GetMapping("/{id}") public Job get(@PathVariable Long id){return service.get(id);}
 @PostMapping public Job create(@RequestBody Job job){return service.save(job);}
}
