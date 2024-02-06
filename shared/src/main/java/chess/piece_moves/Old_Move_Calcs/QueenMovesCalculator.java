package chess.piece_moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMovesCalculator extends PieceMovesCalculator{

    public QueenMovesCalculator(ChessBoard board, ChessPosition position){
        super(board, position);
    }

    public Collection<ChessMove> Legal_Moves_Calc() {
//        int start_row = position.getRow();
//        int start_col = position.getColumn();

//
//        ChessPosition end_pos = new ChessPosition(start_row, start_col);
//        ChessMove possible_move = new ChessMove(position, end_pos, null);
//
//        throw new RuntimeException();
        ArrayList<ChessMove> legal_moves = new ArrayList<ChessMove>();

        BishopMovesCalculator bishop_calc = new BishopMovesCalculator(board, position);
        RookMovesCalculator rook_calc = new RookMovesCalculator(board, position);

        Collection<ChessMove> bishop_moves = bishop_calc.Legal_Moves_Calc();
        Collection<ChessMove> rook_moves = rook_calc.Legal_Moves_Calc();

        legal_moves.addAll(bishop_moves);

        legal_moves.addAll(rook_moves);

        return legal_moves;

    }
}
