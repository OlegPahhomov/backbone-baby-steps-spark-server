import config.AppDataSource;
import org.flywaydb.core.Flyway;

public class Migration {

        public static void main(String[] args) throws Exception {
            Flyway flyway = new Flyway();
            String url = "postgres://@ec2-54-197-253-142.compute-1.amazonaws.com:5432/dbcanrndvorfiu";
            //flyway.setDataSource("jdbc:postgresql://localhost:5432", "postgres", "postgres");
            //flyway.setDataSource(AppDataSource.getDatasource());
            String user = "tiksmuyqdctiyq";
            String password = "okiquQB9YLY-XaoMoKEVuaVNSC";
            flyway.setDataSource(AppDataSource.getDatasource());
            flyway.clean();
            flyway.migrate();
        }
}
