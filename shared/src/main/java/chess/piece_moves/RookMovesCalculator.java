package chess.piece_moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;

public class RookMovesCalculator extends PieceMovesCalculator{

    public RookMovesCalculator(ChessBoard board, ChessPosition position){
        super(board, position);
    }

    public Collection<ChessMove> Legal_Moves_Calc() {
        throw new RuntimeException("Not implemented");
    }
}
