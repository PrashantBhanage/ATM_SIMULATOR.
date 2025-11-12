import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.sql.*;

/**
 * ATM.java (GUI version)
 * Simple JavaFX-based ATM interface connected to MySQL.
 *
 * Dependencies:
 * - DBConnection.java (for getConnection())
 * - MySQL DB with users & transactions tables.
 */
public class ATM extends Application {
    private Stage stage;
    private Connection conn;
    private int userId = -1;
    private String username = "";

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        conn = DBConnection.getConnection();

        stage.setTitle("ATM Machine");
        stage.setScene(loginScene());
        stage.setResizable(false);
        stage.show();
    }

    // ---------------------- LOGIN SCREEN ----------------------
    private Scene loginScene() {
        Label title = new Label("🏦 ATM Login");
        TextField userField = new TextField();
        userField.setPromptText("Username");
        PasswordField pinField = new PasswordField();
        pinField.setPromptText("PIN");

        Button loginBtn = new Button("Login");
        Label msg = new Label();

        loginBtn.setOnAction(e -> {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM users WHERE username=? AND pin=?")) {
                stmt.setString(1, userField.getText().trim());
                stmt.setString(2, pinField.getText().trim());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        userId = rs.getInt("id");
                        username = rs.getString("username");
                        msg.setText("");
                        stage.setScene(mainMenu());
                    } else {
                        msg.setText("❌ Invalid username or PIN");
                    }
                }
            } catch (Exception ex) {
                msg.setText("DB Error: " + ex.getMessage());
            }
        });

        VBox layout = new VBox(10, title, userField, pinField, loginBtn, msg);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(25));
        return new Scene(layout, 300, 250);
    }

    // ---------------------- MAIN MENU ----------------------
    private Scene mainMenu() {
        Label welcome = new Label("Welcome, " + username);
        Button balBtn = new Button("Check Balance");
        Button depBtn = new Button("Deposit");
        Button witBtn = new Button("Withdraw");
        Button histBtn = new Button("Transaction History");
        Button exitBtn = new Button("Logout");

        Label output = new Label();

        balBtn.setOnAction(e -> checkBalance(output));
        depBtn.setOnAction(e -> handleTransaction("Deposit"));
        witBtn.setOnAction(e -> handleTransaction("Withdraw"));
        histBtn.setOnAction(e -> showHistory());
        exitBtn.setOnAction(e -> stage.setScene(loginScene()));

        VBox box = new VBox(12, welcome, balBtn, depBtn, witBtn, histBtn, exitBtn, output);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(25));
        return new Scene(box, 350, 400);
    }

    // ---------------------- BALANCE CHECK ----------------------
    private void checkBalance(Label output) {
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT balance FROM users WHERE id=?")) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                output.setText("💰 Balance: ₹ " + rs.getDouble("balance"));
        } catch (Exception ex) {
            output.setText("Error: " + ex.getMessage());
        }
    }

    // ---------------------- DEPOSIT / WITHDRAW ----------------------
    private void handleTransaction(String type) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Enter amount to " + type.toLowerCase());
        dialog.setTitle(type);
        dialog.showAndWait().ifPresent(input -> {
            try {
                double amt = Double.parseDouble(input);
                if (amt <= 0) throw new Exception("Enter a positive amount");

                double balance = 0;
                try (PreparedStatement bal = conn.prepareStatement("SELECT balance FROM users WHERE id=?")) {
                    bal.setInt(1, userId);
                    ResultSet rs = bal.executeQuery();
                    if (rs.next()) balance = rs.getDouble("balance");
                }

                if (type.equals("Withdraw") && amt > balance) {
                    showAlert(Alert.AlertType.WARNING, "Insufficient balance!");
                    return;
                }

                String update = type.equals("Deposit")
                        ? "UPDATE users SET balance = balance + ? WHERE id=?"
                        : "UPDATE users SET balance = balance - ? WHERE id=?";
                try (PreparedStatement stmt = conn.prepareStatement(update)) {
                    stmt.setDouble(1, amt);
                    stmt.setInt(2, userId);
                    stmt.executeUpdate();
                }

                try (PreparedStatement tx = conn.prepareStatement(
                        "INSERT INTO transactions(user_id,type,amount) VALUES (?,?,?)")) {
                    tx.setInt(1, userId);
                    tx.setString(2, type);
                    tx.setDouble(3, amt);
                    tx.executeUpdate();
                }

                showAlert(Alert.AlertType.INFORMATION, type + " Successful: ₹" + amt);
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error: " + ex.getMessage());
            }
        });
    }

    // ---------------------- TRANSACTION HISTORY ----------------------
    private void showHistory() {
        StringBuilder sb = new StringBuilder();
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM transactions WHERE user_id=? ORDER BY timestamp DESC LIMIT 5")) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                sb.append(rs.getString("timestamp")).append(" | ")
                  .append(rs.getString("type")).append(" | ₹")
                  .append(rs.getDouble("amount")).append("\n");
            }
        } catch (Exception ex) {
            sb.append("Error: ").append(ex.getMessage());
        }
        Alert a = new Alert(Alert.AlertType.INFORMATION, sb.toString(), ButtonType.OK);
        a.setHeaderText("Last 5 Transactions");
        a.showAndWait();
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert a = new Alert(type, msg, ButtonType.OK);
        a.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
