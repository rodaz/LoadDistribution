package Diit.EVM.controllers;

import Diit.EVM.models.DbWorker;
import Diit.EVM.objects.Discipline;
import Diit.EVM.objects.LearningYear;
import Diit.EVM.util.Convert;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by Alexey on 22.03.2016.
 */
public class AddDiscControl {

    @FXML
    private ChoiceBox<LearningYear> choYear;
    @FXML
    private TextField fldDisc;
    @FXML
    private TextField fldGroup;
    @FXML
    private TextField fldLect;
    @FXML
    private TextField fldLab;
    @FXML
    private TextField fldPracW;
    @FXML
    private TextField fldCour;
    @FXML
    private TextField fldRev;
    @FXML
    private TextField fldCons;
    @FXML
    private TextField fldCred;
    @FXML
    private TextField fldExam;
    @FXML
    private TextField fldPrac;
    @FXML
    private TextField fldThes;
    @FXML
    private TextField fldGrad;
    @FXML
    private TextField fldInd;
    @FXML
    private TextField fldMod;
    @FXML
    private TextField fldRe;

    private Stage addDiscStage;
    private DbWorker dbWorker = DbWorker.getInstance();

    public void setAddDiscStage(Stage addDiscStage) {
        this.addDiscStage = addDiscStage;
    }

    public void setYear(LearningYear selectedYear){
        choYear.setItems(dbWorker.learningYears);
        choYear.setValue(selectedYear);
    }

    public void addNewDisc(){
        LearningYear learningYear = (LearningYear) choYear.getSelectionModel().getSelectedItem();
        Discipline discipline = new Discipline( 0,
            learningYear.getLearningYearId(),
            fldDisc.getText(),
            fldGroup.getText(),
            Convert.rend(fldLect.getText()),
            Convert.rend(fldLab.getText()),
            Convert.rend(fldPracW.getText()),
            Convert.rend(fldCons.getText()),
            Convert.rend(fldCour.getText()),
            Convert.rend(fldRev.getText()),
            Convert.rend(fldCred.getText()),
            Convert.rend(fldExam.getText()),
            Convert.rend(fldPrac.getText()),
            Convert.rend(fldThes.getText()),
            Convert.rend(fldGrad.getText()),
            Convert.rend(fldInd.getText()),
            Convert.rend(fldMod.getText()),
            0,
            fldRe.getText()
        );
        dbWorker.addDiscToDB(discipline);
        cancelNewDisc();
    }

    public void cancelNewDisc(){
        addDiscStage.close();
    }

}
