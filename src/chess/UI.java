package chess;

import java.awt.*;
import javax.swing.*;


public class UI extends JFrame {

    private BoardPanel board;


    public UI(){
        super("Chess");
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board = new BoardPanel();
        this.add(board, BorderLayout.CENTER);


        pack();

    }

    public void run(){
        setVisible(true);
        repaint();
    }

}