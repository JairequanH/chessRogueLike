public class ChessBoard {
    private Piece[][] board;

    public ChessBoard() {
        board = new Piece[8][8];
        setupBoard();
    }

    private void setupBoard() {
        // Place Pawns
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(true, 1, i); // White Pawns
            board[6][i] = new Pawn(false, 6, i); // Black Pawns
        }
        // Place other pieces (Rooks, Knights, Bishops, Queens, Kings)
        // Add other pieces initialization code...
        board[0][0] = new Rook(true, 0, 0);
        board[0][7] = new Rook(true, 0, 7);
        board[7][0] = new Rook(false, 7, 0);
        board[7][7] = new Rook(false, 7, 7);
        // Place Knights
        board[0][1] = new Knight(true, 0, 1);
        board[0][6] = new Knight(true, 0, 6);
        board[7][1] = new Knight(false, 7, 1);
        board[7][6] = new Knight(false, 7, 6);
        // Place Bishops
        board[0][2] = new Bishop(true, 0, 2);
        board[0][5] = new Bishop(true, 0, 5);
        board[7][2] = new Bishop(false, 7, 2);
        board[7][5] = new Bishop(false, 7, 5);
        // Place Queens
        board[0][3] = new Queen(true, 0, 3);
        board[7][3] = new Queen(false, 7, 3);
        // Place Kings
        board[0][4] = new King(true, 0, 4);
        board[7][4] = new King(false, 7, 4);
    }

    public Piece getPiece(int x, int y) {
        return board[x][y];
    }

    public boolean movePiece(int startX, int startY, int endX, int endY) {
        Piece piece = board[startX][startY];
        Piece capturedPiece = board[endX][endY];

        piece.move(endX, endY); // Use move method to update position
        board[endX][endY] = piece;
        board[startX][startY] = null;

        if (capturedPiece != null && capturedPiece instanceof King) {
            return true; // King captured
        }

        return false;
    }
}
