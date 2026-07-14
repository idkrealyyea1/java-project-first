import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;

public class App extends Application {

    private BorderPane root;
    private VBox contentArea;
    private Button activeButton;

    private static final String NAV_STYLE =
        "-fx-background-color: transparent; " +
        "-fx-text-fill: white; " +
        "-fx-font-size: 14; " +
        "-fx-padding: 12 20; " +
        "-fx-cursor: hand; " +
        "-fx-alignment: center-left; " +
        "-fx-background-radius: 10;";

    private static final String NAV_HOVER_STYLE =
        "-fx-background-color: #3d6b1f; " +
        "-fx-text-fill: white; " +
        "-fx-font-size: 14; " +
        "-fx-padding: 12 20; " +
        "-fx-cursor: hand; " +
        "-fx-alignment: center-left; " +
        "-fx-background-radius: 10;";

    private static final String NAV_ACTIVE_STYLE =
        "-fx-background-color: " + Colors.BRIGHT_LEMON + "; " +
        "-fx-text-fill: " + Colors.DARK_LEMON + "; " +
        "-fx-font-size: 14; " +
        "-fx-padding: 12 20; " +
        "-fx-cursor: hand; " +
        "-fx-alignment: center-left; " +
        "-fx-background-radius: 10;";

    private static final String CARD_STYLE =
        "-fx-background-color: " + Colors.WHITE + "; " +
        "-fx-padding: 20; " +
        "-fx-background-radius: 15; " +
        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 10, 0, 0, 3);";

    private static final String TABLE_STYLE =
        "-fx-background-color: white; " +
        "-fx-border-color: #e0e0e0; " +
        "-fx-border-radius: 10; " +
        "-fx-background-radius: 10;";

    private static final String DIALOG_STYLE =
        "-fx-background-color: " + Colors.LIGHT_BG + ";";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        root = new BorderPane();
        root.setStyle("-fx-background-color: " + Colors.LIGHT_BG + ";");

        root.setLeft(createSidebar());

        contentArea = new VBox();
        contentArea.setPadding(new Insets(30));
        contentArea.setSpacing(20);

        ScrollPane scrollPane = new ScrollPane(contentArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: " + Colors.LIGHT_BG + "; -fx-background-color: " + Colors.LIGHT_BG + ";");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        root.setCenter(scrollPane);

        showDashboard();

        Scene scene = new Scene(root, 1400, 900);
        primaryStage.setTitle("Airport Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // =============================================
    //  SIDEBAR
    // =============================================
    private VBox createSidebar() {
        VBox sidebar = new VBox();
        sidebar.setStyle("-fx-background-color: " + Colors.SIDEBAR_BG + "; -fx-padding: 20;");
        sidebar.setPrefWidth(260);
        sidebar.setSpacing(5);

        VBox logoBox = new VBox(5);
        Label planeIcon = new Label("\u2708\uFE0F");
        planeIcon.setStyle("-fx-font-size: 36;");
        Label title = new Label("AIRPORT");
        title.setStyle("-fx-text-fill: " + Colors.BRIGHT_LEMON + "; -fx-font-size: 20; -fx-font-weight: bold;");
        Label subtitle = new Label("MANAGEMENT");
        subtitle.setStyle("-fx-text-fill: white; -fx-font-size: 12; -fx-text-fill: rgba(255,255,255,0.6);");
        logoBox.getChildren().addAll(planeIcon, title, subtitle);
        logoBox.setPadding(new Insets(0, 0, 10, 0));
        sidebar.getChildren().add(logoBox);

        Region sepSpacer1 = new Region();
        sepSpacer1.setPrefHeight(10);
        sidebar.getChildren().add(sepSpacer1);

        Label navLabel = new Label("  MENU");
        navLabel.setStyle("-fx-text-fill: rgba(255,255,255,0.4); -fx-font-size: 11; -fx-font-weight: bold;");
        sidebar.getChildren().add(navLabel);

        String[][] nav = {
            {"\uD83D\uDCCA  Dashboard", "dashboard"},
            {"\u2708\uFE0F  Flights", "flights"},
            {"\uD83D\uDC65  Passengers", "passengers"},
            {"\uD83D\uDC68\u200D\u2708\uFE0F  Pilots", "pilots"},
            {"\uD83D\uDC69\u200D\u2708\uFE0F  Co-Pilots", "copilots"},
            {"\uD83D\uDCAC  Chat", "chat"}
        };

        for (int i = 0; i < nav.length; i++) {
            Button btn = createNavButton(nav[i][0], nav[i][1]);
            sidebar.getChildren().add(btn);
            if (i == 0) {
                activeButton = btn;
                btn.setStyle(NAV_ACTIVE_STYLE);
                btn.setPrefWidth(220);
            }
        }

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        sidebar.getChildren().add(spacer);

        HBox profileBox = new HBox(10);
        profileBox.setAlignment(Pos.CENTER_LEFT);
        profileBox.setPadding(new Insets(10));
        profileBox.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-background-radius: 10;");
        Label profileIcon = new Label("\uD83D\uDC64");
        profileIcon.setStyle("-fx-font-size: 20;");
        VBox profileInfo = new VBox(2);
        Label adminLabel = new Label("Admin");
        adminLabel.setStyle("-fx-text-fill: " + Colors.BRIGHT_LEMON + "; -fx-font-size: 13; -fx-font-weight: bold;");
        Label roleLabel = new Label("Administrator");
        roleLabel.setStyle("-fx-text-fill: rgba(255,255,255,0.6); -fx-font-size: 11;");
        profileInfo.getChildren().addAll(adminLabel, roleLabel);
        profileBox.getChildren().addAll(profileIcon, profileInfo);
        sidebar.getChildren().add(profileBox);

        return sidebar; 
    }

    private Button createNavButton(String text, String screen) {
        Button btn = new Button(text);
        btn.setStyle(NAV_STYLE);
        btn.setPrefWidth(220);

        btn.setOnMouseEntered(e -> {
            if (btn != activeButton) {
                btn.setStyle(NAV_HOVER_STYLE);
            }
        });
        btn.setOnMouseExited(e -> {
            if (btn != activeButton) {
                btn.setStyle(NAV_STYLE);
            }
        });

        btn.setOnAction(e -> {
            if (activeButton != null) {
                activeButton.setStyle(NAV_STYLE);
            }
            activeButton = btn;
            btn.setStyle(NAV_ACTIVE_STYLE);
            switchScreen(screen);
        });

        return btn;
    }

    // =============================================
    //  SCREEN SWITCHER
    // =============================================
    private void switchScreen(String screen) {
        contentArea.getChildren().clear();
        switch (screen) {
            case "dashboard":  showDashboard(); break;
            case "flights":    showFlights(); break;
            case "passengers": showPassengers(); break;
            case "pilots":     showPilots(); break;
            case "copilots":   showCoPilots(); break;
            case "chat":       showChat(); break;
        }
    }

    // =============================================
    //  SCREEN 1: DASHBOARD
    // =============================================
    private void showDashboard() {
        VBox banner = new VBox(5);
        banner.setStyle(
            "-fx-background-color: linear-gradient(to right, " + Colors.DARK_LEMON + ", #4a7c20); " +
            "-fx-padding: 30; " +
            "-fx-background-radius: 15;"
        );
        Label welcome = new Label("Welcome back, Admin! \uD83D\uDC4B");
        welcome.setStyle("-fx-font-size: 26; -fx-font-weight: bold; -fx-text-fill: white;");
        Label subtitle = new Label("Manage your airport with ease.");
        subtitle.setStyle("-fx-font-size: 14; -fx-text-fill: rgba(255,255,255,0.7);");
        banner.getChildren().addAll(welcome, subtitle);
        contentArea.getChildren().add(banner);

        HBox statsRow = new HBox(15);
        statsRow.setPadding(new Insets(5, 0, 5, 0));
        int totalPassengers = 0;
        for (Flight f : adding.flightslists) {
            totalPassengers += f.getnumofpassengeres();
        }

        statsRow.getChildren().addAll(
            createStatCard("\u2708\uFE0F", "Total Flights", String.valueOf(adding.flightslists.size()), Colors.BRIGHT_LEMON),
            createStatCard("\uD83D\uDC65", "Passengers", String.valueOf(totalPassengers), "#3498db"),
            createStatCard("\uD83D\uDC68\u200D\u2708\uFE0F", "Pilots", String.valueOf(adding.flightersList.size()), "#e67e22"),
            createStatCard("\uD83D\uDC69\u200D\u2708\uFE0F", "Co-Pilots", String.valueOf(adding.copilots.size()), "#9b59b6")
        );
        contentArea.getChildren().add(statsRow);

        Label tableTitle = new Label("\uD83D\uDCCB Recent Flights");
        tableTitle.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: " + Colors.DARK_TEXT + ";");
        contentArea.getChildren().add(tableTitle);

        TableView<Flight> table = new TableView<>();
        table.setPrefHeight(300);
        table.setStyle(TABLE_STYLE);

        TableColumn<Flight, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(60);

        TableColumn<Flight, String> destCol = new TableColumn<>("Destination");
        destCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
        destCol.setPrefWidth(150);

        TableColumn<Flight, String> capCol = new TableColumn<>("Captain");
        capCol.setCellValueFactory(new PropertyValueFactory<>("captain"));
        capCol.setPrefWidth(150);

        TableColumn<Flight, Integer> chairsCol = new TableColumn<>("Chairs");
        chairsCol.setCellValueFactory(new PropertyValueFactory<>("numberOfChairs"));
        chairsCol.setPrefWidth(80);

        TableColumn<Flight, Integer> passCol = new TableColumn<>("Passengers");
        passCol.setCellValueFactory(new PropertyValueFactory<>("numofpassengeres"));
        passCol.setPrefWidth(100);

        TableColumn<Flight, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("flightStatus"));
        statusCol.setPrefWidth(120);

        table.getColumns().addAll(idCol, destCol, capCol, chairsCol, passCol, statusCol);
        table.setItems(FXCollections.observableArrayList(adding.flightslists));

        contentArea.getChildren().add(table);
    }

    private VBox createStatCard(String emoji, String title, String value, String accentColor) {
        VBox card = new VBox();
        card.setStyle(CARD_STYLE);
        card.setPrefWidth(250);
        card.setSpacing(8);

        HBox topRow = new HBox(10);
        topRow.setAlignment(Pos.CENTER_LEFT);
        Label emojiLabel = new Label(emoji);
        emojiLabel.setStyle("-fx-font-size: 30;");
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 13; -fx-text-fill: " + Colors.GREY_TEXT + ";");
        topRow.getChildren().addAll(emojiLabel, titleLabel);

        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-size: 36; -fx-font-weight: bold; -fx-text-fill: " + Colors.DARK_TEXT + ";");

        HBox accentBar = new HBox();
        accentBar.setStyle("-fx-background-color: " + accentColor + "; -fx-background-radius: 2; -fx-pref-height: 3; -fx-pref-width: 60;");

        card.getChildren().addAll(topRow, valueLabel, accentBar);
        return card;
    }

    // =============================================
    //  SCREEN 2: FLIGHTS
    // =============================================
    private void showFlights() {
        Label title = new Label("\u2708\uFE0F Flights Management");
        title.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: " + Colors.DARK_TEXT + ";");

        Button addBtn = createPrimaryButton("+ Add Flight");
        addBtn.setOnAction(e -> addFlightDialog());

        HBox topBar = new HBox(20, title, addBtn);
        topBar.setAlignment(Pos.CENTER_LEFT);
        contentArea.getChildren().add(topBar);

        VBox tableCard = new VBox();
        tableCard.setStyle(CARD_STYLE);
        tableCard.setPadding(new Insets(10));

        TableView<Flight> table = new TableView<>();
        table.setPrefHeight(550);
        table.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        TableColumn<Flight, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(60);

        TableColumn<Flight, String> destCol = new TableColumn<>("Destination");
        destCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
        destCol.setPrefWidth(150);

        TableColumn<Flight, String> capCol = new TableColumn<>("Captain");
        capCol.setCellValueFactory(new PropertyValueFactory<>("captain"));
        capCol.setPrefWidth(150);

        TableColumn<Flight, Integer> chairsCol = new TableColumn<>("Chairs");
        chairsCol.setCellValueFactory(new PropertyValueFactory<>("numberOfChairs"));
        chairsCol.setPrefWidth(80);

        TableColumn<Flight, Integer> passCol = new TableColumn<>("Passengers");
        passCol.setCellValueFactory(new PropertyValueFactory<>("numofpassengeres"));
        passCol.setPrefWidth(100);

        TableColumn<Flight, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("flightStatus"));
        statusCol.setPrefWidth(120);

        table.getColumns().addAll(idCol, destCol, capCol, chairsCol, passCol, statusCol);
        table.setItems(FXCollections.observableArrayList(adding.flightslists));

        tableCard.getChildren().add(table);
        contentArea.getChildren().add(tableCard);
    }

    private void addFlightDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Add New Flight");

        VBox form = new VBox(12);
        form.setPadding(new Insets(25));
        form.setStyle(DIALOG_STYLE);

        Label formTitle = new Label("\u2708\uFE0F New Flight");
        formTitle.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: " + Colors.DARK_TEXT + ";");

        TextField destField = new TextField();
        destField.setPromptText("Destination");
        destField.setStyle("-fx-padding: 10; -fx-background-radius: 8; -fx-border-radius: 8;");

        TextField chairsField = new TextField();
        chairsField.setPromptText("Number of Chairs");
        chairsField.setStyle("-fx-padding: 10; -fx-background-radius: 8; -fx-border-radius: 8;");

        ComboBox<Flighter> captainBox = new ComboBox<>();
        captainBox.setItems(FXCollections.observableArrayList(adding.flightersList));
        captainBox.setPromptText("Select Captain");
        captainBox.setPrefWidth(350);
        captainBox.setStyle("-fx-background-radius: 8;");

        Label copLabel = new Label("Select Co-Pilots (min 2):");
        copLabel.setStyle("-fx-font-weight: bold;");
        ListView<modiefoun> copList = new ListView<>();
        copList.setItems(FXCollections.observableArrayList(adding.copilots));
        copList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        copList.setPrefHeight(120);
        copList.setStyle("-fx-background-radius: 8;");

        ComboBox<String> statusBox = new ComboBox<>();
        statusBox.setItems(FXCollections.observableArrayList("SCHEDULED", "BOARDING", "DEPARTED", "CANCELLED"));
        statusBox.setValue("SCHEDULED");
        statusBox.setStyle("-fx-background-radius: 8;");

        Button saveBtn = createPrimaryButton("Save Flight");
        saveBtn.setOnAction(e -> {
            try {
                String dest = destField.getText();
                int chairs = Integer.parseInt(chairsField.getText());
                Flighter captain = captainBox.getValue();
                ObservableList<modiefoun> selectedCopilots = copList.getSelectionModel().getSelectedItems();
                ArrayList<modiefoun> copilotsList = new ArrayList<>(selectedCopilots);

                if (dest.isEmpty() || captain == null) {
                    showAlert("Please fill all fields and select a captain.");
                    return;
                }
                if (copilotsList.size() < 2) {
                    showAlert("You must select at least 2 co-pilots.");
                    return;
                }

                Flight flight = new Flight(dest, new ArrayList<>(), captain, copilotsList, chairs);
                flight.setFlightStatus(Flight.FlightStatus.valueOf(statusBox.getValue()));
                adding.flightslists.add(flight);
                dialog.close();
                switchScreen("flights");
            } catch (NumberFormatException ex) {
                showAlert("Chairs must be a number.");
            } catch (Exception ex) {
                showAlert("Error: " + ex.getMessage());
            }
        });

        form.getChildren().addAll(formTitle,
            new Label("Destination:"), destField,
            new Label("Number of Chairs:"), chairsField,
            new Label("Captain:"), captainBox,
            copLabel, copList,
            new Label("Status:"), statusBox,
            saveBtn
        );

        Scene scene = new Scene(new ScrollPane(form), 420, 580);
        dialog.setScene(scene);
        dialog.show();
    }

    // =============================================
    //  SCREEN 3: PASSENGERS
    // =============================================
    private void showPassengers() {
        Label title = new Label("\uD83D\uDC65 Passengers Management");
        title.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: " + Colors.DARK_TEXT + ";");

        Button addBtn = createPrimaryButton("+ Add Passenger");
        addBtn.setOnAction(e -> addPassengerDialog());

        HBox topBar = new HBox(20, title, addBtn);
        topBar.setAlignment(Pos.CENTER_LEFT);
        contentArea.getChildren().add(topBar);

        VBox tableCard = new VBox();
        tableCard.setStyle(CARD_STYLE);
        tableCard.setPadding(new Insets(10));

        TableView<Passengere> table = new TableView<>();
        table.setPrefHeight(550);
        table.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        TableColumn<Passengere, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(60);

        TableColumn<Passengere, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(150);

        TableColumn<Passengere, String> idenCol = new TableColumn<>("Identity");
        idenCol.setCellValueFactory(new PropertyValueFactory<>("identity"));
        idenCol.setPrefWidth(120);

        TableColumn<Passengere, String> natCol = new TableColumn<>("Nationality");
        natCol.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        natCol.setPrefWidth(120);

        TableColumn<Passengere, String> passCol = new TableColumn<>("Passport");
        passCol.setCellValueFactory(new PropertyValueFactory<>("passport"));
        passCol.setPrefWidth(120);

        TableColumn<Passengere, String> dateCol = new TableColumn<>("Passport End Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("passEndDate"));
        dateCol.setPrefWidth(130);

        TableColumn<Passengere, String> destCol = new TableColumn<>("Destination");
        destCol.setCellValueFactory(new PropertyValueFactory<>("distination"));
        destCol.setPrefWidth(120);

        table.getColumns().addAll(idCol, nameCol, idenCol, natCol, passCol, dateCol, destCol);

        ArrayList<Passengere> allPassengers = new ArrayList<>();
        for (Flight f : adding.flightslists) {
            allPassengers.addAll(f.getPassengersList());
        }
        table.setItems(FXCollections.observableArrayList(allPassengers));

        tableCard.getChildren().add(table);
        contentArea.getChildren().add(tableCard);
    }

    private void addPassengerDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Add New Passenger");

        VBox form = new VBox(12);
        form.setPadding(new Insets(25));
        form.setStyle(DIALOG_STYLE);

        Label formTitle = new Label("\uD83D\uDC65 New Passenger");
        formTitle.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: " + Colors.DARK_TEXT + ";");

        TextField nameField = new TextField(); nameField.setPromptText("Name");
        nameField.setStyle("-fx-padding: 10; -fx-background-radius: 8; -fx-border-radius: 8;");
        TextField idenField = new TextField(); idenField.setPromptText("Identity");
        idenField.setStyle("-fx-padding: 10; -fx-background-radius: 8; -fx-border-radius: 8;");
        TextField natField = new TextField(); natField.setPromptText("Nationality");
        natField.setStyle("-fx-padding: 10; -fx-background-radius: 8; -fx-border-radius: 8;");
        TextField passField = new TextField(); passField.setPromptText("Passport");
        passField.setStyle("-fx-padding: 10; -fx-background-radius: 8; -fx-border-radius: 8;");
        TextField dateField = new TextField(); dateField.setPromptText("Passport End Date (e.g. 2025-12-31)");
        dateField.setStyle("-fx-padding: 10; -fx-background-radius: 8; -fx-border-radius: 8;");
        TextField destField = new TextField(); destField.setPromptText("Destination");
        destField.setStyle("-fx-padding: 10; -fx-background-radius: 8; -fx-border-radius: 8;");

        Button saveBtn = createPrimaryButton("Save Passenger");
        saveBtn.setOnAction(e -> {
            try {
                Passengere p = new Passengere(
                    nameField.getText(), idenField.getText(), natField.getText(),
                    passField.getText(), dateField.getText(), destField.getText()
                );
                if (!adding.flightslists.isEmpty()) {
                    adding.flightslists.get(0).bookPassenger(p);
                }
                dialog.close();
                switchScreen("passengers");
            } catch (Exception ex) {
                showAlert("Error: " + ex.getMessage());
            }
        });

        form.getChildren().addAll(formTitle,
            new Label("Name:"), nameField,
            new Label("Identity:"), idenField,
            new Label("Nationality:"), natField,
            new Label("Passport:"), passField,
            new Label("Passport End Date:"), dateField,
            new Label("Destination:"), destField,
            saveBtn
        );

        Scene scene = new Scene(new ScrollPane(form), 420, 530);
        dialog.setScene(scene);
        dialog.show();
    }

    // =============================================
    //  SCREEN 4: PILOTS
    // =============================================
    private void showPilots() {
        Label title = new Label("\uD83D\uDC68\u200D\u2708\uFE0F Pilots Management");
        title.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: " + Colors.DARK_TEXT + ";");

        Button addBtn = createPrimaryButton("+ Add Pilot");
        addBtn.setOnAction(e -> addPilotDialog());

        HBox topBar = new HBox(20, title, addBtn);
        topBar.setAlignment(Pos.CENTER_LEFT);
        contentArea.getChildren().add(topBar);

        VBox tableCard = new VBox();
        tableCard.setStyle(CARD_STYLE);
        tableCard.setPadding(new Insets(10));

        TableView<Flighter> table = new TableView<>();
        table.setPrefHeight(550);
        table.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        TableColumn<Flighter, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(60);

        TableColumn<Flighter, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(150);

        TableColumn<Flighter, String> idenCol = new TableColumn<>("Identity");
        idenCol.setCellValueFactory(new PropertyValueFactory<>("identity"));
        idenCol.setPrefWidth(120);

        TableColumn<Flighter, String> natCol = new TableColumn<>("Nationality");
        natCol.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        natCol.setPrefWidth(120);

        TableColumn<Flighter, Double> salCol = new TableColumn<>("Salary");
        salCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        salCol.setPrefWidth(100);

        TableColumn<Flighter, Double> hrsCol = new TableColumn<>("Hours");
        hrsCol.setCellValueFactory(new PropertyValueFactory<>("hours"));
        hrsCol.setPrefWidth(80);

        TableColumn<Flighter, String> licCol = new TableColumn<>("Licenses");
        licCol.setCellValueFactory(new PropertyValueFactory<>("rokhasList"));
        licCol.setPrefWidth(200);

        table.getColumns().addAll(idCol, nameCol, idenCol, natCol, salCol, hrsCol, licCol);
        table.setItems(FXCollections.observableArrayList(adding.flightersList));

        tableCard.getChildren().add(table);
        contentArea.getChildren().add(tableCard);
    }

    private void addPilotDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Add New Pilot");

        VBox form = new VBox(12);
        form.setPadding(new Insets(25));
        form.setStyle(DIALOG_STYLE);

        Label formTitle = new Label("\uD83D\uDC68\u200D\u2708\uFE0F New Pilot");
        formTitle.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: " + Colors.DARK_TEXT + ";");

        TextField nameField = new TextField(); nameField.setPromptText("Name");
        nameField.setStyle("-fx-padding: 10; -fx-background-radius: 8; -fx-border-radius: 8;");
        TextField idenField = new TextField(); idenField.setPromptText("Identity");
        idenField.setStyle("-fx-padding: 10; -fx-background-radius: 8; -fx-border-radius: 8;");
        TextField natField = new TextField(); natField.setPromptText("Nationality");
        natField.setStyle("-fx-padding: 10; -fx-background-radius: 8; -fx-border-radius: 8;");
        TextField salField = new TextField(); salField.setPromptText("Salary");
        salField.setStyle("-fx-padding: 10; -fx-background-radius: 8; -fx-border-radius: 8;");
        TextField hrsField = new TextField(); hrsField.setPromptText("Flight Hours");
        hrsField.setStyle("-fx-padding: 10; -fx-background-radius: 8; -fx-border-radius: 8;");

        Button saveBtn = createPrimaryButton("Save Pilot");
        saveBtn.setOnAction(e -> {
            try {
                double salary = Double.parseDouble(salField.getText());
                double hours = Double.parseDouble(hrsField.getText());
                ArrayList<String> licenses = new ArrayList<>();

                Flighter pilot = new Flighter(
                    nameField.getText(), idenField.getText(), natField.getText(),
                    salary, hours, licenses
                );
                adding.flightersList.add(pilot);
                dialog.close();
                switchScreen("pilots");
            } catch (NumberFormatException ex) {
                showAlert("Salary and Hours must be numbers.");
            }
        });

        form.getChildren().addAll(formTitle,
            new Label("Name:"), nameField,
            new Label("Identity:"), idenField,
            new Label("Nationality:"), natField,
            new Label("Salary:"), salField,
            new Label("Flight Hours:"), hrsField,
            saveBtn
        );

        Scene scene = new Scene(new ScrollPane(form), 420, 440);
        dialog.setScene(scene);
        dialog.show();
    }

    // =============================================
    //  SCREEN 5: CO-PILOTS
    // =============================================
    private void showCoPilots() {
        Label title = new Label("\uD83D\uDC69\u200D\u2708\uFE0F Co-Pilots Management");
        title.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: " + Colors.DARK_TEXT + ";");

        Button addBtn = createPrimaryButton("+ Add Co-Pilot");
        addBtn.setOnAction(e -> addCoPilotDialog());

        HBox topBar = new HBox(20, title, addBtn);
        topBar.setAlignment(Pos.CENTER_LEFT);
        contentArea.getChildren().add(topBar);

        VBox tableCard = new VBox();
        tableCard.setStyle(CARD_STYLE);
        tableCard.setPadding(new Insets(10));

        TableView<modiefoun> table = new TableView<>();
        table.setPrefHeight(550);
        table.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        TableColumn<modiefoun, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(60);

        TableColumn<modiefoun, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(150);

        TableColumn<modiefoun, String> idenCol = new TableColumn<>("Identity");
        idenCol.setCellValueFactory(new PropertyValueFactory<>("identity"));
        idenCol.setPrefWidth(120);

        TableColumn<modiefoun, String> natCol = new TableColumn<>("Nationality");
        natCol.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        natCol.setPrefWidth(120);

        TableColumn<modiefoun, Double> salCol = new TableColumn<>("Salary");
        salCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        salCol.setPrefWidth(100);

        TableColumn<modiefoun, Double> hrsCol = new TableColumn<>("Hours");
        hrsCol.setCellValueFactory(new PropertyValueFactory<>("hours"));
        hrsCol.setPrefWidth(80);

        TableColumn<modiefoun, ArrayList> langCol = new TableColumn<>("Languages");
        langCol.setCellValueFactory(new PropertyValueFactory<>("langsList"));
        langCol.setPrefWidth(200);

        table.getColumns().addAll(idCol, nameCol, idenCol, natCol, salCol, hrsCol, langCol);
        table.setItems(FXCollections.observableArrayList(adding.copilots));

        tableCard.getChildren().add(table);
        contentArea.getChildren().add(tableCard);
    }

    private void addCoPilotDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Add New Co-Pilot");

        VBox form = new VBox(12);
        form.setPadding(new Insets(25));
        form.setStyle(DIALOG_STYLE);

        Label formTitle = new Label("\uD83D\uDC69\u200D\u2708\uFE0F New Co-Pilot");
        formTitle.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: " + Colors.DARK_TEXT + ";");

        TextField nameField = new TextField(); nameField.setPromptText("Name");
        nameField.setStyle("-fx-padding: 10; -fx-background-radius: 8; -fx-border-radius: 8;");
        TextField idenField = new TextField(); idenField.setPromptText("Identity");
        idenField.setStyle("-fx-padding: 10; -fx-background-radius: 8; -fx-border-radius: 8;");
        TextField natField = new TextField(); natField.setPromptText("Nationality");
        natField.setStyle("-fx-padding: 10; -fx-background-radius: 8; -fx-border-radius: 8;");
        TextField salField = new TextField(); salField.setPromptText("Salary");
        salField.setStyle("-fx-padding: 10; -fx-background-radius: 8; -fx-border-radius: 8;");
        TextField hrsField = new TextField(); hrsField.setPromptText("Work Hours");
        hrsField.setStyle("-fx-padding: 10; -fx-background-radius: 8; -fx-border-radius: 8;");

        Label langLabel = new Label("Languages (comma separated, e.g. English, Arabic, French):");
        langLabel.setStyle("-fx-font-weight: bold;");
        TextField langField = new TextField();
        langField.setPromptText("English, Arabic, French");
        langField.setStyle("-fx-padding: 10; -fx-background-radius: 8; -fx-border-radius: 8;");

        Button saveBtn = createPrimaryButton("Save Co-Pilot");
        saveBtn.setOnAction(e -> {
            try {
                double salary = Double.parseDouble(salField.getText());
                double hours = Double.parseDouble(hrsField.getText());
                ArrayList<String> languages = new ArrayList<>();
                String langText = langField.getText().trim();
                if (!langText.isEmpty()) {
                    String[] parts = langText.split(",");
                    for (String lang : parts) {
                        String trimmed = lang.trim();
                        if (!trimmed.isEmpty()) {
                            languages.add(trimmed);
                        }
                    }
                }

                modiefoun copilot = new modiefoun(
                    nameField.getText(), idenField.getText(), natField.getText(),
                    salary, languages, hours
                );
                adding.copilots.add(copilot);
                dialog.close();
                switchScreen("copilots");
            } catch (NumberFormatException ex) {
                showAlert("Salary and Hours must be numbers.");
            }
        });

        form.getChildren().addAll(formTitle,
            new Label("Name:"), nameField,
            new Label("Identity:"), idenField,
            new Label("Nationality:"), natField,
            new Label("Salary:"), salField,
            new Label("Work Hours:"), hrsField,
            langLabel, langField,
            saveBtn
        );

        Scene scene = new Scene(new ScrollPane(form), 420, 520);
        dialog.setScene(scene);
        dialog.show();
    }

    // =============================================
    //  SCREEN 6: CHAT
    // =============================================
    private void showChat() {
        Label title = new Label("\uD83D\uDCAC LAN Chat");
        title.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: " + Colors.DARK_TEXT + ";");
        contentArea.getChildren().add(title);

        TextInputDialog nicknameDialog = new TextInputDialog("Admin");
        nicknameDialog.setTitle("Chat Login");
        nicknameDialog.setHeaderText("Enter your nickname to start chatting");
        nicknameDialog.setContentText("Nickname:");

        nicknameDialog.showAndWait().ifPresent(nickname -> {
            VBox chatCard = new VBox(10);
            chatCard.setStyle(CARD_STYLE);
            chatCard.setPadding(new Insets(15));
            VBox.setVgrow(chatCard, Priority.ALWAYS);

            TextArea messageArea = new TextArea();
            messageArea.setEditable(false);
            messageArea.setWrapText(true);
            messageArea.setStyle("-fx-font-size: 13; -fx-background-radius: 10; -fx-border-radius: 10;");
            VBox.setVgrow(messageArea, Priority.ALWAYS);

            TextField inputField = new TextField();
            inputField.setPromptText("Type your message...");
            inputField.setStyle("-fx-font-size: 13; -fx-padding: 10; -fx-background-radius: 10; -fx-border-radius: 10;");

            Button sendBtn = createPrimaryButton("Send");
            sendBtn.setPrefWidth(100);

            HBox bottomBar = new HBox(10, inputField, sendBtn);
            bottomBar.setAlignment(Pos.CENTER_LEFT);

            chatclient client = new chatclient("localhost", 5555, nickname, messageArea);

            sendBtn.setOnAction(e -> {
                String msg = inputField.getText();
                if (!msg.trim().isEmpty() && client.isConnected()) {
                    client.sendMessage(msg);
                    inputField.clear();
                }
            });

            inputField.setOnAction(e -> sendBtn.fire());

            chatCard.getChildren().addAll(messageArea, bottomBar);
            contentArea.getChildren().add(chatCard);
        });
    }

    // =============================================
    //  HELPER: Show alert
    // =============================================
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // =============================================
    //  HELPER: Create primary button
    // =============================================
    private Button createPrimaryButton(String text) {
        Button btn = new Button(text);
        btn.setStyle(
            "-fx-background-color: " + Colors.BRIGHT_LEMON + "; " +
            "-fx-text-fill: " + Colors.DARK_LEMON + "; " +
            "-fx-font-size: 13; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 10 20; " +
            "-fx-background-radius: 20; " +
            "-fx-cursor: hand;"
        );
        btn.setOnMouseEntered(e -> btn.setStyle(btn.getStyle().replace(Colors.BRIGHT_LEMON, "#d4f548")));
        btn.setOnMouseExited(e -> btn.setStyle(btn.getStyle().replace("#d4f548", Colors.BRIGHT_LEMON)));
        return btn;
    }
}
