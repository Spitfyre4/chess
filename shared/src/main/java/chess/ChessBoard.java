package chess;

import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] squares = new ChessPiece[8][8];
    public ChessBoard() {
        
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
       squares[position.getRow()-1][position.getColumn()-1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return squares[position.getRow()-1][position.getColumn()-1];
    }

    @Override
    public String toString() {
        StringBuilder output_str = new StringBuilder();

        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition pos = new ChessPosition(row, col);
                ChessPiece piece = this.getPiece(pos);

                if (piece != null) {
                    output_str.append("|").append(piece.toString()).append("|");
                } else {
                    output_str.append("| |");
                }
            }

            output_str.append("\n");
        }

        return output_str.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Arrays.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        squares = new ChessPiece[8][8];
        ChessPiece pawn = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        ChessPosition pos = new ChessPosition(1,1);

        //White pawns
        for (int j = 1; j <= 8; j++){
            pawn = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
            pos = new ChessPosition(2, j);
            this.addPiece(pos, pawn);
        }

        //Black pawns
        for (int j = 1; j <= 8; j++){
            pawn = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
            pos = new ChessPosition(7, j);
            this.addPiece(pos, pawn);
        }

        //White Rook
        ChessPiece piece1 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        pos = new ChessPosition(1, 1);
        this.addPiece(pos, piece1);
        ChessPiece piece2 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        pos = new ChessPosition(1, 8);
        this.addPiece(pos, piece2);

        //Black Rook
        piece1 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        pos = new ChessPosition(8, 1);
        this.addPiece(pos, piece1);
        piece2 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        pos = new ChessPosition(8, 8);
        this.addPiece(pos, piece2);

        //White Knight
        piece1 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        pos = new ChessPosition(1, 2);
        this.addPiece(pos, piece1);
        piece2 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        pos = new ChessPosition(1, 7);
        this.addPiece(pos, piece2);

        //Black Knight
        piece1 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        pos = new ChessPosition(8, 2);
        this.addPiece(pos, piece1);
        piece2 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        pos = new ChessPosition(8, 7);
        this.addPiece(pos, piece2);

        //White Bishop
        piece1 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        pos = new ChessPosition(1, 3);
        this.addPiece(pos, piece1);
        piece2 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        pos = new ChessPosition(1, 6);
        this.addPiece(pos, piece2);

        //Black Bishop
        piece1 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        pos = new ChessPosition(8, 3);
        this.addPiece(pos, piece1);
        piece2 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        pos = new ChessPosition(8, 6);
        this.addPiece(pos, piece2);

        //White Queen
        piece1 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        pos = new ChessPosition(1, 4);
        this.addPiece(pos, piece1);

        //Black Queen
        piece1 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        pos = new ChessPosition(8, 4);
        this.addPiece(pos, piece1);

        //White King
        piece1 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        pos = new ChessPosition(1, 5);
        this.addPiece(pos, piece1);

        //Black King
        piece1 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        pos = new ChessPosition(8, 5);
        this.addPiece(pos, piece1);



    }
}
