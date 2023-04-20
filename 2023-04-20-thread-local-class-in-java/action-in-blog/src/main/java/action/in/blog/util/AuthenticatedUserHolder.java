package action.in.blog.util;

import action.in.blog.domain.AuthenticatedUser;

public class AuthenticatedUserHolder {

    private static final ThreadLocal<AuthenticatedUser> holder = new ThreadLocal<>();

    public static AuthenticatedUser get() {
        return holder.get();
    }

    public static void setUser(AuthenticatedUser authenticatedUser) {
        holder.set(authenticatedUser);
    }

    public static void remove() {
        holder.remove();
    }
}
