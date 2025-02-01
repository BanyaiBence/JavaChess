package chess;

public class Knight extends Piece{

    public Knight(int x, int y, char color){
        super(x, y, color);
    }
    public Knight(){}

    public void move(int[] pos) {

    }

    public void move(int x, int y) {

    }
    public char getUnicode(){
        return (color == 'w') ? '♘' : '♞';
    }
}
