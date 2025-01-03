import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GameModeClassic extends GameMode {
    private int selectedX = -1, selectedY = -1; // Track selected piece
    private List<int[]> possibleMoves = new ArrayList<>(); // Track possible moves
    private boolean whiteKingInCheck = false;
    private boolean blackKingInCheck = false;

    public GameModeClassic(JFrame gameWindow) {
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
                        whiteTurn = !whiteTurn;// Switch turns
                        updateTurnLabel(((ChessGameUI) gameWindow).getTurnLabel());
                        checkForSpecialConditions(); // Check for check
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

    @Override
    public boolean isKingInCheck(boolean isWhite) {
        King king = findKing(isWhite);
        return king != null && isInCheck(king);
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
