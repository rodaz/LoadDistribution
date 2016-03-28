package Diit.EVM.models;

import Diit.EVM.objects.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Alexey on 03.03.2016.
 */
public final class DbWorker {

    private static DbWorker instance;

    public Connection conn;
    private Statement stmt;
    private ResultSet res;
    private String query;

    public ObservableList<Lecturer> lecturers = FXCollections.observableArrayList();
    public ObservableList<Discipline> disciplines = FXCollections.observableArrayList();
    public ObservableList<LecturersLoad> lecturersLoads = FXCollections.observableArrayList();
    public ObservableList<LearningYear> learningYears = FXCollections.observableArrayList();
    public ObservableList<String> ranks = FXCollections.observableArrayList("Ассистент","Ст. преподаватель",
            "Доцент","Профессор","Зав. кафедры");
    public List<UserAuth> userAuthList = new ArrayList<>();

    private DbWorker(){
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:lib/lload.db");
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DbWorker getInstance(){
        if (instance == null){
            instance = new DbWorker();
        }
        return instance;
    }
    /**
     * Дисциплины достаем с БД
    */
    public void getDisciplinesFromDB(LearningYear learningYear){
        try {
            stmt = conn.createStatement();
            res = stmt.executeQuery("SELECT * FROM Discipline WHERE Discipline.learnYearId = " + learningYear.getLearningYearId());
            disciplines.clear();
            while (res.next()){
                disciplines.add(new Discipline(res.getInt(1), res.getInt(2), res.getString(3), res.getInt(4),
                        res.getInt(5), res.getInt(6), res.getInt(7), res.getInt(8), res.getInt(9), res.getInt(10), res.getInt(11),
                        res.getInt(12), res.getInt(13), res.getInt(14), res.getInt(15), res.getInt(16), res.getInt(17), res.getString(18)));
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try { res.close();} catch (SQLException e) {e.printStackTrace();}
            try { stmt.close();} catch (SQLException e) {e.printStackTrace();}
        }
    }

    public void getLecturersFromDB() {
        try {
            stmt = conn.createStatement();
            res = stmt.executeQuery("SELECT * FROM Lecturer");
            lecturers.clear();
            while (res.next()){
                lecturers.add(new Lecturer(res.getInt(1), res.getInt(2), res.getString(3), res.getDouble(4),
                        res.getString(5), res.getString(6)));
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try { res.close();} catch (SQLException e) {e.printStackTrace();}
            try { stmt.close();} catch (SQLException e) {e.printStackTrace();}
        }
    }

    public void getLecturersLoadFromDB(Lecturer selectedLecturer, LearningYear selectedYear) {
        try {
            stmt = conn.createStatement();
            res = stmt.executeQuery("SELECT * FROM LecturersLoad JOIN Discipline ON LecturersLoad.discipId=Discipline.discipId "+
                                "WHERE LecturersLoad.lectId = "+selectedLecturer.getLecturerId()+" AND LecturersLoad.learnYearId="+
                                selectedYear.getLearningYearId());
            lecturersLoads.clear();
            while (res.next()){
                lecturersLoads.add(new LecturersLoad(res.getInt(1), res.getInt(2), res.getInt(3), null, res.getInt(4),
                        res.getString(21), res.getInt(5), res.getInt(6), res.getInt(7), res.getInt(8), res.getInt(9),
                        res.getInt(10), res.getInt(11), res.getInt(12), res.getInt(13), res.getInt(14), res.getInt(15),
                        res.getInt(16), res.getInt(17), res.getString(18)));
            }
        } catch (SQLException e) {e.printStackTrace();}
        finally {
            try { res.close();} catch (SQLException e){e.printStackTrace();}
            try { stmt.close();} catch (SQLException e){e.printStackTrace();}
        }
    }

    public void getLecturersLoadFromDB(Discipline selectedDiscipline, LearningYear selectedYear) {
        try {
            stmt = conn.createStatement();
            res = stmt.executeQuery("SELECT * FROM LecturersLoad JOIN Lecturer ON LecturersLoad.lectId=Lecturer.lectId "+
                    "WHERE LecturersLoad.discipId = "+selectedDiscipline.getDisciplineId()+" AND LecturersLoad.learnYearId="+
                    selectedYear.getLearningYearId());
            lecturersLoads.clear();
            while (res.next()){
                lecturersLoads.add(new LecturersLoad(res.getInt(1), res.getInt(2), res.getInt(3), res.getString(21), res.getInt(4),
                        null, res.getInt(5), res.getInt(6), res.getInt(7), res.getInt(8), res.getInt(9),
                        res.getInt(10), res.getInt(11), res.getInt(12), res.getInt(13), res.getInt(14), res.getInt(15),
                        res.getInt(16), res.getInt(17), res.getString(18)));
            }
        } catch (SQLException e) {e.printStackTrace();}
        finally {
            try { res.close();} catch (SQLException e){e.printStackTrace();}
            try { stmt.close();} catch (SQLException e){e.printStackTrace();}
        }
    }

    public void getUsersFromDB(){
        try {
            stmt = conn.createStatement();
            res = stmt.executeQuery("SELECT * FROM UserAuth");
            userAuthList.clear();
            while (res.next()){
                userAuthList.add(new UserAuth(res.getString(2), res.getString(3), res.getString(4)));
            }
        } catch (SQLException e) {e.printStackTrace();}
        finally {
            try { res.close();} catch (SQLException e){e.printStackTrace();}
            try { stmt.close();} catch (SQLException e){e.printStackTrace();}
        }
    }

    public void getLearningYearsFromDB(){
        try {
            stmt = conn.createStatement();
            res = stmt.executeQuery("SELECT * FROM LearningYear");
            learningYears.clear();
            while (res.next()){
                learningYears.add(new LearningYear(res.getInt(1), res.getString(2), res.getInt(3), res.getInt(4),
                        res.getInt(5), res.getInt(6), res.getInt(7), res.getInt(8), res.getString(9), res.getString(10)));
            }
        } catch (SQLException e) {e.printStackTrace();}
        finally {
            try { res.close();} catch (SQLException e){e.printStackTrace();}
            try { stmt.close();} catch (SQLException e){e.printStackTrace();}
        }
    }

    public void addYearToDB(LearningYear newYear){
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO LearningYear (interval, quanRate, hourAssist, hourSenior, hourDocent, hourProf,"+
                    " hourChief, loadData, remark) VALUES ('"+newYear.getInterval()+"' , "+newYear.getQuanRate()+" , "+
                    newYear.getHourAssistant()+" , "+newYear.getHourSenior()+" , "+newYear.getHourDocent()+" , "+
                    newYear.getHourProfessor()+" , "+newYear.getHourChief()+" , "+newYear.getLoadData()+" , '"+
                    newYear.getRemark()+"' )");
            conn.commit();
        } catch (SQLException e) {e.printStackTrace();}
        finally {
            try { res.close();} catch (SQLException e){e.printStackTrace();}
            try { stmt.close();} catch (SQLException e){e.printStackTrace();}
        }
    }

    public void addDiscToDB(Discipline newDisc){
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO Discipline (learnYearId, discipName, numGroup, hourLect, hourLab, hourPracW,"+
                    " hourCons, hourCour, hourRev, hourCred, hourExam, hourPrac, hourThes, hourGrad, hourInd, hourMod, remark)"+
                    " VALUES ("+newDisc.getLearningYearId()+" , '"+newDisc.getDisciplineName()+"' , "+newDisc.getNumGroup()+" , "+
                    newDisc.getHourLect()+" , "+newDisc.getHourLab()+" , "+newDisc.getHourPracW()+" , "+newDisc.getHourCons()+
                    " , "+newDisc.getHourCour()+" , "+newDisc.getHourRev()+" , "+newDisc.getHourCred()+" , "+newDisc.getHourExam()+
                    " , "+newDisc.getHourPrac()+" , "+newDisc.getHourThes()+" , "+newDisc.getHourGrad()+" , "+
                    newDisc.getHourInd()+" , "+newDisc.getHourMod()+" , '"+newDisc.getRemark()+"' )");
            conn.commit();
        } catch (SQLException e) {e.printStackTrace();}
        finally {
            try { res.close();} catch (SQLException e){e.printStackTrace();}
            try { stmt.close();} catch (SQLException e){e.printStackTrace();}
        }
    }

    public void delYearFromDB(LearningYear delYear){
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM LearningYear WHERE learnYearId="+delYear.getLearningYearId());
            conn.commit();
        } catch (SQLException e) {e.printStackTrace();}
        finally {
            try { res.close();} catch (SQLException e){e.printStackTrace();}
            try { stmt.close();} catch (SQLException e){e.printStackTrace();}
        }
    }

