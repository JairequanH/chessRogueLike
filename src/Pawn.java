import javax.swing.*;

public class Pawn extends Piece {

    boolean firstMove = true;

    public Pawn(boolean isWhite, int x, int y) {
        this.isWhite = isWhite;
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean isValidMove(int newX, int newY, ChessBoard board) {
        int xDiff = newX - x;
        int yDiff = Math.abs(newY - y);

        // Debugging statements
        System.out.println("Pawn Move: (" + x + ", " + y + ") to (" + newX + ", " + newY + ")");
        System.out.println("xDiff: " + xDiff + ", yDiff: " + yDiff);

        if (isWhite) {
            // Forward moves for white pawn
            if (xDiff == 2 && yDiff == 0 && firstMove && board.getPiece(newX, newY) == null && board.getPiece(x + 1, y) == null) {
                firstMove = false;
                return true; // Initial two-square move
            }
            if (xDiff == 1 && yDiff == 0 && board.getPiece(newX, newY) == null) return true; // Single square move
            if (xDiff == 1 && yDiff == 1 && board.getPiece(newX, newY) != null && board.getPiece(newX, newY).isWhite != isWhite) return true; // Capture move
        } else {
            // Forward moves for black pawn
            if (xDiff == -2 && yDiff == 0 && firstMove && board.getPiece(newX, newY) == null && board.getPiece(x - 1, y) == null) {
                firstMove = false;
                return true; // Initial two-square move
            }
            if (xDiff == -1 && yDiff == 0 && board.getPiece(newX, newY) == null) return true; // Single square move
            if (xDiff == -1 && yDiff == 1 && board.getPiece(newX, newY) != null && board.getPiece(newX, newY).isWhite != isWhite) return true; // Capture move
        }

        System.out.println("Invalid Move");
        return false;
    }

    @Override
    public ImageIcon getIcon() {
        if (isWhite) {
            return resizeIcon("src/images/pawnPic.png", 50, 50);
        } else {
            return resizeIcon("src/images/blackPawnPic.png", 50, 50);
        }
    }
}
