package carrerforge.controller;
import carrerforge.dto.JobResponse;
import carrerforge.dto.JobSummaryResponse;
import carrerforge.service.JobService;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@CrossOrigin(origins="${app.cors.allowed-origin:http://localhost:5173}") @RestController @RequestMapping("/api/jobs")
public class JobController {
 private final JobService service; public JobController(JobService service){this.service=service;}
 @GetMapping public List<JobSummaryResponse> list(@RequestParam(required=false) String keyword,@RequestParam(required=false) String location,@RequestParam(required=false) String type,@RequestParam(required=false) String level,@RequestParam(required=false) Boolean remote){return service.search(keyword,location,type,level,remote).stream().map(JobSummaryResponse::from).toList();}
 @GetMapping("/{id}") public JobResponse get(@PathVariable Long id){return JobResponse.from(service.get(id));}
 @PostMapping public JobResponse create(@RequestBody carrerforge.entity.Job job){return JobResponse.from(service.save(job));}
}
