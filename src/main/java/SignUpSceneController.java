import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;

public class SignUpSceneController {
    @FXML
    private TextField textField;
    @FXML
    private PasswordField passwordField1;
    @FXML
    private PasswordField passwordField2;
    @FXML
    private Label errText;
    @FXML
    private void goSignIn() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setController(new SignInSceneController());
        URL xmlUrl = getClass().getResource("/SignInScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        Main.getPrimaryStage().getScene().setRoot(root);
    }
    @FXML
    private void signUp() throws SocketException, UnknownHostException {
        errText.setStyle("-fx-background-color: #ff7a7a");
        if (passwordField1.getText().equals(passwordField2.getText()) && passwordField1.getText().length()!=0 && textField.getText().length()!=0) {
            StatusAuth sa = Treatment.authorization(new Authorization("sign up", textField.getText(), passwordField1.getText()));
            if (sa!=null){
                if (sa.isStatus()) {
                    errText.setStyle("-fx-background-color: #8fff9a");
                }
                errText.setText(sa.getMessage());
            } else {
                errText.setText("Истекло время ожидания ответа сервера.");
            }
        } else {
            errText.setText("Пароли не совпадают.");
        }
        errText.setVisible(true);
    }
}
