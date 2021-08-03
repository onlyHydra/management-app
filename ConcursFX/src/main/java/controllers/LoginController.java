package controllers;

import concurs.model.User;
import concurs.services.ConcursException;
import concurs.services.IConcursServices;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LoginController {

    private IConcursServices server;
    private MainController mainController;
    private User currentUser;

    @FXML
    TextField userField;
    @FXML
    Button logBtn;
    @FXML
    Text text;
    @FXML
    PasswordField passwordField;

    Parent mainParent;

    public void setServer(IConcursServices server){
        this.server = server;
    }

    public void setParent(Parent parent){
        mainParent = parent;
    }

    public void setController(MainController mainController){
        this.mainController = mainController;
    }

    public void handleLogin(ActionEvent actionEvent){
        String username = userField.getText();
        String pass = passwordField.getText();
        User user = new User(username, pass);

        try{
            server.login(user, mainController);
            mainController.setUser(user);
            Stage stage = new Stage();
            stage.setTitle(username);
            stage.setScene(new Scene(mainParent));

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    mainController.logout();
                    System.exit(0);
                }
            });
            mainController.setCurse();
            mainController.setParticip();
            stage.show();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }catch (ConcursException ex){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Authentication failure");
            alert.setContentText("Wrong username or password");
            alert.showAndWait();
        }
    }

}
