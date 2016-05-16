package main.java.EVM.objects;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Alexey on 04.03.2016.
 */
public class LearningYear {
    private SimpleIntegerProperty learningYearId;
    private SimpleStringProperty interval;
    private SimpleDoubleProperty quanRate;
    private SimpleIntegerProperty hourAssistant;
    private SimpleIntegerProperty hourSenior;
    private SimpleIntegerProperty hourDocent;
    private SimpleIntegerProperty hourProfessor;
    private SimpleIntegerProperty hourChief;
    private SimpleStringProperty loadData;
    private SimpleStringProperty remark;

    public LearningYear(int learningYearId, String interval, double quanRate, int hourAssistant, int hourSenior,
                    int hourDocent, int hourProfessor, int hourChief, String loadData, String remark){
        this.learningYearId = new SimpleIntegerProperty(learningYearId);
        this.interval = new SimpleStringProperty(interval);
        this.quanRate = new SimpleDoubleProperty(quanRate);
        this.hourAssistant = new SimpleIntegerProperty(hourAssistant);
        this.hourSenior = new SimpleIntegerProperty(hourSenior);
        this.hourDocent = new SimpleIntegerProperty(hourDocent);
        this.hourProfessor = new SimpleIntegerProperty(hourProfessor);
        this.hourChief = new SimpleIntegerProperty(hourChief);
        this.loadData = new SimpleStringProperty(loadData);
        this.remark = new SimpleStringProperty(remark);
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

    public String getInterval() {
        return interval.get();
    }

    public SimpleStringProperty intervalProperty() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval.set(interval);
    }

    public double getQuanRate() {
        return quanRate.get();
    }

    public SimpleDoubleProperty quanRateProperty() {
        return quanRate;
    }

    public void setQuanRate(double quanRate) {
        this.quanRate.set(quanRate);
    }

    public int getHourAssistant() {
        return hourAssistant.get();
    }

    public SimpleIntegerProperty hourAssistantProperty() {
        return hourAssistant;
    }

    public void setHourAssistant(int hourAssistant) {
        this.hourAssistant.set(hourAssistant);
    }

    public int getHourSenior() {
        return hourSenior.get();
    }

    public SimpleIntegerProperty hourSeniorProperty() {
        return hourSenior;
    }

    public void setHourSenior(int hourSenior) {
        this.hourSenior.set(hourSenior);
    }

    public int getHourDocent() {
        return hourDocent.get();
    }

    public SimpleIntegerProperty hourDocentProperty() {
        return hourDocent;
    }

    public void setHourDocent(int hourDocent) {
        this.hourDocent.set(hourDocent);
    }

    public int getHourProfessor() {
        return hourProfessor.get();
    }

    public SimpleIntegerProperty hourProfessorProperty() {
        return hourProfessor;
    }

    public void setHourProfessor(int hourProfessor) {
        this.hourProfessor.set(hourProfessor);
    }

    public int getHourChief() {
        return hourChief.get();
    }

    public SimpleIntegerProperty hourChiefProperty() {
        return hourChief;
    }

    public void setHourChief(int hourChief) {
        this.hourChief.set(hourChief);
    }

    public String getLoadData() {
        return loadData.get();
    }

    public SimpleStringProperty loadDataProperty() {
        return loadData;
    }

    public void setLoadData(String loadData) {
        this.loadData.set(loadData);
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
        return getInterval();
    }
}
