package chess;

public class Queen extends Piece{

    public Queen(int x, int y, char color){
        super(x, y, color);
    }
    public Queen(){}
    public void move(int[] pos) {

    }

    public void move(int x, int y) {

    }
    public char getUnicode(){
        return (color == 'w') ? '♕' : '♛';
    }
}
