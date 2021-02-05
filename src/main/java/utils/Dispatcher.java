package utils;

import controller.DispatchInfo;
import controller.Handler;
import http.HttpRequest;
import http.HttpResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

public class Dispatcher {
    public static final String TEMPLATES = "./templates";
    public static final String STATIC = "./static";
    public static final String TEXT_HTML = "text/html";
    public static final String TEXT_CSS = "text/css";
    public static final String CHARSET_UTF_8 = "charset=utf-8";
    public static final String INDEX_HTML = "/index.html";
    public static final String USER_FORM_HTML = "/user/form.html";
    public static final String USER_LOGIN_HTML = "/user/login.html";
    public static final String LOGIN_FAILED_HTML = "/user/login_failed.html";
    public static final String DUPLICATED_ID = "아이디 중복";
    public static final String NOT_EXIST_ID = "존재하지 않는 아이디";
    public static final String INCORRECT_PASSWORD = "비밀번호 불일치";

    public static HttpResponse dispatch(HttpRequest request) throws IOException, URISyntaxException {
        try {
            return findMatchingHandlers(request).handleRequest(request);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals(NOT_EXIST_ID) || e.getMessage().equals(INCORRECT_PASSWORD)) {
                return HttpResponse.builder()
                        .status(HttpStatus.FOUND)
                        .redirect(LOGIN_FAILED_HTML)
                        .build();
            }
            return HttpResponse.builder()
                    .status(HttpStatus.FOUND)
                    .redirect(USER_FORM_HTML)
                    .build();
        } catch (NullPointerException e) {
            return HttpResponse.builder()
                    .status(HttpStatus.FOUND)
                    .redirect(USER_LOGIN_HTML)
                    .build();
        } catch (Exception e) {
            return HttpResponse.builder()
                    .status(HttpStatus.FOUND)
                    .redirect(INDEX_HTML)
                    .build();
        }
    }

    public static Handler findMatchingHandlers(HttpRequest request) {
        return Arrays.stream(DispatchInfo.values())
                .filter(dispatchInfo -> dispatchInfo.matchWith(request))
                .findAny()
                .map(DispatchInfo::getRequestHandler)
                .orElseThrow(RuntimeException::new);
    }
}
