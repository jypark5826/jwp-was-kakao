package db;

import com.google.common.collect.Maps;
import model.Session;

import java.util.Map;

public class Sessions {
    private Map<String, Session> sessions;

    public Sessions() {
        this.sessions = Maps.newHashMap();
    }

    public Session save(Session session) {
        sessions.put(session.getId(), session);
        return session;
    }

    public Session findById(String sessionId) {
        if (!sessions.containsKey(sessionId)) {
            throw new NullPointerException();
        }
        return sessions.get(sessionId);
    }
}
