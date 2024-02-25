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
        int start_row = position.getRow();
        int start_col = position.getColumn();
        ArrayList<ChessMove> legal_moves = new ArrayList<ChessMove>();
        ChessPosition end_pos;
        ChessMove possible_move;

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

            end_pos = new ChessPosition(start_row + x, start_col + y);
            possible_move = new ChessMove(position, end_pos, null);

            if (offBoard(end_pos)){
                continue;
            }

            if (board.getPiece(end_pos) != null){
                if(board.getPiece(end_pos).getTeamColor() != board.getPiece(position).getTeamColor()){
                    legal_moves.add(possible_move);
                }
                continue;
            }
            legal_moves.add(possible_move);
        }

        return legal_moves;
    }

}
