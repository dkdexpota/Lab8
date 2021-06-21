import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.*;
import java.util.List;
//import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class ViewSceneController extends JComponent{
    public static ArrayList<Shape> circles = new ArrayList<Shape>();
    public static HashMap<String, Color> circleColor = new HashMap<>();
    public static SpaceMarine[] sp;
    HashMap<Integer, String> strCh;
    Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            count();
            repaint();
        }
    });

    ViewSceneController() {
//        super();
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                SwingUtilities.invokeLater(ViewSceneController.this::repaint);
//            }
//        }, 0, 500);
        timer.start();
    }

    private void count() {
        int x = 1000;
        int y = 1000;
        circles.clear();

        sp = null;
        ReturnPack rp;
        Random rand = new Random();
        try {
            rp = getCol();
            if (rp != null) {
                sp = rp.getSp();
                strCh = rp.getStr();
                for (SpaceMarine i : sp) {
                    if (i.getCoordinates().getX() + 50 > x) {
                        x = i.getCoordinates().getX() + 50;
                    }
                    if (i.getCoordinates().getY() + 50 > y) {
                        y = (int) (i.getCoordinates().getY() + 50);
                    }
                    if (!circleColor.containsKey(strCh.get(i.getId()))) {
                        circleColor.put(strCh.get(i.getId()), new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat()));
                    }
                    circles.add(new Ellipse2D.Double(i.getCoordinates().getX(), i.getCoordinates().getY().intValue(), i.getHealth() < 500 ? 25 : i.getHealth() < 2000 ? 40 : 55, i.getHealth() < 500 ? 25 : i.getHealth() < 2000 ? 40 : 55));
                }
            }
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
        this.setPreferredSize(new Dimension(x,y));
    }

    private ReturnPack getCol() throws SocketException, UnknownHostException {
        return Treatment.treatment(Treatment.noArg(new String[]{"show"}));
    }

    @Override
    protected void paintComponent (Graphics g)
    {
        try {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            int i = 0;
            for (Shape shape : circles) {
                g2d.setColor(circleColor.get(strCh.get(sp[i].getId())));
                g2d.draw(shape);
                g2d.fill(shape);
                i++;
            }
        } catch (Exception e) {

        }

    }


}
