package Block;

public class Block extends Shape{
    
    protected int[][] shape;

    Block(){
        super();
        shape = shapes[randomShape];
    }

    private int[][][] shapes = {square, left_l, right_l, left_s, right_s, line, t};
    
    private int randomShape = (int) (Math.random()*(7));

    protected int getH(){return shape.length;}
    protected int getW(){return shape[0].length;}
    protected int[][] getShape(){return shape;}
}
