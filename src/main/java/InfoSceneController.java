import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.UnknownHostException;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

public class InfoSceneController {
    @FXML
    public Label errLabel;
    @FXML
    private TextField idValue;
    @FXML
    private TextField nameValue;
    @FXML
    private TextField coordinates_xValue;
    @FXML
    private TextField coordinates_yValue;
    @FXML
    private TextField healthValue;
    @FXML
    private TextField achievementsValue;
    @FXML
    private TextField chapter_nameValue;
    @FXML
    private TextField chapter_marinesCountValue;
    @FXML
    private ComboBox<String> categoryValue;
    @FXML
    private ComboBox<String> weaponValue;

    @FXML
    private Button updButton;
    @FXML
    private Button rmIdButton;

    @FXML
    private Label nameValueU;
    @FXML
    private Label coordinates_xValueU;
    @FXML
    private Label coordinates_yValueU;
    @FXML
    private Label healthValueU;
    @FXML
    private Label achievementsValueU;
    @FXML
    private Label chapter_nameValueU;
    @FXML
    private Label chapter_marinesCountValueU;
    @FXML
    private Label categoryValueU;
    @FXML
    private Label weaponValueU;

    @FXML
    private void rmEl() throws SocketException, UnknownHostException {
        SendPack sp = Treatment.byNum("remove_by_id", idValue.getText(), errLabel);
        if (sp!=null) {
            ReturnPack rp = Treatment.treatment(sp);
            if (rp != null) {
                if (rp.getExc() != null) {
                    errLabel.setText(rp.getExc());
                } else {
                    errLabel.setText(rp.getInfo()[0]);
                }
            }
        }
    }

    private void setLeng() throws UnsupportedEncodingException {
        nameValueU.setText(new String(MainSceneController.bundleNow.getString("name").getBytes(ISO_8859_1), UTF_8));
        coordinates_xValueU.setText(new String(MainSceneController.bundleNow.getString("coordinates").getBytes(ISO_8859_1), UTF_8) + " x");
        coordinates_yValueU.setText(new String(MainSceneController.bundleNow.getString("coordinates").getBytes(ISO_8859_1), UTF_8) + " y");
        healthValueU.setText(new String(MainSceneController.bundleNow.getString("health").getBytes(ISO_8859_1), UTF_8));
        achievementsValueU.setText(new String(MainSceneController.bundleNow.getString("achievement").getBytes(ISO_8859_1), UTF_8));
        categoryValueU.setText(new String(MainSceneController.bundleNow.getString("category").getBytes(ISO_8859_1), UTF_8));
        weaponValueU.setText(new String(MainSceneController.bundleNow.getString("weapon").getBytes(ISO_8859_1), UTF_8));
        chapter_nameValueU.setText(new String(MainSceneController.bundleNow.getString("chapter").getBytes(ISO_8859_1), UTF_8));
        chapter_marinesCountValueU.setText(new String(MainSceneController.bundleNow.getString("marin").getBytes(ISO_8859_1), UTF_8));
        updButton.setText(new String(MainSceneController.bundleNow.getString("update").getBytes(ISO_8859_1), UTF_8));
        rmIdButton.setText(new String(MainSceneController.bundleNow.getString("remove").getBytes(ISO_8859_1), UTF_8));
    }

    public void setVal(SpaceMarine sp) throws UnsupportedEncodingException {
        setLeng();
        categoryValue.getItems().setAll("ASSAULT", "INCEPTOR", "HELIX", "APOTHECARY");
        weaponValue.getItems().setAll("MELTAGUN", "COMBI_PLASMA_GUN", "GRAV_GUN", "NULL");
        idValue.setText(String.valueOf(sp.getId()));
        nameValue.setText(sp.getName());
        coordinates_xValue.setText(String.valueOf(sp.getCoordinates().getX()));
        coordinates_yValue.setText(String.valueOf(sp.getCoordinates().getY()));
        healthValue.setText(String.valueOf(sp.getHealth()));
        achievementsValue.setText(sp.getAchievements()!=null ? sp.getAchievements() : "");
        chapter_nameValue.setText(sp.getChapter()!=null ? sp.getChapter().getName() : "");
        chapter_marinesCountValue.setText(sp.getChapter()!=null && sp.getChapter().getMarinesCount()!=null ? String.valueOf(sp.getChapter().getMarinesCount()) : null);
        categoryValue.setValue(sp.getCategory().toString());
        weaponValue.setValue(sp.getWeaponType()!=null ? sp.getWeaponType().toString() : "NULL");

    }
    @FXML
    private void updateEl() throws SocketException, UnknownHostException {
        SpaceMarine ps = MakeElement.makeElement(idValue.getText(), nameValue.getText(), coordinates_xValue.getText(), coordinates_yValue.getText(), healthValue.getText(),
                achievementsValue.getText(), categoryValue.getValue(), weaponValue.getValue(), chapter_nameValue.getText(), chapter_marinesCountValue.getText(), errLabel);
        if (ps!=null) {
            ReturnPack rp = Treatment.treatment(new SendPack(Comand.valueOf("update"), null, ps, Treatment.username, Treatment.userpass));
            if (rp != null) {
                if (rp.getExc() != null) {
                    errLabel.setText(rp.getExc());
                } else {
                    errLabel.setText(rp.getInfo()[0]);
                }
            }
        }
    }
}
