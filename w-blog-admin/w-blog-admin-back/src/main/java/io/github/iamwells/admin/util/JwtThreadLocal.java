package io.github.iamwells.admin.util;

public class JwtThreadLocal {
    private static final ThreadLocal<String> JWT_TOKEN = new ThreadLocal<>();

    public static void set(String jwt) {
        JWT_TOKEN.set(jwt);
    }

    public static String get() {
        return JWT_TOKEN.get();
    }

    public static void remove() {
        JWT_TOKEN.remove();
    }

}
