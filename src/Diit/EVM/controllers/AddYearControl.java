package Diit.EVM.controllers;

import Diit.EVM.models.DbWorker;
import Diit.EVM.objects.LearningYear;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
              Double.parseDouble(fldQuanRate.getText()),
              Integer.parseInt(fldAssist.getText()),
              Integer.parseInt(fldSenior.getText()),
              Integer.parseInt(fldDocent.getText()),
              Integer.parseInt(fldProf.getText()),
              Integer.parseInt(fldChief.getText()),
              bool.toString(),
              fldRe.getText()
      );
      dbWorker.addYearToDB(newYear);
      dbWorker.learningYears.add(newYear);
      cancelNewYear();
    }

    public void cancelNewYear(){
        addYearStage.close();
    }

}
