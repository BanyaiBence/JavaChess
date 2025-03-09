package chess;

import java.util.List;

public class Rook extends SlidingPiece {
    public Rook(int x, int y, char color, Board board){
        super(x, y, color, board, new Vector2D[]{
            new Vector2D(0, -1),
            new Vector2D(0, 1),
            new Vector2D(-1, 0),
            new Vector2D(1, 0)
        }
        );
    }
    public char getUnicode(){
        return (color == 'w') ? 'R' : 'r';
    }

}
