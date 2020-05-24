package JDBC;

public class Root {
    private final String login = "root";
    private final String password = "00foruvo";
    private final String connectionURL = "jdbc:mysql://localhost/testing_system?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    public String getConnectionURL() {
        return connectionURL;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
