package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {

    // Classe ChessPiece(Peca do xadrex)
    private Color color;
    private int moveCount;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void increaseMoveCount(){
        moveCount++;
    }

    public void decreaseMoveCount(){
        moveCount--;
    }

    public ChessPosition getChessPosition(){
        // Converter a posicao de matriz e converte para posicao de xadrez
        return ChessPosition.fromPosition(position);
    }
    // Sera aproveitada por outras pessas, logo ela é generica
    protected  boolean isThereOpponentPiece(Position position){
        ChessPiece p = (ChessPiece) getBoard().piece(position);
        return  p != null && p.getColor() != color;
    }

}
