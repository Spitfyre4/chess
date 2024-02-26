package chess.piece_moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class RookMovesCalc  extends PieceMovesCalc{
    public RookMovesCalc(ChessBoard board, ChessPosition position) {
        super(board, position);
    }

    @Override
    public Collection<ChessMove> legalMoveCalc() {
        int startRow = position.getRow();
        int startCol = position.getColumn();
        ArrayList<ChessMove> legalMoves = new ArrayList<ChessMove>();
        ChessPosition endPos;
        ChessMove possibleMove;


        for (int i = startRow+1; i <= 8; i++){ //up

            endPos = new ChessPosition(i, startCol);
            possibleMove = new ChessMove(position, endPos, null);
            if (offBoard(endPos)){
                break;
            }

            if (board.getPiece(endPos) != null){
                if(board.getPiece(endPos).getTeamColor() != board.getPiece(position).getTeamColor()){
                    legalMoves.add(possibleMove);
                }
                break;
            }
            legalMoves.add(possibleMove);
        }

        for (int i = startRow-1; i > 0; i--){ //down
            endPos = new ChessPosition(i, startCol);
            possibleMove = new ChessMove(position, endPos, null);
            if (offBoard(endPos)){
                break;
            }

            if (board.getPiece(endPos) != null){
                if(board.getPiece(endPos).getTeamColor() != board.getPiece(position).getTeamColor()){
                    legalMoves.add(possibleMove);
                }
                break;
            }
            legalMoves.add(possibleMove);
        }

        for (int j = startCol+1; j <= 8; j++){ //right

            endPos = new ChessPosition(startRow, j);
            possibleMove = new ChessMove(position, endPos, null);
            if (offBoard(endPos)){
                break;
            }

            if (board.getPiece(endPos) != null){
                if(board.getPiece(endPos).getTeamColor() != board.getPiece(position).getTeamColor()){
                    legalMoves.add(possibleMove);
                }
                break;
            }
            legalMoves.add(possibleMove);
        }

        for (int j = startCol-1; j > 0; j--){ //left

            endPos = new ChessPosition(startRow, j);
            possibleMove = new ChessMove(position, endPos, null);
            if (offBoard(endPos)){
                break;
            }

            if (board.getPiece(endPos) != null){
                if(board.getPiece(endPos).getTeamColor() != board.getPiece(position).getTeamColor()){
                    legalMoves.add(possibleMove);
                }
                break;
            }
            legalMoves.add(possibleMove);
        }



        return legalMoves;
    }
}
