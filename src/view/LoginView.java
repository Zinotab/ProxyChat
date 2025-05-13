package view;

import javax.swing.*;

import controller.MVCChat;
import model.CourseModel;
import model.MessageListModel;
import model.ParticipantListModel;
import model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class LoginView extends JFrame implements Observer {
    private ParticipantListModel participantModel;
    private JTextField txtId;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private MessageListModel messageModel;

    public LoginView(ParticipantListModel participantModel, MessageListModel messageModel) {
        this.participantModel = participantModel;
        this.messageModel = messageModel;
        participantModel.addObserver(this);
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Login");
        setSize(400, 250);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        
        getContentPane().setBackground(new Color(173, 216, 230)); 

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(173, 216, 230)); 
        
        // Username panel
        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        idPanel.setBackground(new Color(173, 216, 230));
        JLabel lblId = new JLabel("Name    :");
        txtId = new JTextField(15);
        idPanel.add(lblId);
        idPanel.add(txtId);
        
        // Password panel
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passwordPanel.setBackground(new Color(173, 216, 230));
        JLabel lblPassword = new JLabel("Password:");
        txtPassword = new JPasswordField(15);
        passwordPanel.add(lblPassword);
        passwordPanel.add(txtPassword);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(173, 216, 230));
        btnLogin = new JButton("Login");
        buttonPanel.add(btnLogin);
        
        formPanel.add(idPanel);
        formPanel.add(passwordPanel);
        formPanel.add(buttonPanel);
        
        add(formPanel, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("Login", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titleLabel, BorderLayout.NORTH);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = participantModel.signIn(txtId.getText(), new String(txtPassword.getPassword()));
                if (user != null) {
                    JOptionPane.showMessageDialog(null, "Welcome, " + user.getPseudoName() + "!");
                    new ParticipantListView(participantModel);
                    new ChatView(messageModel, user, participantModel);
                    
                    CourseModel courseModel = MVCChat.getCourseModel();
                    if (courseModel != null) {
                        new CourseView(courseModel);
                    }
                    
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {}
}
