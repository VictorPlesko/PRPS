package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import MainPackage.ShowNewForm;
import SaveDateClass.CreateTest;
import animation.Shake;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CreateTestController {

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
    private TextField subjectNameTF;

    @FXML
    private TextField timeTF;

    @FXML
    private TextField numQusTF;

    @FXML
    private Button addQesButton;

    @FXML
    void initialize() {
        logOutButton.setOnAction(event -> {
            new ShowNewForm().showForm(logOutButton, new ShowNewForm().getLoginForm());
        });
        testsButton.setOnAction(event -> {
            new ShowNewForm().showForm(testsButton, new ShowNewForm().getTestsForm());
        });
        addQesButton.setOnAction(event -> {
            if (!subjectNameTF.getText().trim().equals("") && !timeTF.getText().trim().equals("") &&
            !numQusTF.getText().trim().equals("")){
                CreateTest.newTest = new CreateTest(subjectNameTF.getText().trim(),
                        timeTF.getText().trim(),
                        Integer.valueOf(numQusTF.getText().trim()));
                new ShowNewForm().showForm(addQesButton, new ShowNewForm().getQuestionForm());
            }else{
                empryTF();
            }
        });
    }

    private void empryTF(){
        if (subjectNameTF.getText().trim().equals("")){
            startAnim(subjectNameTF);
        }
        if (timeTF.getText().trim().equals("")){
            startAnim(timeTF);
        }
        if (numQusTF.getText().trim().equals("")){
            startAnim(numQusTF);
        }
    }

    private void startAnim(TextField tx){
        Shake anim= new Shake(tx);
        anim.playAnim();
    }
}
