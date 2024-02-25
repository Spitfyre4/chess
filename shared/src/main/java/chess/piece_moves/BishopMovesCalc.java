package chess.piece_moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMovesCalc extends PieceMovesCalc{
    public BishopMovesCalc(ChessBoard board, ChessPosition position) {
        super(board, position);
    }

    @Override
    public Collection<ChessMove> legalMoveCalc() {
        int startRow = position.getRow();
        int start_col = position.getColumn();
        ArrayList<ChessMove> legal_moves = new ArrayList<ChessMove>();
        ChessPosition end_pos;
        ChessMove possible_move;

        int j = start_col;
        for(int i = startRow+1; i<=8; i++){ //up right Diagonal
            j++;

            end_pos = new ChessPosition(i, j);
            possible_move = new ChessMove(position, end_pos, null);
            if (offBoard(end_pos)){
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

        j = start_col;
        for(int i = startRow+1; i<=8; i++){ //up left Diagonal
            j--;

            end_pos = new ChessPosition(i, j);
            possible_move = new ChessMove(position, end_pos, null);
            if (offBoard(end_pos)){
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

        j = start_col;
        for(int i = startRow-1; i>0; i--){ //down right Diagonal
            j++;

            end_pos = new ChessPosition(i, j);
            possible_move = new ChessMove(position, end_pos, null);
            if (offBoard(end_pos)){
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

        j = start_col;
        for(int i = startRow-1; i>0; i--){ //down left Diagonal
            j--;

            end_pos = new ChessPosition(i, j);
            possible_move = new ChessMove(position, end_pos, null);
            if (offBoard(end_pos)){
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
