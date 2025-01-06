import javax.swing.*;
import java.awt.*;

public class ChessGameUI extends JFrame {
    private ChessBoardPanel boardPanel;
    private GameMode gameMode;
    private JLabel turnLabel;
    private JLabel timerLabel;

    public ChessGameUI(GameModeType gameModeType) {
        this(gameModeType, 30); // Default timer value for non-timed modes
    }

    public ChessGameUI(GameModeType gameModeType, int timerDuration) {
        setLayout(new BorderLayout());

        // Initialize the turn label
        turnLabel = new JLabel("White's Turn", SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(turnLabel, BorderLayout.NORTH);

        // Initialize the game mode
        switch (gameModeType) {
            case CLASSIC_NO_CHECK:
                gameMode = new GameModeNoCheck(this);
                break;
            case CLASSIC:
                gameMode = new GameModeClassic(this);
                break;
            case TIMED:
                // Initialize the timer label for the timed mode
                timerLabel = new JLabel("White's Turn: " + timerDuration + "s remaining", SwingConstants.CENTER);
                timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
                add(timerLabel, BorderLayout.SOUTH);

                // Start the timed game mode
                gameMode = new GameModeTimed(this, timerLabel, timerDuration);
                ((GameModeTimed) gameMode).startTimer();
                break;
            default:
                throw new IllegalArgumentException("Invalid game mode type");
        }

        // Initialize the chessboard panel
        boardPanel = new ChessBoardPanel(gameMode);
        add(boardPanel, BorderLayout.CENTER);

        // Set JFrame properties
        setSize(600, 700); // Adjusted height for timer label
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JLabel getTurnLabel() {
        return turnLabel;
    }

    public void updateTurnLabel() {
        turnLabel.setText(gameMode.whiteTurn ? "White's Turn" : "Black's Turn");
    }

}
