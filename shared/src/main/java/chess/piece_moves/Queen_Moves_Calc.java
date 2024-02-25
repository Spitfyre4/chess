package chess.piece_moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class Queen_Moves_Calc  extends PieceMovesCalc{
    public Queen_Moves_Calc(ChessBoard board, ChessPosition position) {
        super(board, position);
    }

    @Override
    public Collection<ChessMove> legalMoveCalc() {
        ArrayList<ChessMove> legal_moves = new ArrayList<ChessMove>();
        Bishop_Moves_Calc bishop_calc = new Bishop_Moves_Calc(board, position);
        Rook_Moves_Calc rook_calc = new Rook_Moves_Calc(board, position);

        legal_moves.addAll(bishop_calc.legalMoveCalc());
        legal_moves.addAll(rook_calc.legalMoveCalc());

        return legal_moves;
    }
}
