package Diit.EVM.controllers;

import Diit.EVM.models.DbWorker;
import Diit.EVM.objects.UserAuth;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Created by Alexey on 15.03.2016.
 */
public class AuthControl {

    @FXML
    TextField fldLog;
    @FXML
    TextField fldPsw;

    private DbWorker dbWorker = DbWorker.getInstance();
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private Parent fxmlMain;
    private MainControl mainControl;
    private Stage authStage;
    private Stage mainStage;

    public Stage getAuthStage() {
        return authStage;
    }

    public void setAuthStage(Stage mainStage) {
        this.authStage = mainStage;
    }

    @FXML
    private void initialize(){
        /**
         * Фокус на логин.
         * Если вызвать просто в ините, то работать не будет,
         * так как не успевает поле прогрузиться.
        */
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                fldLog.requestFocus();
               // createMain();
            }
        });
    }
    /**
     * Обработчик кнопки Cancel. Закрывает окно.
     */
    public void cancelAuth() {
        getAuthStage().close();
    }
    /**
     * Обработчик кнопки Enter. Проверяет введенные логин и пароль.
     * Если ввод правильный вызывает {@link #createMain()}
     */
    public void enterAuth() {
        dbWorker.getUsersFromDB();

        int sz = dbWorker.userAuthList.size();
        int i = 0;
        for (UserAuth user : dbWorker.userAuthList){
            i = i+1;
            if ((fldLog.getText().equals(user.getLogin()))&&(fldPsw.getText().equals(user.getPsw()))) {
                if (i == 1){
                    createMain();
                } else {
                    createUser(user.getName());
                }

                break;
            } else {
                if (i == sz) {
                Alert alert = new Alert(Alert.AlertType.NONE, "Неправильный логин или пароль", ButtonType.OK);
                alert.setTitle("Ошибка");
                alert.show();
                break;
                }
            }
        }
    }

    private void createUser(String userName) {
        mainStage = new Stage();
        try {
            fxmlLoader.setLocation(getClass().getResource("../view/user.fxml"));
            fxmlMain = fxmlLoader.load();
            mainControl = fxmlLoader.getController();
        } catch (IOException e) {e.printStackTrace();}
        mainControl.setMainStage(mainStage);
        mainControl.closeConn();
        mainControl.initUser(userName);
        mainStage.setTitle("EVM");
        mainStage.getIcons().add(new Image("file:resources/images/icon.png"));
        mainStage.setScene(new Scene(fxmlMain, 600, 400));
        mainStage.show();
        if (authStage != null){
            authStage.close();
        }
    }

    /**
     * Создает главное окно c контроллером {@link MainControl}
     */
    public void createMain() {
        mainStage = new Stage();
        try {
            fxmlLoader.setLocation(getClass().getResource("../view/main.fxml"));
            fxmlMain = fxmlLoader.load();
            mainControl = fxmlLoader.getController();
        } catch (IOException e) {e.printStackTrace();}
        mainControl.setMainStage(mainStage);
        mainControl.closeConn();
        mainControl.init();
        mainStage.setTitle("EVM");
        mainStage.getIcons().add(new Image("file:resources/images/icon.png"));
        mainStage.setScene(new Scene(fxmlMain, 600, 400));
        mainStage.show();
        if (authStage != null){
            authStage.close();
        }
    }
}
