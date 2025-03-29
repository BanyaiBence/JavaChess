package chess;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;


public class UI extends JFrame {

    private final BoardPanel board;


    public UI(Config config){
        super("Chess");
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        board = new BoardPanel(config);
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
        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_C && board.consoleMode != ConsoleMode.CONSOLE){
                    board.toggleConsole();
                    return;
                }
                if (board.consoleMode == ConsoleMode.CONSOLE){
                    if (keyEvent.getKeyCode() == KeyEvent.VK_CONTROL){
                        try {
                            String text = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor).toString();
                            board.consoleInput.append(text);
                        } catch (UnsupportedFlavorException | IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                        board.executeCommand();
                    } else if (keyEvent.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                        if (!board.consoleInput.isEmpty()) {
                            board.consoleInput.deleteCharAt(board.consoleInput.length() - 1);
                        }
                    } else if (keyEvent.getKeyCode() >= 32 && keyEvent.getKeyCode() <= 126) {
                        board.consoleInput.append(keyEvent.getKeyChar());
                    }
                    board.repaint();
                    return;
                }
                switch(keyEvent.getKeyCode()){
                    case KeyEvent.VK_D:
                        board.stepForwardInHistory();
                        break;
                    case KeyEvent.VK_A:
                        board.stepBackInHistory();
                        break;
                    case KeyEvent.VK_Q:
                        board.toggleDebug();
                        break;
                    case KeyEvent.VK_H:
                        board.toggleHistory();
                        break;

                    default:
                        break;
                }
            }
        };
        addWindowListener(windowAdapter);
        addKeyListener(keyAdapter);

        setFocusable(true);
        requestFocusInWindow();

        pack();

    }

    public void run(){
        setVisible(true);
        board.repaint();
        repaint();
    }

}