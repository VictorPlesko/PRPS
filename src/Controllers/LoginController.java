package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import JDBC.Root;
import MainPackage.ShowNewForm;
import User.User;
import animation.Shake;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sun.security.util.Password;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginTextField;

    @FXML
    private Button singInButton;

    @FXML
    private PasswordField passwordTestField;

    @FXML
    void initialize() throws ClassNotFoundException {
        singInButton.setOnAction(event -> {
            String login = loginTextField.getText().trim();
            String password = passwordTestField.getText().trim();
            if (!login.equals("") && !password.equals("")){
                if (loginUser(login,password)){
                    new ShowNewForm().showForm(singInButton, new ShowNewForm().getMainMenuForm());
                }
            }else{
                startLoginFormAnimate();
            }
        });
    }

    private boolean loginUser(String login, String password){
        Root root = new Root();
        try(Connection connection = DriverManager.getConnection(root.getConnectionURL(), root.getLogin(), root.getPassword());
            Statement statement = connection.createStatement())  {
            PreparedStatement preparedStatement = connection.prepareStatement("select * FROM user WHERE login = ?");
            preparedStatement.setString(1,login);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean enter = false;
            while (resultSet.next()){
                enter = true;
                String passwordBD = resultSet.getString("password");
                if (!password.equals(passwordBD)){
                    clearTextField();
                    startLoginFormAnimate();
                    connection.close();
                    return false;
                }
                User user = new User(resultSet.getString("login"),
                        resultSet.getString("name"),
                        resultSet.getInt("type"),
                        resultSet.getString("surname"),
                        resultSet.getString("patronymi"));
            }
            if (!enter){
                clearTextField();
                startLoginFormAnimate();
                connection.close();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void clearTextField(){
        loginTextField.setText("");
        passwordTestField.setText("");
    }

    private void startLoginFormAnimate(){
        Shake userLoginAnim = new Shake(loginTextField);
        Shake userPasswordAnim = new Shake(passwordTestField);
        userLoginAnim.playAnim();
        userPasswordAnim.playAnim();
    }
}
