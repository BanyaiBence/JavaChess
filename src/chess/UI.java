package chess;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


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


        WindowAdapter windowAdapter = new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    FileWriter writer = new FileWriter("history.txt", true);
                    List<String> gameHistory = board.getHistory();
                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    writer.write(formatter.format(date) + "\n");

                    for (String move : gameHistory){
                        writer.write(move + "\n");
                    }
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
        };
        addWindowListener(windowAdapter);

        pack();

    }

    public void run(){
        setVisible(true);
        board.repaint();
        repaint();
    }

}