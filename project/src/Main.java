import javafx.application.Application;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private BorderPane root;
    private StackPane centerStack;
    private TopBar topBar;
    private String currentPage = "";
    private StackPane baseRoot;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        baseRoot = new StackPane();
        baseRoot.setStyle("-fx-background-color: " + Colors.BACKGROUND + ";");

        root = new BorderPane();
        root.setStyle("-fx-background-color: " + Colors.BACKGROUND + ";");

        Sidebar sidebar = new Sidebar(this);
        root.setLeft(sidebar);

        topBar = new TopBar();
        topBar.setSearchCallback(query -> filterSearch(query));
        root.setTop(topBar);

        centerStack = new StackPane();
        centerStack.setStyle("-fx-background-color: " + Colors.BACKGROUND + ";");
        centerStack.setPadding(new Insets(0));
        root.setCenter(centerStack);

        navigateTo("dashboard");

        DataStorage.loadPassengers();

        baseRoot.getChildren().add(root);

        primaryStage.setOnCloseRequest(e -> {
            DataStorage.writeFlightsLog();
        });

        WelcomePage[] welcomeHolder = new WelcomePage[1];
        WelcomePage welcome = new WelcomePage(() -> {
            baseRoot.getChildren().remove(welcomeHolder[0]);
        });
        welcomeHolder[0] = welcome;
        baseRoot.getChildren().add(welcome);

        Scene scene = new Scene(baseRoot, 1600, 900);
        primaryStage.setTitle("Airport Management System");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public void navigateTo(String page) {
        centerStack.getChildren().clear();
        currentPage = page;

        try {
            switch (page) {
                case "dashboard":
                    centerStack.getChildren().add(new DashboardPage(this));
                    topBar.setTitle("Dashboard");
                    break;
                case "flights":
                    centerStack.getChildren().add(new FlightsPage());
                    topBar.setTitle("Flights");
                    break;
                case "passengers":
                    centerStack.getChildren().add(new PassengersPage());
                    topBar.setTitle("Passengers");
                    break;
                case "pilots":
                    centerStack.getChildren().add(new PilotsPage());
                    topBar.setTitle("Pilots");
                    break;
                case "copilots":
                    centerStack.getChildren().add(new CoPilotsPage());
                    topBar.setTitle("Co-Pilots");
                    break;
                case "reports":
                    centerStack.getChildren().add(new ReportsPage());
                    topBar.setTitle("Reports");
                    break;
                case "chat":
                    centerStack.getChildren().add(new ChatPage());
                    topBar.setTitle("Chat");
                    break;
                case "normalemployees":
                    centerStack.getChildren().add(new NormalEmployeesPage());
                    topBar.setTitle("Normal Employees");
                    break;
                case "settings":
                    centerStack.getChildren().add(new SettingsPage());
                    topBar.setTitle("Settings");
                    break;
                default:
                    centerStack.getChildren().add(new DashboardPage(this));
                    topBar.setTitle("Dashboard");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            VBox errorCard = new VBox(15);
            errorCard.setAlignment(Pos.CENTER);
            errorCard.setPadding(new Insets(60));
            errorCard.setStyle(
                "-fx-background-color: " + Colors.CARD + "; " +
                "-fx-background-radius: 20; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.06), 12, 0, 0, 4);"
            );
            Label errIcon = new Label("\u26A0");
            errIcon.setStyle("-fx-font-size: 48; -fx-text-fill: " + Colors.RED + ";");
            Label errTitle = new Label("Something went wrong");
            errTitle.setStyle("-fx-text-fill: " + Colors.TEXT_PRIMARY + "; -fx-font-size: 18; -fx-font-weight: bold;");
            Label errMsg = new Label(e.getMessage() != null ? e.getMessage() : "Unknown error");
            errMsg.setStyle("-fx-text-fill: " + Colors.TEXT_SECONDARY + "; -fx-font-size: 12;");
            errorCard.getChildren().addAll(errIcon, errTitle, errMsg);
            centerStack.getChildren().add(errorCard);
            topBar.setTitle("Error");
        }

        FadeTransition fade = new FadeTransition(Duration.millis(250), centerStack);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);
        fade.play();
    }

    private void filterSearch(String query) {
        if (centerStack.getChildren().isEmpty()) return;
        javafx.scene.Node node = centerStack.getChildren().get(0);
        if (node instanceof FlightsPage) {
            ((FlightsPage) node).filterTable(query);
        } else if (node instanceof PassengersPage) {
            ((PassengersPage) node).filterTable(query);
        } else if (node instanceof PilotsPage) {
            ((PilotsPage) node).filterTable(query);
        } else if (node instanceof CoPilotsPage) {
            ((CoPilotsPage) node).filterTable(query);
        } else if (node instanceof NormalEmployeesPage) {
            ((NormalEmployeesPage) node).filterTable(query);
        }
    }
}
