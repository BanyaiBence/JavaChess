package chess;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece{

    public King(int x, int y, char color, Board board){
        super(x, y, color, board);
    }
    public King(){}
    public char getUnicode(){
        return (color == 'w') ? 'K' : 'k';
    }
    public List<Vector2D> getValidMoves(){
        List<Vector2D> moves = new ArrayList<>();

        Vector2D[] offsets = new Vector2D[]{
                new Vector2D(1, 0),
                new Vector2D(-1, 0),
                new Vector2D(0, 1),
                new Vector2D(0, -1),
                new Vector2D(1, 1),
                new Vector2D(-1, -1),
                new Vector2D(-1, 1),
                new Vector2D(1, -1)
        };
        for (Vector2D offset : offsets){
            Vector2D pos = this.pos.add(offset);

            if (!pos.inBounds()){continue;}
            Piece piece = board.getPiece(pos);
            if (piece == null || piece.color != this.color){
                moves.add(pos);
            }
        }
        return moves;
    }
}
