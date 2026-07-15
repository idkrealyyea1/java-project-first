import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ChatPage extends HBox {

    private static final String INPUT_STYLE =
        "-fx-background-color: " + Colors.CARD + "; " +
        "-fx-border-color: " + Colors.BORDER + "; -fx-border-radius: 12; " +
        "-fx-background-radius: 12; -fx-padding: 10 15; -fx-font-size: 13;";

    private chatclient client;
    private TextArea messageArea;

    public ChatPage() {
        setPadding(new Insets(0));
        setStyle("-fx-background-color: " + Colors.BACKGROUND + ";");
        setSpacing(0);

        VBox sidebar = createUsersSidebar();
        sidebar.setPrefWidth(220);

        VBox chatArea = createChatArea();
        HBox.setHgrow(chatArea, Priority.ALWAYS);

        getChildren().addAll(sidebar, chatArea);
    }

    private VBox createUsersSidebar() {
        VBox sidebar = new VBox(5);
        sidebar.setStyle(
            "-fx-background-color: " + Colors.CARD + "; " +
            "-fx-border-color: " + Colors.CHAT_MID + "; -fx-border-width: 0 2 0 0;"
        );
        sidebar.setPadding(new Insets(20));

        HBox headerRow = new HBox(8);
        headerRow.setAlignment(Pos.CENTER_LEFT);
        Circle headerDot = new Circle(6);
        headerDot.setFill(Color.web(Colors.CHAT_SOLID));
        Label header = new Label("Online Users");
        header.setStyle("-fx-text-fill: " + Colors.TEXT_PRIMARY + "; -fx-font-size: 14; -fx-font-weight: bold;");
        headerRow.getChildren().addAll(headerDot, header);

        Label onlineDot = new Label("  \u25CF  Connected to server");
        onlineDot.setStyle("-fx-text-fill: " + Colors.GREEN + "; -fx-font-size: 11;");

        VBox users = new VBox(8);
        users.setPadding(new Insets(15, 0, 0, 0));

        String[][] onlineUsers = {
            {"A", "Administrator", Colors.ACCENT}
        };

        for (String[] u : onlineUsers) {
            HBox userRow = new HBox(10);
            userRow.setAlignment(Pos.CENTER_LEFT);
            userRow.setPadding(new Insets(8, 10, 8, 10));
            userRow.setStyle("-fx-background-color: " + Colors.PASSENGER_LIGHT + "; -fx-background-radius: 10;");

            StackPane avatar = new StackPane();
            Circle circle = new Circle(14);
            circle.setFill(Color.web(u[2]));
            Label letter = new Label(u[0]);
            letter.setStyle("-fx-text-fill: " + Colors.SIDEBAR + "; -fx-font-size: 11; -fx-font-weight: bold;");
            avatar.getChildren().addAll(circle, letter);

            Label name = new Label(u[1]);
            name.setStyle("-fx-text-fill: " + Colors.TEXT_PRIMARY + "; -fx-font-size: 12;");

            userRow.getChildren().addAll(avatar, name);
            users.getChildren().add(userRow);
        }

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Label info = new Label("Server: localhost:5555");
        info.setStyle("-fx-text-fill: " + Colors.TEXT_SECONDARY + "; -fx-font-size: 10;");

        sidebar.getChildren().addAll(headerRow, onlineDot, users, spacer, info);
        return sidebar;
    }

    private VBox createChatArea() {
        VBox chat = new VBox();
        chat.setStyle("-fx-background-color: " + Colors.BACKGROUND + ";");

        HBox topBar = new HBox(15);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(15, 25, 15, 25));
        topBar.setStyle("-fx-background-color: " + Colors.CARD + "; -fx-border-color: " + Colors.CHAT_MID + "; -fx-border-width: 0 0 2 0;");

        StackPane chatIcon = new StackPane();
        Circle chatDot = new Circle(6);
        chatDot.setFill(Color.web(Colors.CHAT_SOLID));
        chatIcon.getChildren().add(chatDot);

        Label chatTitle = new Label("Airport Chat (LAN)");
        chatTitle.setStyle("-fx-text-fill: " + Colors.TEXT_PRIMARY + "; -fx-font-size: 16; -fx-font-weight: bold;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label status = new Label("\u25CF  Connected");
        status.setStyle("-fx-text-fill: " + Colors.GREEN + "; -fx-font-size: 12; -fx-font-weight: bold;");

        topBar.getChildren().addAll(chatIcon, chatTitle, spacer, status);

        messageArea = new TextArea();
        messageArea.setEditable(false);
        messageArea.setWrapText(true);
        messageArea.setStyle(
            "-fx-background-color: " + Colors.CARD + "; " +
            "-fx-border-color: transparent; " +
            "-fx-font-size: 13; " +
            "-fx-text-fill: " + Colors.TEXT_PRIMARY + "; " +
            "-fx-padding: 15;"
        );
        VBox.setVgrow(messageArea, Priority.ALWAYS);

        HBox inputArea = new HBox(10);
        inputArea.setAlignment(Pos.CENTER_LEFT);
        inputArea.setPadding(new Insets(15, 25, 20, 25));
        inputArea.setStyle("-fx-background-color: " + Colors.CARD + ";");

        TextField nicknameField = new TextField();
        nicknameField.setPromptText("Nickname");
        nicknameField.setPrefWidth(120);
        nicknameField.setStyle(INPUT_STYLE);

        TextField messageField = new TextField();
        messageField.setPromptText("Type a message...");
        messageField.setStyle(INPUT_STYLE);
        HBox.setHgrow(messageField, Priority.ALWAYS);

        Button connectBtn = new Button("Connect");
        connectBtn.setStyle(
            "-fx-background-color: " + Colors.GREEN + "; -fx-text-fill: white; " +
            "-fx-font-size: 12; -fx-font-weight: bold; -fx-background-radius: 12; " +
            "-fx-padding: 10 18; -fx-cursor: hand;"
        );

        Button sendBtn = new Button("Send  \u2192");
        sendBtn.setStyle(
            "-fx-background-color: " + Colors.CHAT_SOLID + "; -fx-text-fill: white; " +
            "-fx-font-size: 12; -fx-font-weight: bold; -fx-background-radius: 12; " +
            "-fx-padding: 10 18; -fx-cursor: hand;"
        );
        sendBtn.setDisable(true);

        connectBtn.setOnAction(e -> {
            String nick = nicknameField.getText().trim();
            if (nick.isEmpty()) nick = "Admin";
            client = new chatclient("localhost", 5555, nick, messageArea);
            sendBtn.setDisable(false);
            connectBtn.setDisable(true);
            connectBtn.setText("\u2713 Connected");
            connectBtn.setStyle(
                "-fx-background-color: " + Colors.TEXT_SECONDARY + "; -fx-text-fill: white; " +
                "-fx-font-size: 12; -fx-font-weight: bold; -fx-background-radius: 12; " +
                "-fx-padding: 10 18;"
            );
        });

        sendBtn.setOnAction(e -> {
            String msg = messageField.getText().trim();
            if (!msg.isEmpty() && client != null) {
                client.sendMessage(msg);
                messageField.clear();
            }
        });

        messageField.setOnAction(e -> {
            String msg = messageField.getText().trim();
            if (!msg.isEmpty() && client != null) {
                client.sendMessage(msg);
                messageField.clear();
            }
        });

        inputArea.getChildren().addAll(nicknameField, messageField, connectBtn, sendBtn);
        chat.getChildren().addAll(topBar, messageArea, inputArea);
        return chat;
    }
}
