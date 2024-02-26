package chess;


import chess.piece_moves.*;

import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    public final PieceType type;
    public final ChessGame.TeamColor pieceColor;
    public ChessPiece(ChessGame.TeamColor pieceColor, PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return type == that.type && pieceColor == that.pieceColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, pieceColor);
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        PieceMovesCalc moveCalc = null;
        ChessPiece piece = board.getPiece(myPosition);

        if (piece.type == PieceType.BISHOP){
            moveCalc = new BishopMovesCalc(board, myPosition);
        }

        if (piece.type == PieceType.KING){
            moveCalc = new KingMovesCalc(board, myPosition);
        }

        if (piece.type == PieceType.KNIGHT){
            moveCalc = new KnightMovesCalc(board, myPosition);
        }

        if (piece.type == PieceType.PAWN){
            moveCalc = new PawnMovesCalc(board, myPosition);
        }

        if (piece.type == PieceType.QUEEN){
            moveCalc = new QueenMovesCalc(board, myPosition);
        }

        if (piece.type == PieceType.ROOK){
            moveCalc = new RookMovesCalc(board, myPosition);
        }

        if (moveCalc != null) {
            return moveCalc.legalMoveCalc();
        }
        else{
            throw new RuntimeException("moveCalc never initialized");
        }
    }
}
