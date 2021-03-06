package project.gpa_calculator.models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class GPA implements Serializable {
    int upper,lower;
    double grade_point;
    String grade,docID;

    public GPA() {
    }

    public GPA(int lower, int upper, double grade_point, String grade){
        this.upper = upper;
        this.lower = lower;
        this.grade_point = grade_point;
        this.grade = grade;
    }

    @Exclude
    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

    public double getGrade_point() {
        return grade_point;
    }

    public int getLower() {
        return lower;
    }

    public int getUpper() {
        return upper;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setGrade_point(double grade_point) {
        this.grade_point = grade_point;
    }

    public void setLower(int lower) {
        this.lower = lower;
    }

    public void setUpper(int upper) {
        this.upper = upper;
    }
}
