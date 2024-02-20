package hexlet.code;

import hexlet.code.repository.UrlRepository;
import io.javalin.Javalin;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import okhttp3.mockwebserver.MockWebServer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppTest {

    private static Javalin app;
    private static String baseUrl;
    private MockWebServer mockWebServer;

    @BeforeAll
    public static void setUp() throws SQLException, IOException {
        app = App.getApp();
        app.start(0);
        baseUrl = "http://localhost:" + app.port();
    }

    @AfterAll
    public static void tearDown() {
        app.stop();
    }

    @BeforeEach
    public void clearDatabase() throws SQLException {
        UrlRepository.deleteAllEntities();
    }

    @Test
    public void testAddUrlAndVerifyInDatabaseAndPage() {
        String urlToAdd = "https://www.example.com";
        HttpResponse<String> addUrlResponse = Unirest.post(baseUrl + "/urls")
                .field("url", urlToAdd)
                .asString();

        assertEquals(200, addUrlResponse.getStatus());

        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;");
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM urls WHERE name = ?")) {
            statement.setString(1, urlToAdd);
            ResultSet resultSet = statement.executeQuery();
            assertTrue(resultSet.next());
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpResponse<String> getPageResponse = Unirest.get(baseUrl + "/urls").asString();
        assertTrue(getPageResponse.getBody().contains(urlToAdd));
    }

    @Test
    public void testAddInvalidUrl() {
        String invalidUrl = "invalid-url";

        HttpResponse<String> addUrlResponse = Unirest.post(baseUrl + "/urls")
                .field("url", invalidUrl)
                .asString();

        assertEquals(200, addUrlResponse.getStatus());

        String body = addUrlResponse.getBody();
        assertTrue(body.contains("Invalid URL"));
        Assertions.assertFalse(body.contains("Url is added successfully!"));
    }

    @Test
    public void testAddSameUrl() {
        String urlToAdd = "https://www.example.com";

        HttpResponse<String> firstAddUrlResponse = Unirest.post(baseUrl + "/urls")
                .field("url", urlToAdd)
                .asString();

        assertEquals(200, firstAddUrlResponse.getStatus());

        HttpResponse<String> secondAddUrlResponse = Unirest.post(baseUrl + "/urls")
                .field("url", urlToAdd)
                .asString();

        assertEquals(200, secondAddUrlResponse.getStatus());

        String secondResponseBody = secondAddUrlResponse.getBody();
        assertTrue(secondResponseBody.contains("URL is already exists!"));
        assertFalse(secondResponseBody.contains("Url is added successfully!"));
    }

    @Test
    public void testAddUrlAndVerifyInPage() {
        String urlToAdd = "https://www.example.com";

        HttpResponse<String> addUrlResponse = Unirest.post(baseUrl + "/urls")
                .field("url", urlToAdd)
                .asString();
        assertTrue(addUrlResponse.getBody().contains("Url is added successfully!"));

        assertEquals(200, addUrlResponse.getStatus());

        HttpResponse<String> getPageResponse = Unirest.get(baseUrl + "/urls").asString();
        assertTrue(getPageResponse.getBody().contains(urlToAdd));

    }

}
