package chess;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece{

    public Knight(int x, int y, char color, Board board){
        super(x, y, color, board);
    }
    public Knight(){}


    public char getUnicode(){
        return (color == 'w') ? 'N' : 'n';
    }
    public List<int[]> getValidMoves(){
        List<int[]> moves = new ArrayList<>();

        int[][] offsets = new int[][]{
            {2, 1},
            {2, -1},
            {-2, 1},
            {-2, -1},
            {1, 2},
            {1, -2},
            {-1, 2},
            {-1, -2}
        };
        for (int[] offset : offsets){
            int x = this.x + offset[0];
            int y = this.y + offset[1];
            if (x < 0 || x > 7 || y < 0 || y > 7){continue;}
            Piece piece = board.getPiece(x, y);
            if (piece == null || piece.color != this.color){
                moves.add(new int[]{x, y});
            }
        }
        return moves;
    }
}
