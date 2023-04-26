package Panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import Block.Tetris;

public class GamePanel extends JPanel implements ActionListener, ItemListener{

    private Tetris tetris = new Tetris();
    private JButton newbt = new JButton("New Game");
    private JButton startbt = new JButton("Start Game");
    private int score = 0;
    private int level = 0;
    private JLabel levellb = new JLabel("Level: " + level);
    private JLabel scorelb = new JLabel("Score: " + score);
    private JLabel namelb = new JLabel("Enter Your Name");
    private JTextField nametf = new JTextField(30);
    private JLabel label = new JLabel(nametf.getText() + " Have Fun!!!", SwingConstants.CENTER);
    private JPanel plb = new JPanel(new GridLayout(1,2));
    private JPanel pbt = new JPanel(new GridLayout(1,3));
    private JPanel p = new JPanel(new GridLayout(2,1));
    private JPanel ptf = new JPanel(new FlowLayout());
    private JPanel p1 = new JPanel(new GridLayout(2,1));
    private String[] color = {"Choose Color","CYAN","GREEN", "BLUE", "RED", "YELLOW", "ORANGE"};
    private JComboBox<String> colorbox = new JComboBox<>(color);
    private int time = 1000;
    private Timer timer = new Timer(time, this);;

    GamePanel(){
        setLayout(new BorderLayout());
        add(tetris, BorderLayout.CENTER);
        scorelb.setFont(new Font("Arial", Font.BOLD, 16));
        levellb.setFont(new Font("Arial", Font.BOLD, 16));
        plb.add(scorelb);
        plb.add(levellb);
        pbt.add(newbt);
        pbt.add(startbt);
        pbt.add(colorbox);
        p.add(plb);
        p.add(pbt);
        ptf.add(namelb);
        ptf.add(nametf);
        p1.add(ptf);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        p1.add(label);
        add(p1, BorderLayout.NORTH);
        add(p, BorderLayout.SOUTH);

        newbt.addActionListener(this);
        startbt.addActionListener(this);
        colorbox.addItemListener(this);

        timer.start();

    }

    private void setRestart(){
        score = 0;
        level = 0;
        scorelb.setText("Score: " + score);
        levellb.setText("Level: " + level);
        tetris.reStart();
        time = 1000;
        timer.setDelay(time);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer){
            label.setText(nametf.getText() + " Have Fun!!!");
            if(tetris.checkClearLine == true){
                score += tetris.countClearLine;
                level++;
                scorelb.setText("Score: " + score);
                levellb.setText("Level: " + level);
                tetris.checkClearLine = false;
                tetris.countClearLine = 0;
                time -= 50;
                timer.stop();
                timer.setDelay(time);
                timer.restart();
            }
    
            if(tetris.checkGameOver == true){
                levellb.setText("Game Over!");
                timer.stop();
            }
        }else if (e.getSource() == newbt){
            setRestart();
        }else if (e.getSource() == startbt){
            if (tetris.checkGameOver == true){
                setRestart();
            }else{
                tetris.timer.start();
                tetris.requestFocusInWindow();
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (colorbox.getSelectedItem() == "Choose Color" && e.getStateChange() == 1){
            tetris.col = null;
        }else if (colorbox.getSelectedItem() == "CYAN" && e.getStateChange() == 1){
            tetris.col = Color.CYAN;
        }else if (colorbox.getSelectedItem() == "GREEN" && e.getStateChange() == 1){
            tetris.col = Color.GREEN;
        }else if (colorbox.getSelectedItem() == "BLUE" && e.getStateChange() == 1){
            tetris.col = Color.BLUE;
        }else if (colorbox.getSelectedItem() == "RED" && e.getStateChange() == 1){
            tetris.col = Color.RED;
        }else if (colorbox.getSelectedItem() == "YELLOW" && e.getStateChange() == 1){
            tetris.col =  Color.YELLOW;
        }else if (colorbox.getSelectedItem() == "ORANGE" && e.getStateChange() == 1){
            tetris.col = Color.ORANGE;
        }

        tetris.requestFocusInWindow();
    }
}

