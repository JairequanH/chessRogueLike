import javax.swing.*;
import java.awt.*;

public class ChessGameUI extends JFrame {
    private ChessBoardPanel boardPanel;
    private ChessGameState gameState;
    private JLabel turnLabel;

    public ChessGameUI(boolean classicMode) {
        gameState = new ChessGameState(classicMode, this);
        boardPanel = new ChessBoardPanel(gameState);
        turnLabel = new JLabel("White's Turn", SwingConstants.CENTER); // Initialize turn label
        turnLabel.setFont(new Font("Arial", Font.BOLD, 16));
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        add(turnLabel, BorderLayout.NORTH); // Add turn label to the top
        add(boardPanel, BorderLayout.CENTER);
        setSize(600, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        ChessGameModeScreen modeScreen = new ChessGameModeScreen();
        modeScreen.setVisible(true);
    }
}
