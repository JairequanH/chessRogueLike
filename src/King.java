import javax.swing.*;

public class King extends Piece {
    public King(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
    }

    @Override
    public boolean isValidMove(int newX, int newY, ChessBoard board) {
        int xDiff = Math.abs(newX - this.x);
        int yDiff = Math.abs(newY - this.y);

        // Debugging statements
        System.out.println("King Move: (" + this.x + ", " + this.y + ") to (" + newX + ", " + newY + ")");
        System.out.println("xDiff: " + xDiff + ", yDiff: " + yDiff);

        // King moves one square in any direction
        if (xDiff > 1 || yDiff > 1) {
            return false; // King can only move one square in any direction
        }

        // Check destination
        Piece destinationPiece = board.getPiece(newX, newY);
        if (destinationPiece != null && destinationPiece.isWhite == this.isWhite) {
            return false; // Cannot capture own piece
        }

        return true; // Valid move
    }

    @Override
    public ImageIcon getIcon() {
        if (isWhite) {
            return resizeIcon("src/images/kingPic.png", 50, 50);
        } else {
            return resizeIcon("src/images/blackKingPic.png", 50, 50);
        }
    }
}
