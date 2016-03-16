package Diit.EVM.controllers;

import Diit.EVM.models.DbWorker;
import Diit.EVM.objects.Discipline;
import Diit.EVM.objects.Lecturer;
import Diit.EVM.objects.LecturersLoad;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
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
    @FXML
    private Label lblUserName;

    TableView<Lecturer> tableOfLecturers = new TableView<>();
    TableView<Discipline> tableOfDisciplines = new TableView<>();
    TableView<LecturersLoad> tableOfLoad = new TableView<>();
    ListView<Lecturer> listOfLecturers = new ListView<>();
    ListView<Discipline> listOfDisciplines = new ListView<>();

    private DbWorker dbWorker = DbWorker.getInstance();
    private Stage mainStage;
    private Lecturer selectedLecturer;
    private Discipline selectedDiscipline;
    private String userName;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setMainStage(Stage mainStage) {this.mainStage = mainStage;}

    @FXML
    private void initialize(){
        //dbWorker.getLecturersLoadFromDB();
        initListeners();
        /**
         * Имя лектора отображаем.
        */
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lblUserName.setText(userName);
            }
        });
        Cell<String> cell = new Cell<>();
        cell.getItem();

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
    /**
     * Отображает лист со всеми лекторами.
     */
    public void viewLect(ActionEvent actionEvent){
        dbWorker.getLecturersFromDB();  //считывает базу данных лекторов
        listOfLecturers.setItems(dbWorker.lecturers);   //помещает список лекторов в лист
        pane.setCenter(listOfLecturers);    //лист устанавливаем в корневой контейнер
        btnLect.setVisible(true);   //отображаем кнопку выбора лектора
        btnDisc.setVisible(false);  //скрываем кнопку выбора дисциплины
    }
    /**
     * Отображает лист со всеми дисциплинами
     */
    public void viewDisc(ActionEvent actionEvent){
        dbWorker.getDisciplinesFromDB();
        listOfDisciplines.setItems(dbWorker.disciplines);
        pane.setCenter(listOfDisciplines);
        btnDisc.setVisible(true);
        btnLect.setVisible(false);
    }
    /**
     * Обработчик нажатия на кнопку выбора лектора
     * todo NPE(NullPointerException)
     */
    public void choiseLect(ActionEvent actionEvent){
        selectedLecturer = listOfLecturers.getSelectionModel().getSelectedItem(); //определяем выбранного лектора
        hasSelectLect();
    }
    /**
     * Обработчик нажатия на кнопку выбора дисциплины
     * todo NPE
     */
    public void choiseDisc(ActionEvent actionEvent){
        selectedDiscipline = listOfDisciplines.getSelectionModel().getSelectedItem();
        hasSelectDisc();
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
    /**
     * Отображение таблицы нагрузки лекторов
     */
    private void hasSelectLect() {
        /* Собственно, раньше отображало таблицу с всеми дисциплинами
        dbWorker.getDisciplinesFromDB();
        if (tableOfDisciplines.getItems().size() == 0) {
            createTableOfDisciplines();
        }
        pane.setCenter(tableOfDisciplines);
        */
        dbWorker.getLecturersLoadFromDB(selectedLecturer); // нагрузка с базы данных по выбранному лектору
        if (tableOfLoad.getItems().size() == 0){ //если не создана ещё таблица нагрузки
            createTableOfLoad(); //создаем таблицу нагрузки
        }
        pane.setCenter(tableOfLoad); //таблицу нагрузки помещаем в корневой контейнер
        btnLect.setVisible(false); //скрываем кнопку выбора
    }

    /**
     * Отображение таблицы всех лекторов(пока всех, в будущем только имеющих выбранную дисциплину)
     */
    private void hasSelectDisc(){
        dbWorker.getLecturersFromDB();
        if (tableOfLecturers.getItems().size() == 0){
            createTableOfLecturers();
        }
        pane.setCenter(tableOfLecturers);
        btnDisc.setVisible(false);  //скрываем кнопку выбора
    }
    /**
     * Создание таблицы лекторов. Только для теста.
     */
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
    /**
     * @deprecated Тут создаем таблицу дисциплин.
     */
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
    /**
     * Создание таблицы нагрузки
     */
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
}
