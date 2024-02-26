package chess.piece_moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class RookMovesCalc  extends PieceMovesCalc{
    private final ArrayList<ChessMove> legalMoves = new ArrayList<ChessMove>();

    public RookMovesCalc(ChessBoard board, ChessPosition position) {
        super(board, position);
    }

    public boolean rookMoves(int i, int j){
        ChessPosition endPos = new ChessPosition(i, j);
        ChessMove possibleMove = new ChessMove(position, endPos, null);
        return bishopRookAdd(endPos, possibleMove, legalMoves);
    }

    @Override
    public Collection<ChessMove> legalMoveCalc() {
        int startRow = position.getRow();
        int startCol = position.getColumn();

        for (int i = startRow+1; i <= 8; i++){ //up
            boolean b = rookMoves(i, startCol);
            if(b){
                break;
            }
        }

        for (int i = startRow-1; i > 0; i--){ //down
            boolean b = rookMoves(i, startCol);
            if(b){
                break;
            }
        }

        for (int j = startCol+1; j <= 8; j++){ //right
            boolean b = rookMoves(startRow, j);
            if(b){
                break;
            }
        }

        for (int j = startCol-1; j > 0; j--){ //left
            boolean b = rookMoves(startRow, j);
            if(b){
                break;
            }
        }

        return legalMoves;
    }
}
