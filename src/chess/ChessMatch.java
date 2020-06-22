package chess;

import boardgame.Board;
import boardgame.Position;
import chesspieces.Rock;

public class ChessMatch {

    private Board board;

    public ChessMatch() {
        board = new Board(8, 8);
        initialSetup();
    }

    public ChessPiece[][] getPieces(){
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i=0; i< board.getRows(); i++){
            for (int j=0; j<board.getColumns(); j++){
                mat[i][j] = (ChessPiece) board.piece(i,j);
            }
        }
        return mat;
    }
    // Iniciando a partida de xadrez
    private void initialSetup(){
        board.placePiece(new Rock(board,Color.WHITE), new Position(2, 1));
        board.placePiece(new Rock(board,Color.WHITE), new Position(0, 4));
        board.placePiece(new Rock(board,Color.WHITE), new Position(7, 4));
    }
}
