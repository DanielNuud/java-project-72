package hexlet.code;

import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class AppTest {

    private Javalin app;

    private static MockWebServer testServer;
    private static String website;
    private static String name;

    @BeforeAll
    public static void setServer() throws Exception {
        testServer = new MockWebServer();

        Path path = Paths.get("src/test/resources/test.html").toAbsolutePath().normalize();
        String content = Files.readString(path);
        testServer.enqueue(new MockResponse().setBody(content));

        testServer.start();

        website = testServer.url("/").toString();
        name = String.format("http://localhost:%s", testServer.getPort());
    }

    @BeforeEach
    public void setUp() throws Exception {
        app = App.getApp();
    }

    @AfterEach
    public void close() {
        app.stop();
    }

    @AfterAll
    public static void closeServer() throws IOException {
        testServer.shutdown();
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Page Analyzer");
        });
    }

    @Test
    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("List is empty");
        });
    }

    @Test
    public void testUrlsPageWithPageNumber() {
        JavalinTest.test(app, (server, client) -> {
            client.post("/urls", "url=" + website);
            assertThat(client.get("/urls?page=1").code()).isEqualTo(200);
            assertThat(client.get("/urls?page=1").body().string()).contains("/urls?page=1");
            assertThat(client.get("/urls?page=99").body().string()).contains("Page is not found");
        });
    }

    @Test
    public void testCreateUrl() {
        JavalinTest.test(app, (server, client) -> {
            Object requestBody = "url=" + website;
            var request = client.post("/urls", requestBody);
            assertThat(request.code()).isEqualTo(200);
            assertTrue(UrlRepository.existsByName(name));
        });
    }

    @Test
    public void testCreateUrlFail() {
        JavalinTest.test(app, (server, client) -> {
            Object requestBody = "url=invalid-url";
            client.post("/urls", requestBody);
            assertFalse(UrlRepository.existsByName("invalid-url"));
        });
    }

    @Test
    public void testRunCheck() {
        JavalinTest.test(app, (server, client) -> {
            client.post("/urls", "url=" + website);

            var urlId = UrlRepository.findByName(name).getId();
            client.post(String.format("/urls/%s/checks", urlId));

            var check = UrlCheckRepository.find(urlId).get(0);
            assertThat(check.getStatusCode()).isEqualTo(200);
            assertThat(check.getH1()).isEqualTo("Hello, world!");
            assertThat(check.getTitle()).isEqualTo("Test");
            assertThat(check.getDescription()).isEqualTo("Test Webpage");
            assertThat(check.getCreatedAt()).isBeforeOrEqualTo(new Date(System.currentTimeMillis()));
        });
    }

    @Test
    public void testRunCheckFail() {
        JavalinTest.test(app, (server, client) -> {
            var fakeWebsite = "http://localhost:7070";
            client.post("/urls", "url=" + fakeWebsite);

            var urlId = UrlRepository.findByName(fakeWebsite).getId();
            client.post(String.format("/urls/%s/checks", urlId));

            assertThat(UrlCheckRepository.find(urlId)).isEmpty();
        });
    }
}
