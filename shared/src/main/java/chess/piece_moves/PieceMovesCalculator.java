package chess.piece_moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;

public abstract class PieceMovesCalculator{

    protected final ChessBoard board;
    protected final ChessPosition position;

    public PieceMovesCalculator(ChessBoard board, ChessPosition position){
        this.board = board;
        this.position = position;
    }

    public abstract Collection<ChessMove> Legal_Moves_Calc();

//    public PieceMovesCalculator() {
//        this.board = new ChessBoard();
//        this.position = new ChessPosition(0, 0);
//    }
}
