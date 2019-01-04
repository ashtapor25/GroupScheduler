package ui.ScreenFrames;

import ui.GroupScheduler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginScreen extends JFrame {

    private GroupScheduler gs;

    public LoginScreen(GroupScheduler gs) {
        super("Group Scheduler");
        this.gs = gs;
        this.setSize(300, 200);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        this.add(panel);

        panel.setLayout(null);

        JLabel logo = new JLabel("Group Scheduler");
        logo.setFont(new Font("Serif", Font.BOLD, 16));
        logo.setBounds(10, 10, 200, 30);
        panel.add(logo);

        JLabel userLabel = new JLabel("User");
        userLabel.setBounds(10, 50, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 50, 160, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 80, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 80, 160, 25);
        panel.add(passwordText);

        JButton loginButton = new JButton("login");
        loginButton.setBounds(10, 120, 80, 25);
        panel.add(loginButton);
        loginButton.addActionListener(e -> {gs.changeUser(userText.getText(), String.valueOf(passwordText.getPassword()).hashCode());
                                            userText.setText("");
                                            passwordText.setText("");});

        JButton registerButton = new JButton("register");
        registerButton.setBounds(180, 120, 80, 25);
        panel.add(registerButton);
        registerButton.addActionListener(e -> { gs.addGuest(userText.getText(), String.valueOf(passwordText.getPassword()).hashCode());
                                                userText.setText("");
                                                passwordText.setText("");});

        this.setVisible(true);
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
