package db.migration;

import org.apache.commons.dbutils.QueryRunner;
import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.sql.Connection;

public class V2__Insert_books implements JdbcMigration {
    public void migrate(Connection connection) throws Exception {
        new QueryRunner().update(connection,
                "INSERT INTO book (title, author, releaseDate) VALUES " +
                        "('Olegaman Adventures', 'Oleg', '2020-10-20')," +
                        "('Clean code', 'C Martin ', '2001-01-02')," +
                        "('Intellij is yber alles', 'IntelliJ Bob', '2015-05-25');"
        );
    }
}
