package chess;

import boardgame.Board;
import boardgame.Position;
import chesspieces.Rock;

public class ChessMatch {

    // Classe ChesssMatch(Partida de xadrex)
    private Board board;

    public ChessMatch() {
        board = new Board(8, 8);
        initialSetup();
    }

    // Retorna uma matriz de pecas de xadrex referente a essa partida
    public ChessPiece[][] getPieces(){
        // Qtd de linha e coluna do tabuleiro (board.getRows()][board.getColumns())
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        // Percorrendo a linha da matriz
        for (int i=0; i< board.getRows(); i++){
            // Percorrendo a coluna da matriz
            for (int j=0; j<board.getColumns(); j++){
                // Tive que fazer o casting pois assim ele interpreta que é uma peca de xadrez e nao do tabuleiro
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
