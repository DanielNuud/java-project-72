package hexlet.code.dto.urls;

import hexlet.code.dto.BasePage;
import hexlet.code.model.UrlCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UrlPage extends BasePage {
    private List<UrlCheck> checks;
    private String name;
    private Timestamp createdAt;
    private Long id;
}
