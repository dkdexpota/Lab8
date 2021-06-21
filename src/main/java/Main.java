import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;

public class Main extends Application{
    static Scene signIn;
    static Stage primaryStage;
    public static void main(String[] args){
        Application.launch();
    }
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setWidth(1280);
        primaryStage.setHeight(720);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });

        FXMLLoader loader = new FXMLLoader();
        loader.setController(new SignInSceneController());
        URL xmlUrl = getClass().getResource("/SignInScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        signIn = new Scene(root);

        primaryStage.setScene(signIn);
        setPrimaryStage(primaryStage);
        primaryStage.show();
    }
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setPrimaryStage(Stage pStage) {
        primaryStage = pStage;
    }
}