package controller;

import http.HttpResponse;
import org.springframework.http.HttpStatus;
import utils.Dispatcher;

public class StaticController {
    public static Handler staticHandler = (request) -> HttpResponse.builder()
            .status(HttpStatus.OK)
            .contentType(String.format("%s;%s", Dispatcher.TEXT_CSS, Dispatcher.CHARSET_UTF_8))
            .body(Dispatcher.STATIC + request.getUri())
            .build();
}
