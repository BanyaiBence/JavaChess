package chess;

import java.util.List;

public class Queen extends SlidingPiece{

    public Queen(int x, int y, char color, Board board){

        super(x, y, color, board, new int[][]{
                new int[] {0, -1},
                new int[] {0, 1},
                new int[] {-1, 0},
                new int[] {1, 0},
                new int[] {-1, -1},
                new int[] {-1, 1},
                new int[] {1, -1},
                new int[] {1, 1}
        });
    }
    public Queen(){
        super(new int[][]{
                new int[] {0, -1},
                new int[] {0, 1},
                new int[] {-1, 0},
                new int[] {1, 0},
                new int[] {-1, -1},
                new int[] {-1, 1},
                new int[] {1, -1},
                new int[] {1, 1}
        });
    }
    public void move(int[] pos) {

    }

    public void move(int x, int y) {

    }
    public char getUnicode(){
        return (color == 'w') ? '♕' : '♛';
    }
}
