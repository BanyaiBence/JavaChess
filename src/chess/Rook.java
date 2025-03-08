package chess;

import java.util.List;

public class Rook extends SlidingPiece {
    public Rook(int x, int y, char color, Board board){
        super(x, y, color, board, new int[][]{
            new int[] {0, -1},
            new int[] {0, 1},
            new int[] {-1, 0},
            new int[] {1, 0}
        }
        );
    }
    public Rook(){
        super(new int[][]{
                new int[] {0, -1},
                new int[] {0, 1},
                new int[] {-1, 0},
                new int[] {1, 0}
        });
    }

    public char getUnicode(){
        return (color == 'w') ? 'R' : 'r';
    }

}
