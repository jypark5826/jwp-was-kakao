package controller;

import http.HttpResponse;
import org.springframework.http.HttpStatus;
import utils.Dispatcher;

public class TemplateController {
    public static Handler templateHandler = (request) -> HttpResponse.builder()
            .status(HttpStatus.OK)
            .body(Dispatcher.TEMPLATES + request.getUri())
            .build();
}
