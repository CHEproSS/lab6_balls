package balls;

import java.awt.*;
import java.util.Random;

public class Constructor extends GameObject {
    private static final Random rand = new Random();

    public Constructor(double x, double y, double size) {
        super(x, y, size);
    }

    @Override
    public void interact(BouncingBall ball, Field field) {
        if (contains(ball)) {
            double angle = rand.nextDouble() * 2 * Math.PI;
            double speedX = ball.getSpeedX();
            double speedY = ball.getSpeedY();
            BouncingBall newBall = ball.createBouncingBall(field,ball.getX() - ball.getRadius() , ball.getY() - ball.getRadius(), ball.getRadius(), ball.getSpeedX(), ball.getSpeedY());
            field.addFixedBall(newBall);
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillOval((int)(x - size / 2), (int)(y - size / 2), (int)size, (int)size);
    }
}
