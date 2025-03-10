package chess;
import java.util.Arrays;
import java.util.List;

public class Knight extends Piece{
    private static final Vector2D[] OFFSETS = {
            new Vector2D(2, 1),
            new Vector2D(2, -1),
            new Vector2D(-2, 1),
            new Vector2D(-2, -1),
            new Vector2D(1, 2),
            new Vector2D(1, -2),
            new Vector2D(-1, 2),
            new Vector2D(-1, -2)
    };

    public Knight(int x, int y, char color, Board board){
        super(x, y, color, board);
    }

    public char getUnicode(){
        return (color == 'w') ? 'N' : 'n';
    }

    public List<Vector2D> getMoves(){
        return Arrays.stream(OFFSETS)
            .map(this.pos::add)
            .filter(Vector2D::inBounds)
            .filter(board::isEmpty)
            .toList();
    }

    public List<Vector2D> getAttacks(){
        return Arrays.stream(OFFSETS)
            .map(this.pos::add)
            .filter(Vector2D::inBounds)
            .filter(pos -> !board.isEmpty(pos) && board.getPiece(pos).color != color)
            .toList();
    }
}
