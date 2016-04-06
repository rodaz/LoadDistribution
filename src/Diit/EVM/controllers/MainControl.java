package Diit.EVM.controllers;

import Diit.EVM.models.DbWorker;
import Diit.EVM.objects.Discipline;
import Diit.EVM.objects.LearningYear;
import Diit.EVM.objects.Lecturer;
import Diit.EVM.objects.LecturersLoad;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Alexey on 02.03.2016.
 */
public class MainControl {
    @FXML
    private BorderPane pane;
    @FXML
    private Pane pnHome;
    @FXML
    private ListView<LearningYear> listOfLearningYears;
    @FXML
    private MenuBar mnMain;
    @FXML
    private Button btnAddYear;
    @FXML
    private Button btnAddDisc;
    @FXML
    private Button btnAddLect;
    @FXML
    private Button btnAddLoad;
    @FXML
    private Button btnSaveYear;
    @FXML
    private Button btnSaveDisc;
    @FXML
    private Button btnSaveLect;
    @FXML
    private Button btnSaveLoad;
    @FXML
    private Button btnDelYear;
    @FXML
    private Button btnDelDisc;
    @FXML
    private Button btnDelLect;
    @FXML
    private Button btnDelLoad;
    @FXML
    private Button btnRest;

    TableView<Lecturer> tableOfLecturers = new TableView<>();
    TableView<Discipline> tableOfDisciplines = new TableView<>();
    TableView<LecturersLoad> tableOfLoadLect = new TableView<>();
    TableView<LecturersLoad> tableOfLoadDisc = new TableView<>();
    ListView<Lecturer> listOfLecturers = new ListView<>();
    ListView<Discipline> listOfDisciplines = new ListView<>();

    private DbWorker dbWorker = DbWorker.getInstance();
    private Stage mainStage;
    private Lecturer selectedLecturer;
    private Discipline selectedDiscipline;
    private LearningYear selectedYear;
    private String userName;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setMainStage(Stage mainStage) {this.mainStage = mainStage;}

    @FXML
    private void initialize(){
        hideAllButtons();   //скрываем все кнопки
        viewYears();    //отображаем список годов
        initListeners();    //инициализация слушателей

        Cell<String> cell = new Cell<>();
        cell.getItem();

    }

    /**
     * Скрываем все кнопки
    */
    public void hideAllButtons(){
        btnAddDisc.setVisible(false);
        btnAddYear.setVisible(false);
        btnAddLect.setVisible(false);
        btnAddLoad.setVisible(false);
        btnSaveDisc.setVisible(false);
        btnSaveLect.setVisible(false);
        btnSaveLoad.setVisible(false);
        btnSaveYear.setVisible(false);
        btnDelDisc.setVisible(false);
        btnDelLect.setVisible(false);
        btnDelLoad.setVisible(false);
        btnDelYear.setVisible(false);
    }

    /**
     * Отображает лист со всеми учебными годами
    */
    private void viewYears() {
        dbWorker.getLearningYearsFromDB();
        listOfLearningYears.setItems(dbWorker.learningYears);
        btnAddYear.setVisible(true);
        btnSaveYear.setVisible(true);
        btnDelYear.setVisible(true);
    }

    /**
     * Анонимный вызов абстрактного класса.
     * Для возможности редактирования данных прямо в таблице.
     */
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
    StringConverter<Double> dsc = new StringConverter<Double>() {
        @Override
        public String toString(Double t) {
            return t == null ? null : t.toString();
        }
        @Override
        public Double fromString(String string) {
            return Double.parseDouble(string);
        }
    };
    /**
     * Отображает лист со всеми лекторами.
     */
    public void viewLect(){
        mainStage.setTitle(selectedYear.getInterval());
        dbWorker.getLecturersFromDB();  //считывает базу данных лекторов
        listOfLecturers.setItems(dbWorker.lecturers);   //помещает список лекторов в лист
        pane.setCenter(listOfLecturers);    //лист устанавливаем в корневой контейнер
        hideAllButtons();
    }
    /**
     * Отображает лист с дисциплинами за выбранный учебный год
     */
    public void viewDisc(){
        mainStage.setTitle(selectedYear.getInterval());
        dbWorker.getDisciplinesFromDB(selectedYear);
        listOfDisciplines.setItems(dbWorker.disciplines);
        pane.setCenter(listOfDisciplines);
        hideAllButtons();
    }

