package Panel;

import javax.swing.*;

import Block.Tetris;

public class TetrisFrame{

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris Game");
        GamePanel game = new GamePanel();

        frame.add(game);
        frame.setSize(Tetris.width+20, Tetris.height+160);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}