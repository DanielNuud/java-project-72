package hexlet.code.controller;

import hexlet.code.dto.BasePage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UrlController {

    public static void build(Context ctx) {
        String message = ctx.consumeSessionAttribute("message");
        var page = new BasePage(message);
        ctx.render("urls/build.jte", Collections.singletonMap("page", page));
    }

    public static void create(Context ctx) throws SQLException {
        var input = ctx.formParamAsClass("url", String.class)
                .get()
                .toLowerCase()
                .trim();

        String normalizedURL;

        try {
            normalizedURL = normalizeUrl(input);
        } catch (Exception e) {
            ctx.sessionAttribute("message", "Wrong URL");
            ctx.redirect(NamedRoutes.rootPath());
            return;
        }

        if (UrlRepository.findByName(normalizedURL) != null) {
            ctx.sessionAttribute("message", "Url is already exists!");
            ctx.redirect(NamedRoutes.urlsPath());
        } else {
            var url = new Url(normalizedURL, new Timestamp(System.currentTimeMillis()));
            UrlRepository.save(url);
            ctx.sessionAttribute("message", "Url is successfully added!");
            ctx.redirect(NamedRoutes.urlsPath());
        }
    }

    public static void index(Context ctx) throws SQLException {
        var urls = UrlRepository.getEntities();
        final int itemsPerPage = 10;
        var pageCount = getPage(urls.size(), itemsPerPage);
        int pageNumber = ctx.queryParamAsClass("page", Integer.class).getOrDefault(pageCount);


        if (!urls.isEmpty() && pageNumber > pageCount) {
            throw new NotFoundResponse("Page is not found");
        } else if (!urls.isEmpty()) {
            urls = UrlRepository.getEntitiesPerPage(itemsPerPage, pageNumber);
        }
        var checks = UrlCheckRepository.getAllLastChecks();
        var page = new UrlsPage(urls, checks, pageNumber);

        String message = ctx.consumeSessionAttribute("message");
        page.setMessage(message);
        ctx.render("urls/index.jte", Collections.singletonMap("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.findById(id)
                .orElseThrow(() -> new NotFoundResponse("URL with id = " + id + " not found"));
        var checks = UrlCheckRepository.find(id);

        var page = new UrlPage(checks, url.getName(), url.getCreatedAt(), id);
        String message = ctx.consumeSessionAttribute("message");
        page.setMessage(message);

        ctx.render("urls/show.jte", Collections.singletonMap("page", page));
    }

    private static String normalizeUrl(String path) throws URISyntaxException, MalformedURLException {
        var newPath = new URI(path).toURL();
        return String.format("%s://%s", newPath.getProtocol(), newPath.getAuthority());
    }

    private static int getPage(int a, int b) {
        return (a % b == 0) ? (a / b) : (a / b + 1);
    }
}
