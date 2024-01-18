package chess.piece_moves;

import chess.ChessBoard;
import chess.ChessPosition;

public class PieceMovesCalculator{

    protected final ChessBoard board;
    protected final ChessPosition position;

    public PieceMovesCalculator(ChessBoard board, ChessPosition position){
        this.board = board;
        this.position = position;
    }

//    public PieceMovesCalculator() {
//        this.board = new ChessBoard();
//        this.position = new ChessPosition(0, 0);
//    }
}
