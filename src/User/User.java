package User;

public class User {
    private static String login;
    private static String name;
    private static int type;
    private static String surname;
    private static String patronymi;

    public User(String login, String name, int type, String surname, String patronymi) {
        this.login = login;
        this.name = name;
        this.type = type;
        this.surname = surname;
        this.patronymi = patronymi;
    }

    public static int getType() {
        return type;
    }

    public static String getLogin() {
        return login;
    }

    public static String getName() {
        return name;
    }

    public static String getSurname() {
        return surname;
    }

    public static String getPatronymi() {
        return patronymi;
    }
}
