package chess;

import boardgame.Position;

public class ChessPosition {

    // Classe que representa a posicao comum do tabuleiro do pdf..
    private char column;
    private int row;


    public ChessPosition( char column, int row) {

        if (column < 'a' || column >'h'|| row < 1 || row > 8){
            throw  new ChessException("Error instantiating ChessPosition. Valid values are from a1 to h8");
        }
        this.column = column;
        this.row = row;

    }

    public int getRow() {
        return row;
    }

    public char getColumn() {
        return column;
    }

    // Retorna uma nova posicao Matrix
    protected Position toPosition(){
        return  new Position(8 -row, column - 'a');
    }
    // Retorna uma nova posicao Xadrez
    protected  static  ChessPosition fromPosition(Position position){
        return  new ChessPosition((char)('a' + position.getColumn()), (char) (8 - position.getRow()));
    }

    @Override
    public String toString() {
        return "" + column + row;
    }
}
