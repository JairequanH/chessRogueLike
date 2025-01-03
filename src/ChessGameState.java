import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class ChessGameState {
    private ChessBoard board;
    private boolean whiteTurn;
    private boolean classicMode;
    private int selectedX = -1, selectedY = -1; // Track selected piece
    private List<int[]> possibleMoves = new ArrayList<>(); // Track possible moves
    private boolean whiteKingInCheck = false;
    private boolean blackKingInCheck = false;
    private JFrame gameWindow; // Reference to main game window

    public ChessGameState(boolean classicMode, JFrame gameWindow) {
        this.classicMode = classicMode;
        this.board = new ChessBoard();
        this.whiteTurn = true;
        this.gameWindow = gameWindow;
    }

    public boolean isSelectedSquare(int x, int y) {
        return x == selectedX && y == selectedY;
    }

    public boolean isPossibleMove(int x, int y) {
        for (int[] move : possibleMoves) {
            if (move[0] == x && move[1] == y) {
                return true;
            }
        }
        return false;
    }

    public Piece getPiece(int x, int y) {
        return board.getPiece(x, y);
    }

    public boolean isKingInCheck(boolean isWhite) {
        return isWhite ? whiteKingInCheck : blackKingInCheck;
    }

    public void handleSquareClick(int x, int y) {
        if (selectedX == -1 && selectedY == -1) {
            // Select piece
            Piece selectedPiece = board.getPiece(x, y);
            if (selectedPiece != null && selectedPiece.isWhite == whiteTurn) {
                selectedX = x;
                selectedY = y;
                possibleMoves = getPossibleMoves(selectedPiece); // Get possible moves for the selected piece
            }
        } else {
            // Handle click on another ally piece
            Piece selectedPiece = board.getPiece(selectedX, selectedY);
            Piece clickedPiece = board.getPiece(x, y);
            if (clickedPiece != null && clickedPiece.isWhite == whiteTurn) {
                // Select new piece
                selectedX = x;
                selectedY = y;
                possibleMoves = getPossibleMoves(clickedPiece); // Get possible moves for the new selected piece
            } else {
                // Move piece
                if (selectedPiece.isValidMove(x, y, board)) {
                    boolean kingCaptured = board.movePiece(selectedX, selectedY, x, y);
                    if (kingCaptured) {
                        displayEndGameScreen(whiteTurn ? "White" : "Black"); // Display end game screen with winner
                    } else {
                        whiteTurn = !whiteTurn; // Switch turns
                        if (classicMode) {
                            checkForCheck(); // Check for check in classic mode
                        }
                    }
                    selectedX = -1;
                    selectedY = -1;
                    possibleMoves.clear(); // Clear possible moves
                }
            }
        }
    }

    private List<int[]> getPossibleMoves(Piece piece) {
        List<int[]> moves = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (piece.isValidMove(i, j, board)) {
                    moves.add(new int[]{i, j});
                }
            }
        }
        return moves;
    }

    private void checkForCheck() {
        King whiteKing = findKing(true);
        King blackKing = findKing(false);

        whiteKingInCheck = whiteKing != null && isInCheck(whiteKing);
        blackKingInCheck = blackKing != null && isInCheck(blackKing);

        if (whiteKingInCheck) {
            System.out.println("White King is in check!");
        } else if (blackKingInCheck) {
            System.out.println("Black King is in check!");
        }
    }

    private King findKing(boolean isWhite) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPiece(i, j);
                if (piece instanceof King && piece.isWhite == isWhite) {
                    return (King) piece;
                }
            }
        }
        return null;
    }

    private boolean isInCheck(King king) {
        int kingX = king.x;
        int kingY = king.y;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPiece(i, j);
                if (piece != null && piece.isWhite != king.isWhite) {
                    if (piece.isValidMove(kingX, kingY, board)) {
                        return true;
                    }
                }
            }
        }
        return false;
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
            new ChessGameUI(classicMode).setVisible(true);
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
