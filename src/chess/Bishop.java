package chess;

public class Bishop extends Piece{
    public Bishop(int x, int y, char color){
        super(x, y, color);
    }
    public Bishop(){}
    public void move(int x, int y){}
    public void move(int[] pos){}
    public char getUnicode(){
        return (color == 'w') ? '♗' : '♝';
    }
}
