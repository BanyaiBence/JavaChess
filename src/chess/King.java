package chess;

public class King extends Piece{

    public King(int x, int y, char color){
        super(x, y, color);
    }
    public King(){}
    public void move(int[] pos) {

    }

    public void move(int x, int y) {

    }
    public char getUnicode(){
        return (color == 'w') ? '♔' : '♚';
    }
}
