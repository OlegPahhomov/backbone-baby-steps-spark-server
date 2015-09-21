import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import config.AppDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import spark.Request;
import spark.ResponseTransformer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class Application {

    public static final String API_BOOKS = "/api/books";
    public static final String API_BOOKS_ID = "/api/books/:id";
    static ResponseTransformer toJson = new Gson()::toJson;
    static QueryRunner queryRunner = new QueryRunner();
    static JsonParser jsonParser = new JsonParser();

    static {
    }

    public static void main(String[] args) throws SQLException {
        port(getHerokuAssignedPort());
        //port(8081);

        start();

        after((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
            response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.type("application/json");
        });

    }

    private static void start() throws SQLException {
        get(API_BOOKS, (request, response) -> {
            try (Connection connection = AppDataSource.getConnection()) {
                return queryRunner.query(connection, "SELECT * FROM BOOK", new MapListHandler());
            }
        }, toJson);
        get(API_BOOKS_ID, (request, response) -> {
            Integer id = Integer.valueOf(request.params(":id"));
            try (Connection connection = AppDataSource.getConnection()) {
                return queryRunner.query(connection, "SELECT * FROM BOOK where id=?", new MapListHandler(), id);
            }
        }, toJson);
        post(API_BOOKS, (request, response) -> {
            Book book = Book.makeBook(toJson(request));
            try (Connection connection = AppDataSource.getTransactConnection()) {
                List<Map<String, Object>> responseObject = queryRunner.insert(connection,
                        "INSERT INTO book (title, author, releaseDate) VALUES (?, ?, '" + book.releaseDate + "');",
                        new MapListHandler(), book.title, book.author);

                connection.commit();
                return responseObject;
            }
        }, toJson);

        options("/*", (request, response) -> "*");

        put(API_BOOKS_ID, (request, response) -> {
            Integer id = Integer.valueOf(request.params(":id"));
            Book book = Book.makeBook(toJson(request));
            try (Connection connection = AppDataSource.getTransactConnection()) {
                List<Map<String, Object>> responseObject = queryRunner.insert(connection,
                        "UPDATE book SET" +
                                " title = ?," +
                                " author = ?, " +
                                " releaseDate = '" + book.releaseDate + "' " +
                                " WHERE id = ?;",
                        new MapListHandler(), book.title, book.author, id);

                connection.commit();
                return responseObject;
            }
        }, toJson);

        delete(API_BOOKS_ID, (request, response) -> {
            Integer id = Integer.valueOf(request.params(":id"));
            try (Connection connection = AppDataSource.getTransactConnection()) {
                queryRunner.update(connection, "DELETE FROM BOOK where id=?", id);
                connection.commit();
                return "";
            }
        }, toJson);
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }

    private static JsonObject toJson(Request request) {
        return jsonParser.parse(request.body()).getAsJsonObject();
    }
}
