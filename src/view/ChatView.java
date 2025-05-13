package view;

import javax.swing.*;
import model.*;
import java.awt.*;
import java.util.*;

public class ChatView extends JFrame implements Observer {
    private MessageListModel model;
    private User currentUser;
    private ParticipantListModel participantModel;
    private JList<String> listMessages;
    private JTextField txtMessage;
    private JButton btnSend;
    private JComboBox<String> comboReceiver;
    private JCheckBox chkUseProxy;
    private JCheckBox chkUseEncryption;
    

    private ChatProxy chatProxy;
    private RealSubject realSubject;
    private boolean useProxy = false;

    public ChatView(MessageListModel model, User currentUser, ParticipantListModel participantModel) {
        this.model = model;
        this.currentUser = currentUser;
        this.participantModel = participantModel;
        
        // Initialize proxy objects
        this.chatProxy = new ChatProxy();
        this.chatProxy.request(currentUser.getPseudoName());
        this.realSubject = new RealSubject();
        
        // Generate RSA key pair for the current user
        RSAUtil.generateKeyPair(currentUser.getPseudoName());
        
        model.addObserver(this);
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Chat - " + currentUser.getPseudoName());
        setSize(500, 400);
        setLayout(new BorderLayout());

        listMessages = new JList<>(new Vector<>());
        add(new JScrollPane(listMessages), BorderLayout.CENTER);

        // Top panel with receiver dropdown and checkboxes
        JPanel topPanel = new JPanel(new BorderLayout());
        
        // Receiver dropdown
        comboReceiver = new JComboBox<>();
        comboReceiver.addActionListener(e -> updateMessageDisplay());
        updateReceiverList();
        topPanel.add(comboReceiver, BorderLayout.CENTER);
        
        // Options panel (proxy and encryption)
        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        // Proxy checkbox
        chkUseProxy = new JCheckBox("Use Proxy");
        chkUseProxy.addActionListener(e -> useProxy = chkUseProxy.isSelected());
        optionsPanel.add(chkUseProxy);
        
        // Encryption checkbox
        chkUseEncryption = new JCheckBox("Use Encryption");
        chkUseEncryption.addActionListener(e -> {
            boolean useEncryption = chkUseEncryption.isSelected();
            model.toggleEncryption(useEncryption);
            if (useEncryption) {
                JOptionPane.showMessageDialog(this, 
                    "RSA Encryption enabled. Messages will be encrypted if both sender and receiver have key pairs.",
                    "Encryption Enabled", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
        optionsPanel.add(chkUseEncryption);
        
        topPanel.add(optionsPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Message input and send button
        JPanel panelSend = new JPanel(new BorderLayout());
        txtMessage = new JTextField();
        btnSend = new JButton("Send");

        btnSend.addActionListener(e -> sendMessage());
        txtMessage.addActionListener(e -> sendMessage());

        panelSend.add(txtMessage, BorderLayout.CENTER);
        panelSend.add(btnSend, BorderLayout.EAST);
        add(panelSend, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void sendMessage() {
        String receiver = (String) comboReceiver.getSelectedItem();
        String content = txtMessage.getText().trim();

        if (receiver != null && !content.isEmpty()) {
            
            if (!RSAUtil.hasKeyPair(receiver)) {
                RSAUtil.generateKeyPair(receiver);
            }
            
            if (useProxy) {
                chatProxy.sendMessage(receiver, content, model);
            } else {
                model.sendMessage(currentUser.getPseudoName(), receiver, content);
            }
            txtMessage.setText("");
        }
    }

    private void updateReceiverList() {
        comboReceiver.removeAllItems();
        participantModel.getParticipants().stream()
            .filter(user -> !user.getPseudoName().equals(currentUser.getPseudoName()))
            .forEach(user -> comboReceiver.addItem(user.getPseudoName()));
    }

    private void updateMessageDisplay() {
        Vector<String> messages = new Vector<>();
        String currentUserId = currentUser.getPseudoName();
        String selectedReceiver = (String) comboReceiver.getSelectedItem();

        for (Message msg : model.getMessagesForUser(currentUserId)) {
            String displayPrefix = "";
            
            if (msg.isEncrypted()) {
                displayPrefix = "[Encrypted] ";
            }
            
            if (msg.getReceiver().equals(currentUserId)) {
                messages.add(displayPrefix + msg.getSender() + " → You: " + msg.getContent());
            } else if (msg.getReceiver().equals(selectedReceiver)) {
                messages.add(displayPrefix + "You → " + selectedReceiver + ": " + msg.getContent());
            }
        }

        listMessages.setListData(messages);
        if (!messages.isEmpty()) {
            listMessages.ensureIndexIsVisible(messages.size() - 1);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        SwingUtilities.invokeLater(this::updateMessageDisplay);
    }
}