package carrerforge.dto;

import carrerforge.entity.Lesson;

public record LessonResponse(Long id, String title, int lessonOrder, String content) {
    public static LessonResponse from(Lesson lesson) {
        return new LessonResponse(lesson.getId(), lesson.getTitle(), lesson.getLessonOrder(), lesson.getContent());
    }
}