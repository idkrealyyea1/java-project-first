import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FlightsPage extends VBox {

    private static final String INPUT_STYLE =
        "-fx-background-color: " + Colors.FLIGHT_LIGHT + "; " +
        "-fx-border-color: " + Colors.FLIGHT_MID + "; -fx-border-radius: 12; " +
        "-fx-background-radius: 12; -fx-padding: 10 15; -fx-font-size: 13;";

    private TableView<Flight> table;

    public FlightsPage() {
        setPadding(new Insets(30));
        setSpacing(20);
        setStyle("-fx-background-color: " + Colors.BACKGROUND + ";");

        VBox card = new VBox(15);
        card.setStyle(
            "-fx-background-color: " + Colors.CARD + "; -fx-background-radius: 20; " +
            "-fx-border-color: " + Colors.FLIGHT_MID + "; -fx-border-width: 0 0 3 0; " +
            "-fx-border-radius: 20; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 3);"
        );
        card.setPadding(new Insets(25));

        HBox headerRow = new HBox(15);
        headerRow.setAlignment(Pos.CENTER_LEFT);

        StackPane iconCircle = new StackPane();
        Circle bg = new Circle(20);
        bg.setFill(Color.web(Colors.FLIGHT_LIGHT));
        Label iconLabel = new Label("F");
        iconLabel.setStyle("-fx-text-fill: " + Colors.FLIGHT_SOLID + "; -fx-font-size: 14; -fx-font-weight: bold;");
        iconCircle.getChildren().addAll(bg, iconLabel);

        VBox titles = new VBox(2);
        Label title = new Label("Flight Management");
        title.setStyle("-fx-text-fill: " + Colors.TEXT_PRIMARY + "; -fx-font-size: 20; -fx-font-weight: bold;");
        Label subtitle = new Label("All registered flights in the system");
        subtitle.setStyle("-fx-text-fill: " + Colors.TEXT_SECONDARY + "; -fx-font-size: 12;");
        titles.getChildren().addAll(title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button addBtn = new Button("+ Add Flight");
        addBtn.setStyle(
            "-fx-background-color: " + Colors.FLIGHT_SOLID + "; -fx-text-fill: white; " +
            "-fx-font-size: 13; -fx-font-weight: bold; -fx-background-radius: 12; " +
            "-fx-padding: 10 20; -fx-cursor: hand;"
        );
        addBtn.setOnMouseEntered(e -> addBtn.setStyle(
            "-fx-background-color: " + Color.web(Colors.FLIGHT_SOLID).deriveColor(0, 1, 0.9, 1) + "; -fx-text-fill: white; " +
            "-fx-font-size: 13; -fx-font-weight: bold; -fx-background-radius: 12; " +
            "-fx-padding: 10 20; -fx-cursor: hand;"
        ));
        addBtn.setOnMouseExited(e -> addBtn.setStyle(
            "-fx-background-color: " + Colors.FLIGHT_SOLID + "; -fx-text-fill: white; " +
            "-fx-font-size: 13; -fx-font-weight: bold; -fx-background-radius: 12; " +
            "-fx-padding: 10 20; -fx-cursor: hand;"
        ));
        addBtn.setOnAction(e -> showAddFlightDialog());

        headerRow.getChildren().addAll(iconCircle, titles, spacer, addBtn);

        table = new TableView<>();
        table.setPrefHeight(500);
        table.setStyle("-fx-background-color: transparent; -fx-border-color: " + Colors.BORDER + ";");

        TableColumn<Flight, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(60);

        TableColumn<Flight, String> destCol = new TableColumn<>("Destination");
        destCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
        destCol.setPrefWidth(180);

        TableColumn<Flight, String> captainCol = new TableColumn<>("Captain");
        captainCol.setCellValueFactory(cell -> {
            Flighter cap = cell.getValue().getNameofCaptin();
            return new SimpleStringProperty(cap != null ? cap.getName() : "N/A");
        });
        captainCol.setPrefWidth(150);

        TableColumn<Flight, Integer> seatsCol = new TableColumn<>("Seats");
        seatsCol.setCellValueFactory(new PropertyValueFactory<>("numberofchairs"));
        seatsCol.setPrefWidth(80);

        TableColumn<Flight, String> passengersCol = new TableColumn<>("Passengers");
        passengersCol.setCellValueFactory(cell ->
            new SimpleStringProperty(String.valueOf(cell.getValue().getnumofpassengeres()))
        );
        passengersCol.setPrefWidth(100);

        TableColumn<Flight, String> copilotsCol = new TableColumn<>("Co-Pilots");
        copilotsCol.setCellValueFactory(cell ->
            new SimpleStringProperty(cell.getValue().getCopiloList() != null ?
                String.valueOf(cell.getValue().getCopiloList().size()) : "0")
        );
        copilotsCol.setPrefWidth(90);

        TableColumn<Flight, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("flightStatus"));
        statusCol.setPrefWidth(140);
        statusCol.setCellFactory(col -> new javafx.scene.control.TableCell<>() {
            private final javafx.scene.control.ComboBox<String> combo = new javafx.scene.control.ComboBox<>();
            {
                combo.setStyle(
                    "-fx-background-color: " + Colors.FLIGHT_LIGHT + "; " +
                    "-fx-border-color: " + Colors.FLIGHT_MID + "; " +
                    "-fx-border-radius: 8; -fx-background-radius: 8; " +
                    "-fx-font-size: 11; -fx-padding: 2 6;"
                );
                combo.setOnAction(e -> {
                    int idx = getIndex();
                    if (idx >= 0 && idx < getTableView().getItems().size()) {
                        Flight flight = getTableView().getItems().get(idx);
                        String val = combo.getValue();
                        if (val != null && !val.equals(flight.getFlightStatus().toString())) {
                            flight.setFlightStatus(status.FlightStatus.valueOf(val));
                            updateItem(val, false);
                        }
                    }
                });
            }
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    combo.getItems().clear();
                    Flight flight = getTableView().getItems().get(getIndex());
                    status.FlightStatus current = flight.getFlightStatus();
                    switch (current) {
                        case SCHEDULED:
                            combo.getItems().addAll("BOARDING", "CANCELLED");
                            combo.setDisable(false);
                            break;
                        case BOARDING:
                            combo.getItems().addAll("DEPARTED", "CANCELLED");
                            combo.setDisable(false);
                            break;
                        case DEPARTED:
                            combo.getItems().add("DEPARTED");
                            combo.setDisable(true);
                            break;
                        case CANCELLED:
                            combo.getItems().add("CANCELLED");
                            combo.setDisable(true);
                            break;
                    }
                    combo.setValue(item);
                    setGraphic(combo);
                }
            }
        });

        TableColumn<Flight, Void> actionCol = new TableColumn<>("Actions");
        actionCol.setPrefWidth(100);
        actionCol.setCellFactory(col -> new javafx.scene.control.TableCell<>() {
            {
                Button deleteBtn = new Button("\u2716");
                deleteBtn.setStyle(
                    "-fx-background-color: " + Colors.CHAT_LIGHT + "; -fx-text-fill: " + Colors.CHAT_SOLID + "; " +
                    "-fx-font-size: 12; -fx-background-radius: 8; -fx-padding: 4 10; -fx-cursor: hand;"
                );
                deleteBtn.setOnAction(e -> {
                    Flight flight = getTableView().getItems().get(getIndex());
                    adding.flightslists.remove(flight);
                    getTableView().getItems().remove(flight);
                });
                setGraphic(deleteBtn);
            }
        });

        table.getColumns().addAll(idCol, destCol, captainCol, seatsCol, passengersCol, copilotsCol, statusCol, actionCol);
        table.getItems().addAll(adding.flightslists);

        card.getChildren().addAll(headerRow, table);
        getChildren().add(card);
    }

    public void filterTable(String query) {
        if (query == null || query.trim().isEmpty()) {
            table.getItems().setAll(adding.flightslists);
            return;
        }
        String q = query.toLowerCase();
        table.getItems().clear();
        for (Flight f : adding.flightslists) {
            String dest = f.getDestination() != null ? f.getDestination().toLowerCase() : "";
            String captain = f.getNameofCaptin() != null ? f.getNameofCaptin().getName().toLowerCase() : "";
            String status = f.getFlightStatus() != null ? f.getFlightStatus().toString().toLowerCase() : "";
            if (dest.contains(q) || captain.contains(q) || status.contains(q)
                || String.valueOf(f.getId()).contains(q)) {
                table.getItems().add(f);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void showAddFlightDialog() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Add New Flight");

        VBox root = new VBox(15);
        root.setPadding(new Insets(25));
        root.setStyle("-fx-background-color: " + Colors.CARD + ";");

        Label title = new Label("Add New Flight");
        title.setStyle("-fx-text-fill: " + Colors.TEXT_PRIMARY + "; -fx-font-size: 18; -fx-font-weight: bold;");

        TextField destField = new TextField();
        destField.setPromptText("Destination");
        destField.setStyle(INPUT_STYLE);
        destField.setMaxWidth(Double.MAX_VALUE);

        TextField seatsField = new TextField();
        seatsField.setPromptText("Number of Seats");
        seatsField.setStyle(INPUT_STYLE);
        seatsField.setMaxWidth(Double.MAX_VALUE);

        ComboBox<String> captainBox = new ComboBox<>();
        captainBox.setPromptText("Select Captain");
        captainBox.setStyle(INPUT_STYLE);
        captainBox.setMaxWidth(Double.MAX_VALUE);
        for (Flighter f : adding.flightersList) {
            captainBox.getItems().add(f.getName());
        }

        TextField copilotField = new TextField();
        copilotField.setPromptText("Number of Co-Pilots (0 = none)");
        copilotField.setStyle(INPUT_STYLE);
        copilotField.setMaxWidth(Double.MAX_VALUE);

        Label errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill: " + Colors.CHAT_SOLID + "; -fx-font-size: 12;");

        Button saveBtn = new Button("Save Flight");
        saveBtn.setStyle(
            "-fx-background-color: " + Colors.FLIGHT_SOLID + "; -fx-text-fill: white; " +
            "-fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 12; " +
            "-fx-padding: 12 30; -fx-cursor: hand; -fx-max-width: Infinity;"
        );

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setStyle(
            "-fx-background-color: transparent; -fx-text-fill: " + Colors.TEXT_SECONDARY + "; " +
            "-fx-font-size: 13; -fx-cursor: hand; -fx-max-width: Infinity;"
        );

        saveBtn.setOnAction(e -> {
            try {
                String dest = destField.getText();
                int seats = Integer.parseInt(seatsField.getText());
                String captainName = captainBox.getValue();

                if (dest.isEmpty() || captainName == null) {
                    errorLabel.setText("Please fill all required fields");
                    return;
                }

                Flighter captain = null;
                for (Flighter f : adding.flightersList) {
                    if (f.getName().equals(captainName)) {
                        captain = f;
                        break;
                    }
                }

                if (captain == null) {
                    errorLabel.setText("Captain not found");
                    return;
                }

                int copilotCount = 0;
                if (!copilotField.getText().trim().isEmpty()) {
                    copilotCount = Integer.parseInt(copilotField.getText().trim());
                }

                if (copilotCount < 0) {
                    errorLabel.setText("Co-pilot count cannot be negative");
                    return;
                }

                java.util.ArrayList<modiefoun> copilotList = new java.util.ArrayList<>();
                if (copilotCount > 0) {
                    if (adding.copilots.size() < copilotCount) {
                        errorLabel.setText("Not enough co-pilots. Available: " + adding.copilots.size());
                        return;
                    }
                    copilotList.addAll(adding.copilots.subList(0, copilotCount));
                }
                java.util.ArrayList<Passengere> passengerList = new java.util.ArrayList<>();

                Flight flight = new Flight(dest, passengerList, captain, copilotList, seats);
                adding.flightslists.add(flight);
                table.getItems().setAll(adding.flightslists);
                DataStorage.savePassengers();

                dialog.close();
            } catch (NumberFormatException ex) {
                errorLabel.setText("Please enter valid numbers");
            }
        });

        cancelBtn.setOnAction(e -> dialog.close());

        root.getChildren().addAll(title, destField, seatsField, captainBox, copilotField, errorLabel, saveBtn, cancelBtn);
        Scene scene = new Scene(root, 420, 420);
        dialog.setScene(scene);
        dialog.show();
    }
}
