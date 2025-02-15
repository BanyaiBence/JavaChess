package chess;

import java.util.List;

public class Main {

    public static void main(String[] args){
        Board board = new Board();
        board.setPiece(3, 4, new Rook(3, 4, 'w', board));
        Piece k = board.getPiece(3, 4);
        List<int[]> moves = k.getValidMoves();
        System.out.print(board.showMoves(moves));

    }




}
