import javax.swing.*;
import java.awt.*;

public abstract class Piece {
    protected boolean isWhite;
    protected int x, y;
    protected int iconX = 50, iconY = 50;

    public Piece(){

    }

    public Piece(boolean isWhite, int x, int y) {
        this.isWhite = isWhite;
        this.x = x;
        this.y = y;
    }

    public abstract boolean isValidMove(int newX, int newY, ChessBoard board);

    public ImageIcon getIcon(){
        return resizeIcon("src/images/queenPic.png", iconX, iconY);
    };

    protected ImageIcon resizeIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
}
