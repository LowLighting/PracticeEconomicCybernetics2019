package lab1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 500;
    public static final int X_BEGIN = 100;
    private static final int X_END = 500;
    public static final int Y = 100;
    private static Color figureColor;
    private static Color borderColor;
    private static int borderWidth;
    private static int angle = 1, step = 1, x;
    private static Figure figure = new Figure(angle, step);

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setTitle("Animation");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        frame.setBounds(dimension.width / 6, dimension.height / 5, WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        borderColor = Color.black;
        figureColor = Color.lightGray;
        borderWidth = 3;
        final JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(final Graphics g) {
                super.paintComponent(g);
                setBackground(Color.pink);
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawLine(X_BEGIN, Main.Y, X_END, Main.Y);
                g2d.setColor(borderColor);
                g2d.setStroke(new BasicStroke(borderWidth));
                g2d.draw(figure);
                g2d.setColor(figureColor);
                g2d.fill(figure);
            }
        };
        frame.add(panel);
        Timer timer = new Timer(25, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                angle = (angle >= 360) ? 1 : angle + 5;
                x += step;
                if (x == 400 || x == 0) {
                    step = -step;
                }
                figure = figure.changeAngle(angle, x);
                panel.repaint();
            }
        });
        timer.start();
    }
};


