package chess.piece_moves;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalc  extends PieceMovesCalc {

    private ArrayList<ChessMove> legalMoves = new ArrayList<ChessMove>();
    public PawnMovesCalc(ChessBoard board, ChessPosition position) {
        super(board, position);
    }

    public void promotionAdd(ChessPosition pos){
        ChessMove possibleMove;
        possibleMove = new ChessMove(position, pos, ChessPiece.PieceType.BISHOP);
        legalMoves.add(possibleMove);

        possibleMove = new ChessMove(position, pos, ChessPiece.PieceType.KNIGHT);
        legalMoves.add(possibleMove);

        possibleMove = new ChessMove(position, pos, ChessPiece.PieceType.QUEEN);
        legalMoves.add(possibleMove);

        possibleMove = new ChessMove(position, pos, ChessPiece.PieceType.ROOK);
        legalMoves.add(possibleMove);
    }

    public void singleMoveLoop(int row, int col, ChessGame.TeamColor color, boolean diagonal) {
        ChessMove possibleMove;
        ChessPosition endPos = new ChessPosition(row, col);

        int lastRow = 8;
        if (color == ChessGame.TeamColor.BLACK) {
            lastRow = 1;
        }

        if(!diagonal) {
            if (endPos.getRow() == lastRow) {
                if (board.getPiece(endPos) == null) {
                    promotionAdd(endPos);
                }
            } else {
                if (board.getPiece(endPos) == null) {
                    possibleMove = new ChessMove(position, endPos, null);
                    legalMoves.add(possibleMove);
                }
            }
        }

        if(diagonal) {
            if (!offBoard(endPos)) {
                if (board.getPiece(endPos) != null && board.getPiece(endPos).pieceColor != board.getPiece(position).pieceColor) {
                    if (endPos.getRow() == 1) {
                        promotionAdd(endPos);
                    } else {
                        possibleMove = new ChessMove(position, endPos, null);
                        legalMoves.add(possibleMove);
                    }
                }
            }
        }
    }

    public void doubleMove(int row, int col, boolean isBlack){
        int direction = isBlack ? -1 : 1;
        ChessPosition endPos1 = new ChessPosition(row + direction, col);
        ChessMove mov1 = new ChessMove(position, endPos1, null);

        ChessPosition endPos2 = new ChessPosition(row + (2 * direction), col);
        ChessMove mov2 = new ChessMove(position, endPos2, null);

        if (board.getPiece(endPos1) == null) {
            legalMoves.add(mov1);
            if (board.getPiece(endPos2) == null) {
                legalMoves.add(mov2);
            }
        }
    }

    @Override
    public Collection<ChessMove> legalMoveCalc() {
        int startRow = position.getRow();
        int startCol = position.getColumn();
        ChessPosition endPos;
        ChessMove possibleMove;

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
            
            return legalMoves; //FIX pieceColor
        }
    }

