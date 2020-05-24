package MainPackage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;

public class ShowNewForm {
    public void showForm(Button button, String form){
        button.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(form));
        try {
            fxmlLoader.load();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setTitle("Testing System");
        stage.setScene(new Scene(root));
        stage.show();
    }
    public String getMainMenuForm(){
        return "/Forms/MainMenuForm.fxml";
    }

    public String getLoginForm(){
        return "/Forms/LoginForm.fxml";
    }

    public String getTestsForm(){
        return "/Forms/TestsForm.fxml";
    }

    public String getCreateTestForm(){
        return "/Forms/CreateTestForm.fxml";
    }

    public String getQuestionForm(){
        return "/Forms/QuestionForm.fxml";
    }
    public String getUsersForm(){
        return "/Forms/UsersForm.fxml";
    }
    public String getCreateNewUserForm(){
        return "/Forms/CreateNewUserForm.fxml";
    }
    public String getTakeTestForm(){
        return "/Forms/TakeTestForm.fxml";
    }
    public String getTakeTestQuestionForm(){
        return "/Forms/TakeTestQuestionForm.fxml";
    }
    public String getTestLogForm(){
        return "/Forms/TestLogForm.fxml";
    }
}
