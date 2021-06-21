import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.*;

public class MainSceneController {
    public static ResourceBundle bundleDefault = ResourceBundle.getBundle("text", new Locale("en", "EN"));
    public static ResourceBundle bundleRu = ResourceBundle.getBundle("text", new Locale("ru", "RU"));
    public static ZoneId zid = ZoneId.of("Europe/Moscow");
    public static ResourceBundle bundleIta = ResourceBundle.getBundle("text", new Locale("ita", "ITA"));
    public static ResourceBundle bundleMac = ResourceBundle.getBundle("text", new Locale("mac", "MAC"));
    public static ResourceBundle bundleNow = bundleRu;
    javax.swing.Timer timer = new Timer(4000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Platform.runLater(() -> {
                try {
                    refresh();
                } catch (SocketException | UnknownHostException f) {
                    f.printStackTrace();
                }
            });
        }
    });

    public static boolean plusCheck = false;
    public static boolean viewCheck = false;
    public static boolean corrCheck = false;
    @FXML
    private TableColumn<SpaceMarine, Integer> idColumn;
    @FXML
    private TableColumn<SpaceMarine, String> nameColumn;
    @FXML
    private TableColumn<SpaceMarine, Integer> coordinates_xColumn;
    @FXML
    private TableColumn<SpaceMarine, Double> coordinates_yColumn;
    @FXML
    private TableColumn<SpaceMarine, java.time.ZonedDateTime> creationDateColumn;
    @FXML
    private TableColumn<SpaceMarine, Long> healthColumn;
    @FXML
    private TableColumn<SpaceMarine, String> achievementsColumn;
    @FXML
    private TableColumn<SpaceMarine, String> categoryColumn;
    @FXML
    private TableColumn<SpaceMarine, String> weaponTypeColumn;
    @FXML
    private TableColumn<SpaceMarine, String> chapter_nameColumn;
    @FXML
    private TableColumn<SpaceMarine, Integer> chapter_marinesCountColumn;
    @FXML
    private ComboBox<String> filterBox;
    @FXML
    private Label typeLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label countLabel;
    @FXML
    private ComboBox<String> languageBox;
    @FXML
    private Label username;
    @FXML
    private TextField filterValue;
    @FXML
    private TableView<SpaceMarine> tableView;
    @FXML
    private Label countInfo;
    @FXML
    private Button findButton;

    public MainSceneController() {
        timer.start();
    }

    @FXML
    private void translate() throws UnsupportedEncodingException, SocketException, UnknownHostException {
        switch (Language.valueOf(languageBox.getValue())) {
            case русский:
                zid = ZoneId.of("Europe/Moscow");
                bundleNow = bundleRu;
                swichLeng(bundleRu);
                break;
            case македонски:
                zid = ZoneId.of("UTC+2");
                bundleNow = bundleMac;
                swichLeng(bundleMac);
                break;
            case english:
                zid = ZoneId.of("UTC+1");
                bundleNow = bundleDefault;
                swichLeng(bundleDefault);
                break;
            case italiano:
                zid = ZoneId.of("Europe/Rome");
                bundleNow = bundleIta;
                swichLeng(bundleIta);
                break;
        }
    }

    private void swichLeng(ResourceBundle bundle) throws UnsupportedEncodingException, SocketException, UnknownHostException {
        Locale.setDefault(bundle.getLocale());
        nameColumn.setText(new String(bundle.getString("name").getBytes(ISO_8859_1), UTF_8));
        coordinates_xColumn.setText(new String(bundle.getString("coordinates").getBytes(ISO_8859_1), UTF_8) + " x");
        coordinates_yColumn.setText(new String(bundle.getString("coordinates").getBytes(ISO_8859_1), UTF_8) + " y");
        creationDateColumn.setText(new String(bundle.getString("date").getBytes(ISO_8859_1), UTF_8));
        healthColumn.setText(new String(bundle.getString("health").getBytes(ISO_8859_1), UTF_8));
        achievementsColumn.setText(new String(bundle.getString("achievement").getBytes(ISO_8859_1), UTF_8));
        categoryColumn.setText(new String(bundle.getString("category").getBytes(ISO_8859_1), UTF_8));
        weaponTypeColumn.setText(new String(bundle.getString("weapon").getBytes(ISO_8859_1), UTF_8));
        chapter_nameColumn.setText(new String(bundle.getString("chapter").getBytes(ISO_8859_1), UTF_8));
        chapter_marinesCountColumn.setText(new String(bundle.getString("marin").getBytes(ISO_8859_1), UTF_8));
        findButton.setText(new String(bundle.getString("find").getBytes(ISO_8859_1), UTF_8));


        refresh();
    }

    @FXML
    private void find() throws SocketException, UnknownHostException {
        if (filterValue.getText().length() != 0) {
            switch (SpacePols.valueOf(filterBox.getValue())) {
                case id:
                    setTable(Arrays.stream(Treatment.treatment(Treatment.noArg(new String[]{"show"})).getSp()).filter(x -> x.getId().toString().equals(filterValue.getText())).toArray(SpaceMarine[]::new));
                    break;
                case name:
                    setTable(Arrays.stream(Treatment.treatment(Treatment.noArg(new String[]{"show"})).getSp()).filter(x -> x.getName().equals(filterValue.getText())).toArray(SpaceMarine[]::new));
                    break;
                case coordinates_x:
                    setTable(Arrays.stream(Treatment.treatment(Treatment.noArg(new String[]{"show"})).getSp()).filter(x -> Integer.valueOf(x.getCoordinates().getX()).toString().equals(filterValue.getText())).toArray(SpaceMarine[]::new));
                    break;
                case coordinates_y:
                    setTable(Arrays.stream(Treatment.treatment(Treatment.noArg(new String[]{"show"})).getSp()).filter(x -> x.getCoordinates().getY().toString().equals(filterValue.getText())).toArray(SpaceMarine[]::new));
                    break;
                case creationDate:
                    setTable(Arrays.stream(Treatment.treatment(Treatment.noArg(new String[]{"show"})).getSp()).filter(x -> x.getCreationDate().toString().equals(filterValue.getText())).toArray(SpaceMarine[]::new));
                    break;
                case health:
                    setTable(Arrays.stream(Treatment.treatment(Treatment.noArg(new String[]{"show"})).getSp()).filter(x -> Long.valueOf(x.getHealth()).toString().equals(filterValue.getText())).toArray(SpaceMarine[]::new));
                    break;
                case achievements:
                    setTable(Arrays.stream(Treatment.treatment(Treatment.noArg(new String[]{"show"})).getSp()).filter(x -> x.getAchievements().equals(filterValue.getText())).toArray(SpaceMarine[]::new));
                    break;
                case category:
                    setTable(Arrays.stream(Treatment.treatment(Treatment.noArg(new String[]{"show"})).getSp()).filter(x -> x.getCategory().toString().equals(filterValue.getText())).toArray(SpaceMarine[]::new));
                    break;
                case weaponType:
                    setTable(Arrays.stream(Treatment.treatment(Treatment.noArg(new String[]{"show"})).getSp()).filter(x -> (x.getWeaponType() != null && x.getWeaponType().toString().equals(filterValue.getText())) || (x.getWeaponType() == null && filterValue.getText().length() == 0)).toArray(SpaceMarine[]::new));
                    break;
                case chapter_name:
                    setTable(Arrays.stream(Treatment.treatment(Treatment.noArg(new String[]{"show"})).getSp()).filter(x -> (x.getChapter() != null && x.getChapter().getName().equals(filterValue.getText())) || (x.getChapter() == null && filterValue.getText().length() == 0)).toArray(SpaceMarine[]::new));
                    break;
                case chapter_marinesCount:
                    setTable(Arrays.stream(Treatment.treatment(Treatment.noArg(new String[]{"show"})).getSp()).filter(x -> x.getChapter() != null && ((x.getChapter().getMarinesCount() != null && x.getChapter().getMarinesCount().toString().equals(filterValue.getText())) || (x.getChapter().getMarinesCount() == null && filterValue.getText().length() == 0))).toArray(SpaceMarine[]::new));
                    break;
            }
        }
        countInfo.setText(String.valueOf(tableView.getItems().size()));
    }

    public void setBox() throws UnsupportedEncodingException, SocketException, UnknownHostException {
        filterBox.getItems().setAll(Stream.of(SpacePols.values()).map(SpacePols::name).collect(Collectors.toList()));
        filterBox.setValue("id");
        languageBox.getItems().setAll(Stream.of(Language.values()).map(Language::name).collect(Collectors.toList()));
        languageBox.setValue("русский");
        swichLeng(bundleRu);
        username.setText(Treatment.username);
    }

    public void info() throws SocketException, UnknownHostException {
        ReturnPack rp = Treatment.treatment(Treatment.noArg(new String[]{"info"}));
        if (rp != null && rp.getExc() == null) {
            typeLabel.setText(new String(bundleNow.getString("type").getBytes(ISO_8859_1), UTF_8));
            timeLabel.setText(new String(bundleNow.getString("time").getBytes(ISO_8859_1), UTF_8));
            countLabel.setText(new String(bundleNow.getString("size").getBytes(ISO_8859_1), UTF_8));
            typeLabel.setText(typeLabel.getText() + rp.getInfo()[0]);
            timeLabel.setText(timeLabel.getText() + rp.getInfo()[1]);
            countLabel.setText(countLabel.getText() + rp.getInfo()[2]);
        }
    }

    public void setTable(SpaceMarine[] sp) {
        TableColumn<SpaceMarine, ?> so = null;
        ObservableList<SpaceMarine> usersData = FXCollections.observableArrayList();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<SpaceMarine, String>("name"));
        coordinates_xColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCoordinates().getX()).asObject());
        coordinates_yColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getCoordinates().getY()).asObject());
        creationDateColumn.setCellValueFactory(new PropertyValueFactory<SpaceMarine, java.time.ZonedDateTime>("creationDate"));
        healthColumn.setCellValueFactory(new PropertyValueFactory<SpaceMarine, Long>("health"));
        achievementsColumn.setCellValueFactory(new PropertyValueFactory<SpaceMarine, String>("achievements"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<SpaceMarine, String>("category"));
        weaponTypeColumn.setCellValueFactory(new PropertyValueFactory<SpaceMarine, String>("weaponType"));
        chapter_nameColumn.setCellValueFactory(cellData -> cellData.getValue().getChapter() != null ? new SimpleStringProperty(cellData.getValue().getChapter().getName()) : null);
        chapter_marinesCountColumn.setCellValueFactory(cellData -> cellData.getValue().getChapter() != null ? cellData.getValue().getChapter().getMarinesCount() != null ? new SimpleIntegerProperty(cellData.getValue().getChapter().getMarinesCount()).asObject() : null : null);
        usersData.addAll(Arrays.asList(sp));
        if (tableView.getSortOrder().size() > 0) {
            so = tableView.getSortOrder().get(0);
        }
        tableView.setItems(usersData);
        tableView.setRowFactory(tv -> {
            TableRow<SpaceMarine> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {

                    if (!corrCheck) {
                        FXMLLoader loader = new FXMLLoader();
                        InfoSceneController psc = new InfoSceneController();
                        loader.setController(psc);
                        URL xmlUrl = getClass().getResource("/InfoScene.fxml");
                        loader.setLocation(xmlUrl);
                        Parent root = null;
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Scene secondScene = new Scene(root, 350, 400);
                        Stage newWindow = new Stage();
                        newWindow.setOnCloseRequest(new EventHandler<WindowEvent>() {
                            @Override
                            public void handle(WindowEvent event) {
                                MouseCheck.corrCheck = false;
                            }
                        });
                        newWindow.setTitle("Info Stage");
                        newWindow.setScene(secondScene);
                        newWindow.show();
                        try {
                            psc.setVal(row.getItem());
                        } catch (UnsupportedEncodingException unsupportedEncodingException) {
                            unsupportedEncodingException.printStackTrace();
                        }
                        MouseCheck.corrCheck = true;
                    }
                }
            });
            return row;
        });
        if (so != null) {
            tableView.getSortOrder().add(so);
        }
    }

    private void show() throws SocketException, UnknownHostException {
        ReturnPack rp = Treatment.treatment(Treatment.noArg(new String[]{"show"}));
        if (rp != null) {
            SpaceMarine[] sp = rp.getSp();
            if (sp!=null) {
                for (SpaceMarine spaceMarine : sp) {
                    spaceMarine.setCreationDate(spaceMarine.getCreationDate().withZoneSameInstant(zid));
                }
                setTable(sp);
            }
        }

    }

    @FXML
    public void refresh() throws SocketException, UnknownHostException {
        show();
        info();
        find();
    }

    @FXML
    private void add() throws IOException {
        if (!plusCheck) {
            FXMLLoader loader = new FXMLLoader();
            PlusSceneController psc = new PlusSceneController();
            loader.setController(psc);
            URL xmlUrl = getClass().getResource("/PlusScene.fxml");
            loader.setLocation(xmlUrl);
            Parent root = loader.load();
            Scene secondScene = new Scene(root, 350, 400);
            Stage newWindow = new Stage();
            newWindow.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    MainSceneController.plusCheck = false;
                }
            });
            newWindow.setTitle("Pluse Stage");
            newWindow.setScene(secondScene);
            psc.setBox();
            newWindow.show();
            plusCheck = true;
        }
    }

    @FXML
    private void viewPane() throws IOException, InterruptedException {
        if (!viewCheck) {
            JFrame frame = new JFrame("View Stage");
            //frame.addMouseListener(new MouseCheck());
            ViewSceneController viewSceneController = new ViewSceneController();
            viewSceneController.addMouseListener(new MouseCheck());
            viewSceneController.setPreferredSize(new Dimension(1000, 1000));
            frame.setSize(1280, 720);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setViewportView(viewSceneController);
            //scrollPane.addMouseListener(new MouseCheck());
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            frame.add(scrollPane);
            frame.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(java.awt.event.WindowEvent e) {
                }

                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    MainSceneController.viewCheck = false;
                    viewSceneController.timer.stop();
                }

                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                }

                @Override
                public void windowIconified(java.awt.event.WindowEvent e) {
                }

                @Override
                public void windowDeiconified(java.awt.event.WindowEvent e) {
                }

                @Override
                public void windowActivated(java.awt.event.WindowEvent e) {
                }

                @Override
                public void windowDeactivated(java.awt.event.WindowEvent e) {
                }
            });
            frame.setVisible(true);
            viewCheck = true;
        }
    }

    @FXML
    public void infoView() {

    }
}
