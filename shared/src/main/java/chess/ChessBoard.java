package chess;

import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] square = new ChessPiece[8][8];

    public ChessBoard() {

    }

    public ChessBoard(ChessBoard ogBoard){
        for(int i = 1; i <=8; i++){
            for(int j = 1; j <=8; j++){
                ChessPosition pos = new ChessPosition(i, j);
                ChessPiece piece = ogBoard.getPiece(pos);
                if(piece != null) {
                    this.addPiece(pos, piece);
                }

            }
        }
    }

    public void removePiece(ChessPosition position){
        square[position.getRow()-1][position.getColumn()-1] = null;
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        square[position.getRow()-1][position.getColumn()-1] = piece;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Arrays.deepEquals(square, that.square);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(square);
    }



    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return square[position.getRow()-1][position.getColumn()-1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        square = new ChessPiece[8][8];
        ChessPosition pos;
        ChessPiece piece;

        for(int j = 1; j <=8; j++){ //Pawns
            pos = new ChessPosition(2, j);
            ChessPiece wPawn = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
            this.addPiece(pos, wPawn);

            pos = new ChessPosition(7, j);
            ChessPiece bPawn = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
            this.addPiece(pos, bPawn);
        }

        //ROOKS
        pos = new ChessPosition(1, 1); //White Rook 1
        piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        this.addPiece(pos, piece);

        pos = new ChessPosition(1, 8); //White Rook 2
        piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        this.addPiece(pos, piece);

        pos = new ChessPosition(8, 1); //Black Rook 1
        piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        this.addPiece(pos, piece);

        pos = new ChessPosition(8, 8); //Black Rook 2
        piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        this.addPiece(pos, piece);


        //KNIGHTS
        pos = new ChessPosition(1, 2); //White Knight 1
        piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        this.addPiece(pos, piece);

        pos = new ChessPosition(1, 7); //White Knight 2
        piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        this.addPiece(pos, piece);

        pos = new ChessPosition(8, 2); //Black Knight 1
        piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        this.addPiece(pos, piece);

        pos = new ChessPosition(8, 7); //Black Knight 2
        piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        this.addPiece(pos, piece);


        //BISHOP
        pos = new ChessPosition(1, 3); //White Bishop 1
        piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        this.addPiece(pos, piece);

        pos = new ChessPosition(1, 6); //White Bishop 2
        piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        this.addPiece(pos, piece);

        pos = new ChessPosition(8, 3); //Black Bishop 1
        piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        this.addPiece(pos, piece);

        pos = new ChessPosition(8, 6); //Black Bishop 2
        piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        this.addPiece(pos, piece);


        //QUEEN
        pos = new ChessPosition(1, 4); //White Queen
        piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        this.addPiece(pos, piece);

        pos = new ChessPosition(8, 4); //Black Queen
        piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        this.addPiece(pos, piece);


        //KING
        pos = new ChessPosition(1, 5); //White King
        piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        this.addPiece(pos, piece);

        pos = new ChessPosition(8, 5); //Black King
        piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        this.addPiece(pos, piece);




    }
}
