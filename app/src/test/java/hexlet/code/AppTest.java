package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.MethodOrderer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import io.javalin.Javalin;

import java.io.IOException;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AppTest {

    private static Javalin app;
    private static String baseUrl;
    private static final String NONEXISTENT_URL_ID = "111";
    public static Timestamp getSqlTime() {
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        return new Timestamp(t);
    }

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


    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class UrlTest {

        @Test
        @Order(1)
        void testMain() {
            HttpResponse<String> response = Unirest.get(baseUrl).asString();
            assertEquals(response.getStatus(), 200);
            assertTrue(response.getBody().contains("Page Analyzer"));
            assertTrue(response.getBody().contains("Example: https://www.example.com"));
        }

        @Test
        @Order(2)
        void testListUrls() throws SQLException {
            UrlRepository.save(new Url("https://www.example1.com", getSqlTime()));
            UrlRepository.save(new Url("https://www.example2.com", getSqlTime()));
            UrlRepository.save(new Url("https://www.example3.com", getSqlTime()));

            HttpResponse<String> response = Unirest.get(baseUrl + "/urls").asString();
            String body = response.getBody();

            assertEquals(response.getStatus(), 200);
            assertTrue(body.contains("https://www.example1.com"));
            assertTrue(body.contains("https://www.example2.com"));
            assertTrue(body.contains("https://www.example3.com"));

        }

        @Test
        @Order(3)
        void testShowUrl() throws SQLException {
            UrlRepository.save(new Url("https://www.example4.com", getSqlTime()));
            HttpResponse<String> response = Unirest.get(baseUrl + "/urls/4").asString();
            String body = response.getBody();

            assertEquals(response.getStatus(), 200);
            assertTrue(body.contains("https://www.example4.com"));
        }

        @Test
        @Order(4)
        void testAddUrl() throws SQLException {
            String input = "https://www.example.com";
            HttpResponse<String> responsePost = Unirest.post(baseUrl + "/urls")
                    .field("url", input).asString();

            String body = responsePost.getBody();

            assertEquals(responsePost.getStatus(), 200);
            assertTrue(body.contains(input));
            assertTrue(body.contains("Url is added successfully!"));

            List<Url> urls = UrlRepository.getEntities();
            Optional<Url> actualUrl = urls.stream().filter(url -> url.getName().equals(input)).findFirst();
            System.out.println(body);
            assertTrue(actualUrl.isPresent());
            assertEquals(input, actualUrl.get().getName());
        }

        @Test
        @Order(5)
        void testCreateWrongUrl() {
            String input = "invalid-url";
            HttpResponse<String> responsePost = Unirest.post(baseUrl + "/urls")
                    .field("url", input).asString();

            String body = responsePost.getBody();

            assertTrue(body.contains("Invalid URL"));
        }


        @Test
        @Order(6)
        void testCreateExistingUrl() {
            String input = "https://www.example.com";
            HttpResponse<String> responsePost = Unirest.post(baseUrl + "/urls")
                    .field("url", input).asString();

            String body = responsePost.getBody();

            assertTrue(body.contains("URL is already exists!"));
        }

        @Test
        @Order(7)
        void testNotFoundUrlId() {
            HttpResponse<String> response = Unirest.get(baseUrl + "/urls/" + NONEXISTENT_URL_ID).asString();
            String body = response.getBody();

            assertEquals(response.getStatus(), 404);
            assertTrue(body.contains("Url with id = 111 not found"));
        }
    }
}
