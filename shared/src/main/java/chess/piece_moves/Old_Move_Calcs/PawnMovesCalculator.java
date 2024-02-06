package chess.piece_moves;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalculator extends PieceMovesCalculator {

    public PawnMovesCalculator(ChessBoard board, ChessPosition position) {
        super(board, position);
    }

    public Collection<ChessMove> Legal_Moves_Calc() {
        int start_row = position.getRow();
        int start_col = position.getColumn();
        ArrayList<ChessMove> legal_moves = new ArrayList<ChessMove>();

        ChessPosition end_pos = new ChessPosition(start_row, start_col);
        ChessMove possible_move = new ChessMove(position, end_pos, null);


        if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
            boolean promo = false;

            if (position.getRow() == 2) { //does forward motion
                ChessPosition move_1 = new ChessPosition(start_row + 1, start_col);
                ChessPosition move_2 = new ChessPosition(start_row + 2, start_col);
                possible_move = new ChessMove(position, move_2, null);
                if (board.getPiece(move_1) == null && board.getPiece(move_2) == null) {
                    legal_moves.add(possible_move);
                }

                possible_move = new ChessMove(position, move_1, null);
                if (board.getPiece(move_1) == null) {
                    legal_moves.add(possible_move);
                }
            } else {
                end_pos = new ChessPosition(start_row + 1, start_col);
                if (end_pos.getRow() == 8) {
                    promo = true;
                }
                if (promo) {
                    possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.ROOK);
                    if (board.getPiece(end_pos) == null) {
                        legal_moves.add(possible_move);
                    }

                    possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.KNIGHT);
                    if (board.getPiece(end_pos) == null) {
                        legal_moves.add(possible_move);
                    }

                    possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.BISHOP);
                    if (board.getPiece(end_pos) == null) {
                        legal_moves.add(possible_move);
                    }

                    possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.QUEEN);
                    if (board.getPiece(end_pos) == null) {
                        legal_moves.add(possible_move);
                    }
                } else {
                    possible_move = new ChessMove(position, end_pos, null);
                    if (board.getPiece(end_pos) == null) {
                        legal_moves.add(possible_move);
                    }
                }
            }

            end_pos = new ChessPosition(start_row + 1, start_col + 1); // up right diagonal
            if(board.getPiece(end_pos) != null) {
                if (board.getPiece(end_pos).getTeamColor() == ChessGame.TeamColor.BLACK) {
                    if (end_pos.getRow() == 8) {
                        promo = true;
                    }
                    if (promo) {
                        possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.ROOK);
                        legal_moves.add(possible_move);


                        possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.KNIGHT);
                        legal_moves.add(possible_move);


                        possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.BISHOP);
                        legal_moves.add(possible_move);


                        possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.QUEEN);
                        legal_moves.add(possible_move);

                    } else {
                        possible_move = new ChessMove(position, end_pos, null);
                        legal_moves.add(possible_move);

                    }
                }
            }
            if(board.getPiece(end_pos) != null){
                end_pos = new ChessPosition(start_row + 1, start_col - 1); // up left diagonal
                if (board.getPiece(end_pos).getTeamColor() == ChessGame.TeamColor.BLACK) {
                    if (end_pos.getRow() == 8) {
                        promo = true;
                    }
                    if (promo) {
                        possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.ROOK);
                        legal_moves.add(possible_move);

                        possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.KNIGHT);
                        legal_moves.add(possible_move);


                        possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.BISHOP);
                        legal_moves.add(possible_move);


                        possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.QUEEN);
                        legal_moves.add(possible_move);

                    } else {
                        possible_move = new ChessMove(position, end_pos, null);
                        legal_moves.add(possible_move);

                    }
                }
            }
        }


        if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.BLACK)) {
            boolean promo = false;

            if (position.getRow() == 7) { //does forward motion
                ChessPosition move_1 = new ChessPosition(start_row - 1, start_col);
                ChessPosition move_2 = new ChessPosition(start_row - 2, start_col);
                possible_move = new ChessMove(position, move_2, null);
                if (board.getPiece(move_1) == null && board.getPiece(move_2) == null) {
                    legal_moves.add(possible_move);
                }

                possible_move = new ChessMove(position, move_1, null);
                if (board.getPiece(move_1) == null) {
                    legal_moves.add(possible_move);
                }
            } else {
                end_pos = new ChessPosition(start_row -1, start_col);
                if (end_pos.getRow() == 1) {
                    promo = true;
                }
                if (promo) {
                    possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.ROOK);
                    if (board.getPiece(end_pos) == null) {
                        legal_moves.add(possible_move);
                    }

                    possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.KNIGHT);
                    if (board.getPiece(end_pos) == null) {
                        legal_moves.add(possible_move);
                    }

                    possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.BISHOP);
                    if (board.getPiece(end_pos) == null) {
                        legal_moves.add(possible_move);
                    }

                    possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.QUEEN);
                    if (board.getPiece(end_pos) == null) {
                        legal_moves.add(possible_move);
                    }
                } else {
                    possible_move = new ChessMove(position, end_pos, null);
                    if (board.getPiece(end_pos) == null) {
                        legal_moves.add(possible_move);
                    }
                }
            }

            end_pos = new ChessPosition(start_row - 1, start_col + 1); // down right diagonal
            if(board.getPiece(end_pos) != null){
                if (board.getPiece(end_pos).getTeamColor() == ChessGame.TeamColor.WHITE) {
                    if (end_pos.getRow() == 1) {
                        promo = true;
                    }
                    if (promo) {
                        possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.ROOK);
                        legal_moves.add(possible_move);


                        possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.KNIGHT);
                        legal_moves.add(possible_move);


                        possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.BISHOP);
                        legal_moves.add(possible_move);


                        possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.QUEEN);
                        legal_moves.add(possible_move);

                    } else {
                        possible_move = new ChessMove(position, end_pos, null);
                        legal_moves.add(possible_move);

                    }
                }
            }

            end_pos = new ChessPosition(start_row - 1, start_col - 1); // down left diagonal
            if(board.getPiece(end_pos) != null) {
                if (board.getPiece(end_pos).getTeamColor() == ChessGame.TeamColor.WHITE) {
                    if (end_pos.getRow() == 1) {
                        promo = true;
                    }
                    if (promo) {
                        possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.ROOK);
                        legal_moves.add(possible_move);

                        possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.KNIGHT);
                        legal_moves.add(possible_move);


                        possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.BISHOP);
                        legal_moves.add(possible_move);


                        possible_move = new ChessMove(position, end_pos, ChessPiece.PieceType.QUEEN);
                        legal_moves.add(possible_move);

                    } else {
                        possible_move = new ChessMove(position, end_pos, null);
                        legal_moves.add(possible_move);

                    }
                }
            }
        }

        return legal_moves;
    }

}

