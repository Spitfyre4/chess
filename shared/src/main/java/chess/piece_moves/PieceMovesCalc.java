package chess.piece_moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
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

    public void kMoves(ArrayList<ChessPosition> moves, ArrayList<ChessMove> legalMoves){
        int startRow = position.getRow();
        int startCol = position.getColumn();
        ChessPosition endPos;
        ChessMove possibleMove;

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
    }

    public boolean bishopRookAdd(ChessPosition endPos, ChessMove possibleMove, ArrayList<ChessMove> legalMoves){
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

}