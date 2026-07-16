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

public class CoPilotsPage extends VBox {

    private static final String INPUT_STYLE =
        "-fx-background-color: " + Colors.COPILOT_LIGHT + "; " +
        "-fx-border-color: " + Colors.COPILOT_MID + "; -fx-border-radius: 12; " +
        "-fx-background-radius: 12; -fx-padding: 10 15; -fx-font-size: 13;";

    private TableView<modiefoun> table;

    public CoPilotsPage() {
        setPadding(new Insets(30));
        setSpacing(20);
        setStyle("-fx-background-color: " + Colors.BACKGROUND + ";");

        VBox card = new VBox(15);
        card.setStyle(
            "-fx-background-color: " + Colors.CARD + "; -fx-background-radius: 20; " +
            "-fx-border-color: " + Colors.COPILOT_MID + "; -fx-border-width: 0 0 3 0; " +
            "-fx-border-radius: 20; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 3);"
        );
        card.setPadding(new Insets(25));

        HBox headerRow = new HBox(15);
        headerRow.setAlignment(Pos.CENTER_LEFT);

        StackPane iconCircle = new StackPane();
        Circle bg = new Circle(20);
        bg.setFill(Color.web(Colors.COPILOT_LIGHT));
        Label iconLabel = new Label("\u2462");
        iconLabel.setStyle("-fx-text-fill: " + Colors.COPILOT_SOLID + "; -fx-font-size: 14; -fx-font-weight: bold;");
        iconCircle.getChildren().addAll(bg, iconLabel);

        VBox titles = new VBox(2);
        Label title = new Label("Co-Pilot Roster");
        title.setStyle("-fx-text-fill: " + Colors.TEXT_PRIMARY + "; -fx-font-size: 20; -fx-font-weight: bold;");
        Label subtitle = new Label("All certified co-pilots");
        subtitle.setStyle("-fx-text-fill: " + Colors.TEXT_SECONDARY + "; -fx-font-size: 12;");
        titles.getChildren().addAll(title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button addBtn = new Button("+ Add Co-Pilot");
        addBtn.setStyle(
            "-fx-background-color: " + Colors.COPILOT_SOLID + "; -fx-text-fill: white; " +
            "-fx-font-size: 13; -fx-font-weight: bold; -fx-background-radius: 12; " +
            "-fx-padding: 10 20; -fx-cursor: hand;"
        );
        addBtn.setOnMouseEntered(e -> addBtn.setStyle(
            "-fx-background-color: " + Colors.deriveColor(Colors.COPILOT_SOLID, 0, 1, 0.9, 1) + "; -fx-text-fill: white; " +
            "-fx-font-size: 13; -fx-font-weight: bold; -fx-background-radius: 12; " +
            "-fx-padding: 10 20; -fx-cursor: hand;"
        ));
        addBtn.setOnMouseExited(e -> addBtn.setStyle(
            "-fx-background-color: " + Colors.COPILOT_SOLID + "; -fx-text-fill: white; " +
            "-fx-font-size: 13; -fx-font-weight: bold; -fx-background-radius: 12; " +
            "-fx-padding: 10 20; -fx-cursor: hand;"
        ));
        addBtn.setOnAction(e -> showAddCoPilotDialog());

        headerRow.getChildren().addAll(iconCircle, titles, spacer, addBtn);

        table = new TableView<>();
        table.setPrefHeight(500);
        table.setStyle("-fx-background-color: transparent; -fx-border-color: " + Colors.BORDER + ";");

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

        TableColumn<modiefoun, String> salaryCol = new TableColumn<>("Salary");
        salaryCol.setCellValueFactory(cell ->
            new SimpleStringProperty(String.format("$%.2f", cell.getValue().getSalary()))
        );
        salaryCol.setPrefWidth(100);

        TableColumn<modiefoun, String> hoursCol = new TableColumn<>("Hours");
        hoursCol.setCellValueFactory(new PropertyValueFactory<>("hours"));
        hoursCol.setPrefWidth(80);

        TableColumn<modiefoun, String> langsCol = new TableColumn<>("Languages");
        langsCol.setCellValueFactory(cell ->
            new SimpleStringProperty(java.lang.String.join(", ", cell.getValue().getLangsList()))
        );
        langsCol.setPrefWidth(200);

        TableColumn<modiefoun, Void> actionCol = new TableColumn<>("Actions");
        actionCol.setPrefWidth(100);
        actionCol.setCellFactory(col -> new javafx.scene.control.TableCell<>() {
            {
                Button deleteBtn = new Button("\u2716");
                deleteBtn.setStyle(
                    "-fx-background-color: " + Colors.CHAT_LIGHT + "; -fx-text-fill: " + Colors.CHAT_SOLID + "; " +
                    "-fx-font-size: 12; -fx-background-radius: 8; -fx-padding: 4 10; -fx-cursor: hand;"
                );
                deleteBtn.setOnAction(e -> {
                    modiefoun c = getTableView().getItems().get(getIndex());
                    adding.copilots.remove(c);
                    getTableView().getItems().remove(c);
                    DataStorage.saveAll();
                });
                setGraphic(deleteBtn);
            }
        });

        table.getColumns().addAll(idCol, nameCol, idenCol, natCol, salaryCol, hoursCol, langsCol, actionCol);
        table.getItems().addAll(adding.copilots);

        card.getChildren().addAll(headerRow, table);
        getChildren().add(card);
    }

    public void filterTable(String query) {
        if (query == null || query.trim().isEmpty()) {
            table.getItems().setAll(adding.copilots);
            return;
        }
        String q = query.toLowerCase();
        table.getItems().clear();
        for (modiefoun c : adding.copilots) {
            String name = c.getName() != null ? c.getName().toLowerCase() : "";
            String identity = c.getIdentity() != null ? c.getIdentity().toLowerCase() : "";
            String nat = c.getNationality() != null ? c.getNationality().toLowerCase() : "";
            String langs = java.lang.String.join(" ", c.getLangsList()).toLowerCase();
            if (name.contains(q) || identity.contains(q) || nat.contains(q) || langs.contains(q)
                || String.valueOf(c.getId()).contains(q)) {
                table.getItems().add(c);
            }
        }
    }

    private void showAddCoPilotDialog() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Add New Co-Pilot");

        VBox root = new VBox(15);
        root.setPadding(new Insets(25));
        root.setStyle("-fx-background-color: " + Colors.CARD + ";");

        Label title = new Label("Register Co-Pilot");
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
        salaryField.setPromptText("Salary");
        salaryField.setStyle(INPUT_STYLE);
        salaryField.setMaxWidth(Double.MAX_VALUE);

        TextField hoursField = new TextField();
        hoursField.setPromptText("Flight Hours");
        hoursField.setStyle(INPUT_STYLE);
        hoursField.setMaxWidth(Double.MAX_VALUE);

        TextField langField = new TextField();
        langField.setPromptText("Languages (comma-separated)");
        langField.setStyle(INPUT_STYLE);
        langField.setMaxWidth(Double.MAX_VALUE);

        Label errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill: " + Colors.CHAT_SOLID + "; -fx-font-size: 12;");

        Button saveBtn = new Button("Save Co-Pilot");
        saveBtn.setStyle(
            "-fx-background-color: " + Colors.COPILOT_SOLID + "; -fx-text-fill: white; " +
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
                java.util.ArrayList<String> languages = new java.util.ArrayList<>();
                if (!langField.getText().isEmpty()) {
                    for (String l : langField.getText().split(",")) {
                        languages.add(l.trim());
                    }
                }

                modiefoun copilot = new modiefoun(nameField.getText(), idField.getText(), natField.getText(), salary, languages, hours);
                adding.copilots.add(copilot);
                table.getItems().setAll(adding.copilots);
                DataStorage.saveAll();
                dialog.close();
            } catch (NumberFormatException ex) {
                errorLabel.setText("Please enter valid numbers for salary/hours");
            }
        });

        cancelBtn.setOnAction(e -> dialog.close());

        root.getChildren().addAll(title, nameField, idField, natField, salaryField, hoursField, langField, errorLabel, saveBtn, cancelBtn);
        Scene scene = new Scene(root, 420, 500);
        dialog.setScene(scene);
        dialog.show();
    }
}
