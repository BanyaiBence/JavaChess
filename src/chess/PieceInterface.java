package chess;

public interface PieceInterface {

    public int[] getPos();
    public void setPos(int x, int y);
    public void setPos(int[] pos);
    public void move(int[] pos);
    public void move(int x, int y);
    public char getUnicode();
}
