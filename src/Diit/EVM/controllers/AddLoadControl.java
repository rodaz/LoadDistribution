package Diit.EVM.controllers;

import Diit.EVM.models.DbWorker;
import Diit.EVM.objects.Discipline;
import Diit.EVM.objects.LearningYear;
import Diit.EVM.objects.Lecturer;
import Diit.EVM.objects.LecturersLoad;
import Diit.EVM.util.Convert;
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
        if (dbWorker.lecturers.size() == 0){
            dbWorker.getLecturersFromDB();  //считываем лекторов что-бы выбирать в ChoiceBox-e
        }
        choYear.setItems(dbWorker.learningYears);
        choDisc.setItems(dbWorker.disciplines);
        choLect.setItems(dbWorker.lecturers);
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
                fldRe.getText()
        );
        dbWorker.addLoadToDB(load);
        dbWorker.lecturersLoads.add(load);
        cancelNewLoad();
    }

    public void cancelNewLoad(){
        addLoadStage.close();
    }
}
