package chess;

import java.util.List;
import java.util.stream.Stream;

public abstract class SlidingPiece extends Piece {
    protected final Vector2D[] dirs;
    protected SlidingPiece(int x, int y, char color, Board board, Vector2D[] dirs){
        super(x, y, color, board);
        this.dirs = dirs;
    }

    public List<Vector2D> getMoves(){
        return Stream.of(dirs)
            .flatMap(dir ->
            Stream.iterate(this.pos.add(dir), pos ->
                pos.inBounds() && board.isEmpty(pos),
                pos -> pos.add(dir))
        ).toList();
    }

    public List<Vector2D> getAttacks() {
        return Stream.of(dirs)
            .flatMap(dir -> Stream.iterate(this.pos.add(dir), pos -> pos.inBounds() && (board.isEmpty(pos) || board.getPiece(pos).color != this.color), pos -> pos.add(dir))
            .filter(pos -> !board.isEmpty(pos))
            .limit(1)
            ).toList();
    }

}
