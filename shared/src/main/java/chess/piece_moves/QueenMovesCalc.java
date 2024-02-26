package chess.piece_moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMovesCalc  extends PieceMovesCalc{
    public QueenMovesCalc(ChessBoard board, ChessPosition position) {
        super(board, position);
    }

    @Override
    public Collection<ChessMove> legalMoveCalc() {
        ArrayList<ChessMove> legalMoves = new ArrayList<ChessMove>();
        BishopMovesCalc bishopCalc = new BishopMovesCalc(board, position);
        RookMovesCalc rookCalc = new RookMovesCalc(board, position);

        legalMoves.addAll(bishopCalc.legalMoveCalc());
        legalMoves.addAll(rookCalc.legalMoveCalc());

        return legalMoves;
    }
}
