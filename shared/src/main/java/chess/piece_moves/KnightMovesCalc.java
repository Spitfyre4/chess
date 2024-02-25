package chess.piece_moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMovesCalc extends PieceMovesCalc{
    public KnightMovesCalc(ChessBoard board, ChessPosition position) {
        super(board, position);
    }

    @Override
    public Collection<ChessMove> legalMoveCalc() {
        int startRow = position.getRow();
        int startCol = position.getColumn();
        ArrayList<ChessMove> legal_moves = new ArrayList<ChessMove>();
        ChessPosition end_pos;
        ChessMove possible_move;

        ArrayList<ChessPosition> moves = new ArrayList<ChessPosition>();
        moves.add(new ChessPosition(2,1));
        moves.add(new ChessPosition(1,2));
        moves.add(new ChessPosition(-1,2));
        moves.add(new ChessPosition(-2,1));
        moves.add(new ChessPosition(-2,-1));
        moves.add(new ChessPosition(-1,-2));
        moves.add(new ChessPosition(1,-2));
        moves.add(new ChessPosition(2,-1));

        for(ChessPosition pos : moves){
            int x = pos.getRow();
            int y = pos.getColumn();

            end_pos = new ChessPosition(startRow + x, startCol + y);
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

