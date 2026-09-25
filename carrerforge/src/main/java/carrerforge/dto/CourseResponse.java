package carrerforge.dto;

import carrerforge.entity.Course;
import java.util.List;

public record CourseResponse(
        Long id,
        String name,
        String description,
        String category,
        String difficulty,
        String estimatedDuration,
        List<CourseLessonResponse> lessons) {

    public static CourseResponse from(Course course) {
        return new CourseResponse(
                course.getId(),
                course.getName(),
                course.getDescription(),
                course.getCategory(),
                course.getDifficulty(),
                course.getEstimatedDuration(),
                course.getLessons().stream().map(CourseLessonResponse::from).toList());
    }
}