package Diit.EVM.controllers;

import Diit.EVM.models.DbWorker;
import Diit.EVM.objects.LearningYear;
import Diit.EVM.objects.Lecturer;
import Diit.EVM.util.Convert;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by Alexey on 28.03.2016.
 */
public class AddLectControl {

    @FXML
    private ChoiceBox<LearningYear> choYear;
    @FXML
    private ChoiceBox choRank;
    @FXML
    private TextField fldName;
    @FXML
    private TextField fldRate;
    @FXML
    private TextField fldHours;
    @FXML
    private TextField fldRe;

    private DbWorker dbWorker = DbWorker.getInstance();
    private Stage addLectStage;

    public void setAddLectStage(Stage addLectStage) {
        this.addLectStage = addLectStage;
    }

    @FXML
    public void initialize(){
        choYear.setItems(dbWorker.learningYears);
        choRank.setItems(dbWorker.ranks);
    }

    public void setYear(LearningYear year){
        choYear.setValue(year);
    }

    public void addNewLect(){
        LearningYear year = choYear.getValue();
        String rank = (String) choRank.getSelectionModel().getSelectedItem();
        Lecturer lecturer = new Lecturer(0, year.getLearningYearId(), fldName.getText(), Convert.rendD(fldRate.getText()),
                rank, 0, Convert.rend(fldHours.getText()), 0, fldRe.getText());
        dbWorker.addLectToDB(lecturer);
        cancelNewLect();
    }

    public void cancelNewLect(){
        addLectStage.close();
    }
}
