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

public class PlusSceneController {
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
    private Button addButton;
    @FXML
    private Button addMinButton;
    @FXML
    private Button rmGreaterButton;
    @FXML
    private Button updButton;
    @FXML
    private Button rmIdButton;
    @FXML
    private void addEl() throws SocketException, UnknownHostException {
        SpaceMarine ps = MakeElement.makeElement("-1", nameValue.getText(), coordinates_xValue.getText(), coordinates_yValue.getText(), healthValue.getText(),
                achievementsValue.getText(), categoryValue.getValue(), weaponValue.getValue(), chapter_nameValue.getText(), chapter_marinesCountValue.getText(), errLabel);
        if (ps!=null) {
            ReturnPack rp = Treatment.treatment(new SendPack(Comand.valueOf("add"), null, ps, Treatment.username, Treatment.userpass));
            if (rp != null) {
                if (rp.getExc() != null) {
                    errLabel.setText(rp.getExc());
                } else {
                    errLabel.setText(rp.getInfo()[0]);
                }
            }
        }
    }
    @FXML
    private void addIfMin() throws SocketException, UnknownHostException {
        SpaceMarine ps = MakeElement.makeElement("-1", nameValue.getText(), coordinates_xValue.getText(), coordinates_yValue.getText(), healthValue.getText(),
                achievementsValue.getText(), categoryValue.getValue(), weaponValue.getValue(), chapter_nameValue.getText(), chapter_marinesCountValue.getText(), errLabel);
        if (ps!=null) {
            ReturnPack rp = Treatment.treatment(new SendPack(Comand.valueOf("add_if_min"), null, ps, Treatment.username, Treatment.userpass));
            if (rp != null) {
                if (rp.getExc() != null) {
                    errLabel.setText(rp.getExc());
                } else {
                    errLabel.setText(rp.getInfo()[0]);
                }
            }
        }
    }
    private void setLang() throws UnsupportedEncodingException {
        updButton.setText(new String(MainSceneController.bundleNow.getString("update").getBytes(ISO_8859_1), UTF_8));
        rmGreaterButton.setText(new String(MainSceneController.bundleNow.getString("remove_gr").getBytes(ISO_8859_1), UTF_8));
        addButton.setText(new String(MainSceneController.bundleNow.getString("add").getBytes(ISO_8859_1), UTF_8));
        addMinButton.setText(new String(MainSceneController.bundleNow.getString("add_min").getBytes(ISO_8859_1), UTF_8));
        rmIdButton.setText(new String(MainSceneController.bundleNow.getString("remove").getBytes(ISO_8859_1), UTF_8));
    }
    @FXML
    private void rmGreater() throws SocketException, UnknownHostException {
        SpaceMarine ps = MakeElement.makeElement("-1", nameValue.getText(), coordinates_xValue.getText(), coordinates_yValue.getText(), healthValue.getText(),
                achievementsValue.getText(), categoryValue.getValue(), weaponValue.getValue(), chapter_nameValue.getText(), chapter_marinesCountValue.getText(), errLabel);
        if (ps!=null) {
            ReturnPack rp = Treatment.treatment(new SendPack(Comand.valueOf("remove_greater"), null, ps, Treatment.username, Treatment.userpass));
            if (rp != null) {
                if (rp.getExc() != null) {
                    errLabel.setText(rp.getExc());
                } else {
                    errLabel.setText(rp.getInfo()[0]);
                }
            }
        }
    }
    @FXML
    private void upd() throws SocketException, UnknownHostException {
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
    public void setBox() throws UnsupportedEncodingException {
        setLang();
        categoryValue.getItems().setAll("ASSAULT", "INCEPTOR", "HELIX", "APOTHECARY");
        weaponValue.getItems().setAll("MELTAGUN", "COMBI_PLASMA_GUN", "GRAV_GUN", "NULL");
    }
    @FXML
    private void rmId() throws SocketException, UnknownHostException {
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

}
