package balls;

import java.awt.*;

public class Destructor extends GameObject {
    public Destructor(double x, double y, double size) {
        super(x, y, size);
    }


    @Override
    public void interact(BouncingBall ball, Field field) {
        if (contains(ball)) {
            field.removeBall(ball);
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval((int)(x - size / 2),(int)(y - size / 2), (int)size, (int)size);
    }
}
