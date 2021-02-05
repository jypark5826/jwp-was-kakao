package db;

import com.google.common.collect.Maps;
import model.User;
import utils.Dispatcher;

import java.util.Collection;
import java.util.Map;

public class Users {
    private Map<String, User> users;

    public Users() {
        this.users = Maps.newHashMap();
    }

    public void save(User user) {
        if (users.containsKey(user.getUserId())) {
            throw new IllegalArgumentException(Dispatcher.DUPLICATED_ID);
        }
        users.put(user.getUserId(), user);
    }

    public User findById(String userId) {
        if (!users.containsKey(userId)) {
            throw new IllegalArgumentException(Dispatcher.NOT_EXIST_ID);
        }
        return users.get(userId);
    }

    public Collection<User> findAll() {
        return users.values();
    }
}
