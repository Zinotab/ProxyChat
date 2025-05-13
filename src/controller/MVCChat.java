package controller;

import model.CourseModel;
import model.MessageListModel;
import model.ParticipantListModel;
import view.LoginView;
import view.RegisterView;

public class MVCChat {
    private static CourseModel courseModel;
    
    public static void main(String[] args) {
        ParticipantListModel participants = new ParticipantListModel();
        MessageListModel messageModel = new MessageListModel();
        
        
        courseModel = new CourseModel(1, "/courses/java", "This is a sample Java course content.\n\nJava is a high-level, class-based, object-oriented programming language that is designed to have as few implementation dependencies as possible.\n\nIn this course, you will learn about:\n- Java syntax and semantics\n- Object-oriented programming\n- Java Collections Framework\n- Exception handling\n- File I/O operations\n- Multithreading\n\nEach topic will be covered in detail with examples and exercises.");
        
        
        new RegisterView(participants,messageModel);
    }
    
    public static CourseModel getCourseModel() {
        return courseModel;
    }
}