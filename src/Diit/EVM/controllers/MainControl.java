package Diit.EVM.controllers;

import Diit.EVM.models.DbWorker;
import Diit.EVM.objects.Discipline;
import Diit.EVM.objects.Lecturer;
import Diit.EVM.objects.LecturersLoad;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;

import java.sql.SQLException;

/**
 * Created by Alexey on 02.03.2016.
 */
public class MainControl {
    @FXML
    private BorderPane pane;
    @FXML
    private Button btnLect;
    @FXML
    private Button btnDisc;
    @FXML
    private Pane pnHome;

    TableView<Lecturer> tableOfLecturers = new TableView<>();
    TableView<Discipline> tableOfDisciplines = new TableView<>();
    TableView<LecturersLoad> tableOfLoad = new TableView<>();
    ListView<Lecturer> listOfLecturers = new ListView<>();
    ListView<Discipline> listOfDisciplines = new ListView<>();

    private DbWorker dbWorker = DbWorker.getInstance();
    private Stage mainStage;
    private Lecturer selectedLecturer;
    private Discipline selectedDiscipline;
    private Button btnGoHome = new Button();

    public void setMainStage(Stage mainStage) {this.mainStage = mainStage;}

    @FXML
    private void initialize(){
        //dbWorker.getLecturersLoadFromDB();
        initListeners();
    }

    StringConverter<Integer> sc = new StringConverter<Integer>() {
        @Override
        public String toString(Integer t) {
            return t == null ? null : t.toString();
        }

        @Override
        public Integer fromString(String string) {
            return Integer.parseInt(string);
        }
    };

    public void viewLect(ActionEvent actionEvent){
        dbWorker.getLecturersFromDB();
        listOfLecturers.setItems(dbWorker.lecturers);
        pane.setCenter(listOfLecturers);
        btnLect.setVisible(true);
        btnDisc.setVisible(false);
        pane.setBottom(btnGoHome);
        btnGoHome.setText("<---");
    }

    public void viewDisc(ActionEvent actionEvent){
        dbWorker.getDisciplinesFromDB();
        listOfDisciplines.setItems(dbWorker.disciplines);
        pane.setCenter(listOfDisciplines);
        btnDisc.setVisible(true);
        btnLect.setVisible(false);
        pane.setBottom(btnGoHome);
    }

    public void choiseLect(ActionEvent actionEvent){
        selectedLecturer = listOfLecturers.getSelectionModel().getSelectedItem();
        hasSelectLect();
    }

    public void choiseDisc(ActionEvent actionEvent){
        selectedDiscipline = listOfDisciplines.getSelectionModel().getSelectedItem();
        hasSelectDisc();
    }

