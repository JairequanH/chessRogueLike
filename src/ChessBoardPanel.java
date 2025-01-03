import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;

public class ChessBoardPanel extends JPanel {
    private GameMode gameMode;

    public ChessBoardPanel(GameMode gameMode) {
        this.gameMode = gameMode;
        setLayout(new GridLayout(8, 8));
        setupBoard();
    }

    private void setupBoard() {
        removeAll();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JPanel square = new JPanel(new BorderLayout());
                square.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add a border to each square

                if ((i + j) % 2 == 0) {
                    square.setBackground(Color.WHITE);
                } else {
                    square.setBackground(Color.GRAY);
                }

                boolean isSelected = gameMode.isSelectedSquare(gameMode.getSelectedX(), gameMode.getSelectedY(), i, j);
                List<int[]> possibleMoves = new ArrayList<>();
                if (gameMode.getSelectedX() != -1 && gameMode.getSelectedY() != -1) {
                    possibleMoves = gameMode.getPossibleMoves(gameMode.getPiece(gameMode.getSelectedX(), gameMode.getSelectedY()));
                }

                // Highlight selected square with a yellow border
                if (isSelected) {
                    square.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                }

                // Highlight possible moves with a blue border
                for (int[] move : possibleMoves) {
                    if (move[0] == i && move[1] == j) {
                        square.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
                    }
                }

                Piece piece = gameMode.getPiece(i, j);
                if (piece != null) {
                    JLabel pieceLabel = new JLabel(piece.getIcon());
                    square.add(pieceLabel);

                    // Highlight king in red if in check
                    if (piece instanceof King && gameMode.isKingInCheck(piece.isWhite)) {
                        square.setBackground(Color.RED);
                    }
                }
                final int x = i;
                final int y = j;
                square.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        gameMode.handleSquareClick(x, y);
                        setupBoard(); // Refresh the board
                    }
                });
                add(square);
            }
        }
        revalidate();
        repaint();
    }
}
