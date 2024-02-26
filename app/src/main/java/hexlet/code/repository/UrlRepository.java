package hexlet.code.repository;

import hexlet.code.model.Url;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UrlRepository extends BaseRepository {

    public static void save(Url url) throws SQLException {
        var sql = "INSERT INTO urls (name, created_at) VALUES (?, ?)";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, url.getName());
            stmt.setTimestamp(2, url.getCreatedAt());
            stmt.executeUpdate();

            var generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                url.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("Не сформирован ID");
            }
        }
    }

    public static Optional<Url> findById(Long id) throws SQLException {
        var sql = "SELECT * FROM urls WHERE id = ?";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            var resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                var url = getUrl(resultSet);
                return Optional.of(url);
            } else {
                return Optional.empty();
            }
        }
    }

    public static Optional<Url> findByName(String name) throws SQLException {
        var sql = "SELECT * FROM urls WHERE name = ?";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            var resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                var url = getUrl(resultSet);
                return Optional.of(url);
            } else {
                return Optional.empty();
            }
        }
    }

    public static boolean existsByName(String name) throws SQLException {
        var sql = "SELECT * FROM urls WHERE name = ?";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            var resultSet = stmt.executeQuery();
            return resultSet.next();
        }
    }

    public static List<Url> getEntities() throws SQLException {
        var sql = "SELECT * FROM urls ORDER BY id";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {

            var resultSet = stmt.executeQuery();
            var results = new ArrayList<Url>();

            while (resultSet.next()) {
                var url = getUrl(resultSet);
                results.add(url);
            }
            return results;
        }
    }

    public static List<Url> getEntitiesPerPage(int itemsPerPage, int pageNumber) throws SQLException {
        var sql = "SELECT * FROM urls ORDER BY id LIMIT ? OFFSET ?";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {

            var offset = itemsPerPage * (pageNumber - 1);

            stmt.setInt(1, itemsPerPage);
            stmt.setInt(2, offset);
            var resultSet = stmt.executeQuery();
            List<Url> results = new ArrayList<>();

            while (resultSet.next()) {
                var url = getUrl(resultSet);
                results.add(url);
            }
            return results;
        }
    }

    private static Url getUrl(ResultSet resultSet) throws SQLException {
        var id = resultSet.getLong("id");
        var name = resultSet.getString("name");
        var createdAt = resultSet.getTimestamp("created_at");
        return new Url(id, name, createdAt);
    }
}
