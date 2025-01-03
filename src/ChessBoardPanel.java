import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChessBoardPanel extends JPanel {
    private ChessGameState gameState;

    public ChessBoardPanel(ChessGameState gameState) {
        this.gameState = gameState;
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

                // Highlight selected square with a yellow border
                if (gameState.isSelectedSquare(i, j)) {
                    square.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                }

                // Highlight possible moves with a blue border
                if (gameState.isPossibleMove(i, j)) {
                    square.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
                }

                Piece piece = gameState.getPiece(i, j);
                if (piece != null) {
                    JLabel pieceLabel = new JLabel(piece.getIcon());
                    square.add(pieceLabel);

                    // Highlight king in red if in check
                    if (piece instanceof King && gameState.isKingInCheck(piece.isWhite)) {
                        square.setBackground(Color.RED);
                    }
                }
                final int x = i;
                final int y = j;
                square.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        gameState.handleSquareClick(x, y);
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

