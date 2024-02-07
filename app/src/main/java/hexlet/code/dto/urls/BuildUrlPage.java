package hexlet.code.dto.urls;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BuildUrlPage {
    private String name;
    private Timestamp createdAt;
}
