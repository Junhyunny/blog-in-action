package app.auth;

public interface AuthManager {
    AuthUser getUserByToken(String authToken);
}
