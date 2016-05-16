package main.java.EVM;

import main.java.EVM.controllers.AuthControl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Created by Alexey on 25.02.2016.
 */
public class Main extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("view/auth.fxml"));
        Parent fxmlAuth = fxmlLoader.load();
        AuthControl authControl = fxmlLoader.getController();
        authControl.setAuthStage(primaryStage);
        primaryStage.setTitle("Авторизация");
        primaryStage.getIcons().add(new Image("file:resources/images/icon.png"));
        primaryStage.setScene(new Scene(fxmlAuth, 360, 195));
        primaryStage.show();
    }
}
