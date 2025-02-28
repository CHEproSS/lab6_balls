package balls;

import java.awt.*;

public abstract class GameObject {
    protected double x, y, size;

    public GameObject(double x, double y, double size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public abstract void interact(BouncingBall ball, Field field);

    public void paint(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillOval((int)(x - size / 2), (int)(y - size / 2), (int)size, (int)size);
    }

    public boolean contains(BouncingBall ball) {
        double dx = x - ball.getX();
        double dy = y - ball.getY();
        double distanceSquared = dx * dx + dy * dy;
        double radiusSumSquared = (size / 2) * (size / 2);
        return distanceSquared <= radiusSumSquared;
    }
}