import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 * ChatClient.java
 * متصل بالسيرفر البايثون
 * يستقبل ويرسل الرسائل
 */
public class chatclient {
    
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String nickname;
    private TextArea messageArea;
    private boolean isConnected = false;
    
    /**
     * Constructor
     * @param serverIP - IP السيرفر (localhost أو IP address)
     * @param port - البورت (5555)
     * @param nickname - اسمك في الشات
     * @param messageArea - الـ TextArea لعرض الرسائل
     */
    public chatclient(String serverIP, int port, String nickname, TextArea messageArea) {
        this.nickname = nickname;
        this.messageArea = messageArea;
        
        // ابدأ الاتصال في thread منفصل (بدون ما تجمد الواجهة)
        new Thread(() -> connectToServer(serverIP, port)).start();
    }
    
    /**
     * الاتصال بالسيرفر البايثون
     */
    private void connectToServer(String serverIP, int port) {
        try {
            // ============ إنشاء الاتصال ============
            System.out.println("[INFO] Connecting to " + serverIP + ":" + port);
            socket = new Socket(serverIP, port);
            System.out.println("✅ Connected!");
            
            // ============ إعداد Input/Output ============
            // استخدم UTF-8 encoding بشكل صريح (عشان النصوص العربية)
            out = new PrintWriter(
                new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8),
                true  // auto-flush
            );
            
            in = new BufferedReader(
                new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)
            );
            
            // ============ إرسال الـ Nickname ============
            out.println(nickname);
            System.out.println("📤 Sent nickname: " + nickname);
            
            // ============ تعديل الـ flag ============
            isConnected = true;
            
            // ============ حلقة الاستماع ============
            String incomingMessage;
            while ((incomingMessage = in.readLine()) != null) {
                // استقبل الرسالة من السيرفر
                
                String finalMessage = incomingMessage;
                
                // حدّث الـ TextArea من الـ JavaFX thread بأمان
                Platform.runLater(() -> {
                    messageArea.appendText(finalMessage + "\n");
                    // اسكرول للأسفل
                    messageArea.setScrollTop(Double.MAX_VALUE);
                });
            }
            
            // لو خرجنا من الحلقة = قطع الاتصال
            System.out.println("🔌 Disconnected from server");
            isConnected = false;
            
        } catch (IOException e) {
            isConnected = false;
            String errorMsg = "❌ Connection Error: " + e.getMessage();
            System.err.println(errorMsg);
            
            Platform.runLater(() -> {
                messageArea.appendText(errorMsg + "\n");
            });
            
        } finally {
            // ============ تنظيف ============
            try {
                if (socket != null) socket.close();
                if (in != null) in.close();
                if (out != null) out.close();
            } catch (IOException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
    
    /**
     * إرسال رسالة للسيرفر
     */
    public void sendMessage(String message) {
        if (!isConnected || out == null) {
            System.out.println("❌ Not connected to server");
            return;
        }
        
        if (message.trim().isEmpty()) {
            return;
        }
        
        // out.println() بتبعت الرسالة مع newline
        out.println(message);
        System.out.println("📤 Sent: " + message);
    }
    
    /**
     * التحقق من الاتصال
     */
    public boolean isConnected() {
        return isConnected;
    }
}