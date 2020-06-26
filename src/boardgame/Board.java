package boardgame;

public class Board {

    // Classe tabuleiro tera linhas, colunas e matriz de pecas
    private int rows;
    private int columns;
    private Piece [] [] pieces;

    public Board(int rows, int columns) {
        if (rows < 1 || columns < 1){
            throw new BoardException("Error creating board: there must be at least 1 row and 1 column");
        }
        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    // Retorna a peca dada uma linha e coluna
    public Piece piece (int row, int column){

        if (!positionExists(row, column)){
            throw  new BoardException("Position not on the board");
        }
        return pieces[row][column];
    }

    // Sobrecarca do metodo `Piece piece` no qual vou retornar a peca pela posicao
    public Piece piece(Position position){
        if (!positionExists(position)){
            throw  new BoardException("Position not on the board");
        }
        return  pieces[position.getRow()] [position.getColumn()];
    }

    // Colocar a peca no tabuleiro
    public void placePiece(Piece piece, Position position){

        if (thereIsAPiece(position)){
            throw new BoardException("There is alredy a piece on position " + position);
        }
        pieces[position.getRow()] [position.getColumn()] = piece;
        piece.position = position;
    }

    public Piece removePiece(Position position){
        if (!positionExists(position)){
            throw new BoardException("Position not on the board");
        }
        if (piece(position) == null){
            return null;
        }
        Piece aux = piece(position);
        aux.position = null;
        pieces[position.getRow()][position.getColumn()] = null;
        return aux;
    }

    // Verifica se a posicao existe ou nao
    // Metodo auxiliar, pois se mais facil testar pela linha e coluna do que pela posicao
    public boolean positionExists(int row, int column){
        return row >= 0  && row < rows && column >= 0 && column < columns;

    }
    // Verifica se a posicao existe ou nao
    public boolean positionExists(Position position){
        return positionExists(position.getRow(), position.getColumn());
    }

    // Verifica se tem uma peca na posicao
    public boolean thereIsAPiece(Position position){
        if (!positionExists(position)){
            throw  new BoardException("Position not on the board");
        }
        return piece(position) != null;
    }


}
