package carrerforge.dto;

import carrerforge.entity.Lesson;

public record CourseLessonResponse(Long id, String title, int lessonOrder) {
    public static CourseLessonResponse from(Lesson lesson) {
        return new CourseLessonResponse(lesson.getId(), lesson.getTitle(), lesson.getLessonOrder());
    }
}