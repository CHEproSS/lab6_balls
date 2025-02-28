package balls;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.List;

public class BouncingBall implements Runnable {
    private static final int MAX_RADIUS = 100;
    private static final int MIN_RADIUS = 50;
    private static final int MAX_SPEED = 150;
    private Field field;
    private int radius;
    private Color color;
    private double x;
    private double y;
    private double speedX;
    private double speedY;

    public BouncingBall(Field field) {
        this.field = field;
        radius = (int) (Math.random() * (MAX_RADIUS - MIN_RADIUS)) + MIN_RADIUS;
        double angle = Math.random() * 2 * Math.PI;
        speedX = 3 * Math.cos(angle);
        speedY = 3 * Math.sin(angle);
        color = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
        x = Math.random() * (field.getSize().getWidth() - 2 * radius) + radius;
        y = Math.random() * (field.getSize().getHeight() - 2 * radius) + radius;
        new Thread(this).start();
    }

    public BouncingBall createBouncingBall(Field field,double nx,double ny, int nradius, double nspeedX, double nspeedY) {
        this.field = field;
        radius = nradius;
        double angle = Math.random() * 2 * Math.PI;
        speedX = nspeedX;
        speedY = nspeedY;
        color = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
        x = nx;
        y = ny;
        new Thread(this).start();
        return this;
    }

    public void run() {
        try {
            while (true) {
                field.canMove(this);
                move();
                checkCollisions();
                checkGameObjects();
                //field.repaint();
                Thread.sleep(16);
            }
        } catch (InterruptedException ex) {
        }
    }

    private void move() {
        if (x + speedX <= radius || x + speedX >= field.getWidth() - radius) {
            speedX = -speedX;
        }
        if (y + speedY <= radius || y + speedY >= field.getHeight() - radius) {
            speedY = -speedY;
        }
        x += speedX;
        y += speedY;
    }

    private void checkCollisions() {
        List<BouncingBall> balls = field.getBalls();
        for (BouncingBall other : balls) {
            if (other == this) continue;
            double dx = other.x - this.x;
            double dy = other.y - this.y;
            double distance = Math.sqrt(dx * dx + dy * dy);
            if (distance < this.radius + other.radius) {
                double mass1 = this.radius * this.radius;
                double mass2 = other.radius * other.radius;
                double newSpeedX1 = (speedX * (mass1 - mass2) + 2 * mass2 * other.speedX) / (mass1 + mass2);
                double newSpeedY1 = (speedY * (mass1 - mass2) + 2 * mass2 * other.speedY) / (mass1 + mass2);
                double newSpeedX2 = (other.speedX * (mass2 - mass1) + 2 * mass1 * speedX) / (mass1 + mass2);
                double newSpeedY2 = (other.speedY * (mass2 - mass1) + 2 * mass1 * speedY) / (mass1 + mass2);
                this.speedX = newSpeedX1;
                this.speedY = newSpeedY1;
                other.speedX = newSpeedX2;
                other.speedY = newSpeedY2;
                double overlap = (this.radius + other.radius - distance) / 2;
                this.x -= overlap * dx / distance;
                this.y -= overlap * dy / distance;
                other.x += overlap * dx / distance;
                other.y += overlap * dy / distance;
            }
        }
    }

    private void checkGameObjects() {
        for (GameObject obj : field.getGameObjects()) {
            if (obj.contains(this)) {
                obj.interact(this,field);
            }
        }
    }

    public void paint(Graphics2D canvas) {
        canvas.setColor(color);
        Ellipse2D.Double ball = new Ellipse2D.Double(x - radius, y - radius, 2 * radius, 2 * radius);
        canvas.fill(ball);
    }

    public void setPosition(double nx,double ny){
        this.x = nx;
        this.y = ny;
    }

    public void setSpeed(double nSpeedX,double nSpeedY){
        this.speedX = nSpeedX;
        this.speedY = nSpeedY;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double getSpeedX(){
        return speedX;
    }

    public double getSpeedY(){
        return speedY;
    }

    public int getRadius(){
        return radius;
    }


    public boolean contains(int x, int y) {
        return x * x + y * y < (this.getRadius()) * (this.getRadius());
    }
}