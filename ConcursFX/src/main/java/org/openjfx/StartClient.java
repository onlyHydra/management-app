package org.openjfx;

import concurs.services.IConcursServices;
import controllers.LoginController;
import controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartClient extends Application {
    public void start(Stage stage) throws Exception{
        System.out.println("Starting client...");
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
        IConcursServices server = (IConcursServices)factory.getBean("concursService");
        System.out.println("Obtained reference for server...");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent root = loader.load();
        LoginController loginController = loader.getController();
        loginController.setServer(server);

        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/fxml/mainView.fxml"));
        Parent mainRoot = mainLoader.load();
        MainController mainController = mainLoader.getController();
        mainController.setServer(server);

        loginController.setController(mainController);
        loginController.setParent(mainRoot);


        Scene scene = new Scene(root);

        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop(){
        System.out.println("Cleaning up...");
        System.exit(0);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
