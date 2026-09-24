package carrerforge.config;

import carrerforge.entity.Course;
import carrerforge.entity.Job;
import carrerforge.entity.Lesson;
import carrerforge.repository.CourseRepository;
import carrerforge.repository.JobRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
public class SeedData implements CommandLineRunner {

    private final CourseRepository courses;
    private final JobRepository jobs;

    public SeedData(CourseRepository courses, JobRepository jobs) {
        this.courses = courses;
        this.jobs = jobs;
    }

    @Override
    @Transactional
    public void run(String... args) {

        // =========================
        // COURSES
        // =========================

        course(
                "Java",
                "Build a strong foundation for modern Java development.",
                "Backend Development",
                "Beginner",
                "8 hours",
                new String[]{
                        "Java Basics",
                        "OOP Concepts",
                        "Collections",
                        "Exception Handling"
                }
        );

        course(
                "Spring Boot",
                "Create production-ready REST APIs with Spring Boot.",
                "Backend Development",
                "Intermediate",
                "10 hours",
                new String[]{
                        "Spring Boot Basics",
                        "REST API",
                        "Spring Data JPA",
                        "Validation"
                }
        );

        course(
                "React",
                "Build responsive user interfaces with React.",
                "Frontend Development",
                "Beginner",
                "9 hours",
                new String[]{
                        "React Basics",
                        "Components",
                        "Props and useState",
                        "useEffect",
                        "API Integration"
                }
        );

        course(
                "SQL",
                "Query and model relational data with confidence.",
                "Databases",
                "Beginner",
                "7 hours",
                new String[]{
                        "SQL Basics",
                        "SELECT Queries",
                        "Primary Keys",
                        "Foreign Keys",
                        "Joins",
                        "GROUP BY"
                }
        );

        course(
                "Git & GitHub",
                "Use version control in real team workflows.",
                "Developer Tools",
                "Beginner",
                "5 hours",
                new String[]{
                        "Git Basics",
                        "git clone",
                        "git add",
                        "git commit",
                        "git push",
                        "GitHub"
                }
        );

        course(
                "HTML & CSS",
                "Craft accessible, responsive web foundations.",
                "Frontend Development",
                "Beginner",
                "6 hours",
                new String[]{
                        "HTML Basics",
                        "HTML Forms",
                        "CSS Basics",
                        "Flexbox",
                        "Grid",
                        "Responsive Design"
                }
        );

        // =========================
        // JOBS
        // =========================

        job(
                "Frontend Developer",
                "Nova Labs",
                "Bengaluru, India",
                "Full-time",
                "Entry level",
                "₹6–9 LPA",
                true,
                "React, JavaScript, CSS"
        );

        job(
                "Java Backend Engineer",
                "Orbit Systems",
                "Hyderabad, India",
                "Full-time",
                "Mid level",
                "₹10–16 LPA",
                true,
                "Java, Spring Boot, SQL"
        );

        job(
                "Software Engineer Intern",
                "Craftworks",
                "Pune, India",
                "Internship",
                "Entry level",
                "₹25k/month",
                false,
                "JavaScript, Git, HTML"
        );

        job(
                "Data Analyst",
                "Northstar Analytics",
                "Remote, India",
                "Full-time",
                "Entry level",
                "₹7–11 LPA",
                true,
                "SQL, Excel, Python"
        );
    }

    // =========================
    // COURSE SEEDING
    // =========================

    private void course(
            String name,
            String desc,
            String category,
            String difficulty,
            String duration,
            String[] titles
    ) {

        Course course = courses
                .findByName(name)
                .orElseGet(Course::new);

        course.setName(name);
        course.setDescription(desc);
        course.setCategory(category);
        course.setDifficulty(difficulty);
        course.setEstimatedDuration(duration);

        if (course.getLessons().isEmpty()) {

            for (int i = 0; i < titles.length; i++) {

                Lesson lesson = new Lesson();

                lesson.setCourse(course);
                lesson.setLessonOrder(i + 1);
                lesson.setTitle(titles[i]);

                lesson.setContent(
                        "<h3>What you will learn</h3>" +
                        "<p>In this lesson, you will learn the practical foundations of "
                        + titles[i] +
                        ". Focus on the concepts, try the examples, and connect them to a real project.</p>" +

                        "<h3>Key takeaways</h3>" +

                        "<ul>" +
                        "<li>Understand the core idea and vocabulary.</li>" +
                        "<li>Practice with a small, focused example.</li>" +
                        "<li>Use the concept in your next project.</li>" +
                        "</ul>"
                );

                course.getLessons().add(lesson);
            }
        }

        courses.save(course);
    }

    // =========================
    // JOB SEEDING
    // =========================

    private void job(
            String title,
            String company,
            String location,
            String type,
            String level,
            String salary,
            boolean remote,
            String skills
    ) {

        Job job = jobs.findByTitleAndCompany(title, company).orElseGet(Job::new);

        job.setTitle(title);
        job.setCompany(company);
        job.setLocation(location);
        job.setJobType(type);
        job.setExperienceLevel(level);
        job.setSalaryRange(salary);
        job.setRemoteAvailable(remote);
        job.setSkills(skills);

        job.setPostedDate(
                LocalDate.now().minusDays(3)
        );

        job.setDescription(
                "Join " +
                company +
                " and work with a collaborative team on products that make a measurable impact."
        );

        job.setApplicationUrl(null);

        jobs.save(job);
    }
}
