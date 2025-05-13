package view;

import javax.swing.*;

import model.MessageListModel;
import model.ParticipantListModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class RegisterView extends JFrame implements Observer {
    private ParticipantListModel model;
    private JTextField txtId, txtPseudo;
    private JPasswordField txtPassword;
    private JButton btnRegister;

    private MessageListModel messageModel;


    public RegisterView(ParticipantListModel model, MessageListModel messageModel) {
        this.messageModel = messageModel;
        this.messageModel.addObserver(this);
        this.model = model;
        model.addObserver(this);
        initializeUI();
    }

    private void initializeUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        setTitle("Register New User");
        setSize(450, 280);
        setLocationRelativeTo(null);
        
        // Main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 248, 255));
        
        // Header
        JLabel headerLabel = new JLabel("Create New Account", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerLabel.setForeground(new Color(70, 130, 180));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        mainPanel.add(headerLabel, BorderLayout.NORTH);
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);
        
        // Create styled fields
        JLabel idLabel = new JLabel("User ID:");
        JLabel pseudoLabel = new JLabel("Psudoname:");
        JLabel passwordLabel = new JLabel("Password:");
        
        txtId = new JTextField(15);
        txtPseudo = new JTextField(15);
        txtPassword = new JPasswordField(15);
        
        // Add components to form
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(idLabel, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(txtId, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
        formPanel.add(pseudoLabel, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(txtPseudo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.0;
        formPanel.add(passwordLabel, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(txtPassword, gbc);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Register button
        btnRegister = new JButton("Register");
        btnRegister.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegister.setBackground(new Color(70, 130, 180));
        btnRegister.setForeground(Color.BLACK);
        btnRegister.setFocusPainted(false);
        btnRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.registerParticipant(
                    txtId.getText(),
                    txtPseudo.getText(),
                    new String(txtPassword.getPassword())
                );
                JOptionPane.showMessageDialog(null, "User registered successfully!");
            }
        });
        
        JButton btnGoToLogin = new JButton("Login");
        btnGoToLogin.setFont(new Font("Arial", Font.PLAIN, 14));
        btnGoToLogin.setBackground(Color.BLACK);
        btnGoToLogin.setForeground(new Color(70, 130, 180));
        btnGoToLogin.setBorderPainted(false);
        btnGoToLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGoToLogin.setFocusPainted(false);
        btnGoToLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginView(model, messageModel); 
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(240, 248, 255));
        buttonPanel.add(btnRegister);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.add(btnGoToLogin);

        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {}
}
