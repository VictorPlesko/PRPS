package Controllers;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import JDBC.Root;
import MainPackage.ShowNewForm;
import SaveDateClass.TestLogMark;
import SaveDateClass.TestsRead;
import User.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TestLogController {

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
    private TableView<TestLogMark> tableView;

    @FXML
    private TableColumn<TestLogMark, String > nameColumn;

    @FXML
    private TableColumn<TestLogMark, String> surnameColumn;

    @FXML
    private TableColumn<TestLogMark, String> dateColumn;

    @FXML
    private TableColumn<TestLogMark, Integer> markDate;

    @FXML
    void initialize() {
        tableView.setItems(getTestForTable());
        nameColumn.setCellValueFactory(new PropertyValueFactory<TestLogMark, String>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<TestLogMark, String>("surname"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<TestLogMark, String>("date"));
        markDate.setCellValueFactory(new PropertyValueFactory<TestLogMark, Integer>("mark"));
        logOutButton.setOnAction(event -> {
            new ShowNewForm().showForm(logOutButton, new ShowNewForm().getLoginForm());
        });
        testsButton.setOnAction(event -> {
            new ShowNewForm().showForm(testsButton, new ShowNewForm().getTestsForm());
        });
        testViewButton.setOnAction(event -> {
            new ShowNewForm().showForm(testViewButton, new ShowNewForm().getTestLogForm());
        });
    }

    private ObservableList<TestLogMark> getTestForTable() {
        ObservableList<TestLogMark> tests = FXCollections.observableArrayList();
        Root root = new Root();
        try (Connection connection = DriverManager.getConnection(root.getConnectionURL(), root.getLogin(), root.getPassword())) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from passed_tests");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PreparedStatement ps2 = connection.prepareStatement("select name,surname from user where login = ?");
                ps2.setString(1, resultSet.getString("login"));
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    tests.add(new TestLogMark(rs2.getString("name"),rs2.getString("surname"),
                            resultSet.getString("date"), resultSet.getInt("mark")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tests;
    }
}