    public void delDiscFromDB(Discipline delDisc){
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM Discipline WHERE discipId="+delDisc.getDisciplineId());
            conn.commit();
        } catch (SQLException e) {e.printStackTrace();}
        finally {
            try { res.close();} catch (SQLException e){e.printStackTrace();}
            try { stmt.close();} catch (SQLException e){e.printStackTrace();}
        }
    }

    public void addLoadToDB(LecturersLoad load) {
        try {
            stmt = conn.createStatement();
            query = "INSERT INTO LecturersLoad (learnYearId, lectId, discipId, hourLect, hourLab, hourPracW,"+
                    " hourCons, hourCour, hourRev, hourCred, hourExam, hourPrac, hourThes, hourGrad, hourInd, hourMod, remark)"+
                    " VALUES ("+load.getLearningYearId()+" , "+load.getLecturerId()+" , "+load.getDisciplineId()+" , "+
                    load.getHourLect()+" , "+load.getHourLab()+" , "+load.getHourPracW()+" , "+load.getHourCons()+
                    " , "+load.getHourCour()+" , "+load.getHourRev()+" , "+load.getHourCred()+" , "+load.getHourExam()+
                    " , "+load.getHourPrac()+" , "+load.getHourThes()+" , "+load.getHourGrad()+" , "+
                    load.getHourInd()+" , "+load.getHourMod()+" , '"+load.getRemark()+"' )";
            stmt.executeUpdate(query);
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(query);
        }
        finally {
            try { res.close();} catch (SQLException e){e.printStackTrace();}
            try { stmt.close();} catch (SQLException e){e.printStackTrace();}
        }
    }

    public void delLoadFromDB(LecturersLoad load){
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM LecturersLoad WHERE lectLoadId="+load.getLecturersLoadId());
            conn.commit();
        } catch (SQLException e) {e.printStackTrace();}
        finally {
            try { res.close();} catch (SQLException e){e.printStackTrace();}
            try { stmt.close();} catch (SQLException e){e.printStackTrace();}
        }
    }

    public void addLectToDB(Lecturer lecturer){
        try {
            stmt = conn.createStatement();
            query = "INSERT INTO Lecturer (learnYearId, lectName, lectRate, rank, remark)"+
                    " VALUES ("+lecturer.getLearningYearId()+" , '"+lecturer.getLecturerName()+"' , "+lecturer.getLecturerRate()+
                    " , '"+lecturer.getRank()+"' , '"+lecturer.getRemark()+"' )";
            stmt.executeUpdate(query);
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(query);
        }
        finally {
            try { res.close();} catch (SQLException e){e.printStackTrace();}
            try { stmt.close();} catch (SQLException e){e.printStackTrace();}
        }
    }

    public void delLectFromDB(Lecturer lecturer){
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM Lecturer WHERE lectId="+lecturer.getLecturerId());
            conn.commit();
        } catch (SQLException e) {e.printStackTrace();}
        finally {
            try { res.close();} catch (SQLException e){e.printStackTrace();}
            try { stmt.close();} catch (SQLException e){e.printStackTrace();}
        }
    }

    public void updateYearInDB(LearningYear newYear) {
        try {
            stmt = conn.createStatement();
            query = "UPDATE LearningYear SET interval = '"+newYear.getInterval()+"' , quanRate = "+newYear.getQuanRate()+
                    " , hourAssist = "+newYear.getHourAssistant()+" , hourSenior = "+newYear.getHourSenior()+" , hourDocent = " +
                    +newYear.getHourDocent()+", hourProf = "+newYear.getHourProfessor()+" , hourChief = "+newYear.getHourChief()+
                    " , loadData = "+newYear.getLoadData()+", remark = '"+newYear.getLoadData()+
                    "' WHERE learnYearId="+newYear.getLearningYearId();
            stmt.executeUpdate(query);
            conn.commit();
        } catch (SQLException e) {e.printStackTrace();}
        finally {
            try { res.close();} catch (SQLException e){e.printStackTrace();}
            try { stmt.close();} catch (SQLException e){e.printStackTrace();}
        }
    }
}
