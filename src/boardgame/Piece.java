package boardgame;

public class Piece {

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
}
