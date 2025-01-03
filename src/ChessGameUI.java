import javax.swing.*;
import java.awt.*;
import javax.swing.*;

import javax.swing.*;

public class ChessGameUI extends JFrame {
    private ChessBoardPanel boardPanel;
    private GameMode gameMode;
    private JLabel turnLabel;

    public ChessGameUI(GameModeType gameModeType) {
        switch (gameModeType) {
            case CLASSIC_NO_CHECK:
                gameMode = new GameModeNoCheck(this);
                break;
            case CLASSIC:
                gameMode = new GameModeClassic(this);
                break;
            default:
                throw new IllegalArgumentException("Invalid game mode type");
        }
        boardPanel = new ChessBoardPanel(gameMode);
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
}
