package carrerforge.service;

import java.util.List;

import org.springframework.stereotype.Service;

import carrerforge.entity.Course;
import carrerforge.repository.CourseRepository;

@Service
public class CourseService {

    private final CourseRepository repository;

    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    public List<Course> findAll() {
        return repository.findAll();
    }

    public Course findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public Course save(Course course) {
        return repository.save(course);
    }
}