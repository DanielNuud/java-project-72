package hexlet.code.controller;

import hexlet.code.App;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;

import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.MockResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;

import java.io.IOException;
import java.sql.Timestamp;

public class UrlControllerTest {

    private static Javalin app;
    private static MockWebServer mockServer;

    public static Timestamp getSqlTime() {
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        return new Timestamp(t);
    }

    @BeforeEach
    public void setUp() throws Exception {
        app = App.getApp();
        mockServer = new MockWebServer();
        mockServer.start();
        String mockServerUrl = mockServer.url("/").toString();
    }

    public static void tearDown() throws IOException {
        mockServer.shutdown();
    }

    @Test
    public void testMainPage() throws Exception{
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Page Analyzer");
        });
    }

    @Test
    public void testUrlsPage() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testUrlPage() throws Exception {
        var url = new Url("https://www.example.com", getSqlTime());
        UrlRepository.save(url);

        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/" + url.getId());
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testUrlNotFound() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/999999");
            assertThat(response.code()).isEqualTo(404);
        });
    }

    @Test
    public void testIsValidUrl() {
        assertTrue(UrlController.isValidUrl("https://www.example.com"));
        assertFalse(UrlController.isValidUrl("invalid_url"));
    }

    @Test
    public void testUrlNotFound2() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            var url = new Url("https://www.example.com", getSqlTime());
            UrlRepository.save(url);
            var response = client.get("/urls/2");
            assertThat(response.code()).isEqualTo(404);
        });
    }

    @Test
    public void testCreateInvalidUrl() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.post("/urls", "{\"url\":\"invalid-url\"}");
            assertThat(response.code()).isEqualTo(400);
        });
    }
}