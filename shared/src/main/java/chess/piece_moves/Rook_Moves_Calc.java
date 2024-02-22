package chess.piece_moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class Rook_Moves_Calc  extends Piece_Moves_Calc{
    public Rook_Moves_Calc(ChessBoard board, ChessPosition position) {
        super(board, position);
    }

    @Override
    public Collection<ChessMove> legal_move_calc() {
        int start_row = position.getRow();
        int start_col = position.getColumn();
        ArrayList<ChessMove> legal_moves = new ArrayList<ChessMove>();
        ChessPosition end_pos;
        ChessMove possible_move;


        for (int i = start_row+1; i <= 8; i++){ //up

            end_pos = new ChessPosition(i, start_col);
            possible_move = new ChessMove(position, end_pos, null);
            if (off_board(end_pos)){
                break;
            }

            if (board.getPiece(end_pos) != null){
                if(board.getPiece(end_pos).getTeamColor() != board.getPiece(position).getTeamColor()){
                    legal_moves.add(possible_move);
                }
                break;
            }
            legal_moves.add(possible_move);
        }

        for (int i = start_row-1; i > 0; i--){ //down
            end_pos = new ChessPosition(i, start_col);
            possible_move = new ChessMove(position, end_pos, null);
            if (off_board(end_pos)){
                break;
            }

            if (board.getPiece(end_pos) != null){
                if(board.getPiece(end_pos).getTeamColor() != board.getPiece(position).getTeamColor()){
                    legal_moves.add(possible_move);
                }
                break;
            }
            legal_moves.add(possible_move);
        }

        for (int j = start_col+1; j <= 8; j++){ //right

            end_pos = new ChessPosition(start_row, j);
            possible_move = new ChessMove(position, end_pos, null);
            if (off_board(end_pos)){
                break;
            }

            if (board.getPiece(end_pos) != null){
                if(board.getPiece(end_pos).getTeamColor() != board.getPiece(position).getTeamColor()){
                    legal_moves.add(possible_move);
                }
                break;
            }
            legal_moves.add(possible_move);
        }

        for (int j = start_col-1; j > 0; j--){ //left

            end_pos = new ChessPosition(start_row, j);
            possible_move = new ChessMove(position, end_pos, null);
            if (off_board(end_pos)){
                break;
            }

            if (board.getPiece(end_pos) != null){
                if(board.getPiece(end_pos).getTeamColor() != board.getPiece(position).getTeamColor()){
                    legal_moves.add(possible_move);
                }
                break;
            }
            legal_moves.add(possible_move);
        }



        return legal_moves;
    }
}