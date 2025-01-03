import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GameModeNoCheck extends GameMode {
    private int selectedX = -1, selectedY = -1; // Track selected piece
    private List<int[]> possibleMoves = new ArrayList<>(); // Track possible moves

    public GameModeNoCheck(JFrame gameWindow) {
        super(gameWindow);
    }

    @Override
    public void handleSquareClick(int x, int y) {
        if (selectedX == -1 && selectedY == -1) {
            // Select piece
            Piece selectedPiece = getPiece(x, y);
            if (selectedPiece != null && selectedPiece.isWhite == whiteTurn) {
                selectedX = x;
                selectedY = y;
                possibleMoves = getPossibleMoves(selectedPiece); // Get possible moves for the selected piece
            }
        } else {
            // Handle click on another ally piece
            Piece selectedPiece = getPiece(selectedX, selectedY);
            Piece clickedPiece = getPiece(x, y);
            if (clickedPiece != null && clickedPiece.isWhite == whiteTurn) {
                // Select new piece
                selectedX = x;
                selectedY = y;
                possibleMoves = getPossibleMoves(clickedPiece); // Get possible moves for the new selected piece
            } else {
                // Move piece
                if (selectedPiece != null && selectedPiece.isValidMove(x, y, board)) {
                    boolean kingCaptured = board.movePiece(selectedX, selectedY, x, y);
                    if (kingCaptured) {
                        displayEndGameScreen(whiteTurn ? "White" : "Black"); // Display end game screen with winner
                    } else {
                        whiteTurn = !whiteTurn; // Switch turns
                        selectedX = -1;
                        selectedY = -1;
                        possibleMoves.clear(); // Clear possible moves
                    }
                }
            }
        }
    }

    @Override
    public void checkForSpecialConditions() {
        // No special conditions for GameModeNoCheck
    }

    @Override
    public int getSelectedX() {
        return selectedX;
    }

    @Override
    public int getSelectedY() {
        return selectedY;
    }

    @Override
    public List<int[]> getPossibleMoves(Piece piece) {
        if (piece != null) {
            return super.getPossibleMoves(piece); // Call the superclass method if the piece is not null
        }
        return new ArrayList<>(); // Return an empty list if the piece is null
    }
}
