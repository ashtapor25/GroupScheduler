package ui.ScreenFrames;

import Data.Schedule;
import Data.TimeFrame;
import ui.GroupScheduler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class ScheduleAdderScreen extends JFrame {

    private GroupScheduler gs;

    public ScheduleAdderScreen(GroupScheduler gs) {
        super("Adding Schedule...");
        this.gs = gs;
        this.setSize(400, 450);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        this.add(panel);

        panel.setLayout(null);

        JLabel logo = new JLabel("Add a New Schedule!");
        logo.setFont(new Font("Serif", Font.BOLD, 16));
        logo.setBounds(10, 10, 200, 30);
        panel.add(logo);

        JLabel userLabel = new JLabel("Name");
        userLabel.setBounds(10, 50, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 50, 260, 25);
        panel.add(userText);

        JLabel startTimeLabel = new JLabel("Start");
        startTimeLabel.setBounds(10, 80, 80, 25);
        panel.add(startTimeLabel);

        JSpinner startDateSpinner = new JSpinner( new SpinnerDateModel() );
        startDateSpinner.setBounds(100, 80, 120, 25);
        JSpinner.DateEditor startDateEditor = new JSpinner.DateEditor(startDateSpinner, "yyyy/MM/dd HH:mm");
        startDateSpinner.setEditor(startDateEditor);
        startDateSpinner.setValue(new Date());
        panel.add(startDateSpinner);

        JLabel endTimeLabel = new JLabel("End");
        endTimeLabel.setBounds(10, 110, 80, 25);
        panel.add(endTimeLabel);

        JSpinner endDateSpinner = new JSpinner( new SpinnerDateModel() );
        endDateSpinner.setBounds(100, 110, 120, 25);
        JSpinner.DateEditor endDateEditor = new JSpinner.DateEditor(endDateSpinner, "yyyy/MM/dd HH:mm");
        endDateSpinner.setEditor(endDateEditor);
        endDateSpinner.setValue(new Date());
        panel.add(endDateSpinner);

        JLabel descLabel = new JLabel("Description");
        descLabel.setBounds(10, 140, 80, 25);
        panel.add(descLabel);

        JTextArea descText = new JTextArea(5, 20);
        descText.setBounds(100, 142, 260, 125);
        panel.add(descText);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(10, 350, 80, 25);
        submitButton.addActionListener(e -> {

            Date startDate = (Date) startDateSpinner.getValue();
            ZonedDateTime start = ZonedDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault());

            Date endDate = (Date) endDateSpinner.getValue();
            ZonedDateTime end = ZonedDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault());

            TimeFrame timeframe = new TimeFrame(start, end);
            Schedule toAdd = new Schedule(userText.getText(), timeframe);
            toAdd.setDescription(descText.getText());

            gs.submitSchedule(toAdd);});

        panel.add(submitButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(290, 350, 80, 25);
        cancelButton.addActionListener(e -> gs.cancelSchedule());
        panel.add(cancelButton);

        this.setVisible(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                exitProcedure();
            }
        });
    }

    private void exitProcedure() {
        gs.save();
        this.dispose();
        System.exit(0);
    }

}
