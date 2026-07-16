import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NormalEmployeesPage extends VBox {

    private static final String INPUT_STYLE =
        "-fx-background-color: " + Colors.SETTINGS_LIGHT + "; " +
        "-fx-border-color: " + Colors.SETTINGS_MID + "; -fx-border-radius: 12; " +
        "-fx-background-radius: 12; -fx-padding: 10 15; -fx-font-size: 13;";

    private TableView<normalemployee> table;

    public NormalEmployeesPage() {
        setPadding(new Insets(30));
        setSpacing(20);
        setStyle("-fx-background-color: " + Colors.BACKGROUND + ";");

        VBox card = new VBox(15);
        card.setStyle(
            "-fx-background-color: " + Colors.CARD + "; -fx-background-radius: 20; " +
            "-fx-border-color: " + Colors.SETTINGS_MID + "; -fx-border-width: 0 0 3 0; " +
            "-fx-border-radius: 20; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 3);"
        );
        card.setPadding(new Insets(25));

        HBox headerRow = new HBox(15);
        headerRow.setAlignment(Pos.CENTER_LEFT);

        StackPane iconCircle = new StackPane();
        Circle bg = new Circle(20);
        bg.setFill(Color.web(Colors.SETTINGS_LIGHT));
        Label iconLabel = new Label("\u2464");
        iconLabel.setStyle("-fx-text-fill: " + Colors.SETTINGS_SOLID + "; -fx-font-size: 14; -fx-font-weight: bold;");
        iconCircle.getChildren().addAll(bg, iconLabel);

        VBox titles = new VBox(2);
        Label title = new Label("Normal Employees");
        title.setStyle("-fx-text-fill: " + Colors.TEXT_PRIMARY + "; -fx-font-size: 20; -fx-font-weight: bold;");
        Label subtitle = new Label("All regular employees");
        subtitle.setStyle("-fx-text-fill: " + Colors.TEXT_SECONDARY + "; -fx-font-size: 12;");
        titles.getChildren().addAll(title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button addBtn = new Button("+ Add Employee");
        addBtn.setStyle(
            "-fx-background-color: " + Colors.SETTINGS_SOLID + "; -fx-text-fill: white; " +
            "-fx-font-size: 13; -fx-font-weight: bold; -fx-background-radius: 12; " +
            "-fx-padding: 10 20; -fx-cursor: hand;"
        );
        addBtn.setOnMouseEntered(e -> addBtn.setStyle(
            "-fx-background-color: " + Colors.deriveColor(Colors.SETTINGS_SOLID, 0, 1, 0.9, 1) + "; -fx-text-fill: white; " +
            "-fx-font-size: 13; -fx-font-weight: bold; -fx-background-radius: 12; " +
            "-fx-padding: 10 20; -fx-cursor: hand;"
        ));
        addBtn.setOnMouseExited(e -> addBtn.setStyle(
            "-fx-background-color: " + Colors.SETTINGS_SOLID + "; -fx-text-fill: white; " +
            "-fx-font-size: 13; -fx-font-weight: bold; -fx-background-radius: 12; " +
            "-fx-padding: 10 20; -fx-cursor: hand;"
        ));
        addBtn.setOnAction(e -> showAddEmployeeDialog());

        headerRow.getChildren().addAll(iconCircle, titles, spacer, addBtn);

        table = new TableView<>();
        table.setPrefHeight(500);
        table.setStyle("-fx-background-color: transparent; -fx-border-color: " + Colors.BORDER + ";");

        TableColumn<normalemployee, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("id"));
        idCol.setPrefWidth(60);

        TableColumn<normalemployee, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(150);

        TableColumn<normalemployee, String> idenCol = new TableColumn<>("Identity");
        idenCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("identity"));
        idenCol.setPrefWidth(120);

        TableColumn<normalemployee, String> natCol = new TableColumn<>("Nationality");
        natCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("nationality"));
        natCol.setPrefWidth(120);

        TableColumn<normalemployee, String> salaryCol = new TableColumn<>("Salary");
        salaryCol.setCellValueFactory(cell ->
            new SimpleStringProperty(String.format("$%.2f", cell.getValue().getSalary()))
        );
        salaryCol.setPrefWidth(120);

        TableColumn<normalemployee, Void> actionCol = new TableColumn<>("Actions");
        actionCol.setPrefWidth(100);
        actionCol.setCellFactory(col -> new javafx.scene.control.TableCell<>() {
            {
                Button deleteBtn = new Button("\u2716");
                deleteBtn.setStyle(
                    "-fx-background-color: " + Colors.CHAT_LIGHT + "; -fx-text-fill: " + Colors.CHAT_SOLID + "; " +
                    "-fx-font-size: 12; -fx-background-radius: 8; -fx-padding: 4 10; -fx-cursor: hand;"
                );
                deleteBtn.setOnAction(e -> {
                    normalemployee emp = getTableView().getItems().get(getIndex());
                    adding.normalEmployees.remove(emp);
                    getTableView().getItems().remove(emp);
                    DataStorage.saveAll();
                });
                setGraphic(deleteBtn);
            }
        });

        table.getColumns().addAll(idCol, nameCol, idenCol, natCol, salaryCol, actionCol);
        table.getItems().addAll(adding.normalEmployees);

        card.getChildren().addAll(headerRow, table);
        getChildren().add(card);
    }

    public void filterTable(String query) {
        if (query == null || query.trim().isEmpty()) {
            table.getItems().setAll(adding.normalEmployees);
            return;
        }
        String q = query.toLowerCase();
        table.getItems().clear();
        for (normalemployee e : adding.normalEmployees) {
            String name = e.getName() != null ? e.getName().toLowerCase() : "";
            String identity = e.getIdentity() != null ? e.getIdentity().toLowerCase() : "";
            String nat = e.getNationality() != null ? e.getNationality().toLowerCase() : "";
            if (name.contains(q) || identity.contains(q) || nat.contains(q)
                || String.valueOf(e.getId()).contains(q)) {
                table.getItems().add(e);
            }
        }
    }

    private void showAddEmployeeDialog() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Add New Employee");

        VBox root = new VBox(15);
        root.setPadding(new Insets(25));
        root.setStyle("-fx-background-color: " + Colors.CARD + ";");

        Label dlgTitle = new Label("Register Employee");
        dlgTitle.setStyle("-fx-text-fill: " + Colors.TEXT_PRIMARY + "; -fx-font-size: 18; -fx-font-weight: bold;");

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

        Label errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill: " + Colors.CHAT_SOLID + "; -fx-font-size: 12;");

        Button saveBtn = new Button("Save Employee");
        saveBtn.setStyle(
            "-fx-background-color: " + Colors.SETTINGS_SOLID + "; -fx-text-fill: white; " +
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
                normalemployee emp = new normalemployee(nameField.getText(), idField.getText(), natField.getText(), salary);
                adding.normalEmployees.add(emp);
                table.getItems().setAll(adding.normalEmployees);
                DataStorage.saveAll();
                dialog.close();
            } catch (NumberFormatException ex) {
                errorLabel.setText("Please enter a valid number for salary");
            }
        });

        cancelBtn.setOnAction(e -> dialog.close());

        root.getChildren().addAll(dlgTitle, nameField, idField, natField, salaryField, errorLabel, saveBtn, cancelBtn);
        Scene scene = new Scene(root, 420, 400);
        dialog.setScene(scene);
        dialog.show();
    }
}
