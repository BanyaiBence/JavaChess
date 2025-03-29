package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pawn extends Piece{

    public Pawn(int x, int y, char color, Board board){
        super(x, y, color, board);
    }

    public char getUnicode(){
        return (color == 'w') ? 'P' : 'p';
    }

    public List<Vector2D> getMoves() {
        List<Vector2D> moves = new ArrayList<>();
        int dir = (color == 'b') ? 1 : -1;

        Vector2D pos = this.pos.add(0, dir);

        if (!pos.inBounds() || !board.isEmpty(pos)){return moves;}
        moves.add(pos);
        if ((this.color == 'w' && this.pos.y == 6) || (this.color == 'b' && this.pos.y == 1)){
            pos = this.pos.add(0, 2*dir);
            if (board.isEmpty(pos)){
                moves.add(pos);
            }
        }
        return moves;
    }

    public List<Vector2D> getAttacks(){
        List<Vector2D> hits = new ArrayList<>();
        int dir = (color == 'b') ? 1 : -1;

        Vector2D pos1 = this.pos.add(-1, dir);
        Vector2D pos2 = this.pos.add(1,  dir);

        if (pos1.inBounds() && !board.isEmpty(pos1) && board.getPiece(pos1).color != this.color){
            hits.add(pos1);
        }
        if (pos2.inBounds() && !board.isEmpty(pos2) && board.getPiece(pos2).color != this.color){
            hits.add(pos2);
        }
        if (pos1.equals(board.getEnPassant())){
            hits.add(pos1);
        }

        if (pos2.equals(board.getEnPassant())){
            hits.add(pos2);
        }
        return hits;
    }

    public List<Vector2D> getControlled(){
        List<Vector2D> result = new ArrayList<>();
        int dir = (color == 'b') ? 1 : -1;

        Vector2D pos1 = this.pos.add(-1, dir);
        Vector2D pos2 = this.pos.add(1,  dir);

        if (pos1.inBounds()){
            result.add(pos1);
        }
        if (pos2.inBounds()){
            result.add(pos2);
        }
        return result;
    }
}
