import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
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
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class DashboardPage extends VBox {

    private Main mainApp;

    public DashboardPage(Main mainApp) {
        this.mainApp = mainApp;
        setPadding(new Insets(30));
        setSpacing(25);
        setStyle("-fx-background-color: " + Colors.BACKGROUND + ";");

        ScrollPane scroll = new ScrollPane();
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: " + Colors.BACKGROUND + "; -fx-background-color: transparent; -fx-border-color: transparent;");

        VBox content = new VBox(25);
        content.setPadding(new Insets(0, 5, 0, 0));

        content.getChildren().add(createWelcomeBanner());
        content.getChildren().add(createStatCards());
        content.getChildren().add(createManagementGrid());

        HBox bottomRow = new HBox(20);
        HBox.setHgrow(bottomRow, Priority.ALWAYS);
        bottomRow.getChildren().addAll(createUpcomingFlights(), createQuickActions());
        HBox.setHgrow(bottomRow.getChildren().get(0), Priority.ALWAYS);
        HBox.setHgrow(bottomRow.getChildren().get(1), Priority.ALWAYS);
        content.getChildren().add(bottomRow);

        scroll.setContent(content);
        getChildren().add(scroll);
    }

    private StackPane createWelcomeBanner() {
        StackPane banner = new StackPane();
        banner.setPrefHeight(130);
        banner.setStyle("-fx-background-radius: 28; -fx-background-color: " + Colors.SIDEBAR + ";");

        Rectangle gradient = new Rectangle(1400, 130);
        gradient.setArcWidth(28);
        gradient.setArcHeight(28);
        gradient.setFill(new LinearGradient(0, 0, 1, 0.3, true, CycleMethod.NO_CYCLE,
            new Stop(0, Color.web(Colors.SIDEBAR)),
            new Stop(0.6, Color.web("#1a2410")),
            new Stop(1, Color.web(Colors.SIDEBAR))
        ));

        HBox bannerContent = new HBox();
        bannerContent.setAlignment(Pos.CENTER_LEFT);
        bannerContent.setPadding(new Insets(25, 35, 25, 35));

        VBox text = new VBox(6);
        Label welcome = new Label("Welcome to Airport Management System");
        welcome.setStyle("-fx-text-fill: white; -fx-font-size: 24; -fx-font-weight: bold;");
        Label subtitle = new Label("Manage flights, passengers, pilots, and co-pilots efficiently");
        subtitle.setStyle("-fx-text-fill: rgba(255,255,255,0.55); -fx-font-size: 13;");
        Label accentText = new Label("\u2605  Built with confidence");
        accentText.setStyle("-fx-text-fill: " + Colors.ACCENT + "; -fx-font-size: 11; -fx-font-weight: bold;");
        text.getChildren().addAll(welcome, subtitle, accentText);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        VBox statsBox = new VBox(8);
        statsBox.setAlignment(Pos.CENTER_RIGHT);
        statsBox.setPadding(new Insets(0, 10, 0, 0));
        HBox row1 = new HBox(20);
        row1.setAlignment(Pos.CENTER_RIGHT);
        Label fCount = new Label(adding.flightslists.size() + " Flights");
        fCount.setStyle("-fx-text-fill: " + Colors.FLIGHT_MID + "; -fx-font-size: 13; -fx-font-weight: bold;");
        Label pCount = new Label(countAllPassengers() + " Passengers");
        pCount.setStyle("-fx-text-fill: " + Colors.PASSENGER_MID + "; -fx-font-size: 13; -fx-font-weight: bold;");
        row1.getChildren().addAll(fCount, pCount);
        HBox row2 = new HBox(20);
        row2.setAlignment(Pos.CENTER_RIGHT);
        Label pilotCount = new Label(adding.flightersList.size() + " Pilots");
        pilotCount.setStyle("-fx-text-fill: " + Colors.PILOT_MID + "; -fx-font-size: 13; -fx-font-weight: bold;");
        Label coCount = new Label(adding.copilots.size() + " Co-Pilots");
        coCount.setStyle("-fx-text-fill: " + Colors.COPILOT_MID + "; -fx-font-size: 13; -fx-font-weight: bold;");
        Label empCount = new Label(adding.normalEmployees.size() + " Employees");
        empCount.setStyle("-fx-text-fill: " + Colors.SETTINGS_MID + "; -fx-font-size: 13; -fx-font-weight: bold;");
        HBox row3 = new HBox(20);
        row3.setAlignment(Pos.CENTER_RIGHT);
        row3.getChildren().add(empCount);
        row2.getChildren().addAll(pilotCount, coCount);
        statsBox.getChildren().addAll(row1, row2, row3);

        bannerContent.getChildren().addAll(text, spacer, statsBox);
        banner.getChildren().addAll(gradient, bannerContent);
        return banner;
    }

    private HBox createStatCards() {
        HBox box = new HBox(18);
        box.getChildren().addAll(
            createStatCard("F", "Total Flights", String.valueOf(adding.flightslists.size()), Colors.FLIGHT_LIGHT, Colors.FLIGHT_SOLID, Colors.FLIGHT_MID),
            createStatCard("P", "Passengers", String.valueOf(countAllPassengers()), Colors.PASSENGER_LIGHT, Colors.PASSENGER_SOLID, Colors.PASSENGER_MID),
            createStatCard("\u2461", "Pilots", String.valueOf(adding.flightersList.size()), Colors.PILOT_LIGHT, Colors.PILOT_SOLID, Colors.PILOT_MID),
            createStatCard("\u2462", "Co-Pilots", String.valueOf(adding.copilots.size()), Colors.COPILOT_LIGHT, Colors.COPILOT_SOLID, Colors.COPILOT_MID),
            createStatCard("\u2464", "Employees", String.valueOf(adding.normalEmployees.size()), Colors.SETTINGS_LIGHT, Colors.SETTINGS_SOLID, Colors.SETTINGS_MID)
        );
        for (int i = 0; i < box.getChildren().size(); i++) {
            HBox.setHgrow(box.getChildren().get(i), Priority.ALWAYS);
        }
        return box;
    }

    private StackPane createStatCard(String iconLetter, String title, String value, String lightBg, String solid, String mid) {
        StackPane card = new StackPane();
        card.setPrefHeight(130);
        card.setStyle(
            "-fx-background-color: " + lightBg + "; " +
            "-fx-background-radius: 28; " +
            "-fx-border-color: " + mid + "; " +
            "-fx-border-width: 0 0 0 0; " +
            "-fx-border-radius: 28; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.06), 12, 0, 0, 3);"
        );

        HBox content = new HBox(0);
        content.setAlignment(Pos.CENTER_LEFT);

        StackPane accentLine = new StackPane();
        accentLine.setMinWidth(5);
        accentLine.setMaxWidth(5);
        accentLine.setStyle("-fx-background-color: " + solid + "; -fx-background-radius: 3;");

        HBox inner = new HBox(15);
        inner.setAlignment(Pos.CENTER_LEFT);
        inner.setPadding(new Insets(20, 25, 20, 20));

        StackPane iconCircle = new StackPane();
        Circle bg = new Circle(24);
        bg.setFill(Color.web(solid).deriveColor(0, 1, 1, 0.15));
        Label iconLabel = new Label(iconLetter);
        iconLabel.setStyle("-fx-text-fill: " + solid + "; -fx-font-size: 18; -fx-font-weight: bold;");
        iconCircle.getChildren().addAll(bg, iconLabel);

        VBox info = new VBox(4);
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: " + Colors.TEXT_SECONDARY + "; -fx-font-size: 12;");
        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-text-fill: " + solid + "; -fx-font-size: 28; -fx-font-weight: bold;");
        Label changeLabel = new Label("Active");
        changeLabel.setStyle("-fx-text-fill: " + Colors.GREEN + "; -fx-font-size: 10; -fx-font-weight: bold;");
        info.getChildren().addAll(titleLabel, valueLabel, changeLabel);

        inner.getChildren().addAll(iconCircle, info);
        content.getChildren().addAll(accentLine, inner);
        card.getChildren().add(content);
        return card;
    }

    private GridPane createManagementGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(cc, cc);

        grid.getChildren().addAll(
            createManagementCard("F", "Flight Management", "Schedule, modify, and track all flights", "flights", Colors.FLIGHT_LIGHT, Colors.FLIGHT_SOLID, Colors.FLIGHT_MID),
            createManagementCard("P", "Passenger Registry", "Register and manage passenger information", "passengers", Colors.PASSENGER_LIGHT, Colors.PASSENGER_SOLID, Colors.PASSENGER_MID),
            createManagementCard("\u2461", "Pilot Roster", "View and manage licensed pilots", "pilots", Colors.PILOT_LIGHT, Colors.PILOT_SOLID, Colors.PILOT_MID),
            createManagementCard("\u2462", "Co-Pilot Roster", "View and manage certified co-pilots", "copilots", Colors.COPILOT_LIGHT, Colors.COPILOT_SOLID, Colors.COPILOT_MID),
            createManagementCard("\u2464", "Normal Employees", "View and manage regular employees", "normalemployees", Colors.SETTINGS_LIGHT, Colors.SETTINGS_SOLID, Colors.SETTINGS_MID)
        );

        for (int i = 0; i < 5; i++) {
            GridPane.setHgrow(grid.getChildren().get(i), Priority.ALWAYS);
        }

        return grid;
    }

    private StackPane createManagementCard(String iconLetter, String title, String desc, String page, String lightBg, String solid, String mid) {
        StackPane card = new StackPane();
        card.setPrefHeight(150);
        card.setStyle(
            "-fx-background-color: " + Colors.CARD + "; " +
            "-fx-background-radius: 28; " +
            "-fx-border-color: " + solid + "; " +
            "-fx-border-width: 0 0 0 5; " +
            "-fx-border-radius: 28 0 0 28; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.04), 8, 0, 0, 2);"
        );

        VBox content = new VBox(8);
        content.setAlignment(Pos.CENTER_LEFT);
        content.setPadding(new Insets(22));

        StackPane iconCircle = new StackPane();
        Circle bg = new Circle(22);
        bg.setFill(Color.web(lightBg));
        Label iconLabel = new Label(iconLetter);
        iconLabel.setStyle("-fx-text-fill: " + solid + "; -fx-font-size: 16; -fx-font-weight: bold;");
        iconCircle.getChildren().addAll(bg, iconLabel);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: " + Colors.TEXT_PRIMARY + "; -fx-font-size: 15; -fx-font-weight: bold;");

        Label descLabel = new Label(desc);
        descLabel.setStyle("-fx-text-fill: " + Colors.TEXT_SECONDARY + "; -fx-font-size: 11;");
        descLabel.setWrapText(true);

        Button btn = new Button("Manage  \u2192");
        btn.setStyle(
            "-fx-background-color: transparent; " +
            "-fx-text-fill: " + solid + "; " +
            "-fx-font-size: 12; -fx-font-weight: bold; " +
            "-fx-cursor: hand; " +
            "-fx-padding: 4 0 0 0;"
        );
        btn.setOnAction(e -> mainApp.navigateTo(page));

        content.getChildren().addAll(iconCircle, titleLabel, descLabel, btn);
        card.getChildren().add(content);
        return card;
    }

    private VBox createUpcomingFlights() {
        VBox container = new VBox(12);
        container.setStyle(
            "-fx-background-color: " + Colors.CARD + "; " +
            "-fx-background-radius: 28; " +
            "-fx-border-color: " + Colors.FLIGHT_MID + "; " +
            "-fx-border-width: 0 0 3 0; " +
            "-fx-border-radius: 28; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.04), 8, 0, 0, 2);"
        );
        container.setPadding(new Insets(20));

        HBox headerRow = new HBox(10);
        headerRow.setAlignment(Pos.CENTER_LEFT);
        StackPane iconDot = new StackPane();
        Circle dot = new Circle(6);
        dot.setFill(Color.web(Colors.FLIGHT_SOLID));
        iconDot.getChildren().add(dot);
        Label header = new Label("Upcoming Flights");
        header.setStyle("-fx-text-fill: " + Colors.TEXT_PRIMARY + "; -fx-font-size: 15; -fx-font-weight: bold;");
        headerRow.getChildren().addAll(iconDot, header);

        TableView<Flight> table = new TableView<>();
        table.setPrefHeight(250);
        table.setStyle("-fx-background-color: transparent; -fx-border-color: " + Colors.BORDER + ";");

        TableColumn<Flight, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(50);

        TableColumn<Flight, String> destCol = new TableColumn<>("Destination");
        destCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
        destCol.setPrefWidth(160);

        TableColumn<Flight, String> captainCol = new TableColumn<>("Captain");
        captainCol.setCellValueFactory(cell -> {
            Flighter cap = cell.getValue().getNameofCaptin();
            return new javafx.beans.property.SimpleStringProperty(cap != null ? cap.getName() : "N/A");
        });
        captainCol.setPrefWidth(140);

        TableColumn<Flight, Integer> seatsCol = new TableColumn<>("Seats");
        seatsCol.setCellValueFactory(new PropertyValueFactory<>("numberofchairs"));
        seatsCol.setPrefWidth(70);

        TableColumn<Flight, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cell -> {
            status.FlightStatus s = cell.getValue().getFlightStatus();
            return new javafx.beans.property.SimpleStringProperty(s != null ? s.toString() : "SCHEDULED");
        });
        statusCol.setPrefWidth(110);

        table.getColumns().addAll(idCol, destCol, captainCol, seatsCol, statusCol);
        table.getItems().addAll(adding.flightslists);

        container.getChildren().addAll(headerRow, table);
        return container;
    }

    private VBox createQuickActions() {
        VBox container = new VBox(10);
        container.setStyle(
            "-fx-background-color: " + Colors.CARD + "; " +
            "-fx-background-radius: 28; " +
            "-fx-border-color: " + Colors.ACCENT + "; " +
            "-fx-border-width: 0 0 3 0; " +
            "-fx-border-radius: 28; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.04), 8, 0, 0, 2);"
        );
        container.setPadding(new Insets(20));
        container.setPrefWidth(280);

        Label header = new Label("Quick Actions");
        header.setStyle("-fx-text-fill: " + Colors.TEXT_PRIMARY + "; -fx-font-size: 15; -fx-font-weight: bold;");

        container.getChildren().addAll(header,
            createActionBtn("\u2708  Add New Flight", Colors.FLIGHT_SOLID, Colors.FLIGHT_LIGHT, "flights"),
            createActionBtn("\u2460  Register Passenger", Colors.PASSENGER_SOLID, Colors.PASSENGER_LIGHT, "passengers"),
            createActionBtn("\u2461  Add Pilot", Colors.PILOT_SOLID, Colors.PILOT_LIGHT, "pilots"),
            createActionBtn("\u2462  Add Co-Pilot", Colors.COPILOT_SOLID, Colors.COPILOT_LIGHT, "copilots"),
            createActionBtn("\u2709  Open Chat", Colors.CHAT_SOLID, Colors.CHAT_LIGHT, "chat"),
            createActionBtn("\u2464  Add Employee", Colors.SETTINGS_SOLID, Colors.SETTINGS_LIGHT, "normalemployees")
        );
        return container;
    }

    private Button createActionBtn(String text, String solid, String lightBg, String page) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPrefHeight(40);
        btn.setStyle(
            "-fx-background-color: " + lightBg + "; " +
            "-fx-text-fill: " + solid + "; " +
            "-fx-font-size: 12; -fx-font-weight: bold; " +
            "-fx-background-radius: 10; " +
            "-fx-cursor: hand; " +
            "-fx-alignment: center-left; " +
            "-fx-padding: 0 14;"
        );
        btn.setOnMouseEntered(e -> btn.setStyle(
            "-fx-background-color: " + Colors.deriveColor(solid, 0, 1, 1, 0.18) + "; " +
            "-fx-text-fill: " + solid + "; " +
            "-fx-font-size: 12; -fx-font-weight: bold; " +
            "-fx-background-radius: 10; " +
            "-fx-cursor: hand; " +
            "-fx-alignment: center-left; " +
            "-fx-padding: 0 14;"
        ));
        btn.setOnMouseExited(e -> btn.setStyle(
            "-fx-background-color: " + lightBg + "; " +
            "-fx-text-fill: " + solid + "; " +
            "-fx-font-size: 12; -fx-font-weight: bold; " +
            "-fx-background-radius: 10; " +
            "-fx-cursor: hand; " +
            "-fx-alignment: center-left; " +
            "-fx-padding: 0 14;"
        ));
        btn.setOnAction(e -> mainApp.navigateTo(page));
        return btn;
    }

    private int countAllPassengers() {
        int count = 0;
        for (Flight f : adding.flightslists) {
            if (f.getPassengersList() != null) {
                count += f.getPassengersList().size();
            }
        }
        return count;
    }
}
