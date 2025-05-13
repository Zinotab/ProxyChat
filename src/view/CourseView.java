package view;

import javax.swing.*;
import model.CourseModel;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class CourseView extends JFrame implements Observer {
    private CourseModel courseModel;
    private JTextArea contentArea;
    private JLabel idLabel, pathLabel;
    
    public CourseView(CourseModel courseModel) {
        this.courseModel = courseModel;
        this.courseModel.addObserver(this);
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Course Content");
        setSize(450, 370);
        setLayout(new BorderLayout());
        
        // Course Info Panel
        JPanel infoPanel = new JPanel(new GridLayout(2, 2, 10, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        infoPanel.add(new JLabel("Course ID:"));
        idLabel = new JLabel(String.valueOf(courseModel.getCourseId()));
        infoPanel.add(idLabel);
        
        infoPanel.add(new JLabel("Course Path:"));
        pathLabel = new JLabel(courseModel.getCoursePath());
        infoPanel.add(pathLabel);
        
        add(infoPanel, BorderLayout.NORTH);
        
        // Course Content
        contentArea = new JTextArea(courseModel.getCourseContent());
        contentArea.setEditable(false);
        contentArea.setWrapStyleWord(true);
        contentArea.setLineWrap(true);
        
        JScrollPane scrollPane = new JScrollPane(contentArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Course Content"));
        add(scrollPane, BorderLayout.CENTER);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof CourseModel) {
            CourseModel model = (CourseModel) o;
            idLabel.setText(String.valueOf(model.getCourseId()));
            pathLabel.setText(model.getCoursePath());
            contentArea.setText(model.getCourseContent());
        }
    }
}