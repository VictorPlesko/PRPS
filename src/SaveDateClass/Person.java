package SaveDateClass;

public class Person {
    private String login;
    private String password;
    private int type;
    private String name;
    private String surname;
    private String patronymi;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronymi() {
        return patronymi;
    }

    public Person(String login, String password, int type, String name, String surname, String patronymi) {
        this.login = login;
        this.password = password;
        this.type = type;
        this.name = name;
        this.surname = surname;
        this.patronymi = patronymi;
    }
}
