package Diit.EVM.controllers;

import Diit.EVM.models.DbWorker;
import Diit.EVM.objects.UserAuth;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by Alexey on 02.05.2016.
 */
public class AddUserControl {
    @FXML
    private TextField fldName;
    @FXML
    private TextField fldLog;
    @FXML
    private TextField fldPsw;

    private Stage addUserStage;
    private DbWorker dbWorker = DbWorker.getInstance();

    public void setAddUserStage(Stage addUserStage) {
        this.addUserStage = addUserStage;
    }

    public void addNewUser(){
        UserAuth newUser = new UserAuth(0,
                fldName.getText(),
                fldLog.getText(),
                fldPsw.getText()
        );
        dbWorker.addUserToDB(newUser);
        cancelNewUser();
    }

    public void cancelNewUser(){
        addUserStage.close();
    }
}
