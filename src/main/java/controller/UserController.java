package controller;

import db.DataBase;
import http.Body;
import http.Cookie;
import http.Cookies;
import http.HttpResponse;
import model.Session;
import model.User;
import org.springframework.http.HttpStatus;
import utils.Dispatcher;

import java.util.HashMap;
import java.util.Map;

public class UserController {
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    public static final String USER = "user";
    public static final String SESSION = "session";
    public static final String USERS = "users";

    public static Handler createUserHandler = (request) -> {
        Body body = request.getBody();

        User user = new User(body.get(USER_ID),
                body.get(PASSWORD),
                body.get(NAME),
                body.get(EMAIL));
        DataBase.addUser(user);

        return HttpResponse.builder()
                .status(HttpStatus.FOUND)
                .redirect(Dispatcher.INDEX_HTML)
                .build();
    };

    public static Handler loginUserHandler = (request) -> {
        Body body = request.getBody();
        User user = DataBase.findUserById(body.get(USER_ID));
        user.verifyPassword(body.get(PASSWORD));

        Session session = DataBase.addSession();
        session.setAttribute(USER, user);

        Cookie cookie = new Cookie(SESSION, session.getId());
        cookie.setPath("/");

        return HttpResponse.builder()
                .status(HttpStatus.FOUND)
                .redirect(Dispatcher.INDEX_HTML)
                .cookie(cookie)
                .build();
    };

    public static Handler listUserHandler = (request) -> {
        Cookies cookies = request.getCookies();
        Cookie sessionId = cookies.getCookie(SESSION);

        Session session = DataBase.findSessionById(sessionId.getValue());
        session.getAttribute(USER);

        Map<String, Object> params = new HashMap<>();
        params.put(USERS, DataBase.findAllUsers());

        return HttpResponse.builder()
                .status(HttpStatus.OK)
                .contentType(String.format("%s;%s", Dispatcher.TEXT_HTML, Dispatcher.CHARSET_UTF_8))
                .body(request.getUri(), params)
                .build();
    };

    public static Handler logoutUserHandler = (request) -> {
        Cookies cookies = request.getCookies();
        Cookie sessionId = cookies.getCookie(SESSION);

        Session session = DataBase.findSessionById(sessionId.getValue());
        session.invalidate();

        return HttpResponse.builder()
                .status(HttpStatus.FOUND)
                .redirect(Dispatcher.INDEX_HTML)
                .build();
    };
}
