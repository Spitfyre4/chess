package chess.piece_moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;

public abstract class Piece_Moves_Calc {
    protected final ChessBoard board;
    protected final ChessPosition position;

    public Piece_Moves_Calc(ChessBoard board, ChessPosition position) {
        this.board = board;
        this.position = position;
    }

    public abstract Collection<ChessMove> legal_move_calc();

    public boolean off_board(ChessPosition pos){
        int row = pos.getRow();
        int col = pos.getColumn();

        if(row > 8 || row <= 0 || col > 8 || col <= 0){
            return true;
        }
        else{
            return false;
        }
    }

}