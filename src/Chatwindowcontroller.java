
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * ChatWindowController.java
 * نافذة الشات مع الواجهة الرسومية
 */
public class Chatwindowcontroller {
    
    private TextArea messageArea;
    private TextField inputField;
    private Button sendButton;
    private Stage chatStage;
    
    private chatclient chatClient;
    private String nickname;
    
    /**
     * فتح نافذة الشات
     */
    public void openChatWindow(String serverIP, int port, String nickname) {
        this.nickname = nickname;
        
        // ============ إنشاء النافذة ============
        chatStage = new Stage();
        chatStage.setTitle("✈️ Airport Chat - " + nickname);
        chatStage.setWidth(500);
        chatStage.setHeight(600);
        
        // ============ عرض الرسائل (TextArea) ============
        messageArea = new TextArea();
        messageArea.setEditable(false);
        messageArea.setWrapText(true);
        messageArea.setStyle("-fx-font-size: 13; -fx-padding: 5;");
        
        // ============ حقل الإدخال (TextField) ============
        inputField = new TextField();
        inputField.setPromptText("اكتب رسالتك هنا...");
        inputField.setStyle("-fx-font-size: 13; -fx-padding: 5;");
        
        // لما تضغط Enter، ابعت الرسالة
        inputField.setOnAction(event -> sendMessage());
        
        // ============ زر الإرسال (Button) ============
        sendButton = new Button("📤 Send");
        sendButton.setStyle(
            "-fx-font-size: 12; " +
            "-fx-padding: 8 20; " +
            "-fx-background-color: #3498db; " +
            "-fx-text-fill: white; " +
            "-fx-border-radius: 5; " +
            "-fx-cursor: hand;"
        );
        sendButton.setOnAction(event -> sendMessage());
        
        // ============ ترتيب العناصر (Layout) ============
        HBox inputBox = new HBox(10, inputField, sendButton);
        inputBox.setPadding(new Insets(10, 10, 10, 10));
        inputBox.setStyle("-fx-background-color: #ecf0f1;");
        
        VBox root = new VBox(10, messageArea, inputBox);
        root.setPadding(new Insets(10));
        
        // ============ إنشاء الـ Scene ============
        Scene scene = new Scene(root);
        chatStage.setScene(scene);
        
        // ============ الاتصال بالسيرفر ============
        chatClient = new chatclient(serverIP, port, nickname, messageArea);
        
        // رسالة ترحيب
        messageArea.appendText("🔗 Connecting to " + serverIP + ":" + port + "...\n");
        
        messageArea.appendText("💬 Welcome, " + nickname + "!\n");
        messageArea.appendText("────────────────────────────────\n\n");
        
        // ============ عرض النافذة ============
        chatStage.show();
    }
    
    /**
     * إرسال الرسالة
     */
    private void sendMessage() {
        String message = inputField.getText();
        
        if (message.trim().isEmpty()) {
            return;
        }
        
        if (chatClient != null && chatClient.isConnected()) {
            chatClient.sendMessage(message);
            inputField.clear();
            inputField.requestFocus();
        } else {
            messageArea.appendText("❌ Not connected to server\n");
        }
    }
}