package core.execution.http.thymeleaf;

class Template {

    private final String content;

    Template(String content) {
        this.content = content;
    }

    String getContent() {
        return content;
    }
}
