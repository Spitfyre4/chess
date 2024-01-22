package chess.piece_moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class KnightMovesCalculator extends PieceMovesCalculator{

    public KnightMovesCalculator(ChessBoard board, ChessPosition position){
        super(board, position);
    }

    public Collection<ChessMove> Legal_Moves_Calc() {
        int start_row = position.getRow();
        int start_col = position.getColumn();
        ArrayList<ChessMove> legal_moves = new ArrayList<ChessMove>();

        ChessPosition end_pos = new ChessPosition(start_row, start_col);
        ChessMove possible_move = new ChessMove(position, end_pos, null);

        int[][] moves = {{2,1}, {2,1}, {1,2}, {2,-1}, {1,-2}, {-1,-2}, {-2,-1}, {-2,1}, {-1,2}};

        for (int[] move: moves) {
            end_pos = new ChessPosition(start_row + move[0], start_col + move[1]);
            possible_move = new ChessMove(position, end_pos, null);
            if(!this.off_board(end_pos)){
                if(board.getPiece(end_pos) == null){
                    legal_moves.add(possible_move);
                } else if (board.getPiece(end_pos).getTeamColor() != board.getPiece(position).getTeamColor()) {
                    legal_moves.add(possible_move);
                }
            }
        }

        return legal_moves;

    }
}
