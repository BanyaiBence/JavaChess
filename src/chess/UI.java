package chess;

import java.awt.*;
import javax.swing.*;


public class UI extends JFrame {

    private final BoardPanel board;


    public UI(){
        super("Chess");
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        board = new BoardPanel();
        this.add(board, BorderLayout.CENTER);


        pack();

    }

    public void run(){
        setVisible(true);
        board.repaint();
        repaint();
    }

}