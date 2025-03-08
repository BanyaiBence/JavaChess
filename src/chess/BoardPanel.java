package chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.List;

public class BoardPanel extends JPanel {
    public static final int SQUARE_SIZE = 80;
    private Board game;

    public BoardPanel(){
        super();
        setLayout(null);
        setPreferredSize(new Dimension(8 * SQUARE_SIZE, 8 * SQUARE_SIZE));
        setBackground(Color.WHITE);
        this.game = new Board();
        this.game.fromFen("rnb1kbnr/pppppppp/8/1N1P1q2/3Q4/6B1/PPPP1PPP/R3KBNR");

        this.addMouseListener(
                new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        int x = e.getX() / SQUARE_SIZE;
                        int y = e.getY() / SQUARE_SIZE;
                        game.setSelected(x, y);
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        int[] selected = game.getSelected();
                        if (selected[0] == -1){return;}

                        Piece piece = game.getPiece(selected[0], selected[1]);

                        if (piece == null) {return;}
                        List<int[]> moves = piece.getValidMoves();
                        int x = e.getX() / SQUARE_SIZE;
                        int y = e.getY() / SQUARE_SIZE;

                        for (int[] move: moves){
                            if (move[0] == x && move[1] == y){
                                game.move(selected[0], selected[1], x, y);
                                break;
                            }
                        }
                        game.setSelected(-1, -1);
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                }
        );
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (game.getSelected()[0] == -1){return;}
                repaint();
                drawSelected(getGraphics(), e.getX() - SQUARE_SIZE / 2, e.getY() - SQUARE_SIZE / 2);
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });


    }
    private void drawPiece(Graphics g, int x ,int y){
        Piece piece = game.getPiece(x, y);
        if (piece != null){
            String color = piece.color == 'w' ? "w" : "b";
            String pieceName = piece.getClass().getName().toLowerCase();
            pieceName = pieceName.substring(pieceName.lastIndexOf('.') + 1);
            String path = "src/chess/assets/" + color + "_" + pieceName + ".png";
            try {
                BufferedImage img = ImageIO.read(new File(path));
                g.drawImage(img, x * SQUARE_SIZE, y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void drawMoves(Graphics g){
        int[] selected = game.getSelected();
        if (selected[0] == -1){return;}
        g.setColor(Color.BLUE);
        g.fillRect(selected[0] * SQUARE_SIZE, selected[1] * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        Piece piece = game.getPiece(selected[0], selected[1]);

        if (piece == null) {return;}
        List<int[]> moves = piece.getValidMoves();
        g.setColor(Color.RED);
        for (int[] move: moves){
            g.fillRect(move[0] * SQUARE_SIZE, move[1] * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        }
    }
    private void drawPieces(Graphics g){
        int[] selected = game.getSelected();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if (selected[0] == i && selected[1] == j){continue;}
                drawPiece(g, i, j);
            }
        }
    }
    private void drawBoard(Graphics g){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                g.setColor((i + j) % 2 == 0 ? Color.WHITE : Color.BLACK);
                g.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
    }
    private void drawSelected(Graphics g, int x, int y){
        int[] selected = game.getSelected();
        if (selected[0] == -1) {return;}
        Piece piece = game.getPiece(selected[0], selected[1]);
        if (piece == null) {return;}
        String color = piece.color == 'w' ? "w" : "b";
        String pieceName = piece.getClass().getName().toLowerCase();
        pieceName = pieceName.substring(pieceName.lastIndexOf('.') + 1);
        String path = "src/chess/assets/" + color + "_" + pieceName + ".png";
        try {
            BufferedImage img = ImageIO.read(new File(path));
            g.drawImage(img, x, y , SQUARE_SIZE, SQUARE_SIZE, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        drawMoves(g);
        drawPieces(g);
    }
}
