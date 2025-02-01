package chess;

public class Pawn extends Piece{


    public Pawn(int x, int y, char color){
        super(x, y, color);
    }
    public Pawn(){}

    public void move(int[] pos) {

    }

    public void move(int x, int y) {

    }
    public char getUnicode(){
        return (color == 'w') ? '♙' : '♟';
    }
}
