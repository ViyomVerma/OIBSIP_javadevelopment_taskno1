package com.reservation_system;

public class UserSession {
    private static int userId;
    private static String username;
    private static String fullname;

    public static void setUser(int id, String uname, String fname) {
        userId = id;
        username = uname;
        fullname = fname;
    }

    public static int getUserId() {
        return userId;
    }

    public static String getUsername() {
        return username;
    }

    public static String getFullname() {
        return fullname;
    }

    public static void clearSession() {
        userId = 0;
        username = null;
        fullname = null;
    }
}
