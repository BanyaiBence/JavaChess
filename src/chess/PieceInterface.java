package chess;

import java.util.List;

public interface PieceInterface {

     int[] getPos();
     void setPos(int x, int y);
     void setPos(int[] pos);
     void move(int[] pos);
     void move(int x, int y);
     char getUnicode();
     public List<int[]> getValidMoves();
}
