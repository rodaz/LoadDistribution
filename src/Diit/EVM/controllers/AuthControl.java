package Diit.EVM.controllers;

import Diit.EVM.models.DbWorker;
import Diit.EVM.objects.UserAuth;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
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
     * Если ввод правильный вызывает {@link #createMain(String)}
     */
    public void enterAuth() {
        dbWorker.getUsersFromDB();

        for (UserAuth user : dbWorker.userAuthList){
            if ((fldLog.getText().equals(user.getLogin()))&&(fldPsw.getText().equals(user.getPsw()))) {
                createMain(user.getName());
            //break;
            }
        }
    }
    /**
     * Создает главное окно c контроллером {@link MainControl}
     */
    private void createMain(String userName) {
        mainStage = new Stage();
        try {
            fxmlLoader.setLocation(getClass().getResource("../view/main.fxml"));
            fxmlMain = fxmlLoader.load();
            mainControl = fxmlLoader.getController();
        } catch (IOException e) {e.printStackTrace();}
        mainControl.setMainStage(mainStage);
        mainControl.setUserName(userName);
        mainControl.closeConn();
        mainStage.setTitle("EVM");
        mainStage.setScene(new Scene(fxmlMain, 500, 330));
        mainStage.show();
        authStage.close();
    }
}
