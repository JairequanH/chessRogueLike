import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChessGameModeScreen extends JFrame {
    public ChessGameModeScreen() {
        setTitle("Chess Game - Select Mode");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create components
        JLabel titleLabel = new JLabel("Select Game Mode", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        JButton classicNoCheckButton = new JButton("Classic No Check");
        JButton classicButton = new JButton("Classic");
        JButton timedButton = new JButton("Timed");

        // Add action listeners to buttons
        classicNoCheckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChessGameUI chessGameUI = new ChessGameUI(GameModeType.CLASSIC_NO_CHECK);
                chessGameUI.setVisible(true);
                dispose(); // Close mode selection screen
            }
        });

        classicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChessGameUI chessGameUI = new ChessGameUI(GameModeType.CLASSIC);
                chessGameUI.setVisible(true);
                dispose(); // Close mode selection screen
            }
        });

        timedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTimedOptions(); // Show timer options for timed mode
            }
        });

        // Layout components
        setLayout(new BorderLayout());
        add(titleLabel, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(classicNoCheckButton);
        buttonPanel.add(classicButton);
        buttonPanel.add(timedButton);
        add(buttonPanel, BorderLayout.CENTER);
    }

    private void showTimedOptions() {
        // Create a new JFrame for timer options
        JFrame timerOptionsFrame = new JFrame("Select Timer");
        timerOptionsFrame.setSize(300, 200);
        timerOptionsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        timerOptionsFrame.setLocationRelativeTo(this);

        JLabel timerLabel = new JLabel("Select Timer Duration", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JButton timer15Button = new JButton("15 Seconds");
        JButton timer30Button = new JButton("30 Seconds");
        JButton timer60Button = new JButton("60 Seconds");
        JButton customButton = new JButton("Custom");

        timer15Button.addActionListener(e -> startTimedMode(15));
        timer30Button.addActionListener(e -> startTimedMode(30));
        timer60Button.addActionListener(e -> startTimedMode(60));

        customButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(timerOptionsFrame, "Enter custom time (max 180 seconds):");
            try {
                int customTime = Integer.parseInt(input);
                if (customTime > 0 && customTime <= 180) {
                    startTimedMode(customTime);
                } else {
                    JOptionPane.showMessageDialog(timerOptionsFrame, "Please enter a valid number between 1 and 180.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(timerOptionsFrame, "Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.add(timer15Button);
        buttonPanel.add(timer30Button);
        buttonPanel.add(timer60Button);
        buttonPanel.add(customButton);

        timerOptionsFrame.setLayout(new BorderLayout());
        timerOptionsFrame.add(timerLabel, BorderLayout.NORTH);
        timerOptionsFrame.add(buttonPanel, BorderLayout.CENTER);
        timerOptionsFrame.setVisible(true);
    }

    private void startTimedMode(int timerDuration) {
        ChessGameUI chessGameUI = new ChessGameUI(GameModeType.TIMED, timerDuration);
        chessGameUI.setVisible(true);
        dispose(); // Close the current screen
    }

    public static void main(String[] args) {
        ChessGameModeScreen modeScreen = new ChessGameModeScreen();
        modeScreen.setVisible(true);
    }
}
