import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

public class MouseCheck extends Thread implements MouseListener {
    public static boolean corrCheck = false;

    @Override
    public void mouseClicked(MouseEvent e) {
        newStage(e);
    }

    private void newStage(MouseEvent e) {
        Point p = e.getPoint();
        for (int i = 0; i < ViewSceneController.circles.size(); i++) {
            Rectangle rec = ViewSceneController.circles.get(i).getBounds();
            if ((rec.x + rec.height - p.x) * (rec.x + rec.height - p.x) < rec.height * rec.height && (rec.y + rec.height - p.y) * (rec.y + rec.height - p.y) < rec.height * rec.height) {

                int finalI = i;
                Platform.runLater(() -> {
                    if (!corrCheck) {
                        try {
                            SpaceMarine spaceMarine = ViewSceneController.sp[finalI];
                            if (spaceMarine != null) {
                                FXMLLoader loader = new FXMLLoader();
                                InfoSceneController psc = new InfoSceneController();
                                loader.setController(psc);
                                URL xmlUrl = getClass().getResource("/InfoScene.fxml");
                                loader.setLocation(xmlUrl);
                                Parent root = null;
                                try {
                                    root = loader.load();
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                                Scene secondScene = new Scene(root, 350, 400);
                                Stage newWindow = new Stage();
                                newWindow.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                    @Override
                                    public void handle(WindowEvent event) {
                                        corrCheck = false;
                                    }
                                });
                                newWindow.setTitle("Info Stage");
                                newWindow.setScene(secondScene);
                                newWindow.show();
                                psc.setVal(spaceMarine);
                            }
                        } catch (UnsupportedEncodingException unsupportedEncodingException) {
                            unsupportedEncodingException.printStackTrace();
                        }
                        corrCheck = true;
                    }
                });
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
