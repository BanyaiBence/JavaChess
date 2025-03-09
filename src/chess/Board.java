package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// tiles
public class Board {
    private final Piece[][] tiles = new Piece[8][8];

    public Board(){
        fromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
    }
    public Piece getPiece(Vector2D pos){
        return tiles[pos.y][pos.x];
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

    public void setPiece(Vector2D pos, Piece piece) {
        tiles[pos.y][pos.x] = piece;
        if (piece != null){
            piece.setPos(pos);
        }
    }

    public List<Vector2D> controlledBy(char color){
        List<Vector2D> tiles = new ArrayList<>();

        for (Piece[] row : this.tiles){
            for (Piece piece : row){
                if (piece == null || piece.color != color){
                    continue;
                }
                if (piece instanceof Pawn pawn){
                    List<Vector2D> hits = pawn.getValidHits();
                    for (Vector2D hit : hits){
                        if (!tiles.contains(hit)){
                            tiles.add(hit);
                        }
                    }
                    continue;
                }
                List<Vector2D> hits = piece.getValidMoves();
                for (Vector2D hit : hits){
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
                if (piece instanceof King k && piece.color == color){
                    king = k;
                }
            }
        }
        if (king == null){
            return false;
        }
        List<Vector2D> controlledByOpponent = controlledBy(color == 'w' ? 'b' : 'w');

        return controlledByOpponent.contains(king.pos);

    }
    public void clear(){
        for (Piece[] tile : tiles) {
            Arrays.fill(tile, null);
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
                    default:
                        tiles[i][j] = null;
                }
            }
        }
    }

    public void move(Vector2D start, Vector2D end) {
        Piece piece = getPiece(start);
        if (piece == null){
            return;
        }
        setPiece(end, piece);
        setPiece(start, null);
    }

    public boolean isEmpty(Vector2D pos){
        return getPiece(pos) == null;
    }
}




















