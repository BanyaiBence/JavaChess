package chess;

import java.util.Arrays;
import java.util.List;

public class King extends Piece{
    private static final Vector2D[] OFFSETS = new Vector2D[]{
            new Vector2D(1, 0),
            new Vector2D(-1, 0),
            new Vector2D(0, 1),
            new Vector2D(0, -1),
            new Vector2D(1, 1),
            new Vector2D(-1, -1),
            new Vector2D(-1, 1),
            new Vector2D(1, -1)
    };

    public King(int x, int y, char color, Board board){
        super(x, y, color, board);
    }
    public char getUnicode(){
        return (color == 'w') ? 'K' : 'k';
    }
    public List<Vector2D> getMoves(){
        return Arrays.stream(OFFSETS)
                .map(this.pos::add)
                .filter(Vector2D::inBounds)
                .filter(board::isEmpty)
                .filter(pos -> board.isSafeMove(this.pos, pos))
                .toList();
    }
    public List<Vector2D> getAttacks(){
        return Arrays.stream(OFFSETS)
                .map(this.pos::add)
                .filter(Vector2D::inBounds)
                .filter(pos -> !board.isEmpty(pos))
                .filter(pos -> board.getPiece(pos).color != color)
                .filter(pos -> board.isSafeMove(this.pos, pos))
                .toList();
    }
    public List<Vector2D> getControlled(){
        return Arrays.stream(OFFSETS)
                .map(this.pos::add)
                .filter(Vector2D::inBounds)
                .toList();
    }
}
