package view;

import javax.swing.*;
import java.awt.BorderLayout;

import model.ParticipantListModel;
import model.User;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

public class ParticipantListView extends JFrame implements Observer {
    private ParticipantListModel model;
    private JList<String> listParticipants;

    public ParticipantListView(ParticipantListModel model) {
        this.model = model;
        model.addObserver(this);
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Participants List");
        setSize(300, 400);
        setLayout(new BorderLayout());

        Vector<String> participantNames = new Vector<>();
        for (User user : model.getParticipants()) {
            participantNames.add(user.getPseudoName());
        }

        listParticipants = new JList<>(participantNames);
        add(new JScrollPane(listParticipants), BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        Vector<String> participantNames = new Vector<>();
        for (User user : model.getParticipants()) {
            participantNames.add(user.getPseudoName());
        }
        listParticipants.setListData(participantNames);
    }
}
