package Controllers;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import JDBC.Root;
import javafx.event.ActionEvent;
import MainPackage.ShowNewForm;
import SaveDateClass.CreateTest;
import SaveDateClass.Person;
import SaveDateClass.TestsRead;
import User.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class UsersController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button logOutButton;

    @FXML
    private TableView<Person> tableView;

    @FXML
    private TableColumn<Person, String> loginColumn;

    @FXML
    private TableColumn<Person, String> passwordColumn;

    @FXML
    private TableColumn<Person, Integer> typeColumn;

    @FXML
    private TableColumn<Person, String> nameColumn;

    @FXML
    private TableColumn<Person, String> surnameColumn;

    @FXML
    private TableColumn<Person, String> patronymiColumn;

    @FXML
    private Button createUserButton;

    @FXML
    private Button deleteUserButton;

    @FXML
    void deletePerson(ActionEvent event) {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        String login = tableView.getItems().remove(selectedIndex).getLogin();
        Root root = new Root();
        try(Connection connection = DriverManager.getConnection(root.getConnectionURL(), root.getLogin(), root.getPassword());
        Statement statement = connection.createStatement())  {
            statement.executeUpdate("DELETE FROM user WHERE login = '"+login+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        tableView.setItems(getPersonForTable());
        loginColumn.setCellValueFactory(new PropertyValueFactory<Person,String>("login"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<Person,String>("password"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Person,Integer>("type"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Person,String>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<Person,String>("surname"));
        patronymiColumn.setCellValueFactory(new PropertyValueFactory<Person,String>("patronymi"));
        logOutButton.setOnAction(event -> {
            new ShowNewForm().showForm(logOutButton, new ShowNewForm().getLoginForm());
        });
        createUserButton.setOnAction(event -> {
            new ShowNewForm().showForm(createUserButton, new ShowNewForm().getCreateNewUserForm());
        });
        //int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        //tableView.getItems().remove(selectedIndex);
    }

    private ObservableList<Person> getPersonForTable(){
        ObservableList<Person> people = FXCollections.observableArrayList();
        Root root = new Root();
        try(Connection connection = DriverManager.getConnection(root.getConnectionURL(), root.getLogin(), root.getPassword()))  {
            PreparedStatement preparedStatement = connection.prepareStatement("select * FROM user");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                people.add(new Person(resultSet.getString("login"),
                        resultSet.getString("password"),resultSet.getInt("type"),
                        resultSet.getString("name"), resultSet.getString("surname"),
                        resultSet.getString("patronymi")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return people;
    }
}
