package chess;

import java.util.ArrayList;
import java.util.List;

public abstract class SlidingPiece extends Piece {
    protected final int[][] dirs;
    public SlidingPiece(int x, int y, char color, Board board, int[][] dirs){
        super(x, y, color, board);
        this.dirs = dirs;
    }
    public SlidingPiece(int[][] dirs){
        this.dirs = dirs;
    }


    public List<int[]> getValidMoves(){
        List<int[]> moves = new ArrayList<>();

        int[] startPos = new int[]{x, y};
        for (int[] dir : dirs){
            int[] pos = board.add2Pos(startPos, dir);
            while (true){
                if (!board.validPos(pos)){
                    break;
                }
                Piece piece = board.getPiece(pos[0], pos[1]);
                if (piece == null){
                    moves.add(pos);
                    pos = board.add2Pos(pos, dir);
                    continue;
                }
                if (piece.color != color) {
                    moves.add(pos);
                }
                break;
            }
        }
        return moves;
    }

}
