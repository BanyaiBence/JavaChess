package chess;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece{

    public Pawn(int x, int y, char color, Board board){
        super(x, y, color, board);
    }

    public char getUnicode(){
        return (color == 'w') ? 'P' : 'p';
    }

    public List<Vector2D> getValidMoves() {
        List<Vector2D> moves = new ArrayList<>();
        Vector2D pos = this.pos.add(0, color == 'b' ? 1 : -1);

        if (!pos.inBounds() || !board.isEmpty(pos)){return moves;}
        moves.add(pos);

        if (!(this.color == 'w' && this.pos.y == 1) && !(this.color == 'b' && this.pos.y == 6)){
            return moves;
        }
        pos = this.pos.add(0, color == 'b' ? 2 : -2);
        if (board.isEmpty(pos)){
            moves.add(pos);
        }
        return moves;
    }

    public List<Vector2D> getValidHits(){
        List<Vector2D> hits = new ArrayList<>();

        Vector2D pos1 = this.pos.add(-1, color == 'b' ? 1 : -1);
        Vector2D pos2 = this.pos.add(1, color == 'b' ? 1 : -1);

        if (pos1.inBounds() && !board.isEmpty(pos1) && board.getPiece(pos1).color != this.color){
            hits.add(pos1);
        }
        if (pos2.inBounds() && !board.isEmpty(pos2) && board.getPiece(pos2).color != this.color){
            hits.add(pos2);
        }
        return hits;
    }
}
