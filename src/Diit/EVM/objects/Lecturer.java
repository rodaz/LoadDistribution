package Diit.EVM.objects;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Alexey on 03.03.2016.
 */
public class Lecturer {
    private SimpleIntegerProperty lecturerId;
    private SimpleIntegerProperty learningYearId;
    private SimpleStringProperty lecturerName;
    private SimpleDoubleProperty lecturerRate;
    private SimpleStringProperty rank;
    private SimpleIntegerProperty hours;
    private SimpleIntegerProperty totalHours;
    private SimpleIntegerProperty hoursLeft;
    private SimpleStringProperty remark;

    public Lecturer(int lecturerId, int learningYearId, String lecturerName, double lecturerRate, String rank,
                    int hours, int totalHours, int hoursLeft, String remark){
        this.lecturerId = new SimpleIntegerProperty(lecturerId);
        this.learningYearId = new SimpleIntegerProperty(learningYearId);
        this.lecturerName = new SimpleStringProperty(lecturerName);
        this.lecturerRate = new SimpleDoubleProperty(lecturerRate);
        this.rank = new SimpleStringProperty(rank);
        this.hours = new SimpleIntegerProperty(hours);
        this.totalHours = new SimpleIntegerProperty(totalHours);
        this.hoursLeft = new SimpleIntegerProperty(hoursLeft);
        this.remark = new SimpleStringProperty(remark);
    }

    public int getLecturerId() {
        return lecturerId.get();
    }

    public SimpleIntegerProperty lecturerIdProperty() {
        return lecturerId;
    }

    public void setLecturerId(int lecturerId) {
        this.lecturerId.set(lecturerId);
    }

    public int getLearningYearId() {
        return learningYearId.get();
    }

    public SimpleIntegerProperty learningYearIdProperty() {
        return learningYearId;
    }

    public void setLearningYearId(int learningYearId) {
        this.learningYearId.set(learningYearId);
    }

    public String getLecturerName() {
        return lecturerName.get();
    }

    public SimpleStringProperty lecturerNameProperty() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName.set(lecturerName);
    }

    public double getLecturerRate() {
        return lecturerRate.get();
    }

    public SimpleDoubleProperty lecturerRateProperty() {
        return lecturerRate;
    }

    public void setLecturerRate(double lecturerRate) {
        this.lecturerRate.set(lecturerRate);
    }

    public String getRank() {
        return rank.get();
    }

    public SimpleStringProperty rankProperty() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank.set(rank);
    }

    public int getHours() {
        return hours.get();
    }

    public SimpleIntegerProperty hoursProperty() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours.set(hours);
    }

    public int getTotalHours() {
        return totalHours.get();
    }

    public SimpleIntegerProperty totalHoursProperty() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours.set(totalHours);
    }

    public int getHoursLeft() {
        return hoursLeft.get();
    }

    public SimpleIntegerProperty hoursLeftProperty() {
        return hoursLeft;
    }

    public void setHoursLeft(int hoursLeft) {
        this.hoursLeft.set(hoursLeft);
    }

    public String getRemark() {
        return remark.get();
    }

    public SimpleStringProperty remarkProperty() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark.set(remark);
    }

    @Override
    public String toString() {
        return  getLecturerName();
    }
}
