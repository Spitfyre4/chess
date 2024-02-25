package chess.piece_moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMovesCalc  extends PieceMovesCalc{
    public QueenMovesCalc(ChessBoard board, ChessPosition position) {
        super(board, position);
    }

    @Override
    public Collection<ChessMove> legalMoveCalc() {
        ArrayList<ChessMove> legal_moves = new ArrayList<ChessMove>();
        BishopMovesCalc bishop_calc = new BishopMovesCalc(board, position);
        RookMovesCalc rook_calc = new RookMovesCalc(board, position);

        legal_moves.addAll(bishop_calc.legalMoveCalc());
        legal_moves.addAll(rook_calc.legalMoveCalc());

        return legal_moves;
    }
}
