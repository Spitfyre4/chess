package chess.piece_moves;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class Pawn_Moves_Calc  extends Piece_Moves_Calc{
    public Pawn_Moves_Calc(ChessBoard board, ChessPosition position) {
        super(board, position);
    }

    @Override
    public Collection<ChessMove> legal_move_calc() {
        int start_row = position.getRow();
        int start_col = position.getColumn();
        ArrayList<ChessMove> legal_moves = new ArrayList<ChessMove>();
        ChessPosition end_pos;
        ChessMove possible_move;

        if (board.getPiece(position).getTeamColor() == ChessGame.TeamColor.WHITE) { //WHITE Moves
            if (start_row == 2) { //double move
                ChessPosition end_pos1 = new ChessPosition(start_row + 1, start_col);
                ChessMove mov1 = new ChessMove(position, end_pos1, null);

                ChessPosition end_pos2 = new ChessPosition(start_row + 2, start_col);
                ChessMove mov2 = new ChessMove(position, end_pos2, null);

                if (board.getPiece(end_pos1) == null) {
                    legal_moves.add(mov1);
                    if (board.getPiece(end_pos2) == null) {
                        legal_moves.add(mov2);
                    }
                }
            }

            end_pos = new ChessPosition(start_row + 1, start_col); //forward move
            if (end_pos.getRow() == 8) {
                if (board.getPiece(end_pos) == null) {
                    possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.BISHOP);
                    legal_moves.add(possible_move);

                    possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.KNIGHT);
                    legal_moves.add(possible_move);

                    possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.QUEEN);
                    legal_moves.add(possible_move);

                    possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.ROOK);
                    legal_moves.add(possible_move);
                }
            } else {
                if (board.getPiece(end_pos) == null) {
                    possible_move = new ChessMove(position, end_pos, null);
                    legal_moves.add(possible_move);
                }
            }


            end_pos = new ChessPosition(start_row + 1, start_col + 1); //capture up right
            if(!off_board(end_pos)){
            if (board.getPiece(end_pos) != null && board.getPiece(end_pos).piececolor != ChessGame.TeamColor.WHITE) {
                if (end_pos.getRow() == 8) {
                        possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.BISHOP);
                        legal_moves.add(possible_move);

                        possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.KNIGHT);
                        legal_moves.add(possible_move);

                        possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.QUEEN);
                        legal_moves.add(possible_move);

                        possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.ROOK);
                        legal_moves.add(possible_move);

                } else {
                    possible_move = new ChessMove(position, end_pos, null);
                    legal_moves.add(possible_move);
                }
            }
            }

            end_pos = new ChessPosition(start_row + 1, start_col - 1); //capture up left
            if(!off_board(end_pos)) {
            if (board.getPiece(end_pos) != null && board.getPiece(end_pos).piececolor != ChessGame.TeamColor.WHITE) {
                if (end_pos.getRow() == 8) {
                        possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.BISHOP);
                        legal_moves.add(possible_move);

                        possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.KNIGHT);
                        legal_moves.add(possible_move);

                        possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.QUEEN);
                        legal_moves.add(possible_move);

                        possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.ROOK);
                        legal_moves.add(possible_move);

                } else {
                    possible_move = new ChessMove(position, end_pos, null);
                    legal_moves.add(possible_move);
                }
            }
        }
            }

        if (board.getPiece(position).getTeamColor() == ChessGame.TeamColor.BLACK) { //BLACK Moves
            if (start_row == 7) { //double move
                ChessPosition end_pos1 = new ChessPosition(start_row - 1, start_col);
                ChessMove mov1 = new ChessMove(position, end_pos1, null);

                ChessPosition end_pos2 = new ChessPosition(start_row - 2, start_col);
                ChessMove mov2 = new ChessMove(position, end_pos2, null);

                if (board.getPiece(end_pos1) == null) {
                    legal_moves.add(mov1);
                    if (board.getPiece(end_pos2) == null) {
                        legal_moves.add(mov2);
                    }
                }
            }

            end_pos = new ChessPosition(start_row - 1, start_col); //forward move
            if (end_pos.getRow() == 1) {
                if (board.getPiece(end_pos) == null) {
                    possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.BISHOP);
                    legal_moves.add(possible_move);

                    possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.KNIGHT);
                    legal_moves.add(possible_move);

                    possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.QUEEN);
                    legal_moves.add(possible_move);

                    possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.ROOK);
                    legal_moves.add(possible_move);
                }
            } else {
                if (board.getPiece(end_pos) == null) {
                    possible_move = new ChessMove(position, end_pos, null);
                    legal_moves.add(possible_move);
                }
            }


            end_pos = new ChessPosition(start_row - 1, start_col - 1); //capture down left
            if(!off_board(end_pos)) {
                if (board.getPiece(end_pos) != null && board.getPiece(end_pos).piececolor != ChessGame.TeamColor.BLACK) {
                    if (end_pos.getRow() == 1) {
                        possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.BISHOP);
                        legal_moves.add(possible_move);

                        possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.KNIGHT);
                        legal_moves.add(possible_move);

                        possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.QUEEN);
                        legal_moves.add(possible_move);

                        possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.ROOK);
                        legal_moves.add(possible_move);
                    } else {
                        possible_move = new ChessMove(position, end_pos, null);
                        legal_moves.add(possible_move);
                    }
                }
            }

            end_pos = new ChessPosition(start_row - 1, start_col + 1); //capture down right
            if(!off_board(end_pos)) {
                if (board.getPiece(end_pos) != null && board.getPiece(end_pos).piececolor != ChessGame.TeamColor.BLACK) {
                    if (end_pos.getRow() == 1) {
                        possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.BISHOP);
                        legal_moves.add(possible_move);

                        possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.KNIGHT);
                        legal_moves.add(possible_move);

                        possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.QUEEN);
                        legal_moves.add(possible_move);

                        possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.ROOK);
                        legal_moves.add(possible_move);
                    } else {
                        possible_move = new ChessMove(position, end_pos, null);
                        legal_moves.add(possible_move);
                    }
                }
            }
        }


        return  legal_moves;
    }
}
