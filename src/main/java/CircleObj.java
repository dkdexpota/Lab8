import javax.swing.*;
import java.awt.*;

public class CircleObj extends JPanel implements Runnable{
    Color color;
    Double x;
    Double y;
    Double h;
    Double w;
    public CircleObj(Color color, Double x, Double y, Double h, Double w) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
    }
    @Override
    public synchronized void paint(Graphics g) {
//        g2d.draw(shape);
//        g2d.fill(shape);
    }
    @Override
    public void run() {

    }
}
