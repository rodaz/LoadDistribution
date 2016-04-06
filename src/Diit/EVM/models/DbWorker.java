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
                        res.getInt(12), res.getInt(13), res.getInt(14), res.getInt(15), res.getInt(16), res.getInt(17), res.getInt(18), res.getString(19)));
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
                        res.getString(5), res.getInt(6), res.getInt(7), res.getString(8)));
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
                        res.getString(22), res.getInt(5), res.getInt(6), res.getInt(7), res.getInt(8), res.getInt(9),
                        res.getInt(10), res.getInt(11), res.getInt(12), res.getInt(13), res.getInt(14), res.getInt(15),
                        res.getInt(16), res.getInt(17), res.getInt(18), res.getString(19)));
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
                lecturersLoads.add(new LecturersLoad(res.getInt(1), res.getInt(2), res.getInt(3), res.getString(22), res.getInt(4),
                        null, res.getInt(5), res.getInt(6), res.getInt(7), res.getInt(8), res.getInt(9),
                        res.getInt(10), res.getInt(11), res.getInt(12), res.getInt(13), res.getInt(14), res.getInt(15),
                        res.getInt(16), res.getInt(17), res.getInt(18), res.getString(19)));
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

    public void addYearToDB(LearningYear year){
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO LearningYear (interval, quanRate, hourAssist, hourSenior, hourDocent, hourProf,"+
                    " hourChief, loadData, remark) VALUES ('"+year.getInterval()+"' , "+year.getQuanRate()+" , "+
                    year.getHourAssistant()+" , "+year.getHourSenior()+" , "+year.getHourDocent()+" , "+
                    year.getHourProfessor()+" , "+year.getHourChief()+" , "+year.getLoadData()+" , '"+
                    year.getRemark()+"' )");
            learningYears.add(year);
            conn.commit();
        } catch (SQLException e) {e.printStackTrace();}
        finally {
            try { res.close();} catch (SQLException e){e.printStackTrace();}
            try { stmt.close();} catch (SQLException e){e.printStackTrace();}
        }
    }

    public void addDiscToDB(Discipline discipline){
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO Discipline (learnYearId, discipName, numGroup, hourLect, hourLab, hourPracW,"+
                    " hourCons, hourCour, hourRev, hourCred, hourExam, hourPrac, hourThes, hourGrad, hourInd, hourMod, total, remark)"+
                    " VALUES ("+discipline.getLearningYearId()+" , '"+discipline.getDisciplineName()+"' , "+discipline.getNumGroup()+" , "+
                    discipline.getHourLect()+" , "+discipline.getHourLab()+" , "+discipline.getHourPracW()+" , "+discipline.getHourCons()+
                    " , "+discipline.getHourCour()+" , "+discipline.getHourRev()+" , "+discipline.getHourCred()+" , "+discipline.getHourExam()+
                    " , "+discipline.getHourPrac()+" , "+discipline.getHourThes()+" , "+discipline.getHourGrad()+" , "+
                    discipline.getHourInd()+" , "+discipline.getHourMod()+" , "+discipline.getTotal()+" , '"+discipline.getRemark()+"' )");
            disciplines.add(discipline);
            conn.commit();
        } catch (SQLException e) {e.printStackTrace();}
        finally {
            try { res.close();} catch (SQLException e){e.printStackTrace();}
            try { stmt.close();} catch (SQLException e){e.printStackTrace();}
        }
    }

    public void delYearFromDB(LearningYear year){
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM LearningYear WHERE learnYearId="+year.getLearningYearId());
            learningYears.remove(year);
            conn.commit();
        } catch (SQLException e) {e.printStackTrace();}
        finally {
            try { res.close();} catch (SQLException e){e.printStackTrace();}
            try { stmt.close();} catch (SQLException e){e.printStackTrace();}
        }
    }

    public void delDiscFromDB(Discipline discipline){
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM Discipline WHERE discipId="+discipline.getDisciplineId());
            disciplines.remove(discipline);
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
                    " hourCons, hourCour, hourRev, hourCred, hourExam, hourPrac, hourThes, hourGrad, hourInd, hourMod, total, remark)"+
                    " VALUES ("+load.getLearningYearId()+" , "+load.getLecturerId()+" , "+load.getDisciplineId()+" , "+
                    load.getHourLect()+" , "+load.getHourLab()+" , "+load.getHourPracW()+" , "+load.getHourCons()+
                    " , "+load.getHourCour()+" , "+load.getHourRev()+" , "+load.getHourCred()+" , "+load.getHourExam()+
                    " , "+load.getHourPrac()+" , "+load.getHourThes()+" , "+load.getHourGrad()+" , "+
                    load.getHourInd()+" , "+load.getHourMod()+" , "+load.getTotal()+" , '"+load.getRemark()+"' )";
            stmt.executeUpdate(query);
            lecturersLoads.add(load);
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
            lecturersLoads.remove(load);
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
            query = "INSERT INTO Lecturer (learnYearId, lectName, lectRate, rank, hours, totalHours, remark)"+
                    " VALUES ("+lecturer.getLearningYearId()+" , '"+lecturer.getLecturerName()+"' , "+lecturer.getLecturerRate()+
                    " , '"+lecturer.getRank()+"' , "+lecturer.getHours()+" , "+lecturer.getTotalHours()+" , '"+lecturer.getRemark()+"' )";
            stmt.executeUpdate(query);
            lecturers.add(lecturer);
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
            lecturers.remove(lecturer);
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
                    " , loadData = "+newYear.getLoadData()+", remark = '"+newYear.getRemark()+
                    "' WHERE learnYearId="+newYear.getLearningYearId();
            stmt.executeUpdate(query);
            conn.commit();
        } catch (SQLException e) {e.printStackTrace();}
        finally {
            try { res.close();} catch (SQLException e){e.printStackTrace();}
            try { stmt.close();} catch (SQLException e){e.printStackTrace();}
        }
    }

    public void updateLectInDB(Lecturer lecturer){
        try {
            stmt = conn.createStatement();
            query = "UPDATE Lecturer SET learnYearId = "+lecturer.getLearningYearId()+" , lectName = '"+lecturer.getLecturerName()+
                    "' , lectRate = "+lecturer.getLecturerRate()+" , rank = '"+lecturer.getRank()+"' , hours = " +
                    lecturer.getHours()+" totalHours = "+lecturer.getTotalHours()+" remark = '" +
                    lecturer.getRemark()+"' WHERE lectId="+lecturer.getLecturerId();
            stmt.executeUpdate(query);
            conn.commit();
        } catch (SQLException e) {e.printStackTrace();}
        finally {
            try { res.close();} catch (SQLException e){e.printStackTrace();}
            try { stmt.close();} catch (SQLException e){e.printStackTrace();}
        }
    }

    public void updateDiscInDB(Discipline discipline){
        try {
            stmt = conn.createStatement();
            query = "UPDATE Discipline SET learnYearId = "+discipline.getLearningYearId()+" , discipName = '"+discipline.getDisciplineName()+
                    "' , numGroup = "+discipline.getNumGroup()+" , hourLect = "+discipline.getHourLect()+" ," +
                    " hourLab = "+discipline.getHourLab()+" , hourPracW = "+discipline.getHourPracW()+" , hourCons = "+
                    discipline.getHourCons()+" , hourCour = "+discipline.getHourCour()+" , hourRev = "+discipline.getHourRev()+
                    " , hourCred = "+discipline.getHourCred()+" , hourExam = "+discipline.getHourExam()+" , hourPrac = "+
                    discipline.getHourPrac()+" , hourThes = "+discipline.getHourThes()+" , hourGrad = "+discipline.getHourGrad()+
                    " , hourInd = "+discipline.getHourInd()+" , hourMod = "+discipline.getHourMod()+" , total = "+discipline.getTotal()+
                    " , remark = '" + discipline.getRemark()+"' WHERE discipId="+discipline.getDisciplineId();
            stmt.executeUpdate(query);
            conn.commit();
        } catch (SQLException e) {e.printStackTrace();}
        finally {
            try { res.close();} catch (SQLException e){e.printStackTrace();}
            try { stmt.close();} catch (SQLException e){e.printStackTrace();}
        }
    }

    public void updateLoadInDB(LecturersLoad lecturersLoad){
        try {
            stmt = conn.createStatement();
            query = "UPDATE LecturersLoad SET learnYearId = "+lecturersLoad.getLearningYearId()+" , lectId = "+lecturersLoad.getLecturerId()+
                    " , discipId = "+lecturersLoad.getDisciplineId()+" , hourLect = "+lecturersLoad.getHourLect()+" ," +
                    " hourLab = "+lecturersLoad.getHourLab()+" , hourPracW = "+lecturersLoad.getHourPracW()+" , hourCons = "+
                    lecturersLoad.getHourCons()+" , hourCour = "+lecturersLoad.getHourCour()+" , hourRev = "+lecturersLoad.getHourRev()+
                    " , hourCred = "+lecturersLoad.getHourCred()+" , hourExam = "+lecturersLoad.getHourExam()+" , hourPrac = "+
                    lecturersLoad.getHourPrac()+" , hourThes = "+lecturersLoad.getHourThes()+" , hourGrad = "+lecturersLoad.getHourGrad()+
                    " , hourInd = "+lecturersLoad.getHourInd()+" , hourMod = "+lecturersLoad.getHourMod()+" , total = "+lecturersLoad.getTotal()+
                    " , remark = '" + lecturersLoad.getRemark()+"' WHERE lectLoadId="+lecturersLoad.getLecturersLoadId();
            stmt.executeUpdate(query);
            conn.commit();
        } catch (SQLException e) {e.printStackTrace();}
        finally {
            try { res.close();} catch (SQLException e){e.printStackTrace();}
            try { stmt.close();} catch (SQLException e){e.printStackTrace();}
        }
    }
}
