package hexlet.code.repository;

import hexlet.code.model.UrlCheck;
import hexlet.code.model.Url;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UrlCheckRepository extends BaseRepository {
    public static void save(UrlCheck urlCheck) throws SQLException {
        String sql = "INSERT INTO url_checks (status_code, title, h1, description, url_id, created_at) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, urlCheck.getStatusCode());
            preparedStatement.setString(2, urlCheck.getTitle());
            preparedStatement.setString(3, urlCheck.getH1());
            preparedStatement.setString(4, urlCheck.getDescription());
            preparedStatement.setLong(5, urlCheck.getUrl_id());
            preparedStatement.setTimestamp(6, urlCheck.getCreatedAt());
            preparedStatement.execute();
            var generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                urlCheck.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }

    public static List<UrlCheck> findByUrlId(Long urlId) throws SQLException {
        var sql = "SELECT * FROM url_checks WHERE url_id = ?";
        List<UrlCheck> urlChecks = new ArrayList<>();

        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, urlId);
            var resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var statusCode = resultSet.getInt("status_code");
                var title = resultSet.getString("title");
                var h1 = resultSet.getString("h1");
                var description = resultSet.getString("description");
                var url_id = resultSet.getLong("url_id");
                var createdAt = resultSet.getTimestamp("created_at");

                var urlCheck = new UrlCheck(statusCode, title, h1, description, url_id, createdAt);
                urlCheck.setId(id);
                urlChecks.add(urlCheck);
            }
        }

        return urlChecks;
    }

    public static List<UrlCheck> findByUrl(Url url) throws SQLException {
        return findByUrlId(url.getId());
    }


}
