package Controllers;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

import JDBC.Root;
import MainPackage.ShowNewForm;
import SaveDateClass.CreateTest;
import User.User;
import animation.Shake;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sun.security.util.Password;

public class QuestionController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addNewQuesButton;

    @FXML
    private Button saveButton;

    @FXML
    private TextField wordingQuestionTF;

    @FXML
    private Button addWordingButton;

    @FXML
    private TextField correctAnsTF;

    @FXML
    private TextField incorrectTF;

    @FXML
    private Button addCorretAnsButton;

    @FXML
    private Button addIncorrectAnsButton;

    @FXML
    private Label wordingLable;

    @FXML
    private Label correctLable;

    @FXML
    private Label incorrectLable;

    @FXML
    void initialize() {
        if (CreateTest.newTest.getCounter() != CreateTest.newTest.getNumQuestion() - 1) {
            saveButton.setVisible(false);
        }else{
            saveButton.setVisible(true);
            addNewQuesButton.setVisible(false);
        }
        addWordingButton.setOnAction(event -> {
            addElement(wordingQuestionTF,wordingLable,tempWordings);
        });
        addCorretAnsButton.setOnAction(event -> {
            addElement(correctAnsTF,correctLable,tempCorrectAns);
        });
        addIncorrectAnsButton.setOnAction(event -> {
            addElement(incorrectTF,incorrectLable,tempInCorrectAns);
        });
        addNewQuesButton.setOnAction(event -> {
            if (tempWordings.size() > 0 &&
            tempCorrectAns.size() > 0 &&
            tempInCorrectAns.size() > 0){
                copyToTestList();
                new ShowNewForm().showForm(addNewQuesButton, new ShowNewForm().getQuestionForm());
            }else{
                Shake anim= new Shake(addNewQuesButton);
                anim.playAnim();
            }
        });
        saveButton.setOnAction(event -> {
            if (tempWordings.size() > 0 &&
                    tempCorrectAns.size() > 0 &&
                    tempInCorrectAns.size() > 0){
                copyToTestList();
                addInfoToBD();
                new ShowNewForm().showForm(saveButton, new ShowNewForm().getTestsForm());
            }else{
                Shake anim= new Shake(saveButton);
                anim.playAnim();
            }
        });
    }

    private void copyToTestList(){
        CreateTest.newTest.wordings.add(new ArrayList<String>());
        CreateTest.newTest.correctAns.add(new ArrayList<String>());
        CreateTest.newTest.incorrextAns.add(new ArrayList<String>());
        CreateTest.newTest.wordings.get(CreateTest.newTest.getCounter()).addAll(tempWordings);
        CreateTest.newTest.correctAns.get(CreateTest.newTest.getCounter()).addAll(tempCorrectAns);
        CreateTest.newTest.incorrextAns.get(CreateTest.newTest.getCounter()).addAll(tempInCorrectAns);
        CreateTest.newTest.incrementCounter();
    }

    private boolean addElement(TextField tf,Label label, ArrayList<String> strings){
        if (!tf.getText().trim().equals("")){
            strings.add(tf.getText().trim());
            updateLable(label, strings);
            tf.setText("");
            return true;
        }else return false;
    }

    private void updateLable(Label label, ArrayList<String> arrayList){
        label.setText("");
        for (int i = 0; i < arrayList.size(); i++){
            label.setText(label.getText() + arrayList.get(i) + "; ");
        }
    }

    private ArrayList<String> tempWordings = new ArrayList<>();
    private ArrayList<String> tempCorrectAns = new ArrayList<>();
    private ArrayList<String> tempInCorrectAns = new ArrayList<>();

    private boolean addInfoToBD(){
        Root root = new Root();
        try(Connection connection = DriverManager.getConnection(root.getConnectionURL(), root.getLogin(), root.getPassword());
            Statement statement = connection.createStatement())  {
            statement.executeUpdate("INSERT INTO test(time, subject, numberquestion) VALUES ('"+
                    CreateTest.newTest.getTime()+"', '"+ CreateTest.newTest.getSubject() +"','"+
                    CreateTest.newTest.getNumQuestion()+"')");
            for (int i = 0; i < CreateTest.newTest.getNumQuestion(); i++){
                statement.executeUpdate("INSERT INTO question(problem) VALUES ('0')");
                for (int j = 0; j < CreateTest.newTest.wordings.get(i).size(); j++){
                    statement.executeUpdate("INSERT INTO wording(text) VALUES ('"+
                            CreateTest.newTest.wordings.get(i).get(j)+"')");
                    statement.executeUpdate("INSERT INTO link_question_wording(idQuestion, idWording) VALUES (" +
                            "(SELECT idQuestion FROM question ORDER BY idQuestion DESC LIMIT 1), " +
                            "(SELECT idWording FROM wording ORDER BY idWording DESC LIMIT 1))");
                }
                for (int j = 0; j < CreateTest.newTest.correctAns.get(i).size(); j++){
                    statement.executeUpdate("INSERT INTO answer(textAnswer) VALUES ('"+
                            CreateTest.newTest.correctAns.get(i).get(j)+"')");
                    statement.executeUpdate("INSERT INTO link_question_answer(idQuestion, idAnswer, typeAnswer) VALUES (" +
                            "(SELECT idQuestion FROM question ORDER BY idQuestion DESC LIMIT 1), " +
                            "(SELECT idAnswer FROM answer ORDER BY idAnswer DESC LIMIT 1), '1')");
                }
                for (int j = 0; j < CreateTest.newTest.incorrextAns.get(i).size(); j++){
                    statement.executeUpdate("INSERT INTO answer(textAnswer) VALUES ('"+
                            CreateTest.newTest.incorrextAns.get(i).get(j)+"')");
                    statement.executeUpdate("INSERT INTO link_question_answer(idQuestion, idAnswer, typeAnswer) VALUES (" +
                            "(SELECT idQuestion FROM question ORDER BY idQuestion DESC LIMIT 1), " +
                            "(SELECT idAnswer FROM answer ORDER BY idAnswer DESC LIMIT 1), '0')");
                }
                statement.executeUpdate("INSERT INTO link_test_question(idTest, idQuestion) VALUES (" +
                        "(SELECT idTest FROM test ORDER BY idTest DESC LIMIT 1), " +
                        "(SELECT idQuestion FROM question ORDER BY idQuestion DESC LIMIT 1))");
            }
            statement.executeUpdate("INSERT INTO teacher_tests(login, idTest) VALUES (" +
                    "'"+ User.getLogin() +"', " +
                    "(SELECT idTest FROM test ORDER BY idTest DESC LIMIT 1))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
