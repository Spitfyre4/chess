package chess;


import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private TeamColor turn;
    private ChessBoard board;

    public ChessGame() {
        this.turn = TeamColor.WHITE;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return this.turn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        turn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = board.getPiece(startPosition);
        if(piece == null){
            return null;
        }

        ArrayList<ChessMove> moves = new ArrayList<>();
        ChessBoard ogBoard = new ChessBoard(board);

        for(ChessMove move: piece.pieceMoves(board, startPosition)){
            board.addPiece(move.getEndPosition(), piece);
            board.removePiece(move.getStartPosition());
            if(isInCheck(ogBoard.getPiece(startPosition).getTeamColor())){
                this.board = new ChessBoard(ogBoard);
                continue;
            }
            else{
                moves.add(move);
            }
            this.board = new ChessBoard(ogBoard);
        }

        return moves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {

        ChessPosition startPosition = move.getStartPosition();
        ChessPiece piece = board.getPiece(startPosition);

        if (offBoard(move.getEndPosition()) || offBoard(move.getStartPosition()) || piece.getTeamColor()!=this.turn){
            throw new InvalidMoveException();
        }



        if(validMoves(startPosition).contains(move)){
            if (piece.getPieceType().equals(ChessPiece.PieceType.PAWN) && move.getPromotionPiece()!= null){
                piece = new ChessPiece(piece.pieceColor, move.getPromotionPiece());
            }
            board.addPiece(move.getEndPosition(), piece);
            board.removePiece(startPosition);
            if(piece.getTeamColor().equals(TeamColor.WHITE)){
                this.setTeamTurn(TeamColor.BLACK);
            }
            else {
                this.setTeamTurn(TeamColor.WHITE);
            }

        }
        else{
            throw new InvalidMoveException();
        }

    }

    public boolean offBoard(int i, int j){
        if(i<1 || i>8){
            return true;
        }
        else if(j<1 || j>8){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean offBoard(ChessPosition pos){
        int i = pos.getRow();
        int j = pos.getColumn();
        return this.offBoard(i, j);
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ArrayList<ChessMove> totalMoves = new ArrayList<>();
        for(int i = 1; i <=8; i++){
            for(int j = 1; j <=8; j++){
                ChessPosition pos = new ChessPosition(i, j);
                ChessPiece piece = board.getPiece(pos);
                if(piece == null || piece.getTeamColor().equals(teamColor)){
                    continue;
                }
                totalMoves.addAll(piece.pieceMoves(board, pos));
            }
        }
        for(ChessMove move: totalMoves){
            ChessPosition pos = move.getEndPosition();
            ChessPiece piece = board.getPiece(pos);
            if(piece != null) {
                if (piece.getPieceType().equals(ChessPiece.PieceType.KING) && piece.getTeamColor().equals(teamColor)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if(isInCheck(teamColor) && isInStalemate(teamColor)){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {

        for(int i = 1; i <=8; i++){
            for(int j = 1; j <=8; j++){
                ChessPosition pos = new ChessPosition(i, j);
                ChessPiece piece = board.getPiece(pos);
                if(piece == null || piece.getTeamColor().equals(teamColor)){
                    continue;
                }
                if(!validMoves(pos).isEmpty()){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return this.board;
    }
}
