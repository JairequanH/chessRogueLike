import javax.swing.*;

public class Bishop extends Piece {
    public Bishop(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
    }

    @Override
    public boolean isValidMove(int newX, int newY, ChessBoard board) {
        int xDiff = Math.abs(newX - this.x);
        int yDiff = Math.abs(newY - this.y);

        // Debugging statements
        System.out.println("Bishop Move: (" + this.x + ", " + this.y + ") to (" + newX + ", " + newY + ")");
        System.out.println("xDiff: " + xDiff + ", yDiff: " + yDiff);

        // Bishop moves diagonally
        if (xDiff != yDiff) {
            return false; // Not a diagonal move
        }

        // Check path for obstacles
        int xDirection = (newX - this.x) == 0 ? 0 : (newX - this.x) / xDiff;
        int yDirection = (newY - this.y) == 0 ? 0 : (newY - this.y) / yDiff;

        for (int i = 1; i < xDiff; i++) {
            int intermediateX = this.x + i * xDirection;
            int intermediateY = this.y + i * yDirection;
            if (board.getPiece(intermediateX, intermediateY) != null) {
                System.out.println("Obstacle at: (" + intermediateX + ", " + intermediateY + ")");
                return false; // Obstacle in the way
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
            return resizeIcon("src/images/bishopPic.png", 50, 50);
        } else {
            return resizeIcon("src/images/blackBishopPic.png", 50, 50);
        }
    }
}
