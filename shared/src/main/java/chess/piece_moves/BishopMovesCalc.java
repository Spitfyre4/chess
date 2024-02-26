package chess.piece_moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMovesCalc extends PieceMovesCalc{

    private final ArrayList<ChessMove> legalMoves = new ArrayList<ChessMove>();
    public BishopMovesCalc(ChessBoard board, ChessPosition position) {
        super(board, position);
    }

    public boolean moveLoop(boolean isRight, int i, int j){
        ChessPosition endPos;
        ChessMove possibleMove;

        endPos = new ChessPosition(i, j);
        possibleMove = new ChessMove(position, endPos, null);
        if (offBoard(endPos)){
            return true;
        }

        if (board.getPiece(endPos) != null){
            if(board.getPiece(endPos).getTeamColor() != board.getPiece(position).getTeamColor()){
                legalMoves.add(possibleMove);
            }
            return true;
        }
        legalMoves.add(possibleMove);
        return false;
    }

    @Override
    public Collection<ChessMove> legalMoveCalc() {
        int startRow = position.getRow();
        int startCol = position.getColumn();
        int j = startCol;

        for(int i = startRow+1; i<=8; i++){ //up right Diagonal
            j++;
            boolean b = moveLoop(true, i, j);
            if(b){
                break;
            }
        }

        j = startCol;
        for(int i = startRow+1; i<=8; i++){ //up left Diagonal
            j--;
            boolean b = moveLoop(true, i, j);
            if(b){
                break;
            }
        }

        j = startCol;
        for(int i = startRow-1; i>0; i--){ //down right Diagonal
            j++;
            boolean b = moveLoop(true, i, j);
            if(b){
                break;
            }
        }

        j = startCol;
        for(int i = startRow-1; i>0; i--){ //down left Diagonal
            j--;
            boolean b = moveLoop(false, i, j);
            if(b){
                break;
            }
        }

        return legalMoves;
    }


}
