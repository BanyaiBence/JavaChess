package chess;

import java.util.List;

public abstract class Piece implements PieceInterface {
    protected int x=0;
    protected int y=0;
    protected char color='w';
    protected Board board;

    protected Piece(){}
    protected Piece(int x, int y, char color, Board board){
        this.x = x;
        this.y = y;
        this.color = color;
        this.board = board;
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

    public void move(int[] pos) {
        move(pos[0], pos[1]);
    }

    public void move(int x, int y) {
        List<int[]> validMoves = getValidMoves();
        if (validMoves.contains(new int[]{x, y})){
            board.setPiece(this.x, this.y, null);
            this.x = x;
            this.y = y;
            board.setPiece(x, y, this);
        }
    }
}

