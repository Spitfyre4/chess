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

    public boolean off_board(int i, int j){
        if(i<1 || i>8){
            return true;
        }
        else if(j<1 || j>8){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean off_board(ChessPosition pos){
        int i = pos.getRow();
        int j = pos.getColumn();
        return this.off_board(i, j);
    }

//    public PieceMovesCalculator() {
//        this.board = new ChessBoard();
//        this.position = new ChessPosition(0, 0);
//    }
}
