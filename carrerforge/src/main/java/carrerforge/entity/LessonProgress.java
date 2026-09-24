package carrerforge.entity;
import jakarta.persistence.*;
@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"userId","lesson_id"}))
public class LessonProgress {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private Long userId;
 @ManyToOne @JoinColumn(name="lesson_id") private Lesson lesson;
 private boolean completed;
 public Long getId(){return id;} public Long getUserId(){return userId;} public void setUserId(Long v){userId=v;} public Lesson getLesson(){return lesson;} public void setLesson(Lesson v){lesson=v;} public boolean isCompleted(){return completed;} public void setCompleted(boolean v){completed=v;}
}
