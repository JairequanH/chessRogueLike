import javax.swing.*;

public class King extends Piece {

    public King(boolean isWhite, int x, int y) {
        this.isWhite = isWhite;
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean isValidMove(int newX, int newY, ChessBoard board) {
        int xDiff = Math.abs(newX - x);
        int yDiff = Math.abs(newY - y);
        return xDiff <= 1 && yDiff <= 1; // One square move
    }

    @Override
    public ImageIcon getIcon() {
        if (isWhite) {
            return resizeIcon("src/images/kingPic.png", iconX, iconY);
        }else{
            return resizeIcon("src/images/blackKingPic.png", iconX, iconY);
        }
    }
}
