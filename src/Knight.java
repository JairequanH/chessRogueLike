import javax.swing.*;

public class Knight extends Piece {

    public Knight(boolean isWhite, int x, int y) {
        this.isWhite = isWhite;
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean isValidMove(int newX, int newY, ChessBoard board) {
        int xDiff = Math.abs(newX - x);
        int yDiff = Math.abs(newY - y);
        return (xDiff == 2 && yDiff == 1) || (xDiff == 1 && yDiff == 2); // "L" shape move
    }

    @Override
    public ImageIcon getIcon() {
        if (isWhite) {
            return resizeIcon("src/images/knightPic.png", iconX, iconY);
        }else{
            return resizeIcon("src/images/blackKnightPic.png", iconX, iconY);
        }
    }
}
