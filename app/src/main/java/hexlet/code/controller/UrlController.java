package hexlet.code.controller;

import hexlet.code.model.UrlCheck;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.URI;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Collections;

public class UrlController {
    private static final String URL_REGEX = "^(https?|ftp|http?):\\/\\/(www\\.)?[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})" +
            "(:\\d+)?(\\/\\S*)?$";

    public static boolean isValidUrl(String url) {
        Pattern pattern = Pattern.compile(URL_REGEX);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }

    public static Timestamp getSqlTime() {
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        return new Timestamp(t);
    }

    public static void index(Context ctx) throws SQLException {
        var urls = UrlRepository.getEntities();
        var page = new UrlsPage(urls);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flash-type"));
        ctx.render("urls/index.jte", Collections.singletonMap("page", page));
    }

    public static void create(Context ctx) {

        try {
            var name = ctx.formParamAsClass("url", String.class).get();

             if (!isValidUrl(name)) {
                throw new URISyntaxException("Invalid URL", "");
            }

            URI uri = new URI(name);
            String result = uri.getScheme() + "://" + uri.getHost();
            int port = uri.getPort();

            if (port != -1) {
                result += ":" + port;
            }

            var url = new Url(result, getSqlTime());
            UrlRepository.save(url);

            ctx.sessionAttribute("flash", "Url is added successfully!");
            ctx.sessionAttribute("flash-type", "alert alert-success");
            ctx.redirect(NamedRoutes.urlsPath());

        } catch (URISyntaxException e) {
            ctx.sessionAttribute("flash", "Invalid URL");
            ctx.sessionAttribute("flash-type", "alert alert-danger");
            ctx.redirect(NamedRoutes.rootPath());
        } catch (SQLException e) {
            ctx.sessionAttribute("flash", "URL is already exists!");
            ctx.sessionAttribute("flash-type", "alert alert-danger");
            ctx.redirect(NamedRoutes.urlsPath());
        }
    }

    public static void showUrl(Context ctx) throws SQLException {
        long id = ctx.pathParamAsClass("id", Long.class).getOrDefault(null);

        var url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Url with id = " + id + " not found"));

        var urlCheck = UrlCheckRepository.findByUrlId(id);

        var page = new UrlPage(url, urlCheck);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flash-type"));

        ctx.render("urls/show.jte", Collections.singletonMap("page", page));
    }

    public static void createCheck(Context ctx) {
        long urlId = ctx.pathParamAsClass("id", Long.class).getOrDefault(null);

        try {
            var url = UrlRepository.find(urlId);
            Url newUrl = url.orElseThrow(() -> new RuntimeException("URL not found"));

            HttpResponse<String> response = Unirest.get(newUrl.getName().trim())
                    .asString();
            int statusCode = response.getStatus();

            Connection connection = Jsoup.connect(newUrl.getName());
            Document document = connection.get();

            String title = document.title();

            Element h1Element = document.selectFirst("h1");
            String h1 = (h1Element != null) ? h1Element.text() : "";

            Element descriptionElement = document.selectFirst("meta[name=description]");
            String description = (descriptionElement != null) ? descriptionElement.attr("content") : "";

            var urlCheck = new UrlCheck(statusCode, title, h1, description, urlId, getSqlTime());
            UrlCheckRepository.save(urlCheck);
            ctx.sessionAttribute("flash", "URL is checked successfully!");
            ctx.sessionAttribute("flash-type", "alert alert-success");
            ctx.redirect(NamedRoutes.urlPath(urlId));
        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Error during URL check: " + e.getMessage());
            ctx.sessionAttribute("flash-type", "alert alert-danger");
            ctx.redirect(NamedRoutes.urlPath(urlId));
        }
    }
}
