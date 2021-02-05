package db;

import model.Session;
import model.User;

import java.util.Collection;
import java.util.UUID;

public class DataBase {
    private static Users users = new Users();
    private static Sessions sessions = new Sessions();

    public static void addUser(User user) {
        users.save(user);
    }

    public static User findUserById(String userId) {
        return users.findById(userId);
    }

    public static Collection<User> findAllUsers() {
        return users.findAll();
    }

    public static Session addSession() {
        Session session = new Session(UUID.randomUUID().toString());
        return sessions.save(session);
    }

    public static Session findSessionById(String sessionId) {
        return sessions.findById(sessionId);
    }
}
