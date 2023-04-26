package Block;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Tetris extends JPanel implements ActionListener, KeyListener{
    public static final int width = 500;
    public static final int height = 700;
    private int time = 1000;
    public Timer timer = new Timer(time, this);
    private int grid = 50;
    private int gridRow = height/grid;
    private int gridCol = width/grid;
    private int[][] board = new int[gridRow][gridCol];
    private Block shape = new Block();
    public Color col;

    private int[][] block = shape.getShape();

    private int h = shape.getH();
    private int w = shape.getW();
    private boolean checkRotate = true;

    private int y = 0;
    private int x = (int) (Math.random()*(gridCol - w)) * grid;

    private boolean checkBottom = false;

    public boolean checkClearLine = false;
    public int countClearLine = 0;
    public boolean checkGameOver = false;

    public Tetris(){
        addKeyListener(this);
    }

    private void drawShape(Graphics g){
        //Draw existing block
        for(int i=0; i<gridRow; i++){
            for(int j=0; j<gridCol; j++){
                if(board[i][j] == 1){
                    g.setColor(col);
                    g.fillRect(j*grid, i*grid, grid, grid);
                    g.setColor(Color.black);
                    g.drawRect(j*grid, i*grid, grid, grid);
                }
            }
        }

        //Draw newBlock
        for (int i=0; i<h; i++){
            for(int j=0; j<w; j++){
                if (block[i][j] == 1){
                    
                    int X = j*grid + x;
                    int Y = i*grid + y - (h*grid);
    
                    g.setColor(col);
                    g.fillRect(X, Y, grid, grid);
                    g.setColor(Color.BLACK);
                    g.drawRect(X, Y, grid, grid);
                }
            }
        }
    }

    private void newBlock(){

        shape = new Block();
        
        block = shape.getShape();
    
        h = shape.getH();
        w = shape.getW();
        
        y = 0;
        x = (int) (Math.random()*(gridCol - w)) * grid;;

        checkRotate = true;
        checkBottom = false;
    }

    private void rotate() {
        //Rotate clockwise
        int[][] rotateShape = new int[w][h];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                rotateShape[j][h - 1 - i] = block[i][j];
            }
        }
        block = rotateShape;

        //Update its width and height
        if (checkRotate == true){
            h = shape.getW();
            w = shape.getH();
            checkRotate = false;
        }else{
            h = shape.getH();
            w = shape.getW();
            checkRotate = true;
        }
    }

    private void updateBoard(){
        for(int i =0; i<h; i++){
            for(int j=0; j<w; j++){

                int X = j*grid + x;
                int Y = i*grid + y - (h*grid);

                int row = (Y + i)/grid;
                int col = (X + j)/grid;

                if(block[i][j] == 1){
                    try{
                        board[row][col] = 1; //Change board from 0 to 1
                    }catch (Exception e){
                        checkGameOver = true;
                    }
                }
            }
        }
    }

    private boolean isLanded(){
        for(int i =0; i<h; i++){
            for(int j=0; j<w; j++){

                int X = j*grid + x;
                int Y = i*grid + y - (h*grid);

                int row = (Y + i)/grid;
                int col = (X + j)/grid;

                try{
                    if(block[i][j] == 1){
                        if(board[row+1][col] != 0){ //Check 1 grid down ahead
                            checkBottom = true;
                            return true;
                        }
                    }
                }catch(Exception e){}
            }
        }
        return false;
    }

    private void afterLanded(){
        clearLine();

        if(isOver() == true){
            timer.stop();
        }
    }

    private boolean isOver(){ //Top touch the edge
        try{
            for(int i=0; i<gridCol; i++){
                if(board[0][i] == 1){
                    checkGameOver = true;
                }
            }
        }catch(Exception e){
            checkGameOver = false;
        }

        if(checkGameOver == true){
            return true;
        }else{
            return false;
        }
    }

    private void clearLine(){
        for(int r = gridRow-1; r >= 0; r--){ //Find the full row
            boolean rowFull = true;
            for(int c=0; c<gridCol; c++){
                if(board[r][c] == 0){
                    rowFull = false;
                    break;
                }
            }

            if (rowFull == true){
                countClearLine++;
                checkClearLine = true;
                time -= 50;
                timer.stop();
                timer.setDelay(time);
                timer.restart();

                fillRow(r); //fill that row with 0


                //Update the board shiftdown
                for(int i=r; i>0; i--){ //From the 0 row to the top
                    for(int j=0; j<gridCol; j++){
                        board[i][j] = board[i-1][j]; //Set the value to the one that above them
                    }
                }

                fillRow(0); //fill the top row(0)
                
                r++; //to make it check from the bottom again
            }
        }
    }

    private void fillRow(int r){
        for(int i=0; i<gridCol; i++){
            board[r][i] = 0;
        }
    }

    public void reStart(){
        // Stop the timer and remove KeyLister to their initial state
        timer.stop();
        removeKeyListener(this);
    
        // Reset variables to their initial values
        grid = 50;
        gridRow = height/grid;
        gridCol = width/grid;
        board = new int[gridRow][gridCol];
        shape = new Block();
        block = shape.getShape();
        h = shape.getH();
        w = shape.getW();
        y = 0;
        x = (int) (Math.random()*(gridCol - w)) * grid;
        checkRotate = true;
        checkBottom = false;
        checkClearLine = false;
        checkGameOver = false;
    
        // Restart the timer
        time = 1000;
        timer.setDelay(time);
        timer.start();
    
        // Request focus and add key listener to JPanel
        requestFocusInWindow();
        addKeyListener(this);
    
        // Repaint the JPanel
        super.paintComponent(getGraphics());
        repaint();
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        //Draw grid
        g.setColor(Color.BLACK);
        for(int row=0; row < gridCol; row++){
            for(int col=0; col < gridRow; col++){
                g.drawRect(grid*row, grid*col, grid, grid);
            }
        }
        
        drawShape(g);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        // Check if the block is going to the bottom
        int bottomEdge = y;
        if ((bottomEdge <= height - grid)){
            if(isLanded() == false){
                y += grid;
                repaint();
                checkBottom = false;
            }else{
                checkBottom = true;
                updateBoard();
                afterLanded();
                newBlock();
            }
        }else{
            checkBottom = true;
            updateBoard();
            afterLanded();
            newBlock();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(checkBottom == false){
            if (e.getKeyCode() == KeyEvent.VK_RIGHT){
                // Check if the block is going to the right edge
                int rightEdge = x + w * grid * ((int)(Math.sqrt(1)));
                if (rightEdge <= width-grid){
                    x += grid;
                    repaint();
                    }
            } else if(e.getKeyCode() == KeyEvent.VK_LEFT){
                // Check if the block is going to the left edge
                if (x > 0){
                    x -= grid;
                    repaint();
                }
            }else if(e.getKeyCode() == KeyEvent.VK_UP){
                rotate();
                repaint();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

}
