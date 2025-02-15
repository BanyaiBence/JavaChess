package chess;

import java.util.List;

// tiles
public class Board {
    private final Piece[][] tiles = new Piece[8][8];

    public Board(){
        for (int i = 0; i < 8; i++){
            tiles[1][i] = new Pawn(i, 1, 'b', this);
            tiles[6][i] = new Pawn(i, 6, 'w', this);
        }
        tiles[0][0] = new Rook(0, 0, 'b', this);
        tiles[0][7] = new Rook(7, 0, 'b', this);
        tiles[7][0] = new Rook(0, 7, 'w', this);
        tiles[7][7] = new Rook(7, 7, 'w', this);
        tiles[0][1] = new Knight(1, 0, 'b', this);
        tiles[0][6] = new Knight(6, 0, 'b', this);
        tiles[7][1] = new Knight(1, 7, 'w', this);
        tiles[7][6] = new Knight(6, 7, 'w', this);
        tiles[0][2] = new Bishop(2, 0, 'b', this);
        tiles[0][5] = new Bishop(5, 0, 'b', this);
        tiles[7][2] = new Bishop(2, 7, 'w', this);
        tiles[7][5] = new Bishop(5, 7, 'w', this);
        tiles[0][3] = new Queen(3, 0, 'b', this);
        tiles[7][3] = new Queen(3, 7, 'w', this);
        tiles[0][4] = new King(4, 0, 'b', this);
        tiles[7][4] = new King(4, 7, 'w', this);

    }
    public Piece getPiece(int x, int y){
        return tiles[y][x];
    }

    @Override
    public String toString(){
        StringBuilder board = new StringBuilder();
        board.append("  a  b  c d  e  f g  h\n");
        for (int i = 0; i < 8; i++){
            board.append(8 - i).append(" ");
            for (int j = 0; j < 8; j++){
                if (tiles[i][j] == null){
                    board.append("  ");
                } else {
                    board.append(tiles[i][j].toString()).append(" ");
                }
            }
            board.append("\n");
        }
        return board.toString();
    }

    public String showMoves(List<int[]> moves){
        StringBuilder board = new StringBuilder();
        board.append("  a  b  c d  e  f g  h\n");
        for (int i = 0; i < 8; i++){
            board.append(8 - i).append(" ");
            for (int j = 0; j < 8; j++){
                boolean contains = false;
                for (int[] move: moves){
                    if (move[0] == j && move[1] == i){
                        contains = true;
                        break;
                    }
                }
                if (contains){
                    board.append("⬤ ");
                } else if (tiles[i][j] == null){
                    board.append("  ");
                } else {
                    board.append(tiles[i][j].toString()).append(" ");
                }
            }
            board.append("\n");
        }
        return board.toString();
    }

    public void setPiece(int x, int y, Piece piece) {
        tiles[y][x] = piece;
    }

    public boolean validPos(int[] pos){
        return pos[0] >= 0 && pos[0] <= 7 && pos[1] >= 0 && pos[1] <= 7;
    }

    public int[] add2Pos(int[] pos1, int[] pos2){
        int[] pos = new int[2];
        pos[0] = pos1[0] + pos2[0];
        pos[1] = pos1[1] + pos2[1];
        return pos;
    }
}
