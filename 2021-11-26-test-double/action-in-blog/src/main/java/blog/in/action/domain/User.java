package blog.in.action.domain;

public class User {

    private final String username;
    private final String role;

    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public boolean isAdmin() {
        return "ROLE_ADMIN".equals(role);
    }
}
