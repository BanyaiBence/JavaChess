package chess;

import java.util.List;

public class King extends Piece{

    public King(int x, int y, char color, Board board){
        super(x, y, color, board);
    }
    public King(){}
    public void move(int[] pos) {

    }

    public void move(int x, int y) {

    }
    public char getUnicode(){
        return (color == 'w') ? '♔' : '♚';
    }
    public List<int[]> getValidMoves(){
        return null;
    }

}
