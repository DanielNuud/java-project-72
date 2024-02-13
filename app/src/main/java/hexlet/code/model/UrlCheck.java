package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class UrlCheck {
    private Long id;
    private int statusCode;
    private String title;
    private String h1;
    private String description;
    private long url_id;
    private Timestamp createdAt;

    public UrlCheck(int statusCode, String title, String h1, String description, long url_id, Timestamp createdAt) {
        this.statusCode = statusCode;
        this.title = title;
        this.h1 = h1;
        this.description = description;
        this.url_id = url_id;
        this.createdAt = createdAt;
    }
}
