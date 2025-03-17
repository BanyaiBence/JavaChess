package chess;

import java.util.List;

public interface PieceInterface {

     Vector2D getPos();
     void setPos(Vector2D pos);
     char getUnicode();
     List<Vector2D> getMoves();
     List<Vector2D> getAttacks();
     List<Vector2D> getControlled();
}
