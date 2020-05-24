package Controllers;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

import JDBC.Root;
import MainPackage.ShowNewForm;
import SaveDateClass.TakeTestSave;
import SaveDateClass.TestsRead;
import User.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class TakeTestController {

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
    private Label timeLable;

    @FXML
    private Label numberQuestionLable;

    @FXML
    private Button startButton;

    @FXML
    void initialize() {
        logOutButton.setOnAction(event -> {
            new ShowNewForm().showForm(logOutButton, new ShowNewForm().getLoginForm());
        });
        testsButton.setOnAction(event -> {
            new ShowNewForm().showForm(testsButton, new ShowNewForm().getTestsForm());
        });
        timeLable.setText("Time: " + TakeTestSave.getTime());
        numberQuestionLable.setText("Number of questions: " + TakeTestSave.getNumQuestion());
        saveDataTest();
        startButton.setOnAction(event -> {
            new ShowNewForm().showForm(startButton, new ShowNewForm().getTakeTestQuestionForm());
        });
    }

    private void saveDataTest(){
        Root root = new Root();
        try (Connection connection = DriverManager.getConnection(root.getConnectionURL(), root.getLogin(), root.getPassword())) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT idQuestion from link_test_question where idTest = ?");
            preparedStatement.setInt(1, TakeTestSave.getIdTest());
            ResultSet resultSet = preparedStatement.executeQuery();
            int i = 0;
            while (resultSet.next()) {
                TakeTestSave.wording.add(new ArrayList<>());
                TakeTestSave.trueAns.add(new ArrayList<>());
                TakeTestSave.falseAns.add(new ArrayList<>());
                int question  = resultSet.getInt("idQuestion");
                PreparedStatement ps1 = connection.prepareStatement("SELECT idWording from link_question_wording where idQuestion = ?");
                ps1.setInt(1, question);
                ResultSet rs1 = ps1.executeQuery();
                while (rs1.next()){
                    PreparedStatement ps2 = connection.prepareStatement("SELECT text from wording where idWording = ?");
                    ps2.setInt(1, rs1.getInt("idWording"));
                    ResultSet rs2 = ps2.executeQuery();
                    while(rs2.next()){
                        TakeTestSave.wording.get(i).add(rs2.getString("text"));
                    }
                }
                ps1 = connection.prepareStatement("SELECT idAnswer from link_question_answer where idQuestion = ? and typeAnswer = '1'");
                ps1.setInt(1, question);
                rs1 = ps1.executeQuery();
                while (rs1.next()){
                    PreparedStatement ps2 = connection.prepareStatement("SELECT textAnswer from answer where idAnswer = ?");
                    ps2.setInt(1, rs1.getInt("idAnswer"));
                    ResultSet rs2 = ps2.executeQuery();
                    while(rs2.next()){
                        TakeTestSave.trueAns.get(i).add(rs2.getString("textAnswer"));
                    }
                }
                ps1 = connection.prepareStatement("SELECT idAnswer from link_question_answer where idQuestion = ? and typeAnswer = '0'");
                ps1.setInt(1, question);
                rs1 = ps1.executeQuery();
                while (rs1.next()){
                    PreparedStatement ps2 = connection.prepareStatement("SELECT textAnswer from answer where idAnswer = ?");
                    ps2.setInt(1, rs1.getInt("idAnswer"));
                    ResultSet rs2 = ps2.executeQuery();
                    while(rs2.next()){
                        TakeTestSave.falseAns.get(i).add(rs2.getString("textAnswer"));
                    }
                }
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
