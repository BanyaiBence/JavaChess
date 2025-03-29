package chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

enum ConsoleMode {
    NOCONSOLE, HISTORY, CONSOLE
}

public class BoardPanel extends JPanel {
    public static final int S = 100;
    private final Board board;
    private Vector2D mousePos = new Vector2D(-1, -1);
    private Map<String, BufferedImage> pieceImages;
    private List<Vector2D> moves;
    private List<Vector2D> attacks;
    private Vector2D selectedPos = new Vector2D(-1, -1);
    private String selectedPiece;
    private boolean showDebug = false;
    StringBuilder consoleInput = new StringBuilder();
    List<String> consoleHistory = new ArrayList<>();
    ConsoleMode consoleMode = ConsoleMode.NOCONSOLE;
    Config config;

    public BoardPanel(Config  config){
        super();

        this.config = config;

        setLayout(null);
        setPreferredSize(new Dimension(8 * S, 8 * S));
        setBackground(Color.WHITE);
        this.board = new Board();
        this.board.forceState("rnb1kbnr/pppppppp/8/1N1P1q2/3Q4/6B1/PPPP1PPP/R3KBNR");

        loadPieceImages();

        MouseAdapter mouseAdapter = new MouseAdapter(){

            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                moves = new ArrayList<>();
                attacks = new ArrayList<>();

                Vector2D mPos = new Vector2D(x, y);
                Vector2D pos = mPos.div(S);
                if (board.isEmpty(pos)){return;}
                Piece piece = board.getPiece(pos);
                if (piece == null || piece.color != board.getTurn()){return;}
                selectedPos = pos;



                moves = piece.getMoves().stream().filter(p->board.isSafeMove(pos, p)).toList();
                attacks = piece.getAttacks().stream().filter(p->board.isSafeMove(pos, p)).toList();

                mousePos = mPos;
                String color = String.valueOf(piece.color);
                String pieceName = piece.getClass().getName().toLowerCase();
                pieceName = pieceName.substring(pieceName.lastIndexOf('.') + 1);
                selectedPiece = color + "_" + pieceName;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (selectedPiece == null) {return;}
                Vector2D mPos = new Vector2D(e.getX(), e.getY());
                Vector2D pos = mPos.div(S);
                for (Vector2D move: moves){
                    if (move.equals(pos)){
                        board.move(selectedPos, pos);
                        break;
                    }
                }
                for (Vector2D attack: attacks){
                    if (attack.equals(pos)){
                        board.move(selectedPos, pos);
                        break;
                    }
                }
                selectedPiece = null;
                selectedPos = new Vector2D(-1, -1);
                moves = null;
                attacks = null;
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                mousePos = new Vector2D(e.getX(), e.getY());
                repaint();
            }
        };


        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);



    }
    public List<String> getHistory(){
        return board.getHistory();
    }

    private void loadPieceImages() {
        pieceImages = new HashMap<>();
        String[] pieces = new String[]{"w_pawn", "w_rook", "w_knight", "w_bishop", "w_queen", "w_king", "b_pawn", "b_rook", "b_knight", "b_bishop", "b_queen", "b_king"};
        for (String piece: pieces) {
            String path = "src/chess/assets/" + piece + ".png";
            try {
                BufferedImage img = ImageIO.read(new File(path));
                pieceImages.put(piece, img);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void drawPiece(Graphics g, Vector2D pos){
        Piece piece = board.getPiece(pos);
        if (piece != null){
            String color = String.valueOf(piece.color);
            String pieceName = piece.getClass().getName().toLowerCase();
            pieceName = pieceName.substring(pieceName.lastIndexOf('.') + 1);
            if (piece instanceof King && board.inCheck(piece.color)) {
                g.setColor(Color.RED);
                g.fillRect(pos.x * S, pos.y * S, S, S);
            }

            BufferedImage img = pieceImages.get(color + "_" + pieceName);
            g.drawImage(img, pos.x * S + S/16, pos.y * S + S/16, S-S/8, S-S/8, null);

        }
    }
    private void drawDebug(Graphics g){
        List<Vector2D> cByW = board.controlledBy('w');
        List<Vector2D> cByB = board.controlledBy('b');

        for (Vector2D pos: cByW){
            g.setColor(Color.YELLOW);
            g.fillRect(pos.x * S, pos.y * S, S/5, S/5);
        }
        if (config.getDebugOption("show_controlled")){
            for (Vector2D pos: cByB) {
                g.setColor(Color.MAGENTA);
                g.fillRect(pos.x * S + S/5, pos.y * S, S/5, S/5);
            }
        }

        g.setFont(new Font("Arial", Font.PLAIN, S/5));
        g.setColor(Color.ORANGE);
        g.drawString("Console: "+consoleMode.toString(), S, S/5);


        // Draw notation
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                String notation = (char)('A' + i) + "" + (8 - j);
                g.setColor(Color.CYAN);
                g.setFont(new Font("Arial", Font.BOLD, S/3));
                g.drawString(notation, i*S + S/4, j*S + 3*S/4);
                g.setColor(Color.MAGENTA);
                g.setFont(new Font("Arial", Font.PLAIN, S/5));
                g.drawString("(" + j + ", " + i + ")", i*S + S/4, j*S + 2*S/4);
            }
        }
    }

    private void drawMoves(Graphics g){
        if (selectedPos.x == -1){return;}

        g.setColor(Color.BLUE);
        g.setColor(config.getColor("highlighted_square"));
        g.fillRect(selectedPos.x * S + 10, selectedPos.y * S + 10, S -20, S -20);

        g.setColor(Color.YELLOW);
        for (Vector2D move: moves){
            g.fillRect(move.x * S + 10, move.y * S + 10, S -20, S -20);
        }
    }
    private void drawAttacks(Graphics g){
        if (selectedPos.x == -1){return;}

        g.setColor(Color.RED);
        for (Vector2D attack: attacks){
            g.fillRect(attack.x * S + 10, attack.y * S + 10, S -20, S -20);
        }
    }
    private void drawPieces(Graphics g){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Vector2D pos = new Vector2D(i, j);
                if (selectedPos.equals(pos)){continue;}
                drawPiece(g, pos);
            }
        }
    }
    private void drawBoard(Graphics g){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                g.setColor((i + j) % 2 == 1 ? config.getColor("light_square") : config.getColor("dark_square"));
                g.fillRect(i * S, j * S, S, S);

                g.setColor((i + j) % 2 == 0 ? config.getColor("light_square") : config.getColor("dark_square"));
                g.fillRect(i * S + S/32, j * S + S/32, S - S/16, S  - S/16);

            }
        }
    }
    private void drawSelected(Graphics g){
        if (selectedPiece == null){return;}
        BufferedImage img = pieceImages.get(selectedPiece);
        g.drawImage(img, mousePos.x - S/2, mousePos.y - S/2, S, S, null);
    }

    private void drawStepCount(Graphics g){
        Font font = new Font("Arial", Font.BOLD, S/4);
        g.setFont(font);
        g.setColor(Color.ORANGE);
        g.drawString(""+board.getStepCount() + "/" + board.getHistorySize(), 10, 20);
    }

    private void drawHistory(Graphics g){
        Font font = new Font("Arial", Font.BOLD, S/5);
        g.setFont(font);
        List<String> history = board.getHistory();
        for (int i = history.size() - 1; i >= 0; i--){
            String[] parts = history.get(i).split("\s");
            Color color = parts[1].equals("w") ? config.getColor("highlighted_text_color") : config.getColor("text_color");
            g.setColor(color);


            if (i == board.getStepCount()){
                g.drawString("> " + history.get(i), 20, (int) 7.8*S - (S/4 * (history.size() - i)));
                continue;
            }
            g.drawString(history.get(i), 20, (int)(7.8)*S - (S/4 * (history.size() - i)));
        }
    }
    private void drawConsole(Graphics g){
        Font font = new Font("Arial", Font.BOLD, S/5);
        g.setFont(font);
        g.setColor(config.getColor("text_color"));
        g.drawString("> " + consoleInput.toString(), 20, (int)(7.8)*S);
        for (int i = consoleHistory.size() - 1; i >= 0; i--){
            g.drawString(consoleHistory.get(i), 20, (int)(7.8)*S - (S/4 * (consoleHistory.size() - i)));
        }
    }

    public void toggleDebug(){
        showDebug = !showDebug;
        repaint();
    }
    public void toggleHistory(){
        consoleMode = consoleMode == ConsoleMode.HISTORY ? ConsoleMode.NOCONSOLE : ConsoleMode.HISTORY;
        repaint();
    }
    public void toggleConsole(){
        consoleMode = consoleMode == ConsoleMode.CONSOLE ? ConsoleMode.NOCONSOLE : ConsoleMode.CONSOLE;
        consoleInput = new StringBuilder();
        repaint();
    }

    public void stepForwardInHistory(){
        board.stepForwardInHistory();
        repaint();
    }
    public void stepBackInHistory(){
        board.stepBackInHistory();
        repaint();
    }
    public void executeCommand() {
        String command = consoleInput.toString();
        consoleInput = new StringBuilder();
        consoleHistory.add(command);
        consoleMode = ConsoleMode.NOCONSOLE;

        String[] tokens = command.split("\s");
        switch (tokens[0]) {
            case "history":
                toggleHistory();
                break;
            case "debug":
                toggleDebug();
                break;
            case "exit":
                System.exit(0);
                break;
            case "fen":
                board.forceState(tokens[1]);
                break;
            case "add":
                Vector2D pos = Vector2D.fromNotation(tokens[1]);
                board.addPiece(pos, tokens[2].charAt(0));
                break;
                

            default:
                if (tokens.length == 2) {
                    board.move(Vector2D.fromNotation(tokens[0].toUpperCase()), Vector2D.fromNotation(tokens[1].toUpperCase()));
                }

                repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        drawMoves(g);
        drawAttacks(g);
        drawPieces(g);
        drawSelected(g);
        drawStepCount(g);
        if (showDebug){
            drawDebug(g);
        }
        switch (consoleMode){
            case HISTORY:
                drawHistory(g);
                break;
            case CONSOLE:
                drawConsole(g);
                break;
            default:
                break;
        }
    }

}
