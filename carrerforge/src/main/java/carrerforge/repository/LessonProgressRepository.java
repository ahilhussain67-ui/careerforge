package carrerforge.repository;
import carrerforge.entity.LessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface LessonProgressRepository extends JpaRepository<LessonProgress,Long>{ Optional<LessonProgress> findByUserIdAndLessonId(Long userId,Long lessonId); List<LessonProgress> findByUserIdAndLessonCourseId(Long userId,Long courseId); }
