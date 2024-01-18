package chess;

import chess.piece_moves.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
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

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        PieceMovesCalculator piece_calc = null;
        if(this.type == PieceType.KING){
            piece_calc = new KingMovesCalculator(board, myPosition);
        }

        if(this.type == PieceType.QUEEN){
            piece_calc = new QueenMovesCalculator(board, myPosition);
        }

        if(this.type == PieceType.BISHOP){
            piece_calc = new BishopMovesCalculator(board, myPosition);
        }

        if(this.type == PieceType.KNIGHT){
            piece_calc = new KnightMovesCalculator(board, myPosition);
        }

        if(this.type == PieceType.ROOK){
            piece_calc = new RookMovesCalculator(board, myPosition);
        }

        if(this.type == PieceType.PAWN){
            piece_calc = new PawnMovesCalculator(board, myPosition);
        }
        if (piece_calc != null) {
            return piece_calc.Legal_Moves_Calc();
        }
        else{
            throw new RuntimeException("Piece_calc never initialized");
        }
    }
}
