package Diit.EVM.objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Alexey on 12.03.2016.
 */
public class LecturersLoad {
    private SimpleIntegerProperty lecturersLoadId;
    private SimpleIntegerProperty learningYearId;
    private SimpleIntegerProperty lecturerId;
    private SimpleIntegerProperty disciplineId;
    private SimpleStringProperty disciplineName;
    private SimpleIntegerProperty hourLect;
    private SimpleIntegerProperty hourLab;
    private SimpleIntegerProperty hourPracW;
    private SimpleIntegerProperty hourCons;
    private SimpleIntegerProperty hourCour;
    private SimpleIntegerProperty hourRev;
    private SimpleIntegerProperty hourCred;
    private SimpleIntegerProperty hourExam;
    private SimpleIntegerProperty hourPrac;
    private SimpleIntegerProperty hourThes;
    private SimpleIntegerProperty hourGrad;
    private SimpleIntegerProperty hourInd;
    private SimpleIntegerProperty hourMod;
    private SimpleStringProperty remark;

    public LecturersLoad(int lecturersLoadId, int learningYearId, int lecturerId, int disciplineId, String disciplineName,
                         int hourLect, int hourLab, int hourPracW, int hourCons, int hourCour, int hourRev, int hourCred,
                         int hourExam, int hourPrac, int hourThes, int hourGrad, int hourInd, int hourMod, String remark) {
        this.disciplineId = new SimpleIntegerProperty(disciplineId);
        this.learningYearId = new SimpleIntegerProperty(learningYearId);
        this.lecturersLoadId = new SimpleIntegerProperty(lecturersLoadId);
        this.lecturerId = new SimpleIntegerProperty(lecturerId);
        this.disciplineName = new SimpleStringProperty(disciplineName);
        this.hourLect = new SimpleIntegerProperty(hourLect);
        this.hourLab = new SimpleIntegerProperty(hourLab);
        this.hourPracW = new SimpleIntegerProperty(hourPracW);
        this.hourCons = new SimpleIntegerProperty(hourCons);
        this.hourCour = new SimpleIntegerProperty(hourCour);
        this.hourRev = new SimpleIntegerProperty(hourRev);
        this.hourCred = new SimpleIntegerProperty(hourCred);
        this.hourExam = new SimpleIntegerProperty(hourExam);
        this.hourPrac = new SimpleIntegerProperty(hourPrac);
        this.hourThes = new SimpleIntegerProperty(hourThes);
        this.hourGrad = new SimpleIntegerProperty(hourGrad);
        this.hourInd = new SimpleIntegerProperty(hourInd);
        this.hourMod = new SimpleIntegerProperty(hourMod);
        this.remark = new SimpleStringProperty(remark);
    }

    public int getLecturersLoadId() {
        return lecturersLoadId.get();
    }

    public SimpleIntegerProperty lecturersLoadIdProperty() {
        return lecturersLoadId;
    }

    public void setLecturersLoadId(int lecturersLoadId) {
        this.lecturersLoadId.set(lecturersLoadId);
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

    public int getLecturerId() {
        return lecturerId.get();
    }

    public SimpleIntegerProperty lecturerIdProperty() {
        return lecturerId;
    }

    public void setLecturerId(int lecturerId) {
        this.lecturerId.set(lecturerId);
    }

    public int getDisciplineId() {
        return disciplineId.get();
    }

    public SimpleIntegerProperty disciplineIdProperty() {
        return disciplineId;
    }

    public void setDisciplineId(int disciplineId) {
        this.disciplineId.set(disciplineId);
    }

    public int getHourLect() {
        return hourLect.get();
    }

    public SimpleIntegerProperty hourLectProperty() {
        return hourLect;
    }

    public void setHourLect(int hourLect) {
        this.hourLect.set(hourLect);
    }

    public int getHourLab() {
        return hourLab.get();
    }

    public SimpleIntegerProperty hourLabProperty() {
        return hourLab;
    }

    public void setHourLab(int hourLab) {
        this.hourLab.set(hourLab);
    }

    public int getHourPracW() {
        return hourPracW.get();
    }

    public SimpleIntegerProperty hourPracWProperty() {
        return hourPracW;
    }

    public void setHourPracW(int hourPracW) {
        this.hourPracW.set(hourPracW);
    }

    public int getHourCons() {
        return hourCons.get();
    }

    public SimpleIntegerProperty hourConsProperty() {
        return hourCons;
    }

    public void setHourCons(int hourCons) {
        this.hourCons.set(hourCons);
    }

    public int getHourCour() {
        return hourCour.get();
    }

    public SimpleIntegerProperty hourCourProperty() {
        return hourCour;
    }

    public void setHourCour(int hourCour) {
        this.hourCour.set(hourCour);
    }

    public int getHourRev() {
        return hourRev.get();
    }

    public SimpleIntegerProperty hourRevProperty() {
        return hourRev;
    }

    public void setHourRev(int hourRev) {
        this.hourRev.set(hourRev);
    }

    public int getHourCred() {
        return hourCred.get();
    }

    public SimpleIntegerProperty hourCredProperty() {
        return hourCred;
    }

    public void setHourCred(int hourCred) {
        this.hourCred.set(hourCred);
    }

    public int getHourExam() {
        return hourExam.get();
    }

    public SimpleIntegerProperty hourExamProperty() {
        return hourExam;
    }

    public void setHourExam(int hourExam) {
        this.hourExam.set(hourExam);
    }

    public int getHourPrac() {
        return hourPrac.get();
    }

    public SimpleIntegerProperty hourPracProperty() {
        return hourPrac;
    }

    public void setHourPrac(int hourPrac) {
        this.hourPrac.set(hourPrac);
    }

    public int getHourThes() {
        return hourThes.get();
    }

    public SimpleIntegerProperty hourThesProperty() {
        return hourThes;
    }

    public void setHourThes(int hourThes) {
        this.hourThes.set(hourThes);
    }

    public int getHourGrad() {
        return hourGrad.get();
    }

    public SimpleIntegerProperty hourGradProperty() {
        return hourGrad;
    }

    public void setHourGrad(int hourGrad) {
        this.hourGrad.set(hourGrad);
    }

    public int getHourInd() {
        return hourInd.get();
    }

    public SimpleIntegerProperty hourIndProperty() {
        return hourInd;
    }

    public void setHourInd(int hourInd) {
        this.hourInd.set(hourInd);
    }

    public int getHourMod() {
        return hourMod.get();
    }

    public SimpleIntegerProperty hourModProperty() {
        return hourMod;
    }

    public void setHourMod(int hourMod) {
        this.hourMod.set(hourMod);
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

    public String getDisciplineName() {
        return disciplineName.get();
    }

    public SimpleStringProperty disciplineNameProperty() {
        return disciplineName;
    }

    public void setDisciplineName(String disciplineName) {
        this.disciplineName.set(disciplineName);
    }

    @Override
    public String toString() {
        return this.getDisciplineName();
    }
}
