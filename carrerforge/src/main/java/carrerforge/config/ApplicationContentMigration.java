package carrerforge.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class ApplicationContentMigration {
    private final JdbcTemplate jdbc;

    public ApplicationContentMigration(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @PostConstruct
    public void migrateOidContentToText() {
        migrateColumn("job_application", "cover_letter");
        migrateColumn("job", "description");
    }

    private void migrateColumn(String table, String column) {
        String dataType = jdbc.queryForObject(
                "select udt_name from information_schema.columns " +
                        "where table_schema = current_schema() and table_name = ? and column_name = ?",
                String.class, table, column);
        if ("oid".equalsIgnoreCase(dataType)) {
            jdbc.execute("alter table " + table + " alter column " + column + " type TEXT " +
                    "using case when " + column + " is null then null " +
                    "else convert_from(lo_get(" + column + "), 'UTF8') end");
        }
    }
}