package boardgame;

public class Board {

    // Classe tabuleiro tera linhas, colunas e matriz de pecas
    private int rows;
    private int columns;
    private Piece [] [] pieces;


    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    // Retorna a peca dada uma linha e coluna
    public Piece piece (int row, int column){
        return pieces[row][column];
    }

    // Sobrecarca do metodo `Piece piece` no qual vou retornar a peca pela posicao
    public Piece piece(Position position){
        return  pieces[position.getRow()] [position.getColumn()];
    }

    // Colocar a peca no tabuleiro
    public void placePiece(Piece piece, Position position){
        pieces[position.getRow()] [position.getColumn()] = piece;
        piece.position = position;
    }



}
