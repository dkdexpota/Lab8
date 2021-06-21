import javafx.scene.control.Label;

import java.time.ZonedDateTime;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

public class MakeElement {
    static String[] exc = new String[] {
            "Имя не может быть пустым.",
            "Введите целое число.",
            "Введите число.",
            "Число должно быть положительным.",
            "Введите целое число.",
            "Введите значение из списка.",
            "Число должно быть от 1 до 1000."
//            "The name cannot be empty",
//            "Enter an integer",
//            "Enter a number",
//            "The number must be positive",
//            "Enter an integer",
//            "Enter a value from the list",
//            "The number must be between 1 and 1000"
    };
    static String[] info = new String[] {
            "name: ",
            "X: ",
            "Y: ",
            "health: ",
            "achievements: ",
            "category (ASSAULT, INCEPTOR, HELIX, APOTHECARY): ",
            "weaponType (MELTAGUN, COMBI_PLASMA_GUN, GRAV_GUN, null - пустая строка): ",
            "chapter name: ",
            "chapter marinesCount: "
    };
    public static SpaceMarine makeElement(String idStr, String name, String xStr, String yStr, String healthStr, String achievements,
                                          String categoryStr, String weaponTypeStr, String chName, String marinesCountStr, Label errLabel){
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (Exception e) {
            errLabel.setText("id: " + new String(MainSceneController.bundleNow.getString("exc1").getBytes(ISO_8859_1), UTF_8));
            return null;
        }

        ZonedDateTime creationDate = ZonedDateTime.now().withZoneSameLocal(MainSceneController.zid);
        errLabel.setVisible(true);
        if (name.length() == 0){
            errLabel.setText(new String(MainSceneController.bundleNow.getString("exc0").getBytes(ISO_8859_1), UTF_8));
            return null;
        }

        int x;
        try {
            x = Integer.parseInt(xStr);
        } catch (NumberFormatException e) {
            errLabel.setText("x: " + new String(MainSceneController.bundleNow.getString("exc1").getBytes(ISO_8859_1), UTF_8));
            return null;
        }


        double y;
        try {
            y = Double.parseDouble(yStr);
        } catch (NumberFormatException e) {
            errLabel.setText("y: " + new String(MainSceneController.bundleNow.getString("exc2").getBytes(ISO_8859_1), UTF_8));
            return null;
        }

        long health;
        try {
            health = Long.parseLong(healthStr);
            if (health<0) {
                errLabel.setText(new String(MainSceneController.bundleNow.getString("health").getBytes(ISO_8859_1), UTF_8) + ": " + new String(MainSceneController.bundleNow.getString("exc3").getBytes(ISO_8859_1), UTF_8));
                return null;
            }
        } catch (NumberFormatException e) {
            errLabel.setText(new String(MainSceneController.bundleNow.getString("health").getBytes(ISO_8859_1), UTF_8) + ": " + new String(MainSceneController.bundleNow.getString("exc4").getBytes(ISO_8859_1), UTF_8));
            return null;
        }

        if (achievements.length()==0) {
            achievements = null;
        }

        AstartesCategory category;
        try {
            category = AstartesCategory.valueOf(categoryStr);
        } catch (IllegalArgumentException | NullPointerException e) {
            errLabel.setText(new String(MainSceneController.bundleNow.getString("category").getBytes(ISO_8859_1), UTF_8) + ": " + new String(MainSceneController.bundleNow.getString("exc5").getBytes(ISO_8859_1), UTF_8));
            return null;
        }

        Weapon weaponType;
        try {
            if (weaponTypeStr.equals("NULL")) {
                weaponType = null;
            } else {
                weaponType = Weapon.valueOf(weaponTypeStr);
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            errLabel.setText(new String(MainSceneController.bundleNow.getString("weapon").getBytes(ISO_8859_1), UTF_8) + ": " + new String(MainSceneController.bundleNow.getString("exc5").getBytes(ISO_8859_1), UTF_8));
            return null;
        }


        Chapter chapter = null;
        if (chName.length() > 0) {
            Integer marinesCount;
            if (marinesCountStr == null || marinesCountStr.length() == 0) {
                marinesCount = null;
                chapter = new Chapter(chName, marinesCount);
            } else {
                try {
                    marinesCount = Integer.parseInt(marinesCountStr);
                    if (marinesCount <= 1000 && marinesCount > 0) {
                        chapter = new Chapter(chName, marinesCount);
                    } else {
                        errLabel.setText(new String(MainSceneController.bundleNow.getString("marin").getBytes(ISO_8859_1), UTF_8) + ": " + new String(MainSceneController.bundleNow.getString("exc6").getBytes(ISO_8859_1), UTF_8));
                        return null;
                    }
                } catch (NumberFormatException e) {
                    errLabel.setText(new String(MainSceneController.bundleNow.getString("marin").getBytes(ISO_8859_1), UTF_8) + ": " + new String(MainSceneController.bundleNow.getString("exc4").getBytes(ISO_8859_1), UTF_8));
                    return null;
                }
            }
        }
        errLabel.setText(new String(MainSceneController.bundleNow.getString("zaebis").getBytes(ISO_8859_1), UTF_8));
        return new SpaceMarine(id, name, new Coordinates(x, y), creationDate, health, achievements, category, weaponType, chapter);
    }
}
