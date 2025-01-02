import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChessGameUI extends JFrame {
    private ChessBoard board;
    private JPanel boardPanel;
    private JLabel turnLabel; // Label to display the current turn
    private boolean whiteTurn; // Track turns
    private int selectedX = -1, selectedY = -1; // Track selected piece
    private List<int[]> possibleMoves = new ArrayList<>(); // Track possible moves

    public ChessGameUI() {
        board = new ChessBoard();
        boardPanel = new JPanel(new GridLayout(8, 8));
        turnLabel = new JLabel("White's Turn", SwingConstants.CENTER); // Initialize turn label
        turnLabel.setFont(new Font("Arial", Font.BOLD, 16));
        setupBoardPanel();
        setupUI();
        whiteTurn = true; // White starts the game
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        add(turnLabel, BorderLayout.NORTH); // Add turn label to the top
        add(boardPanel, BorderLayout.CENTER);
        setSize(600, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void setupBoardPanel() {
        boardPanel.removeAll();
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
                if (i == selectedX && j == selectedY) {
                    square.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                }

                // Highlight possible moves with a blue border
                for (int[] move : possibleMoves) {
                    if (move[0] == i && move[1] == j) {
                        square.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
                    }
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
        System.out.println("Board refreshed");
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
                possibleMoves = getPossibleMoves(selectedPiece); // Get possible moves for the selected piece
            }
        } else {
            // Handle click on another ally piece
            Piece selectedPiece = board.getPiece(selectedX, selectedY);
            Piece clickedPiece = board.getPiece(x, y);
            if (clickedPiece != null && clickedPiece.isWhite == whiteTurn) {
                // Select new piece
                selectedX = x;
                selectedY = y;
                System.out.println("Selected new piece at: (" + x + ", " + y + ")");
                possibleMoves = getPossibleMoves(clickedPiece); // Get possible moves for the new selected piece
            } else {
                // Move piece
                System.out.println("Trying to move piece from (" + selectedX + ", " + selectedY + ") to (" + x + ", " + y + ")");
                if (selectedPiece.isValidMove(x, y, board)) {
                    boolean kingCaptured = board.movePiece(selectedX, selectedY, x, y);
                    if (kingCaptured) {
                        displayEndGameScreen(whiteTurn ? "White" : "Black"); // Display end game screen with winner
                    } else {
                        whiteTurn = !whiteTurn; // Switch turns
                        updateTurnLabel(); // Update the turn label
                    }
                    selectedX = -1;
                    selectedY = -1;
                    possibleMoves.clear(); // Clear possible moves
                } else {
                    System.out.println("Invalid move from: (" + selectedX + ", " + selectedY + ") to (" + x + ", " + y + ")");
                    // Deselect piece if invalid move
                    selectedX = x;
                    selectedY = y;
                    possibleMoves = getPossibleMoves(selectedPiece);
                }
            }
        }
        setupBoardPanel(); // Refresh the board
    }

    private void updateTurnLabel() {
        turnLabel.setText(whiteTurn ? "White's Turn" : "Black's Turn");
    }

    private List<int[]> getPossibleMoves(Piece piece) {
        List<int[]> moves = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (piece.isValidMove(i, j, board)) {
                    moves.add(new int[]{i, j});
                }
            }
        }
        return moves;
    }

    private void displayEndGameScreen(String winner) {
        JFrame endGameFrame = new JFrame("Game Over");
        endGameFrame.setSize(300, 200);
        endGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        endGameFrame.setLocationRelativeTo(null);

        JLabel messageLabel = new JLabel(winner + " wins!", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 20));
        JButton restartButton = new JButton("Restart Game");
        JButton titleButton = new JButton("Return to Title");

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChessGameUI().setVisible(true);
                endGameFrame.dispose(); // Close end game screen
                dispose(); // Close current game screen
            }
        });

        titleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChessGameStartScreen().setVisible(true);
                endGameFrame.dispose(); // Close end game screen
                dispose(); // Close current game screen
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(restartButton);
        buttonPanel.add(titleButton);

        endGameFrame.setLayout(new BorderLayout());
        endGameFrame.add(messageLabel, BorderLayout.CENTER);
        endGameFrame.add(buttonPanel, BorderLayout.SOUTH);

        endGameFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new ChessGameUI();
    }
}
