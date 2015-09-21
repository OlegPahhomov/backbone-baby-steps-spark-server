import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class Book {

    String title;
    String author;
    LocalDate releaseDate;

    public static Book makeBook(JsonObject userJson) {
        String title1 = userJson.get("title")!= null ? userJson.get("title").getAsString() : "";
        String author1 = userJson.get("author")!= null ? userJson.get("author").getAsString() : "";
        LocalDate releaseDate1 = userJson.get("releasedate") != null ? makeDate(userJson) : null;

        return new Book(title1, author1, releaseDate1);
    }

    private static LocalDate makeDate(JsonObject userJson) {
        return LocalDate.parse(userJson.get("releasedate").getAsString());
    }

    public Book(String title, String author, LocalDate releaseDate) {
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
    }
}
