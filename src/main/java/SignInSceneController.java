import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;

public class SignInSceneController {
    @FXML
    private TextField textField;
    @FXML
    private Button signUp;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errText;
    @FXML
    private void checkUser() throws IOException {
        StatusAuth sa = Treatment.authorization(new Authorization("sign in", textField.getText(), passwordField.getText()));
        if (sa!=null && sa.isStatus()) {
            FXMLLoader loader = new FXMLLoader();
            MainSceneController msc = new MainSceneController();
            loader.setController(msc);
            URL xmlUrl = getClass().getResource("/MainScene.fxml");
            loader.setLocation(xmlUrl);
            Parent root = loader.load();
            Main.getPrimaryStage().getScene().setRoot(root);
            msc.info();
            msc.setBox();
        } else if (sa!=null){
            errText.setText(sa.getMessage());
            errText.setVisible(true);
        } else {
            errText.setText("Истекло время ожидания ответа сервера.");
            errText.setVisible(true);
        }
    }
    @FXML
    private void goSignUp() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setController(new SignUpSceneController());
        URL xmlUrl = getClass().getResource("/SignUpScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        Main.getPrimaryStage().getScene().setRoot(root);
    }
}
