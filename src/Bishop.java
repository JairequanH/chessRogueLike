import javax.swing.*;

public class Bishop extends Piece {

    public Bishop(boolean isWhite, int x, int y) {
        this.isWhite = isWhite;
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean isValidMove(int newX, int newY, ChessBoard board) {
        return Math.abs(newX - x) == Math.abs(newY - y); // Diagonal move
    }

    @Override
    public ImageIcon getIcon() {
        if (isWhite) {
            return resizeIcon("src/images/bishopPic.png", iconX, iconY);
        }else{
            return resizeIcon("src/images/blackBishopPic.png", iconX, iconY);
        }
    }
}