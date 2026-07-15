import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

public class ReportsPage extends VBox {

    private TextArea reportArea;

    public ReportsPage() {
        setPadding(new Insets(30));
        setSpacing(20);
        setStyle("-fx-background-color: " + Colors.BACKGROUND + ";");

        HBox headerRow = new HBox(15);
        headerRow.setAlignment(Pos.CENTER_LEFT);

        StackPane iconCircle = new StackPane();
        Circle bg = new Circle(20);
        bg.setFill(Color.web(Colors.REPORT_LIGHT));
        Label iconLabel = new Label("\u2463");
        iconLabel.setStyle("-fx-text-fill: " + Colors.REPORT_SOLID + "; -fx-font-size: 14; -fx-font-weight: bold;");
        iconCircle.getChildren().addAll(bg, iconLabel);

        VBox titles = new VBox(2);
        Label title = new Label("Reports Center");
        title.setStyle("-fx-text-fill: " + Colors.TEXT_PRIMARY + "; -fx-font-size: 20; -fx-font-weight: bold;");
        Label subtitle = new Label("Generate and view detailed reports");
        subtitle.setStyle("-fx-text-fill: " + Colors.TEXT_SECONDARY + "; -fx-font-size: 12;");
        titles.getChildren().addAll(title, subtitle);

        headerRow.getChildren().addAll(iconCircle, titles);

        HBox buttons = new HBox(12);
        buttons.setAlignment(Pos.CENTER_LEFT);
        buttons.getChildren().addAll(
            createReportButton("Flight Report", Colors.FLIGHT_SOLID, Colors.FLIGHT_LIGHT, this::generateFlightReport),
            createReportButton("Passenger Report", Colors.PASSENGER_SOLID, Colors.PASSENGER_LIGHT, this::generatePassengerReport),
            createReportButton("Pilot Report", Colors.PILOT_SOLID, Colors.PILOT_LIGHT, this::generatePilotReport),
            createReportButton("Co-Pilot Report", Colors.COPILOT_SOLID, Colors.COPILOT_LIGHT, this::generateCoPilotReport),
            createReportButton("Employee Report", Colors.SETTINGS_SOLID, Colors.SETTINGS_LIGHT, this::generateEmployeeReport),
            createReportButton("Full Summary", Colors.REPORT_SOLID, Colors.REPORT_LIGHT, this::generateFullSummary)
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button saveBtn = new Button("\u2B07  Save as .txt");
        saveBtn.setStyle(
            "-fx-background-color: " + Colors.REPORT_SOLID + "; -fx-text-fill: white; " +
            "-fx-font-size: 12; -fx-font-weight: bold; " +
            "-fx-background-radius: 12; -fx-padding: 10 18; -fx-cursor: hand;"
        );
        saveBtn.setOnMouseEntered(e -> saveBtn.setStyle(
            "-fx-background-color: " + Color.web(Colors.REPORT_SOLID).deriveColor(0, 1, 0.9, 1) + "; -fx-text-fill: white; " +
            "-fx-font-size: 12; -fx-font-weight: bold; " +
            "-fx-background-radius: 12; -fx-padding: 10 18; -fx-cursor: hand;"
        ));
        saveBtn.setOnMouseExited(e -> saveBtn.setStyle(
            "-fx-background-color: " + Colors.REPORT_SOLID + "; -fx-text-fill: white; " +
            "-fx-font-size: 12; -fx-font-weight: bold; " +
            "-fx-background-radius: 12; -fx-padding: 10 18; -fx-cursor: hand;"
        ));
        saveBtn.setOnAction(e -> saveReport());

        buttons.getChildren().addAll(spacer, saveBtn);

        reportArea = new TextArea();
        reportArea.setEditable(false);
        reportArea.setPrefHeight(500);
        reportArea.setStyle(
            "-fx-background-color: " + Colors.CARD + "; " +
            "-fx-border-color: " + Colors.REPORT_MID + "; " +
            "-fx-border-radius: 15; -fx-background-radius: 15; " +
            "-fx-border-width: 0 0 2 0; " +
            "-fx-font-family: 'Courier New'; -fx-font-size: 13; " +
            "-fx-text-fill: " + Colors.TEXT_PRIMARY + "; " +
            "-fx-padding: 20;"
        );
        reportArea.setPromptText("Click a report button above to generate a report...");

        VBox.setVgrow(reportArea, Priority.ALWAYS);

        getChildren().addAll(headerRow, buttons, reportArea);
    }

    private Button createReportButton(String text, String solid, String lightBg, Runnable action) {
        Button btn = new Button(text);
        btn.setStyle(
            "-fx-background-color: " + lightBg + "; " +
            "-fx-text-fill: " + solid + "; " +
            "-fx-font-size: 12; -fx-font-weight: bold; " +
            "-fx-background-radius: 12; -fx-padding: 10 18; -fx-cursor: hand;"
        );
        btn.setOnMouseEntered(e -> btn.setStyle(
            "-fx-background-color: " + Color.web(solid).deriveColor(0, 1, 1, 0.2) + "; " +
            "-fx-text-fill: " + solid + "; " +
            "-fx-font-size: 12; -fx-font-weight: bold; " +
            "-fx-background-radius: 12; -fx-padding: 10 18; -fx-cursor: hand;"
        ));
        btn.setOnMouseExited(e -> btn.setStyle(
            "-fx-background-color: " + lightBg + "; " +
            "-fx-text-fill: " + solid + "; " +
            "-fx-font-size: 12; -fx-font-weight: bold; " +
            "-fx-background-radius: 12; -fx-padding: 10 18; -fx-cursor: hand;"
        ));
        btn.setOnAction(e -> action.run());
        return btn;
    }

    private void generateFlightReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("         FLIGHT REPORT\n");
        sb.append("         Generated: ").append(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
        sb.append("========================================\n\n");
        sb.append("Total Flights: ").append(adding.flightslists.size()).append("\n\n");

        for (Flight f : adding.flightslists) {
            sb.append("--- Flight #").append(f.getId()).append(" ---\n");
            sb.append("  Destination:    ").append(f.getDestination()).append("\n");
            sb.append("  Captain:        ").append(f.getNameofCaptin() != null ? f.getNameofCaptin().getName() : "N/A").append("\n");
            sb.append("  Seats:          ").append(f.getNumberofchairs()).append("\n");
            sb.append("  Passengers:     ").append(f.getnumofpassengeres()).append("\n");
            sb.append("  Co-Pilots:      ").append(f.getCopiloList() != null ? f.getCopiloList().size() : 0).append("\n");
            sb.append("  Status:         ").append(f.getFlightStatus()).append("\n");
            if (f.getCopiloList() != null && !f.getCopiloList().isEmpty()) {
                sb.append("  Co-Pilot Names: ");
                for (int i = 0; i < f.getCopiloList().size(); i++) {
                    if (i > 0) sb.append(", ");
                    sb.append(f.getCopiloList().get(i).getName());
                }
                sb.append("\n");
            }
            sb.append("\n");
        }

        reportArea.setText(sb.toString());
    }

    private void generatePassengerReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("       PASSENGER REPORT\n");
        sb.append("       Generated: ").append(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
        sb.append("========================================\n\n");

        int total = 0;
        for (Flight f : adding.flightslists) {
            if (f.getPassengersList() != null) total += f.getPassengersList().size();
        }
        sb.append("Total Passengers: ").append(total).append("\n\n");

        int pNum = 1;
        for (Flight f : adding.flightslists) {
            if (f.getPassengersList() != null) {
                for (Passengere p : f.getPassengersList()) {
                    sb.append(pNum++).append(". ").append(p.getName()).append("\n");
                    sb.append("   Identity:    ").append(p.getIdentity()).append("\n");
                    sb.append("   Nationality: ").append(p.getNationality()).append("\n");
                    sb.append("   Passport:    ").append(p.getPassport()).append("\n");
                    sb.append("   Pass End:    ").append(p.getPassEndDate()).append("\n");
                    sb.append("   Destination: ").append(p.getDistination()).append("\n");
                    sb.append("   Flight:      #").append(f.getId()).append(" -> ").append(f.getDestination()).append("\n");
                    sb.append("\n");
                }
            }
        }

        reportArea.setText(sb.toString());
    }

    private void generatePilotReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("          PILOT REPORT\n");
        sb.append("          Generated: ").append(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
        sb.append("========================================\n\n");
        sb.append("Total Pilots: ").append(adding.flightersList.size()).append("\n\n");

        for (int i = 0; i < adding.flightersList.size(); i++) {
            Flighter p = adding.flightersList.get(i);
            sb.append(i + 1).append(". ").append(p.getName()).append("\n");
            sb.append("   ID:          ").append(p.getId()).append("\n");
            sb.append("   Identity:    ").append(p.getIdentity()).append("\n");
            sb.append("   Nationality: ").append(p.getNationality()).append("\n");
            sb.append("   Salary:      $").append(String.format("%.2f", p.getSalary())).append("\n");
            sb.append("   Hours:       ").append(p.getHours()).append("\n");
            sb.append("   Licenses:    ").append(java.lang.String.join(", ", p.getRokhasList())).append("\n");
            sb.append("\n");
        }

        reportArea.setText(sb.toString());
    }

    private void generateEmployeeReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("       EMPLOYEE REPORT\n");
        sb.append("       Generated: ").append(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
        sb.append("========================================\n\n");
        sb.append("Total Employees: ").append(adding.normalEmployees.size()).append("\n\n");

        for (int i = 0; i < adding.normalEmployees.size(); i++) {
            normalemployee e = adding.normalEmployees.get(i);
            sb.append(i + 1).append(". ").append(e.getName()).append("\n");
            sb.append("   ID:          ").append(e.getId()).append("\n");
            sb.append("   Identity:    ").append(e.getIdentity()).append("\n");
            sb.append("   Nationality: ").append(e.getNationality()).append("\n");
            sb.append("   Salary:      $").append(String.format("%.2f", e.getSalary())).append("\n");
            sb.append("\n");
        }

        reportArea.setText(sb.toString());
    }

    private void generateCoPilotReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("        CO-PILOT REPORT\n");
        sb.append("        Generated: ").append(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
        sb.append("========================================\n\n");
        sb.append("Total Co-Pilots: ").append(adding.copilots.size()).append("\n\n");

        for (int i = 0; i < adding.copilots.size(); i++) {
            modiefoun c = adding.copilots.get(i);
            sb.append(i + 1).append(". ").append(c.getName()).append("\n");
            sb.append("   ID:          ").append(c.getId()).append("\n");
            sb.append("   Identity:    ").append(c.getIdentity()).append("\n");
            sb.append("   Nationality: ").append(c.getNationality()).append("\n");
            sb.append("   Salary:      $").append(String.format("%.2f", c.getSalary())).append("\n");
            sb.append("   Hours:       ").append(c.getHours()).append("\n");
            sb.append("   Languages:   ").append(java.lang.String.join(", ", c.getLangsList())).append("\n");
            sb.append("\n");
        }

        reportArea.setText(sb.toString());
    }

    private void generateFullSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("        FULL SYSTEM SUMMARY\n");
        sb.append("        Generated: ").append(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
        sb.append("========================================\n\n");

        sb.append("FLIGHTS:     ").append(adding.flightslists.size()).append("\n");
        sb.append("PILOTS:      ").append(adding.flightersList.size()).append("\n");
        sb.append("CO-PILOTS:   ").append(adding.copilots.size()).append("\n");
        sb.append("EMPLOYEES:   ").append(adding.normalEmployees.size()).append("\n");

        int totalPass = 0;
        for (Flight f : adding.flightslists) {
            if (f.getPassengersList() != null) totalPass += f.getPassengersList().size();
        }
        sb.append("PASSENGERS:  ").append(totalPass).append("\n\n");

        sb.append("--- FLIGHTS ---\n");
        for (Flight f : adding.flightslists) {
            sb.append("  #").append(f.getId()).append(" to ").append(f.getDestination())
              .append(" | Captain: ").append(f.getNameofCaptin() != null ? f.getNameofCaptin().getName() : "N/A")
              .append(" | ").append(f.getnumofpassengeres()).append("/").append(f.getNumberofchairs()).append(" seats")
              .append(" | ").append(f.getFlightStatus()).append("\n");
        }

        sb.append("\n--- PILOTS ---\n");
        for (Flighter p : adding.flightersList) {
            sb.append("  ").append(p.getName()).append(" | Hours: ").append(p.getHours()).append("\n");
        }

        sb.append("\n--- CO-PILOTS ---\n");
        for (modiefoun c : adding.copilots) {
            sb.append("  ").append(c.getName()).append(" | Languages: ").append(java.lang.String.join(", ", c.getLangsList())).append("\n");
        }

        sb.append("\n--- EMPLOYEES ---\n");
        for (normalemployee e : adding.normalEmployees) {
            sb.append("  ").append(e.getName()).append(" | Salary: $").append(String.format("%.2f", e.getSalary())).append("\n");
        }

        reportArea.setText(sb.toString());
    }

    private void saveReport() {
        String content = reportArea.getText();
        if (content == null || content.trim().isEmpty()) {
            Label empty = new Label("Nothing to save. Generate a report first.");
            empty.setStyle("-fx-text-fill: " + Colors.CHAT_SOLID + "; -fx-font-size: 13;");
            javafx.stage.Stage alert = new javafx.stage.Stage();
            alert.setTitle("No Report");
            VBox box = new VBox(10, empty);
            box.setPadding(new Insets(20));
            box.setStyle("-fx-background-color: " + Colors.CARD + ";");
            Button ok = new Button("OK");
            ok.setStyle("-fx-background-color: " + Colors.REPORT_SOLID + "; -fx-text-fill: white; -fx-background-radius: 10; -fx-padding: 6 20; -fx-cursor: hand;");
            ok.setOnAction(e -> alert.close());
            box.getChildren().add(ok);
            alert.setScene(new javafx.scene.Scene(box, 300, 120));
            alert.show();
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Report");
        fileChooser.setInitialFileName("report_" + java.time.LocalDate.now() + ".txt");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        java.io.File file = fileChooser.showSaveDialog(getScene().getWindow());
        if (file != null) {
            try {
                java.nio.file.Files.writeString(file.toPath(), content);
                Label saved = new Label("Report saved to: " + file.getName());
                saved.setStyle("-fx-text-fill: " + Colors.GREEN + "; -fx-font-size: 13;");
                javafx.stage.Stage alert = new javafx.stage.Stage();
                alert.setTitle("Saved");
                VBox box = new VBox(10, saved);
                box.setPadding(new Insets(20));
                box.setStyle("-fx-background-color: " + Colors.CARD + ";");
                Button ok = new Button("OK");
                ok.setStyle("-fx-background-color: " + Colors.REPORT_SOLID + "; -fx-text-fill: white; -fx-background-radius: 10; -fx-padding: 6 20; -fx-cursor: hand;");
                ok.setOnAction(e -> alert.close());
                box.getChildren().add(ok);
                alert.setScene(new javafx.scene.Scene(box, 350, 120));
                alert.show();
            } catch (Exception ex) {
                Label err = new Label("Error saving file: " + ex.getMessage());
                err.setStyle("-fx-text-fill: " + Colors.CHAT_SOLID + "; -fx-font-size: 13;");
                javafx.stage.Stage alert = new javafx.stage.Stage();
                alert.setTitle("Error");
                VBox box = new VBox(10, err);
                box.setPadding(new Insets(20));
                box.setStyle("-fx-background-color: " + Colors.CARD + ";");
                Button ok = new Button("OK");
                ok.setStyle("-fx-background-color: " + Colors.CHAT_SOLID + "; -fx-text-fill: white; -fx-background-radius: 10; -fx-padding: 6 20; -fx-cursor: hand;");
                ok.setOnAction(e -> alert.close());
                box.getChildren().add(ok);
                alert.setScene(new javafx.scene.Scene(box, 350, 120));
                alert.show();
            }
        }
    }
}
