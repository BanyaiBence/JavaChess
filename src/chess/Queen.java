package chess;


public class Queen extends SlidingPiece{

    public Queen(int x, int y, char color, Board board){

        super(x, y, color, board, new Vector2D[]{
                new Vector2D(0, -1),
                new Vector2D(0, 1),
                new Vector2D(-1, 0),
                new Vector2D(1, 0),
                new Vector2D(-1, -1),
                new Vector2D(-1, 1),
                new Vector2D(1, -1),
                new Vector2D(1, 1)
        });
    }
    public char getUnicode(){
        return (color == 'w') ? 'Q' : 'q';
    }
}
