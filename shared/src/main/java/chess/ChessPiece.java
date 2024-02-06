package chess;

import chess.piece_moves.Bishop_Moves_Calc;
import chess.piece_moves.King_Moves_Calc;
import chess.piece_moves.Piece_Moves_Calc;
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
    public final ChessGame.TeamColor piececolor;
    public ChessPiece(ChessGame.TeamColor pieceColor, PieceType type) {
        this.piececolor = pieceColor;
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
        return piececolor;
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
        return type == that.type && piececolor == that.piececolor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, piececolor);
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Piece_Moves_Calc move_calc = null;
        ChessPiece piece = board.getPiece(myPosition);

        if (piece.type == PieceType.BISHOP){
            move_calc = new Bishop_Moves_Calc(board, myPosition);
        }

        if (piece.type == PieceType.KING){
            move_calc = new King_Moves_Calc(board, myPosition);
        }

        if (piece.type == PieceType.KNIGHT){
            move_calc = new Knight_Moves_Calc(board, myPosition);
        }

        if (piece.type == PieceType.PAWN){
            move_calc = new Pawn_Moves_Calc(board, myPosition);
        }

        if (piece.type == PieceType.QUEEN){
            move_calc = new Queen_Moves_Calc(board, myPosition);
        }

        if (piece.type == PieceType.ROOK){
            move_calc = new Rook_Moves_Calc(board, myPosition);
        }

        if (move_calc != null) {
            return move_calc.legal_move_calc();
        }
        else{
            throw new RuntimeException("move_calc never initialized");
        }
    }
}
