package chess;

import java.util.List;

public class Bishop extends SlidingPiece{
    public Bishop(int x, int y, char color, Board board){
        super(x, y, color, board, new int[][]{
                new int[] {-1, -1},
                new int[] {-1, 1},
                new int[] {1, -1},
                new int[] {1, 1}
        });
    }
    public Bishop(){
        super(new int[][]{
                new int[] {-1, -1},
                new int[] {-1, 1},
                new int[] {1, -1},
                new int[] {1, 1}
        });
    }

    public char getUnicode(){
        return (color == 'w') ? 'B' : 'b';
    }

}
