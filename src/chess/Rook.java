package chess;

public class Rook extends Piece {
    public Rook(int x, int y, char color){
        super(x, y, color);
    }
    public Rook(){}
    public void move(int x, int y){}
    public void move(int[] pos){}
    public char getUnicode(){
        return (color == 'w') ? '♖' : '♜';
    }

}
