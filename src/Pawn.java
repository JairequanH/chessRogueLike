import javax.swing.*;

public class Pawn extends Piece {
    boolean firstMove = true;

    public Pawn(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
    }

    @Override
    public boolean isValidMove(int newX, int newY, ChessBoard board) {
        int xDiff = newX - this.x; // Calculate xDiff from current position
        int yDiff = Math.abs(newY - this.y); // Calculate yDiff from current position

        // Debugging statements
        System.out.println("Pawn Move: (" + this.x + ", " + this.y + ") to (" + newX + ", " + newY + ")");
        System.out.println("xDiff: " + xDiff + ", yDiff: " + yDiff);

        if (isWhite) {
            // Forward moves for white pawn
            if (firstMove && xDiff == 2 && yDiff == 0 && board.getPiece(newX, newY) == null && board.getPiece(this.x + 1, this.y) == null) {
                return true; // Initial two-square move
            }
            if (xDiff == 1 && yDiff == 0 && board.getPiece(newX, newY) == null) return true; // Single square move
            if (xDiff == 1 && yDiff == 1 && board.getPiece(newX, newY) != null && board.getPiece(newX, newY).isWhite != isWhite) return true; // Capture move
        } else {
            // Forward moves for black pawn
            if (firstMove && xDiff == -2 && yDiff == 0 && board.getPiece(newX, newY) == null && board.getPiece(this.x - 1, this.y) == null) {
                return true; // Initial two-square move
            }
            if (xDiff == -1 && yDiff == 0 && board.getPiece(newX, newY) == null) return true; // Single square move
            if (xDiff == -1 && yDiff == 1 && board.getPiece(newX, newY) != null && board.getPiece(newX, newY).isWhite != isWhite) return true; // Capture move
        }

        System.out.println("Invalid Move");
        return false;
    }

    @Override
    public void move(int newX, int newY) {
        super.move(newX, newY);
        this.firstMove = false; // Set firstMove to false after the first move
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
