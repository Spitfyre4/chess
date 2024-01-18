package chess.piece_moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;

public class PawnMovesCalculator extends PieceMovesCalculator{

    public PawnMovesCalculator(ChessBoard board, ChessPosition position){
        super(board, position);
    }

    public Collection<ChessMove> Legal_Moves_Calc() {
        throw new RuntimeException("Not implemented");
    }
}
