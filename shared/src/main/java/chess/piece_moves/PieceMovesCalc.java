package chess.piece_moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;

public abstract class PieceMovesCalc {
    protected final ChessBoard board;
    protected final ChessPosition position;

    public PieceMovesCalc(ChessBoard board, ChessPosition position) {
        this.board = board;
        this.position = position;
    }

    public abstract Collection<ChessMove> legalMoveCalc();

    public boolean offBoard(ChessPosition pos){
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