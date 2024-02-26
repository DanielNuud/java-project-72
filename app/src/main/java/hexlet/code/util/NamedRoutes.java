package hexlet.code.util;

public final class NamedRoutes {

    public static String rootPath() {
        return "/";
    }

    public static String urlPath(Long id) {
        return urlPath(String.valueOf(id));
    }

    public static String urlPath(String id) {
        return "/urls/" + id;
    }

    public static String urlsPath() {
        return "/urls";
    }

    public static String urlsPath(int page) {
        return "/urls?page=" + page;
    }

    public static String urlCheckPath(Long id) {
        return urlCheckPath(String.valueOf(id));
    }

    public static String urlCheckPath(String id) {
        return "/urls/" + id + "/checks";
    }
}
