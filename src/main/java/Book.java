import com.google.gson.JsonObject;

import java.time.LocalDate;

public class Book {

    String title;
    String author;
    LocalDate releaseDate;

    public Book(JsonObject userJson) {
        this(userJson.get("title").getAsString(),
                userJson.get("author").getAsString(),
                userJson.get("releaseDate").getAsString());
    }

    public Book(String title, String author, String fullTimeString) {
        this(title, author, LocalDate.parse(fullTimeString.substring(0, 10)));
    }

    public Book(String title, String author, LocalDate releaseDate) {
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
    }
}
