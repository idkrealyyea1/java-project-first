import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TopBar extends HBox {

    private Label titleLabel;
    private Label timeLabel;
    private TextField search;

    public TopBar() {
        setStyle("-fx-background-color: " + Colors.CARD + "; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.04), 6, 0, 0, 2);");
        setPrefHeight(70);
        setPadding(new Insets(0, 30, 0, 30));
        setAlignment(Pos.CENTER_LEFT);

        titleLabel = new Label("Dashboard");
        titleLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: " + Colors.TEXT_PRIMARY + ";");

        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);

        search = new TextField();
        search.setPromptText("\u2315  Search flights, passengers...");
        search.setPrefWidth(300);
        search.setPrefHeight(38);
        search.setStyle(
            "-fx-background-color: " + Colors.BACKGROUND + "; " +
            "-fx-border-color: " + Colors.BORDER + "; " +
            "-fx-border-radius: 20; " +
            "-fx-background-radius: 20; " +
            "-fx-padding: 0 15; " +
            "-fx-font-size: 12; " +
            "-fx-text-fill: " + Colors.TEXT_PRIMARY + ";"
        );
        search.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                search.setStyle(search.getStyle().replace(Colors.BORDER, Colors.ACCENT));
            } else {
                search.setStyle(search.getStyle().replace(Colors.ACCENT, Colors.BORDER));
            }
        });

        Region spacer2 = new Region();
        spacer2.setPrefWidth(15);

        StackPane notifPane = new StackPane();
        Label notifIcon = new Label("\u266B");
        notifIcon.setStyle("-fx-font-size: 16; -fx-text-fill: " + Colors.TEXT_SECONDARY + "; -fx-cursor: hand;");
        Circle badge = new Circle(7);
        badge.setFill(Color.web(Colors.CHAT_SOLID));
        Label badgeText = new Label("3");
        badgeText.setStyle("-fx-text-fill: white; -fx-font-size: 8; -fx-font-weight: bold;");
        notifPane.getChildren().addAll(notifIcon, badge, badgeText);
        StackPane.setAlignment(badge, Pos.TOP_RIGHT);
        StackPane.setAlignment(badgeText, Pos.TOP_RIGHT);
        StackPane.setMargin(badge, new Insets(-4, -6, 0, 0));
        StackPane.setMargin(badgeText, new Insets(-6, -5, 0, 0));

        Region spacer3 = new Region();
        spacer3.setPrefWidth(20);

        VBox dateBox = new VBox(1);
        LocalDate today = LocalDate.now();
        Label dateLabel = new Label(today.format(DateTimeFormatter.ofPattern("EEEE, MMM d, yyyy")));
        dateLabel.setStyle("-fx-font-size: 12; -fx-text-fill: " + Colors.TEXT_PRIMARY + "; -fx-font-weight: bold;");
        LocalTime now = LocalTime.now();
        timeLabel = new Label(now.format(DateTimeFormatter.ofPattern("h:mm a")));
        timeLabel.setStyle("-fx-font-size: 11; -fx-text-fill: " + Colors.TEXT_SECONDARY + ";");
        dateBox.getChildren().addAll(dateLabel, timeLabel);

        Region spacer4 = new Region();
        spacer4.setPrefWidth(20);

        StackPane profile = new StackPane();
        Circle avatarCircle = new Circle(18);
        avatarCircle.setFill(Color.web(Colors.SIDEBAR));
        Label avatarLetter = new Label("A");
        avatarLetter.setStyle("-fx-text-fill: " + Colors.ACCENT + "; -fx-font-size: 14; -fx-font-weight: bold;");
        Circle onlineDot = new Circle(4);
        onlineDot.setFill(Color.web(Colors.GREEN));
        profile.getChildren().addAll(avatarCircle, avatarLetter, onlineDot);
        StackPane.setAlignment(onlineDot, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(onlineDot, new Insets(0, -2, -2, 0));
        profile.setStyle("-fx-cursor: hand;");

        getChildren().addAll(titleLabel, spacer1, search, spacer2, notifPane, spacer3, dateBox, spacer4, profile);

        startClock();
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void setSearchCallback(java.util.function.Consumer<String> callback) {
        search.textProperty().addListener((obs, oldVal, newVal) -> {
            callback.accept(newVal);
        });
    }

    public void clearSearch() {
        search.clear();
    }

    private void startClock() {
        javafx.animation.Timeline clock = new javafx.animation.Timeline(
            new javafx.animation.KeyFrame(javafx.util.Duration.seconds(1), e -> {
                timeLabel.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("h:mm a")));
            })
        );
        clock.setCycleCount(javafx.animation.Timeline.INDEFINITE);
        clock.play();
    }
}
