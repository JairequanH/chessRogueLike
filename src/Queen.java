import javax.swing.*;

public class Queen extends Piece {
    public Queen(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
    }

    @Override
    public boolean isValidMove(int newX, int newY, ChessBoard board) {
        int xDiff = Math.abs(newX - this.x);
        int yDiff = Math.abs(newY - this.y);

        // Debugging statements
        System.out.println("Queen Move: (" + this.x + ", " + this.y + ") to (" + newX + ", " + newY + ")");
        System.out.println("xDiff: " + xDiff + ", yDiff: " + yDiff);

        // Check if the move is vertical, horizontal, or diagonal
        boolean isVerticalMove = (this.x == newX && this.y != newY);
        boolean isHorizontalMove = (this.y == newY && this.x != newX);
        boolean isDiagonalMove = (xDiff == yDiff);

        if (!isVerticalMove && !isHorizontalMove && !isDiagonalMove) {
            return false; // Not a valid Queen move
        }

        // Check path for obstacles
        if (isVerticalMove) {
            int minY = Math.min(this.y, newY);
            int maxY = Math.max(this.y, newY);
            for (int i = minY + 1; i < maxY; i++) {
                if (board.getPiece(this.x, i) != null) {
                    System.out.println("Obstacle at: (" + this.x + ", " + i + ")");
                    return false; // Obstacle in the way
                }
            }
        } else if (isHorizontalMove) {
            int minX = Math.min(this.x, newX);
            int maxX = Math.max(this.x, newX);
            for (int i = minX + 1; i < maxX; i++) {
                if (board.getPiece(i, this.y) != null) {
                    System.out.println("Obstacle at: (" + i + ", " + this.y + ")");
                    return false; // Obstacle in the way
                }
            }
        } else if (isDiagonalMove) {
            int xDirection = (newX - this.x) / xDiff;
            int yDirection = (newY - this.y) / yDiff;
            for (int i = 1; i < xDiff; i++) {
                if (board.getPiece(this.x + i * xDirection, this.y + i * yDirection) != null) {
                    System.out.println("Obstacle at: (" + (this.x + i * xDirection) + ", " + (this.y + i * yDirection) + ")");
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
            return resizeIcon("src/images/queenPic.png", 50, 50);
        } else {
            return resizeIcon("src/images/blackQueenPic.png", 50, 50);
        }
    }
}
