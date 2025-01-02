import javax.swing.*;

public class Rook extends Piece {
    public Rook(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
    }

    @Override
    public boolean isValidMove(int newX, int newY, ChessBoard board) {
        int xDiff = newX - this.x;
        int yDiff = newY - this.y;

        // Debugging statements
        System.out.println("Rook Move: (" + this.x + ", " + this.y + ") to (" + newX + ", " + newY + ")");
        System.out.println("xDiff: " + xDiff + ", yDiff: " + yDiff);

        // Rook can move either vertically or horizontally
        if (xDiff != 0 && yDiff != 0) {
            return false; // Rook cannot move diagonally
        }

        // Check path for obstacles
        if (xDiff == 0) {
            // Moving vertically
            int minY = Math.min(this.y, newY);
            int maxY = Math.max(this.y, newY);
            for (int i = minY + 1; i < maxY; i++) {
                if (board.getPiece(this.x, i) != null) {
                    System.out.println("Obstacle at: (" + this.x + ", " + i + ")");
                    return false; // Obstacle in the way
                }
            }
        } else {
            // Moving horizontally
            int minX = Math.min(this.x, newX);
            int maxX = Math.max(this.x, newX);
            for (int i = minX + 1; i < maxX; i++) {
                if (board.getPiece(i, this.y) != null) {
                    System.out.println("Obstacle at: (" + i + ", " + this.y + ")");
                    return false; // Obstacle in the way
                }
            }
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
            return resizeIcon("src/images/rookPic.png", 50, 50);
        } else {
            return resizeIcon("src/images/blackRookPic.png", 50, 50);
        }
    }
}
