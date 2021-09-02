package be.kuleuven.mainactivity.ModelClasses;

public class Users {

    public static String username;
    public static String nameUser;
    public static String password;
    public static int tokens;


    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Users.username = username;
    }

    public static String getNameUser() {
        return nameUser;
    }

    public static void setNameUser(String name_user) {
        Users.nameUser = name_user;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Users.password = password;
    }

    public static int getTokens() {
        return tokens;
    }

    public static void setTokens(int tokens) {
        Users.tokens = tokens;
    }
}
