package carrerforge.controller;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import carrerforge.dto.CourseResponse;
import carrerforge.service.CourseService;
@CrossOrigin(origins = "${app.cors.allowed-origin:http://localhost:5173}")
@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<CourseResponse> findAll() {
        return courseService.findAll().stream().map(CourseResponse::from).toList();
    }

    @GetMapping("/{id}")
    public CourseResponse findById(@PathVariable Long id) {
        return CourseResponse.from(courseService.findById(id));
    }

    @PostMapping
    public CourseResponse create(@RequestBody carrerforge.entity.Course course) {
        return CourseResponse.from(courseService.save(course));
    }
}