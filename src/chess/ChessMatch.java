package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chesspieces.*;

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
    private boolean checkMate;

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

    public boolean getCheckMate() {
        return checkMate;
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

        // Atualiza para considerar o chequemate
        // testar se a joga que eu fiz deixou meu oponente em chequemate, ou seja, o codigo abaixo verifica se o jogo acabou
        if (testCheckMater(opponent(currentPlayer))){
            checkMate = true;
        } else {
            nextTurn();
        }

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

        //Piece p = board.removePiece(source);
        ChessPiece p = (ChessPiece) board.removePiece(source);
        p.increaseMoveCount();
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);

        if (capturedPiece != null){
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }

        // Roque pequeno - SPECIALMOVE CASTLING KINGSIDE ROCK
        if (p instanceof King && target.getColumn() == source.getColumn() + 2){
            Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
            Position targeT = new Position(source.getRow(), source.getColumn()+ 1);
            ChessPiece rock= (ChessPiece)board.removePiece(sourceT);
            board.placePiece(rock, targeT);
            rock.increaseMoveCount();
        }
        // Roque grande - SPECIALMOVE CASTLING queenside ROCK
        if (p instanceof King && target.getColumn() == source.getColumn() - 2){
            Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
            Position targeT = new Position(source.getRow(), source.getColumn() - 1);
            ChessPiece rock= (ChessPiece)board.removePiece(sourceT);
            board.placePiece(rock, targeT);
            rock.increaseMoveCount();
        }
        return capturedPiece;
    }

    // Desfaz os movimentos
    private void undoMove(Position source, Position target, Piece capturePiece){

        //Piece p = board.removePiece(target);
        ChessPiece p = (ChessPiece) board.removePiece(target);
        p.decreaseMoveCount();
        board.placePiece(p, source);

        if (capturePiece != null){
            board.placePiece(capturePiece, target);
            capturedPieces.remove(capturePiece);
            piecesOnTheBoard.add(capturePiece);
        }

        // Roque pequeno - SPECIALMOVE CASTLING KINGSIDE ROCK
        if (p instanceof King && target.getColumn() == source.getColumn() + 2){
            Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
            Position targeT = new Position(source.getRow(), source.getColumn()+ 1);
            ChessPiece rock= (ChessPiece)board.removePiece(targeT);
            board.placePiece(rock, sourceT);
            rock.decreaseMoveCount();
        }

        // Roque grande - SPECIALMOVE CASTLING queenside ROCK
        if (p instanceof King && target.getColumn() == source.getColumn() - 2){
            Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
            Position targeT = new Position(source.getRow(), source.getColumn() - 1);
            ChessPiece rock= (ChessPiece)board.removePiece(targeT);
            board.placePiece(rock, sourceT);
            rock.decreaseMoveCount();
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

    private boolean testCheckMater(Color color){
        if (!testCheck(color)){
            return false;
        }
        // obtendo todas as pecas da cor (color) que esta em argumento
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
        // Percorrendo todas as pecas p pertencente a minha lista
        for (Piece p : list){
            //todos movimentos possiveis de p
            boolean [][] mat = p.possibleMoves();
            // percorrenco a matriz
            for(int i=0; i < board.getRows(); i++){
                for(int j=0; j<board.getColumns(); j++){
                    // Essa posicao da matriz é possivel
                    if (mat[i][j]){
                        // testando se a posicao tira do cheque
                        //getChessPosition é a posicao no formato de xadrez e converto ela para o formato toPosition
                        Position source = ((ChessPiece)p).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source, target);
                        boolean testCheck = testCheck(color);
                        // Desfazer o movimento
                        undoMove(source, target, capturedPiece);
                        // Se nao estava em cheque
                        if(!testCheck){
                            return  false;
                        }
                    }
                }
            }
        }
        return true;
    }
    // Iniciando a partida de xadrez, colocando as pecas no tabuleiro
    private void initialSetup(){
        /*placeNewPiece('c', 1, new Rock(board, Color.WHITE));
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
        placeNewPiece('d', 8, new King(board, Color.BLACK));*/

        /*placeNewPiece('h', 7, new Rock(board, Color.WHITE));
        placeNewPiece('d', 1, new Rock(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE));

        placeNewPiece('b', 8, new Rock(board, Color.BLACK));
        placeNewPiece('a', 8, new King(board, Color.BLACK));*/

        placeNewPiece('a', 1, new Rock(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rock(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE));

        placeNewPiece('a', 8, new Rock(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));

        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rock(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK));
    }
}
