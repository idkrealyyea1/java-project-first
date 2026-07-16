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

public class PilotsPage extends VBox {

    private static final String INPUT_STYLE =
        "-fx-background-color: " + Colors.PILOT_LIGHT + "; " +
        "-fx-border-color: " + Colors.PILOT_MID + "; -fx-border-radius: 12; " +
        "-fx-background-radius: 12; -fx-padding: 10 15; -fx-font-size: 13;";

    private TableView<Flighter> table;

    public PilotsPage() {
        setPadding(new Insets(30));
        setSpacing(20);
        setStyle("-fx-background-color: " + Colors.BACKGROUND + ";");

        VBox card = new VBox(15);
        card.setStyle(
            "-fx-background-color: " + Colors.CARD + "; -fx-background-radius: 20; " +
            "-fx-border-color: " + Colors.PILOT_MID + "; -fx-border-width: 0 0 3 0; " +
            "-fx-border-radius: 20; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 3);"
        );
        card.setPadding(new Insets(25));

        HBox headerRow = new HBox(15);
        headerRow.setAlignment(Pos.CENTER_LEFT);

        StackPane iconCircle = new StackPane();
        Circle bg = new Circle(20);
        bg.setFill(Color.web(Colors.PILOT_LIGHT));
        Label iconLabel = new Label("\u2461");
        iconLabel.setStyle("-fx-text-fill: " + Colors.PILOT_SOLID + "; -fx-font-size: 14; -fx-font-weight: bold;");
        iconCircle.getChildren().addAll(bg, iconLabel);

        VBox titles = new VBox(2);
        Label title = new Label("Pilot Roster");
        title.setStyle("-fx-text-fill: " + Colors.TEXT_PRIMARY + "; -fx-font-size: 20; -fx-font-weight: bold;");
        Label subtitle = new Label("All licensed pilots");
        subtitle.setStyle("-fx-text-fill: " + Colors.TEXT_SECONDARY + "; -fx-font-size: 12;");
        titles.getChildren().addAll(title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button addBtn = new Button("+ Add Pilot");
        addBtn.setStyle(
            "-fx-background-color: " + Colors.PILOT_SOLID + "; -fx-text-fill: white; " +
            "-fx-font-size: 13; -fx-font-weight: bold; -fx-background-radius: 12; " +
            "-fx-padding: 10 20; -fx-cursor: hand;"
        );
        addBtn.setOnMouseEntered(e -> addBtn.setStyle(
            "-fx-background-color: " + Colors.deriveColor(Colors.PILOT_SOLID, 0, 1, 0.9, 1) + "; -fx-text-fill: white; " +
            "-fx-font-size: 13; -fx-font-weight: bold; -fx-background-radius: 12; " +
            "-fx-padding: 10 20; -fx-cursor: hand;"
        ));
        addBtn.setOnMouseExited(e -> addBtn.setStyle(
            "-fx-background-color: " + Colors.PILOT_SOLID + "; -fx-text-fill: white; " +
            "-fx-font-size: 13; -fx-font-weight: bold; -fx-background-radius: 12; " +
            "-fx-padding: 10 20; -fx-cursor: hand;"
        ));
        addBtn.setOnAction(e -> showAddPilotDialog());

        headerRow.getChildren().addAll(iconCircle, titles, spacer, addBtn);

        table = new TableView<>();
        table.setPrefHeight(500);
        table.setStyle("-fx-background-color: transparent; -fx-border-color: " + Colors.BORDER + ";");

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

        TableColumn<Flighter, String> salaryCol = new TableColumn<>("Salary");
        salaryCol.setCellValueFactory(cell ->
            new SimpleStringProperty(String.format("$%.2f", cell.getValue().getSalary()))
        );
        salaryCol.setPrefWidth(100);

        TableColumn<Flighter, String> hoursCol = new TableColumn<>("Hours");
        hoursCol.setCellValueFactory(new PropertyValueFactory<>("hours"));
        hoursCol.setPrefWidth(80);

        TableColumn<Flighter, String> rokhasCol = new TableColumn<>("Licenses");
        rokhasCol.setCellValueFactory(cell ->
            new SimpleStringProperty(java.lang.String.join(", ", cell.getValue().getRokhasList()))
        );
        rokhasCol.setPrefWidth(200);

        TableColumn<Flighter, Void> actionCol = new TableColumn<>("Actions");
        actionCol.setPrefWidth(100);
        actionCol.setCellFactory(col -> new javafx.scene.control.TableCell<>() {
            {
                Button deleteBtn = new Button("\u2716");
                deleteBtn.setStyle(
                    "-fx-background-color: " + Colors.CHAT_LIGHT + "; -fx-text-fill: " + Colors.CHAT_SOLID + "; " +
                    "-fx-font-size: 12; -fx-background-radius: 8; -fx-padding: 4 10; -fx-cursor: hand;"
                );
                deleteBtn.setOnAction(e -> {
                    Flighter f = getTableView().getItems().get(getIndex());
                    adding.flightersList.remove(f);
                    getTableView().getItems().remove(f);
                    DataStorage.saveAll();
                });
                setGraphic(deleteBtn);
            }
        });

        table.getColumns().addAll(idCol, nameCol, idenCol, natCol, salaryCol, hoursCol, rokhasCol, actionCol);
        table.getItems().addAll(adding.flightersList);

        card.getChildren().addAll(headerRow, table);
        getChildren().add(card);
    }

    public void filterTable(String query) {
        if (query == null || query.trim().isEmpty()) {
            table.getItems().setAll(adding.flightersList);
            return;
        }
        String q = query.toLowerCase();
        table.getItems().clear();
        for (Flighter f : adding.flightersList) {
            String name = f.getName() != null ? f.getName().toLowerCase() : "";
            String identity = f.getIdentity() != null ? f.getIdentity().toLowerCase() : "";
            String nat = f.getNationality() != null ? f.getNationality().toLowerCase() : "";
            String licenses = java.lang.String.join(" ", f.getRokhasList()).toLowerCase();
            if (name.contains(q) || identity.contains(q) || nat.contains(q) || licenses.contains(q)
                || String.valueOf(f.getId()).contains(q)) {
                table.getItems().add(f);
            }
        }
    }

    private void showAddPilotDialog() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Add New Pilot");

        VBox root = new VBox(15);
        root.setPadding(new Insets(25));
        root.setStyle("-fx-background-color: " + Colors.CARD + ";");

        Label title = new Label("Register Pilot");
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

        TextField salaryField = new TextField();
        salaryField.setPromptText("Base Salary");
        salaryField.setStyle(INPUT_STYLE);
        salaryField.setMaxWidth(Double.MAX_VALUE);

        TextField hoursField = new TextField();
        hoursField.setPromptText("Flight Hours");
        hoursField.setStyle(INPUT_STYLE);
        hoursField.setMaxWidth(Double.MAX_VALUE);

        TextField licenseField = new TextField();
        licenseField.setPromptText("Licenses (comma-separated)");
        licenseField.setStyle(INPUT_STYLE);
        licenseField.setMaxWidth(Double.MAX_VALUE);

        Label errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill: " + Colors.CHAT_SOLID + "; -fx-font-size: 12;");

        Button saveBtn = new Button("Save Pilot");
        saveBtn.setStyle(
            "-fx-background-color: " + Colors.PILOT_SOLID + "; -fx-text-fill: white; " +
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
                if (nameField.getText().isEmpty() || idField.getText().isEmpty()) {
                    errorLabel.setText("Please fill at least name and identity");
                    return;
                }
                double salary = salaryField.getText().isEmpty() ? 0 : Double.parseDouble(salaryField.getText());
                double hours = hoursField.getText().isEmpty() ? 0 : Double.parseDouble(hoursField.getText());
                java.util.ArrayList<String> licenses = new java.util.ArrayList<>();
                if (!licenseField.getText().isEmpty()) {
                    for (String l : licenseField.getText().split(",")) {
                        licenses.add(l.trim());
                    }
                }

                Flighter pilot = new Flighter(nameField.getText(), idField.getText(), natField.getText(), salary, hours, licenses);
                adding.flightersList.add(pilot);
                table.getItems().setAll(adding.flightersList);
                DataStorage.saveAll();
                dialog.close();
            } catch (NumberFormatException ex) {
                errorLabel.setText("Please enter valid numbers for salary/hours");
            }
        });

        cancelBtn.setOnAction(e -> dialog.close());

        root.getChildren().addAll(title, nameField, idField, natField, salaryField, hoursField, licenseField, errorLabel, saveBtn, cancelBtn);
        Scene scene = new Scene(root, 420, 500);
        dialog.setScene(scene);
        dialog.show();
    }
}
