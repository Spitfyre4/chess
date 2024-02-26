package chess.piece_moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class KingMovesCalc  extends PieceMovesCalc{
    public KingMovesCalc(ChessBoard board, ChessPosition position) {
        super(board, position);
    }

    @Override
    public Collection<ChessMove> legalMoveCalc() {
        int startRow = position.getRow();
        int startCol = position.getColumn();
        ArrayList<ChessMove> legalMoves = new ArrayList<ChessMove>();
        ChessPosition endPos;
        ChessMove possibleMove;

        ArrayList<ChessPosition> moves = new ArrayList<ChessPosition>();
        moves.add(new ChessPosition(1,0));
        moves.add(new ChessPosition(1,1));
        moves.add(new ChessPosition(0,1));
        moves.add(new ChessPosition(-1,1));
        moves.add(new ChessPosition(-1,0));
        moves.add(new ChessPosition(-1,-1));
        moves.add(new ChessPosition(0,-1));
        moves.add(new ChessPosition(1,-1));

        for(ChessPosition pos : moves){
            int x = pos.getRow();
            int y = pos.getColumn();

            endPos = new ChessPosition(startRow + x, startCol + y);
            possibleMove = new ChessMove(position, endPos, null);

            if (offBoard(endPos)){
                continue;
            }

            if (board.getPiece(endPos) != null){
                if(board.getPiece(endPos).getTeamColor() != board.getPiece(position).getTeamColor()){
                    legalMoves.add(possibleMove);
                }
                continue;
            }
            legalMoves.add(possibleMove);
        }

        return legalMoves;
    }

}
