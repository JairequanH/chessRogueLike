import javax.swing.*;

public class Rook extends Piece {

    public Rook(boolean isWhite, int x, int y) {
        this.isWhite = isWhite;
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean isValidMove(int newX, int newY, ChessBoard board) {
        return x == newX || y == newY; // Moves in straight lines
    }

    @Override
    public ImageIcon getIcon() {
        if (isWhite) {
            return resizeIcon("src/images/rookPic.png", iconX, iconY);
        }else{
            return resizeIcon("src/images/blackRookPic.png", iconX, iconY);
        }
    }
}
