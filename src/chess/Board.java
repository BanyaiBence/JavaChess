package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// tiles
public class Board {
    private final Piece[][] tiles = new Piece[8][8];
    private int[] selected = new int[]{-1, -1};

    public Board(){
        fromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
    }
    public Piece getPiece(int x, int y){
        return tiles[y][x];
    }

    @Override
    public String toString(){
        StringBuilder board = new StringBuilder();
        board.append("  A B C D E F G H\n");
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
        board.append("  A B C D E F G H\n");
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
                    board.append("X ");
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
        if (piece != null){
            piece.setPos(x, y);
        }
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

    public List<int[]> controlledBy(char color){
        List<int[]> tiles = new ArrayList<>();

        for (Piece[] row : this.tiles){
            for (Piece piece : row){
                if (piece == null || piece.color != color){
                    continue;
                }
                if (piece instanceof Pawn){
                    Pawn pawn = (Pawn) piece;
                    List<int[]> hits = pawn.getValidHits();
                    for (int[] hit : hits){
                        if (!tiles.contains(hit)){
                            tiles.add(hit);
                        }
                    }
                    continue;
                }
                List<int[]> hits = piece.getValidMoves();
                for (int[] hit : hits){
                    if (!tiles.contains(hit)){
                        tiles.add(hit);
                    }
                }
            }
        }

        return tiles;
    }
    public Boolean inCheck(char color){
        King king = null;
        for (Piece[] row : tiles){
            for (Piece piece : row){
                if (piece instanceof King && piece.color == color){
                    king = (King) piece;
                }
            }
        }
        if (king == null){
            return false;
        }
        List<int[]> controlledByOpponent = controlledBy(color == 'w' ? 'b' : 'w');

        if (controlledByOpponent.contains(new int[]{king.x, king.y})){
            return true;
        }
        return false;

    }
    public void clear(){
        for (int i = 0; i < tiles.length; i++){
            Arrays.fill(tiles[i], null);
        }
    }
    public String prepFen(String fen){
        StringBuilder result = new StringBuilder();
        for (char c : fen.toCharArray()){
            if (Character.isDigit(c)){
                int i = Character.getNumericValue(c);
                result.append(" ".repeat(i));
                continue;
            }
            result.append(c);
        }
        return result.toString();
    }

    public void fromFen(String fen){
        clear();

        fen = prepFen(fen);

        String[] rows = fen.split("/");
        for (int i = 0; i < rows.length; i++){
            for (int j = 0; j < rows[i].length(); j++){

                char c = rows[i].charAt(j);
                if (c == ' '){
                    continue;
                }

                char color = 'b';
                if (Character.isUpperCase(c)){
                    color = 'w';
                }
                c = Character.toLowerCase(c);

                switch(Character.toLowerCase(c)){
                    case 'b':
                        tiles[i][j] = new Bishop(j, i, color, this);
                        break;
                    case 'n':
                        tiles[i][j] = new Knight(j, i, color, this);
                        break;
                    case 'p':
                        tiles[i][j] = new Pawn(j, i, color, this);
                        break;
                    case 'q':
                        tiles[i][j] = new Queen(j, i, color, this);
                        break;
                    case 'r':
                        tiles[i][j] = new Rook(j, i, color, this);
                        break;
                    case 'k':
                        tiles[i][j] = new King(j, i, color, this);
                        break;
                }
            }
        }
    }
    public void setSelected(int x, int y){
        selected = new int[]{x, y};
    }
    public int[] getSelected(){
        return selected;
    }

    public void move(int i, int i1, int x, int y) {
        Piece piece = getPiece(i, i1);
        if (piece == null){
            return;
        }
        setPiece(x, y, piece);
        setPiece(i, i1, null);


    }
}




















