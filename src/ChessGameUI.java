import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChessGameUI extends JFrame {
    private ChessBoard board;
    private JPanel boardPanel;
    private boolean whiteTurn; // Track turns
    private int selectedX = -1, selectedY = -1; // Track selected piece

    public ChessGameUI() {
        board = new ChessBoard();
        boardPanel = new JPanel(new GridLayout(8, 8));
        setupBoardPanel();
        add(boardPanel);
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        whiteTurn = true; // White starts the game
    }

    private void setupBoardPanel() {
        boardPanel.removeAll();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JPanel square = new JPanel(new BorderLayout());
                if ((i + j) % 2 == 0) {
                    square.setBackground(Color.WHITE);
                } else {
                    square.setBackground(Color.GRAY);
                }
                Piece piece = board.getPiece(i, j);
                if (piece != null) {
                    JLabel pieceLabel = new JLabel(piece.getIcon());
                    square.add(pieceLabel);
                }
                final int x = i;
                final int y = j;
                square.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        System.out.println("Clicked square: (" + x + ", " + y + ")");
                        handleSquareClick(x, y);
                    }
                });
                boardPanel.add(square);
            }
        }
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    private void handleSquareClick(int x, int y) {
        System.out.println("Handle Click: (" + x + ", " + y + ")");
        if (selectedX == -1 && selectedY == -1) {
            // Select piece
            Piece selectedPiece = board.getPiece(x, y);
            if (selectedPiece != null && selectedPiece.isWhite == whiteTurn) {
                selectedX = x;
                selectedY = y;
                System.out.println("Selected piece at: (" + x + ", " + y + ")");
            }
        } else {
            // Move piece
            Piece selectedPiece = board.getPiece(selectedX, selectedY);
            System.out.println("Trying to move piece from (" + selectedX + ", " + selectedY + ") to (" + x + ", " + y + ")");
            if (selectedPiece.isValidMove(x, y, board)) {
                board.movePiece(selectedX, selectedY, x, y);
                System.out.println("Moved piece to: (" + x + ", " + y + ")");
                whiteTurn = !whiteTurn; // Switch turns
            } else {
                System.out.println("Invalid move from: (" + selectedX + ", " + selectedY + ") to (" + x + ", " + y + ")");
            }
            selectedX = -1;
            selectedY = -1;
        }
        setupBoardPanel(); // Refresh the board
    }
}
