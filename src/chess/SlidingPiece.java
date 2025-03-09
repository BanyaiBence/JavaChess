package chess;

import java.util.ArrayList;
import java.util.List;

public abstract class SlidingPiece extends Piece {
    protected final Vector2D[] dirs;
    protected SlidingPiece(int x, int y, char color, Board board, Vector2D[] dirs){
        super(x, y, color, board);
        this.dirs = dirs;
    }


    public List<Vector2D> getValidMoves(){
        List<Vector2D> moves = new ArrayList<>();

        for (Vector2D dir : dirs){
            Vector2D pos = this.pos.add(dir);
            while (pos.inBounds() && board.isEmpty(pos)){
                moves.add(pos);
                pos = pos.add(dir);
            }
        }
        return moves;
    }

}
