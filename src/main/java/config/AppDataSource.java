package config;

import org.postgresql.ds.PGPoolingDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class AppDataSource {
    private static DataSource dataSource = initLocalDbSource();
//    private static DataSource dataSource = initHerokuDbsource();
    public static final String HOME_PW = "12345";
    public static final String OFFICE_PW = "postgres";


    private static DataSource initHerokuDbsource() {
        PGPoolingDataSource dataSource = new PGPoolingDataSource();
        dataSource.setUser("tiksmuyqdctiyq");
        dataSource.setPassword("okiquQB9YLY-XaoMoKEVuaVNSC");
        dataSource.setServerName("ec2-54-197-253-142.compute-1.amazonaws.com");
        dataSource.setDatabaseName("dbcanrndvorfiu");
        return dataSource;
    }

    private static DataSource initLocalDbSource() {
        PGPoolingDataSource dataSource = new PGPoolingDataSource();
        dataSource.setDatabaseName("library");
        dataSource.setUser("postgres");
//        dataSource.setPassword(HOME_PW);
        dataSource.setPassword(OFFICE_PW);
        dataSource.setServerName("localhost");
        return dataSource;
    }

    public static DataSource getDatasource() {
        return dataSource;
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static Connection getTransactConnection() throws SQLException {
        Connection connection = getConnection();
        connection.setAutoCommit(false);
        return connection;
    }

}