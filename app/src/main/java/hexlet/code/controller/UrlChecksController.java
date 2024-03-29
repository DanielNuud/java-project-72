package hexlet.code.controller;

import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.sql.SQLException;
import java.sql.Timestamp;

public class UrlChecksController {
    public static void check(Context ctx) throws SQLException {
        var urlId = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.findById(urlId)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + urlId + " not found"));

        try {
            HttpResponse<String> response = Unirest.get(url.getName()).asString();
            Document doc = Jsoup.parse(response.getBody());

            var statusCode = response.getStatus();
            var title = doc.title();

            var h1Temp = doc.selectFirst("h1");
            var h1 = h1Temp == null ? "" : h1Temp.text();

            var descriptionTemp = doc.selectFirst("meta[name=description]");
            var description = descriptionTemp == null ? "" : descriptionTemp.attr("content");

            var createdAt = new Timestamp(System.currentTimeMillis());

            var urlCheck = new UrlCheck(statusCode, title, h1, description, urlId, createdAt);
            UrlCheckRepository.save(urlCheck);
            ctx.sessionAttribute("message", "URL is checked successfully!");
            ctx.redirect(NamedRoutes.urlPath(urlId));
        } catch (RuntimeException e) {
            ctx.sessionAttribute("message", "Check is failed");
            ctx.redirect(NamedRoutes.urlPath(urlId));
        }
    }
}
