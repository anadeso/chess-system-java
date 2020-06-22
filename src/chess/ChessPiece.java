package chess;

import boardgame.Board;
import boardgame.Piece;

public class ChessPiece extends Piece {

    // Classe ChessPiece(Peca do xadrex)
    private Color color;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

}