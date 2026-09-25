package carrerforge.repository;

import carrerforge.entity.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Override
    @EntityGraph(attributePaths = "lessons")
    java.util.List<Course> findAll();

    Optional<Course> findByName(String name);
}
