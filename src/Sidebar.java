import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Sidebar extends VBox {

    private Main mainApp;
    private Button activeButton;
    private String activePage = "dashboard";

    private static final String NAV_NORMAL =
        "-fx-background-color: transparent; -fx-text-fill: rgba(255,255,255,0.7); " +
        "-fx-font-size: 13; -fx-padding: 11 15; -fx-cursor: hand; " +
        "-fx-alignment: center-left; -fx-background-radius: 12;";
    private static final String NAV_HOVER =
        "-fx-background-color: rgba(255,255,255,0.08); -fx-text-fill: rgba(255,255,255,0.95); " +
        "-fx-font-size: 13; -fx-padding: 11 15; -fx-cursor: hand; " +
        "-fx-alignment: center-left; -fx-background-radius: 12;";
    private static final String NAV_ACTIVE =
        "-fx-background-color: " + Colors.ACCENT + "; -fx-text-fill: " + Colors.SIDEBAR + "; " +
        "-fx-font-size: 13; -fx-font-weight: bold; -fx-padding: 11 15; -fx-cursor: hand; " +
        "-fx-alignment: center-left; -fx-background-radius: 12;";

    public Sidebar(Main mainApp) {
        this.mainApp = mainApp;
        setStyle("-fx-background-color: " + Colors.SIDEBAR + ";");
        setPrefWidth(250);
        setPadding(new Insets(25));
        setSpacing(4);

        createLogo();
        getChildren().add(createSep());

        Label menuLabel = new Label("  MENU");
        menuLabel.setStyle("-fx-text-fill: rgba(255,255,255,0.25); -fx-font-size: 10; -fx-font-weight: bold;");
        getChildren().add(menuLabel);

        String[][] navItems = {
            {"  \u2302   Dashboard", "dashboard", Colors.FLIGHT_LIGHT, Colors.FLIGHT_SOLID},
            {"  \u2708   Flights", "flights", Colors.FLIGHT_LIGHT, Colors.FLIGHT_SOLID},
            {"  \u2460   Passengers", "passengers", Colors.PASSENGER_LIGHT, Colors.PASSENGER_SOLID},
            {"  \u2461   Pilots", "pilots", Colors.PILOT_LIGHT, Colors.PILOT_SOLID},
            {"  \u2462   Co-Pilots", "copilots", Colors.COPILOT_LIGHT, Colors.COPILOT_SOLID},
            {"  \u2463   Reports", "reports", Colors.REPORT_LIGHT, Colors.REPORT_SOLID},
            {"  \u2709   Chat (LAN)", "chat", Colors.CHAT_LIGHT, Colors.CHAT_SOLID},
            {"  \u2699   Settings", "settings", Colors.SETTINGS_LIGHT, Colors.SETTINGS_SOLID}
        };

        for (String[] item : navItems) {
            Button btn = createNavButton(item[0], item[1]);
            getChildren().add(btn);
            if (item[1].equals(activePage)) {
                activeButton = btn;
                btn.setStyle(NAV_ACTIVE);
            }
        }

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        getChildren().add(spacer);

        getChildren().add(createAdminCard());
    }

    private void createLogo() {
        VBox logoBox = new VBox(3);
        logoBox.setAlignment(Pos.CENTER_LEFT);
        logoBox.setPadding(new Insets(0, 0, 10, 0));

        HBox iconRow = new HBox(10);
        iconRow.setAlignment(Pos.CENTER_LEFT);
        StackPane logoIcon = new StackPane();
        Circle logoCircle = new Circle(18);
        logoCircle.setFill(Color.web(Colors.ACCENT));
        Label logoLetter = new Label("A");
        logoLetter.setStyle("-fx-text-fill: " + Colors.SIDEBAR + "; -fx-font-size: 18; -fx-font-weight: bold;");
        logoIcon.getChildren().addAll(logoCircle, logoLetter);
        Label title = new Label("Airport");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 18; -fx-font-weight: bold;");
        iconRow.getChildren().addAll(logoIcon, title);

        Label subtitle = new Label("Management System");
        subtitle.setStyle("-fx-text-fill: rgba(255,255,255,0.4); -fx-font-size: 11;");

        Label edition = new Label("JavaFX Edition");
        edition.setStyle("-fx-text-fill: rgba(255,255,255,0.2); -fx-font-size: 10;");

        logoBox.getChildren().addAll(iconRow, subtitle, edition);
        getChildren().add(logoBox);
    }

    private Button createNavButton(String text, String page) {
        Button btn = new Button(text);
        btn.setStyle(NAV_NORMAL);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPrefHeight(42);

        btn.setOnMouseEntered(e -> {
            if (!page.equals(activePage)) btn.setStyle(NAV_HOVER);
        });
        btn.setOnMouseExited(e -> {
            if (!page.equals(activePage)) btn.setStyle(NAV_NORMAL);
        });
        btn.setOnAction(e -> {
            if (page.equals(activePage)) return;
            if (activeButton != null) activeButton.setStyle(NAV_NORMAL);
            activeButton = btn;
            activePage = page;
            btn.setStyle(NAV_ACTIVE);
            mainApp.navigateTo(page);
        });

        return btn;
    }

    private HBox createAdminCard() {
        HBox card = new HBox(10);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(12));
        card.setStyle("-fx-background-color: rgba(255,255,255,0.06); -fx-background-radius: 12;");

        StackPane avatar = new StackPane();
        Circle circle = new Circle(16);
        circle.setFill(Color.web(Colors.ACCENT));
        Label avatarText = new Label("A");
        avatarText.setStyle("-fx-text-fill: " + Colors.SIDEBAR + "; -fx-font-size: 14; -fx-font-weight: bold;");
        avatar.getChildren().addAll(circle, avatarText);

        VBox info = new VBox(2);
        Label name = new Label("Administrator");
        name.setStyle("-fx-text-fill: white; -fx-font-size: 12; -fx-font-weight: bold;");
        HBox statusBox = new HBox(5);
        statusBox.setAlignment(Pos.CENTER_LEFT);
        Circle dot = new Circle(4);
        dot.setFill(Color.web(Colors.GREEN));
        Label status = new Label("Online");
        status.setStyle("-fx-text-fill: rgba(255,255,255,0.4); -fx-font-size: 10;");
        statusBox.getChildren().addAll(dot, status);
        info.getChildren().addAll(name, statusBox);

        card.getChildren().addAll(avatar, info);
        return card;
    }

    private javafx.scene.control.Separator createSep() {
        javafx.scene.control.Separator sep = new javafx.scene.control.Separator();
        sep.setStyle("-fx-background-color: rgba(255,255,255,0.08); -fx-padding: 5 0;");
        return sep;
    }
}
