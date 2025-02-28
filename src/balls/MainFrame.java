package balls;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {
    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;
    private JMenuItem pauseMenuItem;
    private JMenuItem resumeMenuItem;
    private Field field = new Field();

    private int randomX() {
        return 100 + (int) (Math.random() * (WIDTH - 200));
    }

    private int randomY() {
        return 100 + (int) (Math.random() * (HEIGHT - 200));
    }

    public MainFrame() {
        super("Программирование и синхронизация потоков");
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH) / 2,
                (kit.getScreenSize().height - HEIGHT) / 2);
        setExtendedState(MAXIMIZED_BOTH);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu ballMenu = new JMenu("Мячи");
        menuBar.add(ballMenu);
        ballMenu.add(new AbstractAction("Добавить мяч") {
            public void actionPerformed(ActionEvent event) {
                field.addBall();
                if (!pauseMenuItem.isEnabled() &&
                        !resumeMenuItem.isEnabled()) {
                    pauseMenuItem.setEnabled(true);
                }
            }
        });

        JMenu controlMenu = new JMenu("Управление");
        menuBar.add(controlMenu);
        pauseMenuItem = controlMenu.add(new AbstractAction("Приостановить движение") {
            public void actionPerformed(ActionEvent event) {
                field.pause();
                pauseMenuItem.setEnabled(false);
                resumeMenuItem.setEnabled(true);
            }
        });
        pauseMenuItem.setEnabled(false);

        resumeMenuItem = controlMenu.add(new AbstractAction("Возобновить движение") {
            public void actionPerformed(ActionEvent event) {
                field.resume();
                pauseMenuItem.setEnabled(true);
                resumeMenuItem.setEnabled(false);
            }
        });
        resumeMenuItem.setEnabled(false);



        JMenu objectMenu = new JMenu("Объекты");
        menuBar.add(objectMenu);
        objectMenu.add(new AbstractAction("Добавить конструктор") {
            public void actionPerformed(ActionEvent event) {
                field.addGameObject(new Constructor(randomX(), randomY(), 150));
                field.repaint();
            }
        });
        objectMenu.add(new AbstractAction("Добавить деструктор") {
            public void actionPerformed(ActionEvent event) {
                field.addGameObject(new Destructor(randomX(), randomY(), 150));
                field.repaint();
            }
        });
        objectMenu.add(new AbstractAction("Добавить телепорт") {
            public void actionPerformed(ActionEvent event) {
                field.addGameObject(new Teleport(randomX(), randomY(), 200, randomX(), randomY()));
                field.repaint();
            }
        });



        getContentPane().add(field, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
