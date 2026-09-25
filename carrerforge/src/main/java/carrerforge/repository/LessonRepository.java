package carrerforge.repository;
import carrerforge.entity.Lesson;
import carrerforge.dto.LessonResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
public interface LessonRepository extends JpaRepository<Lesson,Long>{
	@Query("select new carrerforge.dto.LessonResponse(l.id, l.title, l.lessonOrder, l.content) " +
		   "from Lesson l where l.course.id = :courseId order by l.lessonOrder")
	List<LessonResponse> findResponsesByCourseId(@Param("courseId") Long courseId);
}
