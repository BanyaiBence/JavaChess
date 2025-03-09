package chess;

import java.util.List;

public abstract class Piece implements PieceInterface {
    protected Vector2D pos = new Vector2D(0, 0);
    protected char color='w';
    protected Board board;

    protected Piece(){}
    protected Piece(int x, int y, char color, Board board){
        this.pos = new Vector2D(x, y);
        this.color = color;
        this.board = board;
    }
    public Vector2D getPos(){
        return pos;
    }
    public void setPos(Vector2D pos){
        if (pos.x < 0 || pos.x > 7 || pos.y < 0 || pos.y > 7){
            throw new IllegalArgumentException("X and Y must be between 0 and 7");
        }
        this.pos = pos;
    }
    @Override
    public String toString(){
        return getUnicode() + "";
    }
}

