import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

public class PassengersPage extends VBox {

    private static final String INPUT_STYLE =
        "-fx-background-color: " + Colors.PASSENGER_LIGHT + "; " +
        "-fx-border-color: " + Colors.PASSENGER_MID + "; -fx-border-radius: 12; " +
        "-fx-background-radius: 12; -fx-padding: 10 15; -fx-font-size: 13;";

    private TableView<Passengere> table;

    public PassengersPage() {
        setPadding(new Insets(30));
        setSpacing(20);
        setStyle("-fx-background-color: " + Colors.BACKGROUND + ";");

        VBox card = new VBox(15);
        card.setStyle(
            "-fx-background-color: " + Colors.CARD + "; -fx-background-radius: 20; " +
            "-fx-border-color: " + Colors.PASSENGER_MID + "; -fx-border-width: 0 0 3 0; " +
            "-fx-border-radius: 20; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 3);"
        );
        card.setPadding(new Insets(25));

        HBox headerRow = new HBox(15);
        headerRow.setAlignment(Pos.CENTER_LEFT);

        StackPane iconCircle = new StackPane();
        Circle bg = new Circle(20);
        bg.setFill(Color.web(Colors.PASSENGER_LIGHT));
        Label iconLabel = new Label("P");
        iconLabel.setStyle("-fx-text-fill: " + Colors.PASSENGER_SOLID + "; -fx-font-size: 14; -fx-font-weight: bold;");
        iconCircle.getChildren().addAll(bg, iconLabel);

        VBox titles = new VBox(2);
        Label title = new Label("Passenger Management");
        title.setStyle("-fx-text-fill: " + Colors.TEXT_PRIMARY + "; -fx-font-size: 20; -fx-font-weight: bold;");
        Label subtitle = new Label("All registered passengers");
        subtitle.setStyle("-fx-text-fill: " + Colors.TEXT_SECONDARY + "; -fx-font-size: 12;");
        titles.getChildren().addAll(title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button addBtn = new Button("+ Add Passenger");
        addBtn.setStyle(
            "-fx-background-color: " + Colors.PASSENGER_SOLID + "; -fx-text-fill: white; " +
            "-fx-font-size: 13; -fx-font-weight: bold; -fx-background-radius: 12; " +
            "-fx-padding: 10 20; -fx-cursor: hand;"
        );
        addBtn.setOnMouseEntered(e -> addBtn.setStyle(
            "-fx-background-color: " + Color.web(Colors.PASSENGER_SOLID).deriveColor(0, 1, 0.9, 1) + "; -fx-text-fill: white; " +
            "-fx-font-size: 13; -fx-font-weight: bold; -fx-background-radius: 12; " +
            "-fx-padding: 10 20; -fx-cursor: hand;"
        ));
        addBtn.setOnMouseExited(e -> addBtn.setStyle(
            "-fx-background-color: " + Colors.PASSENGER_SOLID + "; -fx-text-fill: white; " +
            "-fx-font-size: 13; -fx-font-weight: bold; -fx-background-radius: 12; " +
            "-fx-padding: 10 20; -fx-cursor: hand;"
        ));
        addBtn.setOnAction(e -> showAddPassengerDialog());

        headerRow.getChildren().addAll(iconCircle, titles, spacer, addBtn);

        table = new TableView<>();
        table.setPrefHeight(500);
        table.setStyle("-fx-background-color: transparent; -fx-border-color: " + Colors.BORDER + ";");

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

        TableColumn<Passengere, String> endCol = new TableColumn<>("Passport End");
        endCol.setCellValueFactory(new PropertyValueFactory<>("passEndDate"));
        endCol.setPrefWidth(120);

        TableColumn<Passengere, String> destCol = new TableColumn<>("Destination");
        destCol.setCellValueFactory(new PropertyValueFactory<>("distination"));
        destCol.setPrefWidth(130);

        TableColumn<Passengere, Void> actionCol = new TableColumn<>("Actions");
        actionCol.setPrefWidth(100);
        actionCol.setCellFactory(col -> new javafx.scene.control.TableCell<>() {
            {
                Button deleteBtn = new Button("\u2716");
                deleteBtn.setStyle(
                    "-fx-background-color: " + Colors.CHAT_LIGHT + "; -fx-text-fill: " + Colors.CHAT_SOLID + "; " +
                    "-fx-font-size: 12; -fx-background-radius: 8; -fx-padding: 4 10; -fx-cursor: hand;"
                );
                deleteBtn.setOnAction(e -> {
                    Passengere p = getTableView().getItems().get(getIndex());
                    getTableView().getItems().remove(p);
                    removeFromFlights(p);
                    DataStorage.saveAll();
                });
                setGraphic(deleteBtn);
            }
        });

        table.getColumns().addAll(idCol, nameCol, idenCol, natCol, passCol, endCol, destCol, actionCol);
        loadAllPassengers();

        card.getChildren().addAll(headerRow, table);
        getChildren().add(card);
    }

    private void loadAllPassengers() {
        table.getItems().clear();
        for (Flight f : adding.flightslists) {
            if (f.getPassengersList() != null) {
                table.getItems().addAll(f.getPassengersList());
            }
        }
    }

    public void filterTable(String query) {
        if (query == null || query.trim().isEmpty()) {
            loadAllPassengers();
            return;
        }
        String q = query.toLowerCase();
        table.getItems().clear();
        for (Flight f : adding.flightslists) {
            if (f.getPassengersList() != null) {
                for (Passengere p : f.getPassengersList()) {
                    String name = p.getName() != null ? p.getName().toLowerCase() : "";
                    String identity = p.getIdentity() != null ? p.getIdentity().toLowerCase() : "";
                    String passport = p.getPassport() != null ? p.getPassport().toLowerCase() : "";
                    String dest = p.getDistination() != null ? p.getDistination().toLowerCase() : "";
                    if (name.contains(q) || identity.contains(q) || passport.contains(q) || dest.contains(q)) {
                        table.getItems().add(p);
                    }
                }
            }
        }
    }

    private void removeFromFlights(Passengere p) {
        for (Flight f : adding.flightslists) {
            if (f.getPassengersList() != null) {
                f.getPassengersList().remove(p);
            }
        }
    }

    private void showAddPassengerDialog() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Add New Passenger");

        VBox root = new VBox(15);
        root.setPadding(new Insets(25));
        root.setStyle("-fx-background-color: " + Colors.CARD + ";");

        Label title = new Label("Register Passenger");
        title.setStyle("-fx-text-fill: " + Colors.TEXT_PRIMARY + "; -fx-font-size: 18; -fx-font-weight: bold;");

        TextField nameField = new TextField();
        nameField.setPromptText("Full Name");
        nameField.setStyle(INPUT_STYLE);
        nameField.setMaxWidth(Double.MAX_VALUE);

        TextField idField = new TextField();
        idField.setPromptText("Identity Number");
        idField.setStyle(INPUT_STYLE);
        idField.setMaxWidth(Double.MAX_VALUE);

        TextField natField = new TextField();
        natField.setPromptText("Nationality");
        natField.setStyle(INPUT_STYLE);
        natField.setMaxWidth(Double.MAX_VALUE);

        TextField passportField = new TextField();
        passportField.setPromptText("Passport Number");
        passportField.setStyle(INPUT_STYLE);
        passportField.setMaxWidth(Double.MAX_VALUE);

        TextField endDateField = new TextField();
        endDateField.setPromptText("Passport End Date");
        endDateField.setStyle(INPUT_STYLE);
        endDateField.setMaxWidth(Double.MAX_VALUE);

        TextField destField = new TextField();
        destField.setPromptText("Destination");
        destField.setStyle(INPUT_STYLE);
        destField.setMaxWidth(Double.MAX_VALUE);

        Label errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill: " + Colors.CHAT_SOLID + "; -fx-font-size: 12;");

        Button saveBtn = new Button("Save Passenger");
        saveBtn.setStyle(
            "-fx-background-color: " + Colors.PASSENGER_SOLID + "; -fx-text-fill: white; " +
            "-fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 12; " +
            "-fx-padding: 12 30; -fx-cursor: hand; -fx-max-width: Infinity;"
        );

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setStyle(
            "-fx-background-color: transparent; -fx-text-fill: " + Colors.TEXT_SECONDARY + "; " +
            "-fx-font-size: 13; -fx-cursor: hand; -fx-max-width: Infinity;"
        );

        saveBtn.setOnAction(e -> {
            if (nameField.getText().isEmpty() || idField.getText().isEmpty()) {
                errorLabel.setText("Please fill at least name and identity");
                return;
            }
            Passengere p = new Passengere(
                nameField.getText(), idField.getText(), natField.getText(),
                passportField.getText(), endDateField.getText(), destField.getText()
            );
            if (adding.flightslists.isEmpty()) {
                Flight defaultFlight = new Flight("Unassigned", new java.util.ArrayList<>(), null, new java.util.ArrayList<>(), 0);
                adding.flightslists.add(defaultFlight);
            }
            adding.flightslists.get(0).getPassengersList().add(p);
                loadAllPassengers();
                    DataStorage.saveAll();
                    dialog.close();
        });

        cancelBtn.setOnAction(e -> dialog.close());

        root.getChildren().addAll(title, nameField, idField, natField, passportField, endDateField, destField, errorLabel, saveBtn, cancelBtn);
        Scene scene = new Scene(root, 420, 500);
        dialog.setScene(scene);
        dialog.show();
    }
}
