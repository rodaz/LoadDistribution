package Diit.EVM;

import Diit.EVM.controllers.MainControl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        fxmlLoader.setLocation(getClass().getResource("view/main.fxml"));
        Parent fxmlMain = fxmlLoader.load();
        MainControl mainControl = fxmlLoader.getController();
        mainControl.setMainStage(primaryStage);
        mainControl.closeConn();

        primaryStage.setTitle("DIIT");
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(450);
        primaryStage.setScene(new Scene(fxmlMain, 500, 330));
        primaryStage.show();
    }
}
