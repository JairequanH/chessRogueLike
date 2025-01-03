import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GameMode {
    protected ChessBoard board;
    protected boolean whiteTurn;
    protected JFrame gameWindow;

    public GameMode(JFrame gameWindow) {
        this.board = new ChessBoard();
        this.whiteTurn = true;
        this.gameWindow = gameWindow;
    }

    public abstract void handleSquareClick(int x, int y);
    public abstract void checkForSpecialConditions();

    public Piece getPiece(int x, int y) {
        return board.getPiece(x, y);
    }

    public void updateTurnLabel(JLabel turnLabel) {
        turnLabel.setText(whiteTurn ? "White's Turn" : "Black's Turn");
    }


    public abstract int getSelectedX();
    public abstract int getSelectedY();

    public boolean isSelectedSquare(int selectedX, int selectedY, int x, int y) {
        return x == selectedX && y == selectedY;
    }

    public List<int[]> getPossibleMoves(Piece piece) {
        List<int[]> moves = new ArrayList<>();
        if (piece != null) { // Ensure piece is not null before proceeding
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (piece.isValidMove(i, j, board)) {
                        moves.add(new int[]{i, j});
                    }
                }
            }
        }
        return moves;
    }

    public boolean isKingInCheck(boolean isWhite) {
        return false; // Default implementation for game modes that don't check for check
    }

    public void displayEndGameScreen(String winner) {
        JFrame endGameFrame = new JFrame("Game Over");
        endGameFrame.setSize(300, 200);
        endGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        endGameFrame.setLocationRelativeTo(null);

        JLabel messageLabel = new JLabel(winner + " wins!", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 20));
        JButton restartButton = new JButton("Restart Game");
        JButton titleButton = new JButton("Return to Title");

        restartButton.addActionListener(e -> {
            new ChessGameUI(GameModeType.CLASSIC).setVisible(true);
            endGameFrame.dispose(); // Close end game screen
            gameWindow.dispose(); // Close current game window
        });

        titleButton.addActionListener(e -> {
            new ChessGameModeScreen().setVisible(true);
            endGameFrame.dispose(); // Close end game screen
            gameWindow.dispose(); // Close current game window
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(restartButton);
        buttonPanel.add(titleButton);

        endGameFrame.setLayout(new BorderLayout());
        endGameFrame.add(messageLabel, BorderLayout.CENTER);
        endGameFrame.add(buttonPanel, BorderLayout.SOUTH);

        endGameFrame.setVisible(true);
    }
}
