package chesspieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class Rock extends ChessPiece {

    // Classe da peca Rock
    public Rock(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "R";
    }
}
