package boardgame;

public abstract class Piece {

    // Classe Peca tem posicao protegida, na qual nao é a posicao do xadrez, mas
    // sim um array simples

    protected Position position;
    // Associacao com a Peca e Tabuleiro(BOARD)
    private Board board;

    // Sera iniciada como nula, pois ainda nao foi colocada no tabuleiro ainda
    public Piece(Board board) {
        this.board = board;
        position = null;
    }

    protected Board getBoard() {
        return board;
    }

    // Nao seu a possicao por isso declarei o metodo como abstrato
    public abstract  boolean [][] possibleMoves();

    // Metodo concreto no qual esta dependendo do metodo abstrato
    public boolean possibleMove(Position position){
        return possibleMoves()[position.getRow()][position.getColumn()];
    }

    public boolean isThereAnyPossibleMove(){
        boolean[][] mat = possibleMoves();
        for (int i=0; i<mat.length; i++){
            for (int j=0; j<mat.length; j++){
                if (mat[i][j]){
                    return true;
                }
            }
        }
        return false;
    }


}