    private void initListeners(){
        listOfLecturers.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2){
                    selectedLecturer = listOfLecturers.getSelectionModel().getSelectedItem();
                    hasSelectLect();
                }
            }
        });
        listOfDisciplines.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2){
                    selectedDiscipline = listOfDisciplines.getSelectionModel().getSelectedItem();
                    hasSelectDisc();
                }
            }
        });

    }

    private void hasSelectLect() {
        /*dbWorker.getDisciplinesFromDB();
        if (tableOfDisciplines.getItems().size() == 0) {
            createTableOfDisciplines();
        }
        pane.setCenter(tableOfDisciplines);
        */
        dbWorker.getLecturersLoadFromDB(selectedLecturer);
        if (tableOfLoad.getItems().size() == 0){
            createTableOfLoad();
        }
        pane.setCenter(tableOfLoad);
    }

    private void hasSelectDisc(){
        dbWorker.getLecturersFromDB();
        if (tableOfLecturers.getItems().size() == 0){
            createTableOfLecturers();
        }
        pane.setCenter(tableOfLecturers);
    }

    private void createTableOfLecturers() {
        TableColumn<Lecturer, String> lecturerNameCol = new TableColumn<>("ФИО");
        lecturerNameCol.setCellValueFactory(new PropertyValueFactory("lecturerName"));
        TableColumn<Lecturer, Double> lecturerRateCol = new TableColumn<>("Ставка");
        lecturerRateCol.setCellValueFactory(new PropertyValueFactory("lecturerRate"));
        TableColumn<Lecturer, String> rankCol = new TableColumn<>("Статус");
        rankCol.setCellValueFactory(new PropertyValueFactory("rank"));
        TableColumn<Lecturer, String> remarkCol = new TableColumn<>("Заметка");
        remarkCol.setCellValueFactory(new PropertyValueFactory("remark"));

        tableOfLecturers.setItems(dbWorker.lecturers);

        tableOfLecturers.getColumns().setAll(lecturerNameCol, lecturerRateCol, rankCol, remarkCol);
        //tableOfLecturers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void createTableOfDisciplines(){
        TableColumn<Discipline, String> disciplineNameCol = new TableColumn<>("Название");
        disciplineNameCol.setCellValueFactory(new PropertyValueFactory("disciplineName"));
        TableColumn<Discipline, Integer> numGroupCol = new TableColumn<>("Группа");
        numGroupCol.setCellValueFactory(new PropertyValueFactory("numGroup"));
        TableColumn<Discipline, Integer> hourLectCol = new TableColumn<>("Лекции");
        hourLectCol.setCellValueFactory(new PropertyValueFactory("hourLect"));
        TableColumn<Discipline, Integer> hourLabCol = new TableColumn<>("Лаб. раб.");
        hourLabCol.setCellValueFactory(new PropertyValueFactory("hourLab"));
        TableColumn<Discipline, Integer> hourPracWCol = new TableColumn<>("Практ. раб.");
        hourPracWCol.setCellValueFactory(new PropertyValueFactory("hourPracW"));
        TableColumn<Discipline, Integer> hourConsCol = new TableColumn<>("Консул.");
        hourConsCol.setCellValueFactory(new PropertyValueFactory("hourCons"));
        TableColumn<Discipline, Integer> hourCourCol = new TableColumn<>("Курс. пр.");
        hourCourCol.setCellValueFactory(new PropertyValueFactory("hourCour"));
        TableColumn<Discipline, Integer> hourRevCol = new TableColumn<>("Рец. раб.");
        hourRevCol.setCellValueFactory(new PropertyValueFactory("hourRev"));
        TableColumn<Discipline, Integer> hourCredCol = new TableColumn<>("Зачет");
        hourCredCol.setCellValueFactory(new PropertyValueFactory("hourCred"));
        TableColumn<Discipline, Integer> hourExamCol = new TableColumn<>("Экзамен");
        hourExamCol.setCellValueFactory(new PropertyValueFactory("hourExam"));
        TableColumn<Discipline, Integer> hourPracCol = new TableColumn<>("Практика");
        hourPracCol.setCellValueFactory(new PropertyValueFactory("hourPrac"));
        TableColumn<Discipline, Integer> hourThesCol = new TableColumn<>("Диплом и ДЕК");
        hourThesCol.setCellValueFactory(new PropertyValueFactory("hourThes"));
        TableColumn<Discipline, Integer> hourGradCol = new TableColumn<>("Аспирант.");
        hourGradCol.setCellValueFactory(new PropertyValueFactory("hourGrad"));
        TableColumn<Discipline, Integer> hourIndCol = new TableColumn<>("Инд.");
        hourIndCol.setCellValueFactory(new PropertyValueFactory("hourInd"));
        TableColumn<Discipline, Integer> hourModCol = new TableColumn<>("Модуль");
        hourModCol.setCellValueFactory(new PropertyValueFactory("hourMod"));
        TableColumn<Discipline, String> remarkCol = new TableColumn<>("Заметка");
        remarkCol.setCellValueFactory(new PropertyValueFactory("remark"));

        tableOfDisciplines.setItems(dbWorker.disciplines);
        tableOfDisciplines.getColumns().setAll(disciplineNameCol, numGroupCol, hourLectCol, hourLabCol, hourPracWCol,
                hourConsCol, hourCourCol, hourRevCol, hourCredCol, hourExamCol, hourPracCol, hourThesCol, hourGradCol,
                hourIndCol, hourModCol, remarkCol);
        //tableOfDisciplines.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void createTableOfLoad(){

        //Table Column create ----------------------------------------------------------------
        TableColumn<LecturersLoad, String> disciplineNameCol = new TableColumn<>("Название");
        disciplineNameCol.setCellValueFactory(new PropertyValueFactory("disciplineName"));
        disciplineNameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<LecturersLoad, Integer> hourLectCol = new TableColumn<>("Лекции");
        hourLectCol.setCellValueFactory(new PropertyValueFactory("hourLect"));
        hourLectCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));

        TableColumn<LecturersLoad, Integer> hourLabCol = new TableColumn<>("Лаб. раб.");
        hourLabCol.setCellValueFactory(new PropertyValueFactory("hourLab"));
        hourLabCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));

        TableColumn<LecturersLoad, Integer> hourPracWCol = new TableColumn<>("Практ. раб.");
        hourPracWCol.setCellValueFactory(new PropertyValueFactory("hourPracW"));
        hourPracWCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));

        TableColumn<LecturersLoad, Integer> hourConsCol = new TableColumn<>("Консул.");
        hourConsCol.setCellValueFactory(new PropertyValueFactory("hourCons"));
        hourConsCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));

        TableColumn<LecturersLoad, Integer> hourCourCol = new TableColumn<>("Курс. пр.");
        hourCourCol.setCellValueFactory(new PropertyValueFactory("hourCour"));
        hourCourCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));

        TableColumn<LecturersLoad, Integer> hourRevCol = new TableColumn<>("Рец. раб.");
        hourRevCol.setCellValueFactory(new PropertyValueFactory("hourRev"));
        hourRevCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));

        TableColumn<LecturersLoad, Integer> hourCredCol = new TableColumn<>("Зачет");
        hourCredCol.setCellValueFactory(new PropertyValueFactory("hourCred"));
        hourCredCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));

        TableColumn<LecturersLoad, Integer> hourExamCol = new TableColumn<>("Экзамен");
        hourExamCol.setCellValueFactory(new PropertyValueFactory("hourExam"));
        hourExamCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));

        TableColumn<LecturersLoad, Integer> hourPracCol = new TableColumn<>("Практика");
        hourPracCol.setCellValueFactory(new PropertyValueFactory("hourPrac"));
        hourPracCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));

        TableColumn<LecturersLoad, Integer> hourThesCol = new TableColumn<>("Диплом и ДЕК");
        hourThesCol.setCellValueFactory(new PropertyValueFactory("hourThes"));
        hourThesCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));

        TableColumn<LecturersLoad, Integer> hourGradCol = new TableColumn<>("Аспирант.");
        hourGradCol.setCellValueFactory(new PropertyValueFactory("hourGrad"));
        hourGradCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));

        TableColumn<LecturersLoad, Integer> hourIndCol = new TableColumn<>("Инд.");
        hourIndCol.setCellValueFactory(new PropertyValueFactory("hourInd"));
        hourIndCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));

        TableColumn<LecturersLoad, Integer> hourModCol = new TableColumn<>("Модуль");
        hourModCol.setCellValueFactory(new PropertyValueFactory("hourMod"));
        hourModCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));

        TableColumn<LecturersLoad, String> remarkCol = new TableColumn<>("Заметка");
        remarkCol.setCellValueFactory(new PropertyValueFactory("remark"));
        remarkCol.setCellFactory(TextFieldTableCell.forTableColumn());
        //------------------------------------------------------------------------------

        tableOfLoad.setItems(dbWorker.lecturersLoads);
        tableOfLoad.setEditable(true);
        tableOfLoad.getColumns().setAll(disciplineNameCol, hourLectCol, hourLabCol, hourPracWCol,
                hourConsCol, hourCourCol, hourRevCol, hourCredCol, hourExamCol, hourPracCol, hourThesCol, hourGradCol,
                hourIndCol, hourModCol, remarkCol);

    }

    public void closeConn() {
        mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    dbWorker.conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
