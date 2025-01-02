import javax.swing.*;

public class Queen extends Piece {

    public Queen(boolean isWhite, int x, int y) {
        this.isWhite = isWhite;
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean isValidMove(int newX, int newY, ChessBoard board) {
        return x == newX || y == newY || Math.abs(newX - x) == Math.abs(newY - y); // Rook + Bishop move
    }

    @Override
    public ImageIcon getIcon() {
        if (isWhite) {
            return resizeIcon("src/images/queenPic.png", iconX, iconY);
        }else{
            return resizeIcon("src/images/blackQueenPic.png", iconX, iconY);
        }
    }
}
