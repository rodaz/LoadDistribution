package main.java.EVM.controllers;

import main.java.EVM.models.DbWorker;
import main.java.EVM.objects.Discipline;
import main.java.EVM.objects.LearningYear;
import main.java.EVM.objects.Lecturer;
import main.java.EVM.objects.LecturersLoad;
import main.java.EVM.util.Convert;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

/**
 * Created by Alexey on 21.03.2016.
 */
public class AddYearControl {
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

    private Stage addYearStage;
    private DbWorker dbWorker = DbWorker.getInstance();

    public void setAddYearStage(Stage addYearStage) {
        this.addYearStage = addYearStage;
    }

    public void addNewYear(){
      Boolean bool = chLoad.isSelected();
      LearningYear newYear = new LearningYear(0,
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
      dbWorker.addYearToDB(newYear);
      if (bool.equals(true)){
          copyFromLastYear();
      }
      cancelNewYear();
    }

    private void copyFromLastYear(){
        dbWorker.getLearningYearsFromDB();
        List years = dbWorker.learningYears;
        int size = years.size();
        if (size <= 1){
            return;
        }
        LearningYear lastYear = (LearningYear) years.get(size-2);
        LearningYear actYear = (LearningYear) years.get(size-1);
        dbWorker.getLecturersFromDB(lastYear);
        int sizeLect = dbWorker.lecturers.size();
        for (int i = 0; i < sizeLect; i++){
            Lecturer lect = dbWorker.lecturers.get(i);
            lect.setLearningYearId(actYear.getLearningYearId());
            dbWorker.addLectToDB(lect);
        }
        dbWorker.getDisciplinesFromDB(lastYear);
        int sizeDisc = dbWorker.disciplines.size();
        for (int i = 0; i < sizeDisc; i++){
            Discipline disc = dbWorker.disciplines.get(i);
            disc.setLearningYearId(actYear.getLearningYearId());
            dbWorker.addDiscToDB(disc);
        }
        dbWorker.getLecturersLoadFromDB(lastYear);
        int sizeLoad = dbWorker.lecturersLoads.size();
        for (int i = 0; i < sizeLoad; i++){
            LecturersLoad load = dbWorker.lecturersLoads.get(i);
            load.setLearningYearId(actYear.getLearningYearId());
            dbWorker.addLoadToDB(load);
        }
    }

    public void cancelNewYear(){
        addYearStage.close();
    }

}
