import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChessGameStartScreen extends JFrame {
    public ChessGameStartScreen() {
        setTitle("Chess Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create components
        JLabel titleLabel = new JLabel("Welcome to Chess Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        JButton startButton = new JButton("Start Game");
        JButton exitButton = new JButton("Exit");

        // Add action listeners to buttons
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Start the game
                ChessGameUI chessGameUI = new ChessGameUI();
                chessGameUI.setVisible(true);
                dispose(); // Close start screen
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the game
            }
        });

        // Layout components
        setLayout(new BorderLayout());
        add(titleLabel, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(startButton);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.CENTER);
    }
}
