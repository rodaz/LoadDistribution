package Diit.EVM.controllers;

import Diit.EVM.models.DbWorker;
import Diit.EVM.objects.UserAuth;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by Alexey on 15.03.2016.
 */
public class AuthControl {

    @FXML
    TextField fldLog;
    @FXML
    TextField fldPsw;

    private DbWorker dbWorker = DbWorker.getInstance();

    private Stage mainStage;

    public Stage getMainStage() {
        return mainStage;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void cancelAuth() {
        getMainStage().close();
    }

    public void enterAuth() {
        dbWorker.getUsersFromDB();

        for (UserAuth user : dbWorker.userAuthList){
            if ((fldLog.getText().equals(user.getLogin()))&&(fldPsw.getText().equals(user.getPsw()))) {
                System.out.println("Welcome, "+user.getName());
            }
            else {
                System.out.println("Not Correct");
            }
        }
    }
}
