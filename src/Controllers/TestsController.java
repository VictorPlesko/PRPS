package Controllers;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import JDBC.Root;
import MainPackage.ShowNewForm;
import SaveDateClass.TakeTestSave;
import SaveDateClass.TestsRead;
import User.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TestsController {

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
    private Button createTestButton;

    @FXML
    private Button editTestButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button tekaTestButton;

    @FXML
    private TableView<TestsRead> tableView;

    @FXML
    private TableColumn<TestsRead, String> subjectColumn;

    @FXML
    private TableColumn<TestsRead, String> timeColumn;

    @FXML
    private TableColumn<TestsRead, Integer> numQuestionColumn;

    @FXML
    void deleteTest(ActionEvent event) {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        int idTest = tableView.getItems().remove(selectedIndex).getIdTest();
        Root root = new Root();
        try (Connection connection = DriverManager.getConnection(root.getConnectionURL(), root.getLogin(), root.getPassword());
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM test WHERE idTest = '" + idTest + "'");
            statement.executeUpdate("DELETE FROM teacher_tests WHERE idTest = '" + idTest + "'");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT idQuestion FROM " +
                    "link_test_question where idTest = ?");
            preparedStatement.setInt(1, idTest);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idQuestion = resultSet.getInt("idQuestion");
                PreparedStatement ps2 = connection.prepareStatement("SELECT idWording FROM " +
                        "link_question_wording where idQuestion = ?");
                ps2.setInt(1, idQuestion);
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    statement.executeUpdate("DELETE from wording where idWording = '" + rs2.getInt("idWording") + "' ");
                }
                statement.executeUpdate("DELETE FROM link_question_wording WHERE idQuestion = '" + idQuestion + "'");
                PreparedStatement ps3 = connection.prepareStatement("SELECT idAnswer FROM " +
                        "link_question_answer where idQuestion = ?");
                ps3.setInt(1, idQuestion);
                ResultSet rs3 = ps3.executeQuery();
                while (rs3.next()) {
                    statement.executeUpdate("DELETE from answer where idAnswer = '" + rs3.getInt("idAnswer") + "' ");
                }
                statement.executeUpdate("DELETE FROM link_question_answer WHERE idQuestion = '" + idQuestion + "'");
                statement.executeUpdate("DELETE FROM question WHERE idQuestion = '" + idQuestion + "'");
            }
            statement.executeUpdate("DELETE FROM link_test_question WHERE idTest = '" + idTest + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void takeTestAction(ActionEvent event) {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        new TakeTestSave(tableView.getItems().get(selectedIndex).getIdTest(),tableView.getItems().get(selectedIndex).getTime(),
                tableView.getItems().get(selectedIndex).getNumQuestion());
        new ShowNewForm().showForm(tekaTestButton, new ShowNewForm().getTakeTestForm());
    }

    @FXML
    void initialize() {
        if (User.getType() == 0) {
            createTestButton.setVisible(false);
            deleteButton.setVisible(false);
            editTestButton.setVisible(false);
        } else {
            tekaTestButton.setVisible(false);
        }
        tableView.setItems(getTestForTable());
        subjectColumn.setCellValueFactory(new PropertyValueFactory<TestsRead, String>("subject"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<TestsRead, String>("time"));
        numQuestionColumn.setCellValueFactory(new PropertyValueFactory<TestsRead, Integer>("numQuestion"));
        logOutButton.setOnAction(event -> {
            new ShowNewForm().showForm(logOutButton, new ShowNewForm().getLoginForm());
        });
        testViewButton.setOnAction(event -> {
            new ShowNewForm().showForm(testViewButton, new ShowNewForm().getTestLogForm());
        });
        testsButton.setOnAction(event -> {
            new ShowNewForm().showForm(testsButton, new ShowNewForm().getTestsForm());
        });
        createTestButton.setOnAction(event -> {
            new ShowNewForm().showForm(createTestButton, new ShowNewForm().getCreateTestForm());
        });
    }

    private ObservableList<TestsRead> getTestForTable() {
        ObservableList<TestsRead> tests = FXCollections.observableArrayList();
        Root root = new Root();
        try (Connection connection = DriverManager.getConnection(root.getConnectionURL(), root.getLogin(), root.getPassword())) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT idTest from teacher_tests where login = ?");
            preparedStatement.setString(1, User.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PreparedStatement ps2 = connection.prepareStatement("select idTest, subject, time, numberQuestion from test where idTest = ?");
                ps2.setString(1, resultSet.getString("idTest"));
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    tests.add(new TestsRead(rs2.getInt("idTest"), rs2.getString("subject"), rs2.getString("time"),
                            rs2.getInt("numberQuestion")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tests;
    }
}
