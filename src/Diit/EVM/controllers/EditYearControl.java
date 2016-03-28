package Diit.EVM.controllers;

import Diit.EVM.models.DbWorker;
import Diit.EVM.objects.LearningYear;
import Diit.EVM.util.Convert;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by Alexey on 28.03.2016.
 */
public class EditYearControl {
    @FXML
    private TextField fldInterval;
    @FXML
    private TextField fldQuanRate;
    @FXML
    private TextField fldAssist;
    @FXML
    private TextField fldSenior;
    @FXML
    private TextField fldDocent;
    @FXML
    private TextField fldProf;
    @FXML
    private TextField fldChief;
    @FXML
    private TextField fldRe;
    @FXML
    private CheckBox chLoad;

    private Stage editStage;
    private DbWorker dbWorker = DbWorker.getInstance();
    private LearningYear learningYear;

    public void setLearningYear(LearningYear learningYear) {
        this.learningYear = learningYear;
    }

    public void setEditStage(Stage editStage) {
        this.editStage = editStage;
    }

    @FXML
    public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                fldInterval.setText(learningYear.getInterval());
                fldQuanRate.setText(String.valueOf(learningYear.getQuanRate()));
                fldAssist.setText(String.valueOf(learningYear.getHourAssistant()));
                fldSenior.setText(String.valueOf(learningYear.getHourSenior()));
                fldDocent.setText(String.valueOf(learningYear.getHourDocent()));
                fldProf.setText(String.valueOf(learningYear.getHourProfessor()));
                fldChief.setText(String.valueOf(learningYear.getHourChief()));
                fldRe.setText(learningYear.getRemark());
                if (learningYear.getLoadData() != null){
                    chLoad.setSelected(true);
                }
            }
        });

    }

    public void editYear(){
        Boolean bool = chLoad.isSelected();
        LearningYear upYear = new LearningYear(0,
                fldInterval.getText(),
                Convert.rendD(fldQuanRate.getText()),
                Convert.rend(fldAssist.getText()),
                Convert.rend(fldSenior.getText()),
                Convert.rend(fldDocent.getText()),
                Convert.rend(fldProf.getText()),
                Convert.rend(fldChief.getText()),
                bool.toString(),
                fldRe.getText()
        );
        dbWorker.updateYearInDB(upYear);
        cancelEditYear();
    }

    public void cancelEditYear(){
        editStage.close();
    }
}
