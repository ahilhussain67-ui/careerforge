package carrerforge.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class LessonContentMigration {
    private final JdbcTemplate jdbc;

    public LessonContentMigration(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @PostConstruct
    public void migrateOidContentToText() {
        String dataType = jdbc.queryForObject(
            "select udt_name from information_schema.columns " +
                "where table_schema = current_schema() and table_name = 'lesson' and column_name = 'content'",
                String.class);

        if ("oid".equalsIgnoreCase(dataType)) {
            jdbc.execute("alter table lesson alter column content type TEXT " +
                    "using case when content is null then null " +
                    "else convert_from(lo_get(content), 'UTF8') end");
        }
    }
}