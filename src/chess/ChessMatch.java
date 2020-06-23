package chess;

import boardgame.Board;
import boardgame.Position;
import chesspieces.King;
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
    // Recebe coordenadas do Xadrez
    private void placeNewPiece(char column, int row, ChessPiece piece){
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
    }
    // Iniciando a partida de xadrez, colocando as pecas no tabuleiro
    private void initialSetup(){
        placeNewPiece('b', 6, new Rock(board,Color.WHITE));
        placeNewPiece('e', 8 ,new King(board,Color.BLACK));
        placeNewPiece('e', 1,new King(board,Color.WHITE));
    }
}
