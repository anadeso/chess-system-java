package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chesspieces.King;
import chesspieces.Rock;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {

    private int turn;
    // Jogador atual
    private Color currentPlayer;
    // Classe ChesssMatch(Partida de xadrex)
    private Board board;
    private boolean check;

    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();

    // Partida de xadrez
    public ChessMatch() {
        board = new Board(8, 8);
        turn = 1;
        currentPlayer =  Color.WHITE;
        check = false;
        initialSetup();
    }

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean getCheck(){
        return  check;
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

    // Imprimir a partir de uma posicao de origm as posicoes possiveis
    public boolean [][] possibleMoves(ChessPosition sourcePosition){
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    // Retira peca da posicao de origem e insere na posicao de destino
    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition){
        // Converter posicao do parametro para matrix
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source,target);
        Piece capturedPiece = makeMove(source, target);

        if (testCheck(currentPlayer)){
            undoMove(source, target, capturedPiece);
            throw new ChessException("You can't put yourself in check");
        }

        check = (testCheck(opponent(currentPlayer))) ? true  : false;

        nextTurn();
        return  (ChessPiece) capturedPiece;
    }

    // Valida posicao de origem
    private void validateSourcePosition(Position position){
        if (!board.thereIsAPiece(position)){
            throw new ChessException("There is no piece on source position");
        }
        if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()){
            throw  new ChessException("The chosen piece is not yours");
        }
        if (!board.piece(position).isThereAnyPossibleMove()){
            throw new ChessException("There is no possible moves for the chosen piece");
        }
    }

    // Validar a posicao de destino, verifica se esta fazendo certo ou nao posicao de uma peca
    private void validateTargetPosition(Position source, Position target){

        // Se a peca de origem a posicao de destino nao é um movimento possivel
        if (!board.piece(source).possibleMove(target)){
            throw new ChessException("The chosen piece can`t move to target position");
        }

    }

    // Movimentacao das pecas
    private Piece makeMove(Position source, Position target){
        Piece p = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);

        if (capturedPiece != null){
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }
        return capturedPiece;
    }

    // Desfaz os movimentos
    private void undoMove(Position source, Position target, Piece capturePiece){
        Piece p = board.removePiece(target);
        board.placePiece(p, source);

        if (capturePiece != null){
            board.placePiece(capturePiece, target);
            capturedPieces.remove(capturePiece);
            piecesOnTheBoard.add(capturePiece);
        }
    }

    private void nextTurn(){
        turn++;
        // Se o jogador atual for white entao black senao branco
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }
    // Recebe coordenadas do Xadrez
    private void placeNewPiece(char column, int row, ChessPiece piece){
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        // Adicionando peca na lista
        piecesOnTheBoard.add(piece);
    }

    private Color opponent (Color color){
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private ChessPiece King(Color color){
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
        for(Piece p : list) {
            if (p instanceof King) {
                return (ChessPiece) p;
            }
        }
        throw new IllegalStateException("There is no " + color + " king on the board");
    }

    private boolean testCheck(Color color){
        // Pegando posicao do rei em formato de matriz
        Position kingPosition = King(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
        for (Piece p : opponentPieces){
            boolean[][] mat = p.possibleMoves();
            if (mat[kingPosition.getRow()][kingPosition.getColumn()]){
                return true;
            }
        }
        return false;
    }

    // Iniciando a partida de xadrez, colocando as pecas no tabuleiro
    private void initialSetup(){
        placeNewPiece('c', 1, new Rock(board, Color.WHITE));
        placeNewPiece('c', 2, new Rock(board, Color.WHITE));
        placeNewPiece('d', 2, new Rock(board, Color.WHITE));
        placeNewPiece('e', 2, new Rock(board, Color.WHITE));
        placeNewPiece('e', 1, new Rock(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rock(board, Color.BLACK));
        placeNewPiece('c', 8, new Rock(board, Color.BLACK));
        placeNewPiece('d', 7, new Rock(board, Color.BLACK));
        placeNewPiece('e', 7, new Rock(board, Color.BLACK));
        placeNewPiece('e', 8, new Rock(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
    }
}
