package http;

public enum TemplateRequestExtension {
    HTML(".html"), ICO(".ico");

    private String extension;

    TemplateRequestExtension(String extension) {
        this.extension = extension;
    }


    public boolean endsWith(String extension) {
        return extension.endsWith(this.extension);
    }
}
