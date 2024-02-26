package chess.piece_moves;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalc  extends PieceMovesCalc {

    private ArrayList<ChessMove> legal_moves = new ArrayList<ChessMove>();
    public PawnMovesCalc(ChessBoard board, ChessPosition position) {
        super(board, position);
    }

    public void promotionAdd(ChessPosition pos){
        ChessMove possible_move;
        possible_move = new ChessMove(position, pos, ChessPiece.PieceType.BISHOP);
        legal_moves.add(possible_move);

        possible_move = new ChessMove(position, pos, ChessPiece.PieceType.KNIGHT);
        legal_moves.add(possible_move);

        possible_move = new ChessMove(position, pos, ChessPiece.PieceType.QUEEN);
        legal_moves.add(possible_move);

        possible_move = new ChessMove(position, pos, ChessPiece.PieceType.ROOK);
        legal_moves.add(possible_move);
    }

    public void singleMoveLoop(int row, int col, ChessGame.TeamColor color, boolean diagonal) {
        ChessMove possible_move;
        ChessPosition end_pos = new ChessPosition(row, col);

        int last_row = 8;
        if (color == ChessGame.TeamColor.BLACK) {
            last_row = 1;
        }

        if(!diagonal) {
            if (end_pos.getRow() == last_row) {
                if (board.getPiece(end_pos) == null) {
                    promotionAdd(end_pos);
                }
            } else {
                if (board.getPiece(end_pos) == null) {
                    possible_move = new ChessMove(position, end_pos, null);
                    legal_moves.add(possible_move);
                }
            }
        }

        if(diagonal) {
            if (!offBoard(end_pos)) {
                if (board.getPiece(end_pos) != null && board.getPiece(end_pos).pieceColor != board.getPiece(position).pieceColor) {
                    if (end_pos.getRow() == 1) {
                        promotionAdd(end_pos);
                    } else {
                        possible_move = new ChessMove(position, end_pos, null);
                        legal_moves.add(possible_move);
                    }
                }
            }
        }
    }

    public void doubleMove(int row, int col, boolean isBlack){
        int direction = isBlack ? -1 : 1;
        ChessPosition end_pos1 = new ChessPosition(row + direction, col);
        ChessMove mov1 = new ChessMove(position, end_pos1, null);

        ChessPosition end_pos2 = new ChessPosition(row + (2 * direction), col);
        ChessMove mov2 = new ChessMove(position, end_pos2, null);

        if (board.getPiece(end_pos1) == null) {
            legal_moves.add(mov1);
            if (board.getPiece(end_pos2) == null) {
                legal_moves.add(mov2);
            }
        }
    }

    @Override
    public Collection<ChessMove> legalMoveCalc() {
        int startRow = position.getRow();
        int startCol = position.getColumn();
        ChessPosition end_pos;
        ChessMove possible_move;

        if (board.getPiece(position).getTeamColor() == ChessGame.TeamColor.WHITE) { //WHITE Moves
            if (startRow == 2) { //double move
                doubleMove(startRow, startCol, false);
            }

            //forward move
            this.singleMoveLoop(startRow + 1, startCol, ChessGame.TeamColor.WHITE, false);

            //capture up right
            this.singleMoveLoop(startRow + 1, startCol + 1, ChessGame.TeamColor.WHITE, true);

            //capture up left
            this.singleMoveLoop(startRow + 1, startCol - 1, ChessGame.TeamColor.WHITE, true);

        }

            if (board.getPiece(position).getTeamColor() == ChessGame.TeamColor.BLACK) { //BLACK Moves
                if (startRow == 7) { //double move
                    doubleMove(startRow, startCol, true);
                }
                //forward move
                this.singleMoveLoop(startRow - 1, startCol, ChessGame.TeamColor.BLACK, false);

                //capture down left
                this.singleMoveLoop(startRow - 1, startCol-1, ChessGame.TeamColor.BLACK, true);

                //capture down right
                this.singleMoveLoop(startRow - 1, startCol+1, ChessGame.TeamColor.BLACK, true);
                
            }
            
            return legal_moves; //FIX pieceColor
        }
    }

