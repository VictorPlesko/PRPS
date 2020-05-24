package Controllers;

import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import JDBC.Root;
import MainPackage.ShowNewForm;
import SaveDateClass.CreateTest;
import SaveDateClass.TakeTestSave;
import User.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

public class TakeTestQuestionController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button nextQuestionButton;

    @FXML
    private Button saveButton;

    @FXML
    private Label questionLabel;

    @FXML
    private RadioButton AnswerRadioButton1;

    @FXML
    private RadioButton AnswerRadioButton2;

    @FXML
    private RadioButton AnswerRadioButton3;

    @FXML
    private RadioButton AnswerRadioButton4;

    @FXML
    void initialize() {
        if (TakeTestSave.getNumQuestion() - 1 == TakeTestSave.counter){
            nextQuestionButton.setVisible(false);
        }else{
            saveButton.setVisible(false);
        }
        questionLabel.setText(Integer.toString(TakeTestSave.counter + 1) + ". " + TakeTestSave.wording.get(TakeTestSave.counter).get(0));
        int sizeAns = TakeTestSave.trueAns.get(TakeTestSave.counter).size() + TakeTestSave.falseAns.get(TakeTestSave.counter).size();
        if (sizeAns == 2){
            AnswerRadioButton4.setVisible(false);
            AnswerRadioButton3.setVisible(false);
            AnswerRadioButton2.setText(TakeTestSave.falseAns.get(TakeTestSave.counter).get(0));
        }else if (sizeAns == 3){
            AnswerRadioButton4.setVisible(false);
            AnswerRadioButton2.setText(TakeTestSave.falseAns.get(TakeTestSave.counter).get(0));
            AnswerRadioButton3.setText(TakeTestSave.falseAns.get(TakeTestSave.counter).get(1));
        }else{
            int temp = 0;
            while (TakeTestSave.falseAns.get(TakeTestSave.counter).size() - 1 != temp || temp != 3){
                if (temp == 0){
                    AnswerRadioButton2.setText(TakeTestSave.falseAns.get(TakeTestSave.counter).get(0));
                }else if (temp == 1){
                    AnswerRadioButton3.setText(TakeTestSave.falseAns.get(TakeTestSave.counter).get(1));
                }else  if (temp == 2){
                    AnswerRadioButton4.setText(TakeTestSave.falseAns.get(TakeTestSave.counter).get(2));
                }
                temp++;
            }
        }
        AnswerRadioButton1.setText(TakeTestSave.trueAns.get(TakeTestSave.counter).get(0));

        TakeTestSave.counter++;
        nextQuestionButton.setOnAction(event -> {
            if (AnswerRadioButton1.isSelected()){
                TakeTestSave.correct++;
            }else{
                TakeTestSave.incorrect++;
            }
            new ShowNewForm().showForm(nextQuestionButton, new ShowNewForm().getTakeTestQuestionForm());
        });
        saveButton.setOnAction(event -> {
            int mark = Math.round((float)TakeTestSave.correct / TakeTestSave.getNumQuestion() * 10);
            Root root = new Root();
            try (Connection connection = DriverManager.getConnection(root.getConnectionURL(), root.getLogin(), root.getPassword());
                Statement statement = connection.createStatement()){
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
                statement.executeUpdate("INSERT INTO passed_tests(login, idTest, date, mark) VALUES (" +
                        "'"+ User.getLogin() +"', '"+ TakeTestSave.getIdTest() +"', '"+dateFormat.format( new Date() ) +"', '"+mark+"')");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            new ShowNewForm().showForm(nextQuestionButton, new ShowNewForm().getTestsForm());
        });
    }
}
