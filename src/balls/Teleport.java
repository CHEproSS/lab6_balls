package balls;

import java.awt.*;

public class Teleport extends GameObject {
    private double targetX, targetY;

    public Teleport(double x, double y, double size, double targetX, double targetY) {
        super(x, y, size);
        this.targetX = targetX;
        this.targetY = targetY;
    }

    @Override
    public void interact(BouncingBall ball, Field field) {
        if (contains(ball)) {
            ball.setPosition(targetX, targetY);
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval((int)(x - size / 2), (int)(y - size / 2),(int) size,(int) size);
        g.setColor(Color.CYAN);
        g.drawOval((int)(targetX - size / 2), (int)(targetY - size / 2),(int) size,(int) size);
    }
}

