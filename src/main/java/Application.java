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

    static ResponseTransformer toJson = new Gson()::toJson;
    static QueryRunner queryRunner = new QueryRunner();
    static JsonParser jsonParser = new JsonParser();

    static {
    }

    public static void main(String[] args) throws SQLException {
        port(8081);

        start();

        after((request, response) -> {// For security reasons do not forget to change "*" to url
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Credentials", "true");
            response.type("application/json");
        });

        after("/picture/:id", (request, response) -> response.type("image/jpg"));
        after("/picture/small/:id", (request, response) -> response.type("image/jpg"));
    }

    private static void start() throws SQLException {
        get("/api/books", (request, response) -> {
            try (Connection connection = AppDataSource.getConnection()) {
                return queryRunner.query(connection, "SELECT * FROM BOOK", new MapListHandler());
            }
        }, toJson);
        get("/api/books/:id", (request, response) -> {
            Integer id = Integer.valueOf(request.params(":id"));
            try (Connection connection = AppDataSource.getConnection()) {
                return queryRunner.query(connection, "SELECT * FROM BOOK where id=?", new MapListHandler(), id);
            }
        }, toJson);
        post("/api/books", (request, response) -> {
            Book book = new Book(toJson(request));
            try (Connection connection = AppDataSource.getTransactConnection()) {
                List<Map<String, Object>> responseObject = queryRunner.insert(connection,
                        "INSERT INTO book (title, author, releaseDate) VALUES (?, ?, '" + book.releaseDate + "');",
                        new MapListHandler(), book.title, book.author);

                connection.commit();
                return responseObject;
            }
        }, toJson);

        put("/api/books/:id", (request, response) -> {
            Integer id = Integer.valueOf(request.params(":id"));
            Book book = new Book(toJson(request));
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

        delete("/api/books/:id", (request, response) -> {
            Integer id = Integer.valueOf(request.params(":id"));
            try (Connection connection = AppDataSource.getTransactConnection()) {
                queryRunner.update(connection, "DELETE FROM BOOK where id=?", id);
                connection.commit();
                return "success";
            }
        }, toJson);
    }

    private static JsonObject toJson(Request request) {
        return jsonParser.parse(request.body()).getAsJsonObject();
    }
}
