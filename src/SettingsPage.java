import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SettingsPage extends VBox {

    public SettingsPage() {
        setPadding(new Insets(30));
        setSpacing(20);
        setStyle("-fx-background-color: " + Colors.BACKGROUND + ";");

        HBox headerRow = new HBox(15);
        headerRow.setAlignment(Pos.CENTER_LEFT);

        StackPane iconCircle = new StackPane();
        Circle bg = new Circle(20);
        bg.setFill(Color.web(Colors.SETTINGS_LIGHT));
        Label iconLabel = new Label("\u2699");
        iconLabel.setStyle("-fx-text-fill: " + Colors.SETTINGS_SOLID + "; -fx-font-size: 16; -fx-font-weight: bold;");
        iconCircle.getChildren().addAll(bg, iconLabel);

        VBox titles = new VBox(2);
        Label title = new Label("Settings");
        title.setStyle("-fx-text-fill: " + Colors.TEXT_PRIMARY + "; -fx-font-size: 20; -fx-font-weight: bold;");
        Label subtitle = new Label("System configuration and information");
        subtitle.setStyle("-fx-text-fill: " + Colors.TEXT_SECONDARY + "; -fx-font-size: 12;");
        titles.getChildren().addAll(title, subtitle);

        headerRow.getChildren().addAll(iconCircle, titles);

        VBox generalCard = createCard("General Settings", Colors.SETTINGS_MID);
        generalCard.getChildren().addAll(
            createSettingRow("M", "System Mode", "Desktop Application", Colors.FLIGHT_SOLID),
            createSettingRow("\u26BF", "Security", "Basic", Colors.PILOT_SOLID),
            createSettingRow("D", "Data Storage", "In-Memory (ArrayList)", Colors.PASSENGER_SOLID)
        );

        VBox aboutCard = createCard("About", Colors.SETTINGS_MID);
        aboutCard.getChildren().addAll(
            createSettingRow("A", "Application", "Airport Management System", Colors.FLIGHT_SOLID),
            createSettingRow("V", "Version", "1.0 JavaFX Edition", Colors.COPILOT_SOLID),
            createSettingRow("R", "Runtime", "JDK 17 + OpenJFX 11", Colors.PILOT_SOLID),
            createSettingRow("T", "Developed By", "Airport Team", Colors.PASSENGER_SOLID)
        );

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        getChildren().addAll(headerRow, generalCard, aboutCard, spacer);
    }

    private VBox createCard(String heading, String borderColor) {
        VBox card = new VBox(10);
        card.setStyle(
            "-fx-background-color: " + Colors.CARD + "; " +
            "-fx-background-radius: 20; " +
            "-fx-border-color: " + borderColor + "; -fx-border-width: 0 0 2 0; " +
            "-fx-border-radius: 20; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.04), 8, 0, 0, 2);"
        );
        card.setPadding(new Insets(20, 25, 20, 25));

        Label header = new Label(heading);
        header.setStyle("-fx-text-fill: " + Colors.TEXT_PRIMARY + "; -fx-font-size: 15; -fx-font-weight: bold;");

        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: " + Colors.BORDER + ";");

        card.getChildren().addAll(header, sep);
        return card;
    }

    private HBox createSettingRow(String iconLetter, String label, String value, String accentColor) {
        HBox row = new HBox(12);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(8, 0, 8, 0));

        StackPane iconDot = new StackPane();
        Circle dot = new Circle(12);
        dot.setFill(Color.web(accentColor).deriveColor(0, 1, 1, 0.15));
        Label letter = new Label(iconLetter);
        letter.setStyle("-fx-text-fill: " + accentColor + "; -fx-font-size: 10; -fx-font-weight: bold;");
        iconDot.getChildren().addAll(dot, letter);

        Label nameLabel = new Label(label);
        nameLabel.setStyle("-fx-text-fill: " + Colors.TEXT_SECONDARY + "; -fx-font-size: 13;");
        nameLabel.setMinWidth(140);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-text-fill: " + Colors.TEXT_PRIMARY + "; -fx-font-size: 13; -fx-font-weight: bold;");

        row.getChildren().addAll(iconDot, nameLabel, spacer, valueLabel);
        return row;
    }
}
