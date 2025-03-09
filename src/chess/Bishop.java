package chess;

public class Bishop extends SlidingPiece{
    public Bishop(int x, int y, char color, Board board){
        super(x, y, color, board, new Vector2D[]{
                new Vector2D(-1, -1),
                new Vector2D(-1, 1),
                new Vector2D(1, -1),
                new Vector2D(1, 1)
        });
    }

    public char getUnicode(){
        return (color == 'w') ? 'B' : 'b';
    }

}
