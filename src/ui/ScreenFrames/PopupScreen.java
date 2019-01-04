package ui.ScreenFrames;

import javax.swing.*;

public class PopupScreen extends JFrame {

    public PopupScreen(String message) {
        super("Error!");
        this.setSize(200, 150);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        this.add(panel);

        JLabel messageLabel = new JLabel(message);
        messageLabel.setBounds(10, 30, 180, 20);
        panel.add(messageLabel);

        JButton okayButton = new JButton("OK");
        okayButton.setBounds(50, 70, 100, 20);
        okayButton.addActionListener(e -> this.dispose());
        panel.add(okayButton);

        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
