package hexlet.code.controller;

import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import io.javalin.http.Context;;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.URI;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;

public class UrlController {
    private static final String URL_REGEX = "^(https?|ftp):\\/\\/(www\\.)?[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})+(\\/\\S*)?$";

    public static boolean isValidUrl(String url) {
        Pattern pattern = Pattern.compile(URL_REGEX);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }
    public static void index(Context ctx) throws SQLException {
        var urls = UrlRepository.getEntities();
        var page = new UrlsPage(urls);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flash-type"));
        ctx.render("urls/index.jte", Collections.singletonMap("page", page));
    }

    public static void create(Context ctx) throws SQLException {

        try {
            var name = ctx.formParamAsClass("url", String.class).get();

            if (!isValidUrl(name)) {
                throw new URISyntaxException("Invalid URL", "");
            }

            URI uri = new URI(name);
            String result = uri.getScheme() + "://" + uri.getHost();
            int port = uri.getPort();
            long currentTimeMillis = System.currentTimeMillis();
            Timestamp timestamp = new Timestamp(currentTimeMillis);

            if (port != -1) {
                result += ":" + port;
            }

            var url = new Url(result, timestamp);
            UrlRepository.save(url);

            ctx.sessionAttribute("flash", "Url is added successfully!");
            ctx.sessionAttribute("flash-type", "alert alert-success");
            ctx.redirect("/urls");

        } catch (URISyntaxException e) {
            ctx.sessionAttribute("flash", "Invalid URL");
            ctx.sessionAttribute("flash-type", "alert alert-danger");
            ctx.redirect("/");
        }
    }

}
