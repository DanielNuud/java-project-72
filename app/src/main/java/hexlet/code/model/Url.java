package hexlet.code.model;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class Url {
    private Long id;
    private String name;
    private Timestamp createdAt;

    public Url(String name, Timestamp createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }
}
