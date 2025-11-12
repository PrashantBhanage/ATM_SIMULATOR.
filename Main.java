public class Main {
    public static void main(String[] args) {
        System.out.println("Starting ATM GUI...");

        // Quick DB check (safe - doesn't abort launch)
        try {
            DBConnection.testConnection();
        } catch (Exception e) {
            System.err.println("DB check failed (continuing to launch GUI): " + e.getMessage());
        }

        // Launch the JavaFX application class directly (no dependency on ATM.launch)
        javafx.application.Application.launch(ATM.class, args);
    }
}
