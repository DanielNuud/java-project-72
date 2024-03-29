package hexlet.code.controller;


import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import kong.unirest.Unirest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.sql.SQLException;
import java.sql.Timestamp;

public class UrlChecksController {
    public static void check(Context ctx) throws SQLException {
        var urlId = ctx.pathParamAsClass("id", Long.class).get();

        var url = UrlRepository.findById(urlId)
                    .orElseThrow(() -> new NotFoundResponse("URL with ID " + urlId + " is not found"));

        try {
            var response = Unirest.get(url.getName()).asString();
            Document responseBody = Jsoup.parse(response.getBody());

            int statusCode = response.getStatus();

            String h1 = responseBody.selectFirst("h1") != null
                    ? responseBody.selectFirst("h1").text() : "";

            String title = responseBody.title();

            String description = !responseBody.select("meta[name=description]").isEmpty()
                    ? responseBody.select("meta[name=description]").get(0).attr("content") : "";

            var createdAt = new Timestamp(System.currentTimeMillis());

            var urlCheck = new UrlCheck(statusCode, h1, title, description, urlId, createdAt);
            UrlCheckRepository.save(urlCheck);
            ctx.sessionAttribute("message", "URL is checked successfully!");
            ctx.redirect(NamedRoutes.urlPath(urlId));
        } catch (RuntimeException e) {
            ctx.sessionAttribute("message", "Check is failed");
            ctx.redirect(NamedRoutes.urlPath(urlId));
        }
    }
}
