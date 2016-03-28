package Diit.EVM.controllers;

import Diit.EVM.models.DbWorker;
import Diit.EVM.objects.Discipline;
import Diit.EVM.objects.LearningYear;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by Alexey on 22.03.2016.
 */
public class AddDiscControl {

    @FXML
    private ChoiceBox choYear;
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

    @FXML
    public void initialize(){
        choYear.setItems(dbWorker.learningYears);
    }

    public void addNewDisc(){
        LearningYear learningYear = (LearningYear) choYear.getSelectionModel().getSelectedItem();
        Discipline discipline = new Discipline( 0,
            learningYear.getLearningYearId(),
            fldDisc.getText(),
            Integer.parseInt(fldGroup.getText()),
            Integer.parseInt(fldLect.getText()),
            Integer.parseInt(fldLab.getText()),
            Integer.parseInt(fldPracW.getText()),
            Integer.parseInt(fldCons.getText()),
            Integer.parseInt(fldCour.getText()),
            Integer.parseInt(fldRev.getText()),
            Integer.parseInt(fldCred.getText()),
            Integer.parseInt(fldExam.getText()),
            Integer.parseInt(fldPrac.getText()),
            Integer.parseInt(fldThes.getText()),
            Integer.parseInt(fldGrad.getText()),
            Integer.parseInt(fldInd.getText()),
            Integer.parseInt(fldMod.getText()),
            fldRe.getText()
        );
        dbWorker.addDiscToDB(discipline);
        dbWorker.disciplines.add(discipline);
        cancelNewDisc();
    }

    public void cancelNewDisc(){
        addDiscStage.close();
    }

}
