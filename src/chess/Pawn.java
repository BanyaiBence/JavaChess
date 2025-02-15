package chess;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece{


    public Pawn(int x, int y, char color, Board board){
        super(x, y, color, board);
    }
    public Pawn(){}

    public void move(int[] pos) {

    }

    public void move(int x, int y) {

    }
    public char getUnicode(){
        return (color == 'w') ? '♙' : '♟';
    }

    public List<int[]> getValidMoves() {
        List<int[]> moves = new ArrayList<>();
        int[] nextPos = new int[]{x, color == 'b' ? y+1 : y-1};
        moves.add(nextPos);
        if (y == 1 && color == 'b') {
            nextPos = new int[]{x, y + 2};
            moves.add(nextPos);
        }
        if (y == 6 && color == 'w'){
            nextPos = new int[]{x, y - 2};
            moves.add(nextPos);
        }
        int[][] posS = new int[][]{
                new int[]{x - 1, color == 'b' ? y+1 : y-1},
                new int[]{x + 1, color == 'b' ? y+1 : y-1}
        };

        for (int[] pos : posS) {
            if (board.validPos(pos)) {
                Piece piece = board.getPiece(pos[0], pos[1]);
                if (piece != null && piece.color != color) {
                    moves.add(pos);
                }
            }
        }
        return moves;
    }
}
