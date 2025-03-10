package chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardPanel extends JPanel {
    public static final int S = 80;
    private final Board board;
    private Vector2D mousePos = new Vector2D(-1, -1);
    private Map<String, BufferedImage> pieceImages;
    private List<Vector2D> moves;
    private List<Vector2D> attacks;
    private Vector2D selectedPos = new Vector2D(-1, -1);
    private String selectedPiece;


    public BoardPanel(){
        super();
        setLayout(null);
        setPreferredSize(new Dimension(8 * S, 8 * S));
        setBackground(Color.WHITE);
        this.board = new Board();
        this.board.fromFen("rnb1kbnr/pppppppp/8/1N1P1q2/3Q4/6B1/PPPP1PPP/R3KBNR");

        loadPieceImages();

        MouseAdapter mouseAdapter = new MouseAdapter(){

            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                Vector2D mPos = new Vector2D(x, y);
                Vector2D pos = mPos.div(S);
                if (board.isEmpty(pos)){return;}
                Piece piece = board.getPiece(pos);
                selectedPos = pos;
                moves = piece.getMoves();
                attacks = piece.getAttacks();
                mousePos = mPos;
                String color = String.valueOf(piece.color);
                String pieceName = piece.getClass().getName().toLowerCase();
                pieceName = pieceName.substring(pieceName.lastIndexOf('.') + 1);
                selectedPiece = color + "_" + pieceName;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
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

            BufferedImage img = pieceImages.get(color + "_" + pieceName);
            g.drawImage(img, pos.x * S, pos.y * S, S, S, null);

        }
    }
    private void drawMoves(Graphics g){
        if (selectedPos.x == -1){return;}

        g.setColor(Color.BLUE);
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
                g.setColor((i + j) % 2 == 0 ? Color.WHITE : Color.BLACK);
                g.fillRect(i * S, j * S, S, S);
            }
        }
    }
    private void drawSelected(Graphics g){
        if (selectedPiece == null){return;}
        BufferedImage img = pieceImages.get(selectedPiece);
        g.drawImage(img, mousePos.x - S/2, mousePos.y - S/2, S, S, null);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        drawMoves(g);
        drawAttacks(g);
        drawPieces(g);
        drawSelected(g);
    }
}
