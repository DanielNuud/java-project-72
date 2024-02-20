package hexlet.code.util;

public class NamedRoutes {
    public static String rootPath() {
        return "/";
    }
    public static String urlsPath() {
        return "/urls";
    }

    public static String urlPath(Long id) {
        return "/urls/" + String.valueOf(id);
    }

    public static String urlPath() {
        return "/urls/{id}";
    }

    public static String urlCheckPath() {
        return urlPath() + "/check";
    }

    public static String urlCheckPath(Long id) {
        return urlPath(id) + "/check";
    }
}
