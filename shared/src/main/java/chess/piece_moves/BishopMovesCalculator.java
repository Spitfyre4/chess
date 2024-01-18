package chess.piece_moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;
public class BishopMovesCalculator extends PieceMovesCalculator{

    public BishopMovesCalculator(ChessBoard board, ChessPosition position){
        super(board, position);
    }
    public Collection<ChessMove> Legal_Moves_Calc(){
        int start_row = position.getRow();
        int start_col = position.getColumn();
        ArrayList<ChessMove> legal_moves = new ArrayList<ChessMove>();
        for (int i = start_row+1; i < 8; i++) { //up-right diagonal
            int j = start_col;
            j++;
            ChessPosition end_pos = new ChessPosition(i, j);
            ChessMove possible_move = new ChessMove(position, end_pos, null);
            if (board.getPiece(end_pos)!= null){
                break;
            }
            legal_moves.add(possible_move);
        }

        for (int i = start_row-1; i > 0; i--) { //up-left diagonal
            int j = start_col;
            j++;
            ChessPosition end_pos = new ChessPosition(i, j);
            ChessMove possible_move = new ChessMove(position, end_pos, null);
            if (board.getPiece(end_pos)!= null){
                break;
            }
            legal_moves.add(possible_move);
        }

        for (int i = start_row+1; i < 8; i++) { //down-right diagonal
            int j = start_col;
            j--;
            ChessPosition end_pos = new ChessPosition(i, j);
            ChessMove possible_move = new ChessMove(position, end_pos, null);
            if (board.getPiece(end_pos)!= null){
                break;
            }
            legal_moves.add(possible_move);
        }

        for (int i = start_row-1; i > 0; i--) { //down-left diagonal
            int j = start_col;
            j--;
            ChessPosition end_pos = new ChessPosition(i, j);
            ChessMove possible_move = new ChessMove(position, end_pos, null);
            if (board.getPiece(end_pos)!= null){
                break;
            }
            legal_moves.add(possible_move);
        }

        return  legal_moves;
    }
}
