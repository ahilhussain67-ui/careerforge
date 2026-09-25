package carrerforge.service;
import carrerforge.dto.LessonResponse;
import carrerforge.entity.*;
import carrerforge.repository.*;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class LessonService {
 private final LessonRepository lessons; private final LessonProgressRepository progress;
 public LessonService(LessonRepository lessons, LessonProgressRepository progress){this.lessons=lessons;this.progress=progress;}
 public List<LessonResponse> forCourse(Long courseId){return lessons.findResponsesByCourseId(courseId);}
 public Lesson get(Long id){return lessons.findById(id).orElseThrow(() -> new NoSuchElementException("Lesson not found"));}
 public List<Long> completed(Long userId, Long courseId){return progress.findByUserIdAndLessonCourseId(userId,courseId).stream().filter(item -> item != null && item.isCompleted()).map(p->p.getLesson().getId()).toList();}
 public boolean setCompleted(Long userId,Long lessonId,boolean completed){ LessonProgress item=progress.findByUserIdAndLessonId(userId,lessonId).orElseGet(LessonProgress::new); item.setUserId(userId); item.setLesson(get(lessonId)); item.setCompleted(completed); return progress.save(item).isCompleted(); }
}
