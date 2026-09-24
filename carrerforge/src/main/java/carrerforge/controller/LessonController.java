package carrerforge.controller;
import carrerforge.entity.Lesson;
import carrerforge.service.LessonService;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@CrossOrigin(origins="${app.cors.allowed-origin:http://localhost:5173}") @RestController @RequestMapping("/api")
public class LessonController {
 private final LessonService service; public LessonController(LessonService service){this.service=service;}
 @GetMapping("/courses/{courseId}/lessons") public List<Lesson> lessons(@PathVariable Long courseId){return service.forCourse(courseId);}
 @GetMapping("/courses/{courseId}/progress") public Map<String,Object> progress(@PathVariable Long courseId,@RequestParam Long userId){List<Long> completed=service.completed(userId,courseId);return Map.of("completedLessonIds",completed,"completedCount",completed.size());}
 @PutMapping("/lessons/{lessonId}/progress") public Map<String,Boolean> update(@PathVariable Long lessonId,@RequestBody Map<String,Object> body){Long userId=Long.valueOf(body.get("userId").toString());boolean completed=Boolean.parseBoolean(body.getOrDefault("completed",true).toString());return Map.of("completed",service.setCompleted(userId,lessonId,completed));}
}
