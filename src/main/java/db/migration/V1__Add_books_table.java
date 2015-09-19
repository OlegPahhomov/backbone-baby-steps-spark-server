package db.migration;

import org.apache.commons.dbutils.QueryRunner;
import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.sql.Connection;

public class V1__Add_books_table implements JdbcMigration {
    public void migrate(Connection connection) throws Exception {
        new QueryRunner().update(connection,
                "CREATE TABLE book (\n" +
                        "  id      BIGSERIAL PRIMARY KEY                  NOT NULL,\n" +
                        "  title    TEXT                               ,\n" +
                        "  author    TEXT                               ,\n" +
                        "  releaseDate DATE                                   \n" +
                        ");"
        );
    }
}