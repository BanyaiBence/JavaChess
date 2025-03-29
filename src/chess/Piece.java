package chess;


public abstract class Piece implements PieceInterface {
    protected Vector2D pos = new Vector2D(0, 0);
    protected char color='w';
    protected Board board;

    protected Piece(int x, int y, char color, Board board){
        this.pos = new Vector2D(x, y);
        this.color = color;
        this.board = board;
    }
    public Vector2D getPos(){
        return pos;
    }
    public void setPos(Vector2D pos){
        this.pos = pos;
    }
    @Override
    public String toString(){
        return getUnicode() + "";
    }
}

