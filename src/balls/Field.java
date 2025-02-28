package balls;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Field extends JPanel {
    private List<BouncingBall> balls = new ArrayList<>(1);
    private List<GameObject> gameObjects = new ArrayList<>(1);
    private volatile boolean paused = false;
    private BouncingBall selectedBall = null;
    private int startX, startY;

    private Timer repaintTimer = new Timer(10, new ActionListener() {
        public void actionPerformed(ActionEvent ev) {
            repaint();
        }
    });



    public Field() {
        setBackground(Color.WHITE);
        repaintTimer.start();

        // Добавляем обработчики событий мыши


        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (BouncingBall ball : balls) {
                    if (ball.contains(e.getX(), e.getY())) {
                        selectedBall = ball;
                        startX = e.getX();
                        startY = e.getY();
                        paused = true; // Останавливаем все мячи
                        break;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (selectedBall != null) {
                    double dx = e.getX() - startX;
                    double dy = e.getY() - startY;
                    double speedFactor = 0.1; // Коэффициент скорости
                    selectedBall.setSpeed(dx * speedFactor, dy * speedFactor);
                    paused = false; // Возобновляем движение
                    selectedBall = null;
                }
            }
        });

    }



    public synchronized void addBall() {
        BouncingBall ball = new BouncingBall(this);
        balls.add(ball);
    }

    public synchronized void addFixedBall(BouncingBall ball) {
        balls.add(ball);
    }

    public synchronized List<BouncingBall> getBalls() {
        return balls;
    }

    public synchronized List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public synchronized void addGameObject(GameObject obj) {
        gameObjects.add(obj);

    }

    public synchronized void removeBall(BouncingBall ball) {
        balls.remove(ball);
    }

    public synchronized void pause() {
        paused = true;
    }

    public synchronized void resume() {
        paused = false;
        notifyAll();
    }

    public synchronized void canMove(BouncingBall ball) throws InterruptedException {
        while (paused) {
            wait();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        synchronized (this) {
            for (GameObject obj : gameObjects) {
                obj.paint(g2);
            }
            for (BouncingBall ball : balls) {
                ball.paint(g2);
            }
        }
    }
}
