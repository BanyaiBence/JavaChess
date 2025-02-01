package chess;

public abstract class Piece implements PieceInterface {
    protected int x=0;
    protected int y=0;
    protected char color='w';

    protected Piece(){}
    protected Piece(int x, int y, char color){
        this.x = x;
        this.y = y;
        this.color = color;
    }
    public int[] getPos(){
        return new int[]{x, y};
    }
    public void setPos(int x, int y){
        if (x < 0 || x > 7 || y < 0 || y > 7){
            throw new IllegalArgumentException("X and Y must be between 0 and 7");
        }
        this.x = x;
        this.y = y;
    }
    public void setPos(int[] pos){
        setPos(pos[0], pos[1]);
    }
    @Override
    public String toString(){
        return getUnicode() + "";
    }
}

