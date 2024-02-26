package chess.piece_moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMovesCalc extends PieceMovesCalc{
    private ArrayList<ChessMove> legalMoves = new ArrayList<ChessMove>();
    public KnightMovesCalc(ChessBoard board, ChessPosition position) {
        super(board, position);
    }

    @Override
    public Collection<ChessMove> legalMoveCalc() {

        ArrayList<ChessPosition> moves = new ArrayList<ChessPosition>();
        moves.add(new ChessPosition(2,1));
        moves.add(new ChessPosition(1,2));
        moves.add(new ChessPosition(-1,2));
        moves.add(new ChessPosition(-2,1));
        moves.add(new ChessPosition(-2,-1));
        moves.add(new ChessPosition(-1,-2));
        moves.add(new ChessPosition(1,-2));
        moves.add(new ChessPosition(2,-1));

        kMoves(moves, legalMoves);

        return legalMoves;
    }
    }

