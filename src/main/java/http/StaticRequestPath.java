package http;

public enum StaticRequestPath {
    CSS("/css"), FONTS("/fonts"), IMAGES("/images"), JS("/js");

    private String path;

    StaticRequestPath(String path) {
        this.path = path;
    }

    public boolean startsWith(String path) {
        return path.startsWith(this.path);
    }
}