    /**
     * Установка слушателей на двойной клик по елементу листа
     * todo НПЕ при двойном клике на пустую строку
     */
    private void initListeners(){
        listOfLecturers.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2){
                    selectedLecturer = listOfLecturers.getSelectionModel().getSelectedItem();
                    tableViewLoadForLecturer();
                    mainStage.setTitle(selectedYear+" "+selectedLecturer);
                }
            }
        });
        listOfDisciplines.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2){
                    selectedDiscipline = listOfDisciplines.getSelectionModel().getSelectedItem();
                    tableViewLoadForDiscipline();
                    mainStage.setTitle(selectedYear+" "+selectedDiscipline);
                }
            }
        });
        listOfLearningYears.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2){
                    selectedYear = listOfLearningYears.getSelectionModel().getSelectedItem();
                    viewDisc();
                    mnMain.setDisable(false);
                    btnRest.setDisable(false);
                    mainStage.setTitle(selectedYear.toString());
                    hideAllButtons();
                }
            }
        });

    }

    public  void restart(){
        AuthControl authControl = new AuthControl();
        authControl.createMain(userName);
        mainStage.close();
    }

    /**
     * Отображение таблицы дисциплин
     */
    public void tableViewDisciplines() {
        mainStage.setTitle(selectedYear.getInterval());
        dbWorker.getDisciplinesFromDB(selectedYear);
        if (tableOfDisciplines.getItems().size() == 0){
            createTableOfDisciplines();
        }
        pane.setCenter(tableOfDisciplines);
        hideAllButtons();
        btnAddDisc.setVisible(true);
        btnSaveDisc.setVisible(true);
        btnDelDisc.setVisible(true);
    }

    /**
     * Отображение таблицы нагрузки лекторов
     */
    public void tableViewLoadForLecturer() {
        dbWorker.getLecturersLoadFromDB(selectedLecturer, selectedYear); // нагрузка с базы данных по выбранному лектору
        if (tableOfLoadLect.getItems().size() == 0){ //если не создана ещё таблица нагрузки
            createTableOfLoadLect(); //создаем таблицу нагрузки
        }
        pane.setCenter(tableOfLoadLect); //таблицу нагрузки помещаем в корневой контейнер
        hideAllButtons();
        showLoadButtons();
    }

    /**
     * Отображение таблицы всех лекторов
     */
    public void tableViewLecturers(){
        mainStage.setTitle(selectedYear.getInterval());
        dbWorker.getLecturersFromDB();
        if (tableOfLecturers.getItems().size() == 0){
            createTableOfLecturers();
        }
        pane.setCenter(tableOfLecturers);
        hideAllButtons();
        btnAddLect.setVisible(true);
        btnSaveLect.setVisible(true);
        btnDelLect.setVisible(true);
    }

    /**
     * Отображение нагрузки лекторов по определённому предмету
    */
    public void tableViewLoadForDiscipline(){
        dbWorker.getLecturersLoadFromDB(selectedDiscipline, selectedYear);
        if (tableOfLoadDisc.getItems().size() == 0){
            createTableOfLoadDisc();
        }
        pane.setCenter(tableOfLoadDisc);
        hideAllButtons();
        showLoadButtons();
    }
    /**
     * Отображение кнопок нагрузки.
     * Вынесены в отдельный метод так как используются два раза.
    */
    private void showLoadButtons(){
        btnAddLoad.setVisible(true);
        btnSaveLoad.setVisible(true);
        btnDelLoad.setVisible(true);
    }

    /**
     * Создание таблицы лекторов.
     */
    private void createTableOfLecturers() {
        TableColumn<Lecturer, String> lecturerNameCol = new TableColumn<>("ФИО");
        lecturerNameCol.setCellValueFactory(new PropertyValueFactory("lecturerName"));
        lecturerNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        TableColumn<Lecturer, Double> lecturerRateCol = new TableColumn<>("Ставка");
        lecturerRateCol.setCellValueFactory(new PropertyValueFactory("lecturerRate"));
        lecturerRateCol.setCellFactory(TextFieldTableCell.forTableColumn(dsc));
        TableColumn<Lecturer, String> rankCol = new TableColumn<>("Статус");
        rankCol.setCellValueFactory(new PropertyValueFactory("rank"));
        rankCol.setCellFactory(TextFieldTableCell.forTableColumn());
        TableColumn<Lecturer, Integer> hoursCol = new TableColumn<>("Часы");
        hoursCol.setCellValueFactory(new PropertyValueFactory("hours"));
        hoursCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));
        TableColumn<Lecturer, Integer> totalHoursCol = new TableColumn<>("Нужно часов");
        totalHoursCol.setCellValueFactory(new PropertyValueFactory("totalHours"));
        totalHoursCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));
        TableColumn<Lecturer, String> remarkCol = new TableColumn<>("Заметка");
        remarkCol.setCellValueFactory(new PropertyValueFactory("remark"));
        remarkCol.setCellFactory(TextFieldTableCell.forTableColumn());

        tableOfLecturers.setItems(dbWorker.lecturers);
        tableOfLecturers.setEditable(true);
        tableOfLecturers.getColumns().setAll(lecturerNameCol, lecturerRateCol, rankCol, hoursCol, totalHoursCol, remarkCol);
    }
    /**
     * Тут создаем таблицу дисциплин.
     */
    private void createTableOfDisciplines(){
        TableColumn<Discipline, String> disciplineNameCol = new TableColumn<>("Название");
        disciplineNameCol.setCellValueFactory(new PropertyValueFactory("disciplineName"));
        disciplineNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        TableColumn<Discipline, Integer> numGroupCol = new TableColumn<>("Группа");
        numGroupCol.setCellValueFactory(new PropertyValueFactory("numGroup"));
        numGroupCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));
        TableColumn<Discipline, Integer> hourLectCol = new TableColumn<>("Лекции");
        hourLectCol.setCellValueFactory(new PropertyValueFactory("hourLect"));
        hourLectCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));
        TableColumn<Discipline, Integer> hourLabCol = new TableColumn<>("Лаб. раб.");
        hourLabCol.setCellValueFactory(new PropertyValueFactory("hourLab"));
        hourLabCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));
        TableColumn<Discipline, Integer> hourPracWCol = new TableColumn<>("Практ. раб.");
        hourPracWCol.setCellValueFactory(new PropertyValueFactory("hourPracW"));
        hourPracWCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));
        TableColumn<Discipline, Integer> hourConsCol = new TableColumn<>("Консул.");
        hourConsCol.setCellValueFactory(new PropertyValueFactory("hourCons"));
        hourConsCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));
        TableColumn<Discipline, Integer> hourCourCol = new TableColumn<>("Курс. пр.");
        hourCourCol.setCellValueFactory(new PropertyValueFactory("hourCour"));
        hourCourCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));
        TableColumn<Discipline, Integer> hourRevCol = new TableColumn<>("Рец. раб.");
        hourRevCol.setCellValueFactory(new PropertyValueFactory("hourRev"));
        hourRevCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));
        TableColumn<Discipline, Integer> hourCredCol = new TableColumn<>("Зачет");
        hourCredCol.setCellValueFactory(new PropertyValueFactory("hourCred"));
        hourCredCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));
        TableColumn<Discipline, Integer> hourExamCol = new TableColumn<>("Экзамен");
        hourExamCol.setCellValueFactory(new PropertyValueFactory("hourExam"));
        hourExamCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));
        TableColumn<Discipline, Integer> hourPracCol = new TableColumn<>("Практика");
        hourPracCol.setCellValueFactory(new PropertyValueFactory("hourPrac"));
        hourPracCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));
        TableColumn<Discipline, Integer> hourThesCol = new TableColumn<>("Диплом и ДЕК");
        hourThesCol.setCellValueFactory(new PropertyValueFactory("hourThes"));
        hourThesCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));
        TableColumn<Discipline, Integer> hourGradCol = new TableColumn<>("Аспирант.");
        hourGradCol.setCellValueFactory(new PropertyValueFactory("hourGrad"));
        hourGradCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));
        TableColumn<Discipline, Integer> hourIndCol = new TableColumn<>("Инд.");
        hourIndCol.setCellValueFactory(new PropertyValueFactory("hourInd"));
        hourIndCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));
        TableColumn<Discipline, Integer> hourModCol = new TableColumn<>("Модуль");
        hourModCol.setCellValueFactory(new PropertyValueFactory("hourMod"));
        hourModCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));
        TableColumn<Discipline, Integer> totalCol = new TableColumn<>("Итого");
        totalCol.setCellValueFactory(new PropertyValueFactory("total"));
        totalCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));
        TableColumn<Discipline, String> remarkCol = new TableColumn<>("Заметка");
        remarkCol.setCellValueFactory(new PropertyValueFactory("remark"));
        remarkCol.setCellFactory(TextFieldTableCell.forTableColumn());

        tableOfDisciplines.setItems(dbWorker.disciplines);
        tableOfDisciplines.setEditable(true);
        tableOfDisciplines.getColumns().setAll(disciplineNameCol, numGroupCol, hourLectCol, hourLabCol, hourPracWCol,
                hourConsCol, hourCourCol, hourRevCol, hourCredCol, hourExamCol, hourPracCol, hourThesCol, hourGradCol,
                hourIndCol, hourModCol, totalCol, remarkCol);
    }
    /**
     * Создание таблицы нагрузки преподавателей
     */
    private void createTableOfLoadLect(){

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

        TableColumn<LecturersLoad, Integer> totalCol = new TableColumn<>("Итого");
        totalCol.setCellValueFactory(new PropertyValueFactory("total"));
        totalCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));

        TableColumn<LecturersLoad, String> remarkCol = new TableColumn<>("Заметка");
        remarkCol.setCellValueFactory(new PropertyValueFactory("remark"));
        remarkCol.setCellFactory(TextFieldTableCell.forTableColumn());

        tableOfLoadLect.setItems(dbWorker.lecturersLoads);
        tableOfLoadLect.setEditable(true);
        tableOfLoadLect.getColumns().setAll(disciplineNameCol, hourLectCol, hourLabCol, hourPracWCol,
                hourConsCol, hourCourCol, hourRevCol, hourCredCol, hourExamCol, hourPracCol, hourThesCol, hourGradCol,
                hourIndCol, hourModCol, totalCol, remarkCol);
    }
    /**
     * Создание таблицы нагрузки дисциплин
    */
    private void createTableOfLoadDisc(){
        TableColumn<LecturersLoad, String> lecturerNameCol = new TableColumn<>("Преподаватель");
        lecturerNameCol.setCellValueFactory(new PropertyValueFactory("lecturerName"));
        lecturerNameCol.setCellFactory(TextFieldTableCell.forTableColumn());

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

        TableColumn<LecturersLoad, Integer> totalCol = new TableColumn<>("Итого");
        totalCol.setCellValueFactory(new PropertyValueFactory("total"));
        totalCol.setCellFactory(TextFieldTableCell.forTableColumn(sc));

        TableColumn<LecturersLoad, String> remarkCol = new TableColumn<>("Заметка");
        remarkCol.setCellValueFactory(new PropertyValueFactory("remark"));
        remarkCol.setCellFactory(TextFieldTableCell.forTableColumn());
        //------------------------------------------------------------------------------

        tableOfLoadDisc.setItems(dbWorker.lecturersLoads);
        tableOfLoadDisc.setEditable(true);
        tableOfLoadDisc.getColumns().setAll(lecturerNameCol, hourLectCol, hourLabCol, hourPracWCol,
                hourConsCol, hourCourCol, hourRevCol, hourCredCol, hourExamCol, hourPracCol, hourThesCol, hourGradCol,
                hourIndCol, hourModCol, totalCol, remarkCol);
    }
    /**
     * Вешаем слушатель закрытия окна.
     * Что-бы при закрытии закрывалось соединение с БД.
     * Отдельно от других слушателей из-за NullPointerExcept.
     */
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

    //***********************************************************************************************************
    //Обработка нереального количества кнопок
    //***********************************************************************************************************

    public void btnAddYearAction() {
        Stage addYearStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent fxmlAddYear = null;
        AddYearControl addYearControl = null;
        try {
            fxmlLoader.setLocation(getClass().getResource("../view/addYear.fxml"));
            fxmlAddYear = fxmlLoader.load();
            addYearControl = fxmlLoader.getController();
        } catch (IOException e) {e.printStackTrace();}
        addYearControl.setAddYearStage(addYearStage);
        addYearStage.setTitle("Добавить");
        addYearStage.setScene(new Scene(fxmlAddYear, 400, 350));
        addYearStage.setResizable(false);
        addYearStage.initModality(Modality.WINDOW_MODAL);
        addYearStage.initOwner(mainStage);
        addYearStage.showAndWait();
    }

    public void btnAddDiscAction() {
        Stage addDiscStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent fxmlAddDisc = null;
        AddDiscControl addDiscControl = null;
        try {
            fxmlLoader.setLocation(getClass().getResource("../view/addDisc.fxml"));
            fxmlAddDisc = fxmlLoader.load();
            addDiscControl = fxmlLoader.getController();
        } catch (IOException e) {e.printStackTrace();}
        addDiscControl.setAddDiscStage(addDiscStage);
        addDiscStage.setTitle("Добавить");
        addDiscStage.setScene(new Scene(fxmlAddDisc, 700, 400));
        addDiscStage.setResizable(false);
        addDiscStage.initModality(Modality.WINDOW_MODAL);
        addDiscStage.initOwner(mainStage);
        addDiscStage.showAndWait();
        tableViewDisciplines();
        tableOfDisciplines.refresh();
    }

    public void btnAddLectAction() {
        Stage addLectStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent fxmlAddLect = null;
        AddLectControl addLectControl = null;
        try {
            fxmlLoader.setLocation(getClass().getResource("../view/addLect.fxml"));
            fxmlAddLect = fxmlLoader.load();
            addLectControl = fxmlLoader.getController();
        } catch (IOException e) {e.printStackTrace();}
        addLectControl.setAddLectStage(addLectStage);
        addLectStage.setTitle("Добавить");
        addLectStage.setScene(new Scene(fxmlAddLect, 400, 350));
        addLectStage.setResizable(false);
        addLectStage.initModality(Modality.WINDOW_MODAL);
        addLectStage.initOwner(mainStage);
        addLectStage.showAndWait();
        tableViewLecturers();
        tableOfLecturers.refresh();
    }

    public void btnAddLoadAction() {
        Stage addLoadStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent fxmlAddLoad = null;
        AddLoadControl addLoadControl = null;
        try {
            fxmlLoader.setLocation(getClass().getResource("../view/addLoad.fxml"));
            fxmlAddLoad = fxmlLoader.load();
            addLoadControl = fxmlLoader.getController();
        } catch (IOException e) {e.printStackTrace();}
        addLoadControl.setAddLoadStage(addLoadStage);
        addLoadStage.setTitle("Добавить");
        if (dbWorker.disciplines.size() == 0){
            dbWorker.getDisciplinesFromDB(selectedYear);    //считываем список дисциплин, что-бы в ChoiceBox-e выбирать
        }
        addLoadStage.setScene(new Scene(fxmlAddLoad, 700, 400));
        addLoadStage.setResizable(false);
        addLoadStage.initModality(Modality.WINDOW_MODAL);
        addLoadStage.initOwner(mainStage);
        addLoadStage.showAndWait();
    }

    public void btnSaveYearAction() {
        Stage editStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent fxmlEditYear = null;
        EditYearControl editYearControl = null;
        try {
            fxmlLoader.setLocation(getClass().getResource("../view/editYear.fxml"));
            fxmlEditYear = fxmlLoader.load();
            editYearControl = fxmlLoader.getController();
        } catch (IOException e) {e.printStackTrace();}
        editYearControl.setEditStage(editStage);
        editYearControl.setLearningYear(listOfLearningYears.getSelectionModel().getSelectedItem());
        editStage.setTitle("Редактировать");
        editStage.setScene(new Scene(fxmlEditYear, 400, 350));
        editStage.setResizable(false);
        editStage.initModality(Modality.WINDOW_MODAL);
        editStage.initOwner(mainStage);
        editStage.showAndWait();
    }

    public void btnSaveDiscAction() {
        for (Discipline disc: dbWorker.disciplines) {
            dbWorker.updateDiscInDB(disc);
        }
    }

    public void btnSaveLectAction() {
        for (Lecturer lect: dbWorker.lecturers){
            dbWorker.updateLectInDB(lect);
        }
    }

    public void btnSaveLoadAction() {
        for (LecturersLoad load: dbWorker.lecturersLoads){
            dbWorker.updateLoadInDB(load);
        }
    }

    public void btnDelYearAction() {
        LearningYear year = listOfLearningYears.getSelectionModel().getSelectedItem();
        dbWorker.delYearFromDB(year);
    }

    public void btnDelDiscAction() {
        Discipline disc = tableOfDisciplines.getSelectionModel().getSelectedItem();
        dbWorker.delDiscFromDB(disc);
    }

    public void btnDelLectAction() {
        Lecturer lect = tableOfLecturers.getSelectionModel().getSelectedItem();
        dbWorker.delLectFromDB(lect);
    }

    public void btnDelLoadAction() {
        LecturersLoad load = tableOfLoadDisc.getSelectionModel().getSelectedItem();
        if (load == null){
            load = tableOfLoadLect.getSelectionModel().getSelectedItem();
        }
        dbWorker.delLoadFromDB(load);
    }
}