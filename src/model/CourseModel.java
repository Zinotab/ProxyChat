package model;

import java.util.Observable;

public class CourseModel extends Observable {
    private int courseId;
    private String coursePath;
    private String courseContent;

    public CourseModel() {}

    public CourseModel(int courseId, String coursePath, String courseContent) {
        this.courseId = courseId;
        this.coursePath = coursePath;
        this.courseContent = courseContent;
    }

    public int getCourseId() {
        return courseId;
    }
    public void setCourseId(int courseId) {
        this.courseId = courseId;
        setChanged();
        notifyObservers();
    }
    public String getCoursePath() {
        return coursePath;
    }
    public void setCoursePath(String coursePath) {
        this.coursePath = coursePath;
        setChanged();
        notifyObservers();
    }
    public String getCourseContent() {
        return courseContent;
    }
    public void setCourseContent(String courseContent) {
        this.courseContent = courseContent;
        setChanged();
        notifyObservers();
    }
}