import javafx.animation.ScaleTransition;
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
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class WelcomePage extends StackPane {

    private Runnable onEnter;

    public WelcomePage(Runnable onEnter) {
        this.onEnter = onEnter;
        setStyle("-fx-background-color: " + Colors.SIDEBAR + ";");
        setMaxWidth(Double.MAX_VALUE);
        setMaxHeight(Double.MAX_VALUE);
        setMinWidth(0);
        setMinHeight(0);

        StackPane.setAlignment(this, Pos.CENTER);

        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(60));
        layout.setMaxWidth(Double.MAX_VALUE);
        layout.setMaxHeight(Double.MAX_VALUE);
        layout.setStyle(
            "-fx-background-color: linear-gradient(to bottom right, #1a2410, " + Colors.SIDEBAR + ", #1a2410);"
        );

        VBox centerBox = new VBox(30);
        centerBox.setAlignment(Pos.CENTER);

        StackPane logoIcon = new StackPane();
        Circle logoCircle = new Circle(32);
        logoCircle.setFill(Color.web(Colors.ACCENT));
        Label logoLetter = new Label("A");
        logoLetter.setStyle("-fx-text-fill: " + Colors.SIDEBAR + "; -fx-font-size: 30; -fx-font-weight: bold;");
        logoIcon.getChildren().addAll(logoCircle, logoLetter);

        VBox titleBox = new VBox(8);
        titleBox.setAlignment(Pos.CENTER);
        Label title = new Label("Airport Management System");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 36; -fx-font-weight: bold;");
        Label subtitle = new Label("Your complete solution for managing flights, pilots, co-pilots, and passengers");
        subtitle.setStyle("-fx-text-fill: rgba(255,255,255,0.4); -fx-font-size: 15;");
        subtitle.setWrapText(true);
        subtitle.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        titleBox.getChildren().addAll(title, subtitle);

        HBox features = new HBox(40);
        features.setAlignment(Pos.CENTER);
        features.getChildren().addAll(
            createFeature("F", "Flights", Colors.FLIGHT_SOLID),
            createFeature("P", "Passengers", Colors.PASSENGER_SOLID),
            createFeature("\u2461", "Pilots", Colors.PILOT_SOLID),
            createFeature("\u2462", "Co-Pilots", Colors.COPILOT_SOLID)
        );

        Region spacer = new Region();
        spacer.setPrefHeight(20);

        StackPane planeIcon = new StackPane();
        Circle planeCircle = new Circle(60);
        planeCircle.setFill(Color.web(Colors.ACCENT).deriveColor(0, 1, 1, 0.1));
        planeCircle.setStroke(Color.web(Colors.ACCENT).deriveColor(0, 1, 1, 0.25));
        planeCircle.setStrokeWidth(2);
        planeCircle.setMouseTransparent(true);
        Label plane = new Label("\u2708");
        plane.setStyle("-fx-font-size: 45; -fx-text-fill: " + Colors.ACCENT + ";");
        plane.setMouseTransparent(true);
        planeIcon.getChildren().addAll(planeCircle, plane);

        ScaleTransition pulse = new ScaleTransition(Duration.seconds(2.5), planeIcon);
        pulse.setFromX(1.0);
        pulse.setFromY(1.0);
        pulse.setToX(1.06);
        pulse.setToY(1.06);
        pulse.setCycleCount(javafx.animation.Animation.INDEFINITE);
        pulse.setAutoReverse(true);
        pulse.play();

        Button enterBtn = new Button("Enter System");
        enterBtn.setPrefWidth(280);
        enterBtn.setPrefHeight(56);
        enterBtn.setStyle(
            "-fx-background-color: " + Colors.ACCENT + "; " +
            "-fx-text-fill: " + Colors.SIDEBAR + "; " +
            "-fx-font-size: 17; -fx-font-weight: bold; " +
            "-fx-background-radius: 16; " +
            "-fx-cursor: hand; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 20, 0, 0, 4);"
        );
        enterBtn.setOnMouseEntered(e -> enterBtn.setStyle(
            "-fx-background-color: #d8ff8a; " +
            "-fx-text-fill: " + Colors.SIDEBAR + "; " +
            "-fx-font-size: 17; -fx-font-weight: bold; " +
            "-fx-background-radius: 16; " +
            "-fx-cursor: hand; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.35), 24, 0, 0, 4);"
        ));
        enterBtn.setOnMouseExited(e -> enterBtn.setStyle(
            "-fx-background-color: " + Colors.ACCENT + "; " +
            "-fx-text-fill: " + Colors.SIDEBAR + "; " +
            "-fx-font-size: 17; -fx-font-weight: bold; " +
            "-fx-background-radius: 16; " +
            "-fx-cursor: hand; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 20, 0, 0, 4);"
        ));
        enterBtn.setOnAction(e -> {
            if (onEnter != null) onEnter.run();
        });

        centerBox.getChildren().addAll(logoIcon, titleBox, features, spacer, planeIcon, enterBtn);
        layout.getChildren().add(centerBox);
        getChildren().add(layout);
    }

    private VBox createFeature(String icon, String label, String solid) {
        StackPane iconPane = new StackPane();
        Circle circle = new Circle(24);
        circle.setFill(Color.web(solid).deriveColor(0, 1, 1, 0.15));
        circle.setStroke(Color.web(solid).deriveColor(0, 1, 1, 0.3));
        circle.setStrokeWidth(1.5);
        circle.setMouseTransparent(true);
        Label letter = new Label(icon);
        letter.setStyle("-fx-text-fill: " + solid + "; -fx-font-size: 14; -fx-font-weight: bold;");
        letter.setMouseTransparent(true);
        iconPane.getChildren().addAll(circle, letter);

        Label text = new Label(label);
        text.setStyle("-fx-text-fill: rgba(255,255,255,0.45); -fx-font-size: 12;");

        VBox feature = new VBox(8);
        feature.setAlignment(Pos.CENTER);
        feature.getChildren().addAll(iconPane, text);
        return feature;
    }
}
