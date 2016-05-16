package main.java.EVM.controllers;

import main.java.EVM.models.DbWorker;
import main.java.EVM.objects.Discipline;
import main.java.EVM.objects.LearningYear;
import main.java.EVM.objects.Lecturer;
import main.java.EVM.objects.LecturersLoad;
import main.java.EVM.util.Convert;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by Alexey on 26.03.2016.
 */
public class AddLoadControl {

    @FXML
    private ChoiceBox<LearningYear> choYear;
    @FXML
    private ChoiceBox<Discipline> choDisc;
    @FXML
    private ChoiceBox<Lecturer> choLect;
    @FXML
    private ChoiceBox choTerm;
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

    private DbWorker dbWorker = DbWorker.getInstance();
    private Stage addLoadStage;

    public void setAddLoadStage(Stage addLoadStage) {
        this.addLoadStage = addLoadStage;
    }

    @FXML
    public void initialize(){
        choYear.setItems(dbWorker.learningYears);
        choDisc.setItems(dbWorker.disciplines);
        choLect.setItems(dbWorker.lecturers);
        choTerm.setItems(FXCollections.observableArrayList(1,2));
    }

    public void setDefault(LearningYear year, Lecturer lect, Discipline disc){
        choYear.setValue(year);
        choLect.setValue(lect);
        choDisc.setValue(disc);
    }

    public void addNewLoad(){
        LearningYear learningYear = choYear.getSelectionModel().getSelectedItem();
        Discipline discipline = choDisc.getSelectionModel().getSelectedItem();
        Lecturer lecturer = choLect.getSelectionModel().getSelectedItem();
        LecturersLoad load = new LecturersLoad( 0,
                learningYear.getLearningYearId(),
                lecturer.getLecturerId(),
                "",
                discipline.getDisciplineId(),
                "",
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
                fldRe.getText(),
                (Integer) choTerm.getSelectionModel().getSelectedItem()
        );
        dbWorker.addLoadToDB(load);
        cancelNewLoad();
    }

    public void cancelNewLoad(){
        addLoadStage.close();
    }
}
