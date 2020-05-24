package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import MainPackage.ShowNewForm;
import User.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.jws.soap.SOAPBinding;

public class MainMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button testViewButton;

    @FXML
    private Button testsButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Button usersButton;

    @FXML
    void initialize() {
        if (User.getType() != 2){
            usersButton.setVisible(false);
        }else {
            testsButton.setVisible(false);
            testViewButton.setVisible(false);
        }
        logOutButton.setOnAction(event -> {
            new ShowNewForm().showForm(logOutButton, new ShowNewForm().getLoginForm());
        });
        testsButton.setOnAction(event -> {
            new ShowNewForm().showForm(testsButton, new ShowNewForm().getTestsForm());
        });
        testViewButton.setOnAction(event -> {
            new ShowNewForm().showForm(testViewButton, new ShowNewForm().getTestLogForm());
        });
        usersButton.setOnAction(event -> {
            new ShowNewForm().showForm(usersButton, new ShowNewForm().getUsersForm());
        });
    }
}
