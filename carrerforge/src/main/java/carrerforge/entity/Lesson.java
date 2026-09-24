package carrerforge.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Lesson {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String title;
    private int lessonOrder;
    @Lob private String content;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "course_id") private Course course;
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; } public void setTitle(String title) { this.title = title; }
    public int getLessonOrder() { return lessonOrder; } public void setLessonOrder(int lessonOrder) { this.lessonOrder = lessonOrder; }
    public String getContent() { return content; } public void setContent(String content) { this.content = content; }
    @JsonIgnore public Course getCourse() { return course; } public void setCourse(Course course) { this.course = course; }
}
