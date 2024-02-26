package chess.piece_moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class KingMovesCalc  extends PieceMovesCalc{
    private final ArrayList<ChessMove> legalMoves = new ArrayList<ChessMove>();
    public KingMovesCalc(ChessBoard board, ChessPosition position) {
        super(board, position);
    }

    @Override
    public Collection<ChessMove> legalMoveCalc() {

        ArrayList<ChessPosition> moves = new ArrayList<ChessPosition>();
        moves.add(new ChessPosition(1,0));
        moves.add(new ChessPosition(1,1));
        moves.add(new ChessPosition(0,1));
        moves.add(new ChessPosition(-1,1));
        moves.add(new ChessPosition(-1,0));
        moves.add(new ChessPosition(-1,-1));
        moves.add(new ChessPosition(0,-1));
        moves.add(new ChessPosition(1,-1));

        kMoves(moves, legalMoves);

        return legalMoves;
    }

}
