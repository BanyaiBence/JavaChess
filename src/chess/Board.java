package chess;

import java.util.*;
import java.util.stream.Collectors;


public class Board {
    private final Piece[][] tiles = new Piece[8][8];
    private List<String> history = new ArrayList<>();
    private char turn;
    private int historyIndex = 0;
    private final List<HashMap<String, Vector2D>> castlingRights = new ArrayList<>();
    Vector2D enPassant = null;

    public Board(){
        fromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq");
        history.add(this.toFen());
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
        List<Vector2D> result = new ArrayList<>();

        for (Piece[] row : this.tiles){
            for (Piece piece : row){
                if (piece == null || piece.color != color){
                    continue;
                }
                List<Vector2D> attacks = piece.getControlled();
                result.addAll(attacks);
            }
        }

        return result;
    }
    public boolean inCheck(char color){
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

        String[] parts = fen.split("\s");
        if (parts.length > 1){
            turn = parts[1].charAt(0);
        } else {
            turn = 'w';
        }
        if (parts.length > 2){
            castlingRights.clear();
            for (char c : parts[2].toCharArray()){
                HashMap<String, Vector2D> rights = new HashMap<>();
                switch(c){
                    case 'K':
                        rights.put("king", new Vector2D(6, 7));
                        rights.put("rook", new Vector2D(5, 7));
                        break;
                    case 'Q':
                        rights.put("king", new Vector2D(2, 7));
                        rights.put("rook", new Vector2D(3, 7));
                        break;
                    case 'k':
                        rights.put("king", new Vector2D(6, 0));
                        rights.put("rook", new Vector2D(5, 0));
                        break;
                    case 'q':
                        rights.put("king", new Vector2D(2, 0));
                        rights.put("rook", new Vector2D(3, 0));
                        break;

                    default:
                        break;
                }
                castlingRights.add(rights);

            }
        }
        fen = prepFen(parts[0]);

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
    public String toFen(){
        return Arrays.stream(tiles)
                .map (row -> {
                    StringBuilder result = new StringBuilder();
                    int empty = 0;
                    for (Piece piece : row){
                        if (piece == null){
                            empty++;
                            continue;
                        }
                        result.append(empty > 0 ? empty : "");
                        result.append(piece.toString());
                        empty = 0;
                    }
                    result.append(empty > 0 ? empty : "");
                    return result.toString();
                }).collect(Collectors.joining("/")) + " " + turn;
    }

    public void move(Vector2D start, Vector2D end) {
        Piece piece = getPiece(start);
        if (piece == null){
            return;
        }
        if (historyIndex < history.size() - 1){
            history = history.subList(0, historyIndex + 1);
            historyIndex = history.size() - 1;
        }

        updateCastlingRights(start.x, start.y);
        setPiece(end, piece);
        setPiece(start, null);
        historyIndex++;
        changeTurn();
        history.add(this.toFen());

        if (piece instanceof Pawn && end.equals(enPassant)){
            Vector2D target = new Vector2D(end.x, start.y);
            setPiece(target, null);
        }


        if (piece instanceof Pawn && Math.abs(start.y - end.y) == 2){
            enPassant = new Vector2D(start.x, (start.y + end.y) / 2);
        } else {
            enPassant = null;
        }
    }

    public void updateCastlingRights(int x, int y){
        if ((x == 0 || x == 7) && (y == 0 || y == 7)){
            castlingRights.remove(new Vector2D(x, y));
        }
        if (x == 4 && (y == 0 || y == 7)){
            castlingRights.remove(new Vector2D(0, y));
            castlingRights.remove(new Vector2D(7, y));
        }
    }

    public void revertMove(){
        if (history.size() < 2){
            return;
        }
        fromFen(history.get(history.size() - 2));
        history.removeLast();
    }

    public boolean isEmpty(Vector2D pos){
        return getPiece(pos) == null;
    }

    public boolean isSafeMove(Vector2D start, Vector2D end){
        Board sim = new Board();
        sim.fromFen(this.toFen());
        sim.move(start, end);
        return !sim.inCheck(sim.getPiece(end).color);
    }

    public List<String> getHistory(){
        return history;
    }
    public void stepBackInHistory(){
        if (historyIndex > 0){
            fromFen(history.get(--historyIndex));
        }
    }
    public void stepForwardInHistory(){
        if (historyIndex < history.size() - 1){
            fromFen(history.get(++historyIndex));
        }
    }

    public int getStepCount(){
        return historyIndex;
    }

    public int getHistorySize(){
        return history.size()-1;
    }

    public void forceState(String fen){
        fromFen(fen);
        historyIndex++;
        history.add(this.toFen());
    }

    public void changeTurn(){
        turn = turn == 'w' ? 'b' : 'w';
    }
    public char getTurn(){
        return turn;
    }


    public Vector2D getEnPassant() {
        return enPassant;
    }

    public void addPiece(Vector2D pos, char pieceNote) {
        char color = Character.isUpperCase(pieceNote) ? 'w' : 'b';
        Piece piece = null;
        switch(Character.toLowerCase(pieceNote)){
            case 'b':
                piece = new Bishop(pos.x, pos.y, color, this);
                break;
            case 'n':
                piece = new Knight(pos.x, pos.y, color, this);
                break;
            case 'p':
                piece = new Pawn(pos.x, pos.y, color, this);
                break;
            case 'q':
                piece = new Queen(pos.x, pos.y, color, this);
                break;
            case 'r':
                piece = new Rook(pos.x, pos.y, color, this);
                break;
            case 'k':
                piece = new King(pos.x, pos.y, color, this);
                break;
            default:
                break;
        }
        setPiece(pos, piece);

    }
}




















