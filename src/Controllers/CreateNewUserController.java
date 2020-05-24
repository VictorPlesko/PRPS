package Controllers;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import JDBC.Root;
import MainPackage.ShowNewForm;
import SaveDateClass.TestsRead;
import User.User;
import animation.Shake;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class CreateNewUserController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button logOutButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button backButton;

    @FXML
    private TextField loginTF;

    @FXML
    private TextField passwordTF;

    @FXML
    private TextField nameTF;

    @FXML
    private TextField surnameTF;

    @FXML
    private TextField patronymiTF;

    @FXML
    private RadioButton studentRB;

    @FXML
    private RadioButton teacherRB;

    @FXML
    void initialize() {
        AtomicInteger type = new AtomicInteger(-1);
        ToggleGroup group = new ToggleGroup();
        studentRB.setToggleGroup(group);
        teacherRB.setToggleGroup(group);
        group.selectedToggleProperty().addListener((ov, t, t1) ->{
            RadioButton chk = (RadioButton) t1.getToggleGroup().getSelectedToggle();
            if (chk.getText().equals("Student")){
               type.set(0);
            }else{
                type.set(1);
            }
        });
        logOutButton.setOnAction(event -> {
            new ShowNewForm().showForm(logOutButton, new ShowNewForm().getLoginForm());
        });
        backButton.setOnAction(event -> {
            new ShowNewForm().showForm(backButton, new ShowNewForm().getUsersForm());
        });
        saveButton.setOnAction(event -> {
            if (!loginTF.getText().trim().equals("") && !passwordTF.getText().trim().equals("") &&
                    !nameTF.getText().trim().equals("") && !surnameTF.getText().trim().equals("") &&
                    !patronymiTF.getText().trim().equals("") && type.get() != -1){
                Root root = new Root();
                try(Connection connection = DriverManager.getConnection(root.getConnectionURL(), root.getLogin(), root.getPassword());
                Statement statement = connection.createStatement())  {
                    statement.executeUpdate("INSERT INTO user(login, password, type, name, surname, patronymi) " +
                            "VALUES ('" + loginTF.getText().trim() +"', '" + patronymiTF.getText().trim() +"'," +
                            "'"+ type.get() +"', '"+ nameTF.getText().trim() +"', '"+ surnameTF.getText().trim() +"'," +
                            "'"+ patronymiTF.getText().trim() +"')");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                new ShowNewForm().showForm(backButton, new ShowNewForm().getUsersForm());
            }else {
                if (loginTF.getText().trim().equals("")){
                    Shake anim= new Shake(loginTF);
                    anim.playAnim();
                }
                if (passwordTF.getText().trim().equals("")){
                    Shake anim= new Shake(passwordTF);
                    anim.playAnim();
                }
                if (nameTF.getText().trim().equals("")){
                    Shake anim= new Shake(nameTF);
                    anim.playAnim();
                }
                if(surnameTF.getText().trim().equals("")) {
                    Shake anim= new Shake(surnameTF);
                    anim.playAnim();
                }
                if (patronymiTF.getText().trim().equals("")) {
                    Shake anim= new Shake(patronymiTF);
                    anim.playAnim();
                }
                if (type.get() == -1) {
                    Shake anim1= new Shake(studentRB);
                    Shake anim2= new Shake(teacherRB);
                    anim1.playAnim();
                    anim2.playAnim();
                }
            }
        });
    }
}
