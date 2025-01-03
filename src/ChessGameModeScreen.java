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
        JButton classicNoCheckButton = new JButton("No Check");
        JButton classicButton = new JButton("Classic");

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

        // Layout components
        setLayout(new BorderLayout());
        add(titleLabel, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(classicNoCheckButton);
        buttonPanel.add(classicButton);
        add(buttonPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        ChessGameModeScreen modeScreen = new ChessGameModeScreen();
        modeScreen.setVisible(true);
    }
}
