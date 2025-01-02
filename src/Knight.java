import javax.swing.*;

public class Knight extends Piece {
    public Knight(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
    }

    @Override
    public boolean isValidMove(int newX, int newY, ChessBoard board) {
        int xDiff = Math.abs(newX - this.x);
        int yDiff = Math.abs(newY - this.y);

        // Debugging statements
        System.out.println("Knight Move: (" + this.x + ", " + this.y + ") to (" + newX + ", " + newY + ")");
        System.out.println("xDiff: " + xDiff + ", yDiff: " + yDiff);

        // Knight moves in an "L" shape
        boolean isLShape = (xDiff == 2 && yDiff == 1) || (xDiff == 1 && yDiff == 2);
        if (!isLShape) {
            return false; // Not an "L" shape move
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
            return resizeIcon("src/images/knightPic.png", 50, 50);
        } else {
            return resizeIcon("src/images/blackKnightPic.png", 50, 50);
        }
    }
}
