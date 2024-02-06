package chess.piece_moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class RookMovesCalculator extends PieceMovesCalculator{

    public RookMovesCalculator(ChessBoard board, ChessPosition position){
        super(board, position);
    }

    public Collection<ChessMove> Legal_Moves_Calc() {
        int start_row = position.getRow();
        int start_col = position.getColumn();
        ArrayList<ChessMove> legal_moves = new ArrayList<ChessMove>();

        ChessPosition end_pos = new ChessPosition(start_row, start_col);
        ChessMove possible_move = new ChessMove(position, end_pos, null);

        for (int i = start_row+1; i <= 8; i++) { //up
            if (this.off_board(i, start_col)){
                break;
            }
            end_pos = new ChessPosition(i, start_col);
            possible_move = new ChessMove(position, end_pos, null);
            if (board.getPiece(end_pos)!= null){
                if(board.getPiece(end_pos).getTeamColor() != board.getPiece(position).getTeamColor()){
                    legal_moves.add(possible_move);
                }
                break;
            }
            legal_moves.add(possible_move);
        }

        for (int i = start_row-1; i >= 1; i--) { //down
            if (this.off_board(i, start_col)){
                break;
            }
            end_pos = new ChessPosition(i, start_col);
            possible_move = new ChessMove(position, end_pos, null);
            if (board.getPiece(end_pos)!= null){
                if(board.getPiece(end_pos).getTeamColor() != board.getPiece(position).getTeamColor()){
                    legal_moves.add(possible_move);
                }
                break;
            }
            legal_moves.add(possible_move);
        }

        for (int j = start_col+1; j <= 8; j++) { //right
            if (this.off_board(start_row, j)){
                break;
            }
            end_pos = new ChessPosition(start_row, j);
            possible_move = new ChessMove(position, end_pos, null);
            if (board.getPiece(end_pos)!= null){
                if(board.getPiece(end_pos).getTeamColor() != board.getPiece(position).getTeamColor()){
                    legal_moves.add(possible_move);
                }
                break;
            }
            legal_moves.add(possible_move);
        }

        for (int j = start_col-1; j >- 1; j--) { //left
            if (this.off_board(start_row, j)){
                break;
            }
            end_pos = new ChessPosition(start_row, j);
            possible_move = new ChessMove(position, end_pos, null);
            if (board.getPiece(end_pos)!= null){
                if(board.getPiece(end_pos).getTeamColor() != board.getPiece(position).getTeamColor()){
                    legal_moves.add(possible_move);
                }
                break;
            }
            legal_moves.add(possible_move);
        }
        return  legal_moves;
    }
}
