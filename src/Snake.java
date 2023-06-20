import java.util.Random;

public class Snake extends Obstacle{
    private int damage;



    public Snake(){
        super("Snake",4,3,12,0);

    }

    @Override
    public int getDamage() {
        this.damage  = (int) (super.getDamage() + Math.random() * 4);
        return damage;
    }

}
