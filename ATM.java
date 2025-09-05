import java.sql.*;
import java.util.Scanner;

public class ATM{
    private Connection conn;
    private Scanner sc;

    public ATM(){
        conn = DBConnection.getConnection();
        sc = new Scanner(System.in);
    }

    public void login(){
        try{
        System.out.println("Enter Username: ");
        String username = sc.nextLine();

        System.out.println("Enter PIN: ");
        String pin = sc.nextLine();

        String query = "SELECT * FROM users WHERE username = ? AND pin = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, username);
        stmt.setString(2, pin);

        ResultSet rs = stmt.executeQuery();

        if(rs.next()){
            System.out.println("\n Login Successful! Welcome "+ username );
            mainMenu(rs.getInt("id")); //pass user is
        }else{
            System.out.println("\n Invalid username or PIN");
        }
    }catch(Exception e){
        e.printStackTrace();
    }
}

private void mainMenu(int userId){
    while(true){
        System.out.println("\n ===ATM MENU===");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transaction Histroy");
        System.out.println("5. Exit");
        System.out.println("Choose Option: ");

        int choice = sc.nextInt();

        switch(choice){
            case 1: checkBalance(userId); break;
            case 2: deposit(userId); break;
            case 3: withdraw(userId); break;
            case 4: transactionHistory(userId); break;
            case 5: System.out.println("Thank You! Exiting...");return;
            default: System.out.println("Invalid choice");
        }
    }
}

private void checkBalance(int userId){
    try{
        String query = "SELECT balance FROM users WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, userId);

        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            System.out.println("Your Balance; "+rs.getDouble("balance"));
        }
    }catch(Exception e){
        e.printStackTrace();
    }
}

private void deposit(int userId){
    try{
        System.out.println("Enter amount to deposite: ");
        double amount = sc.nextDouble();

        String update = "UPDATE users SET balance = balance + ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(update);
        stmt.setDouble(1, amount);
        stmt.setInt(2, userId);
        stmt.executeUpdate();

        saveTransaction(userId, "Deposit", amount);
        System.out.println("Deposit Successfull!");
    }catch(Exception e){
        e.printStackTrace();
    }
}

private void withdraw(int userId){
    try{
        System.out.println("Enter amount to withdraw: ");
        double amount = sc.nextDouble();

        String check = "SELECT balance FROM users WHERE id = ?";
        PreparedStatement stmt1 = conn.prepareStatement(check);
        stmt1.setInt(1, userId);
        ResultSet rs = stmt1.executeQuery();

        if(rs.next()){
            double balance = rs.getDouble("balance");
            if(balance>=amount){
                String update = "UPDATE users SET balance = balance - ? WHERE id = ?";
                PreparedStatement stmt2 = conn.prepareStatement(update);
                stmt2.setDouble(1, amount);
                stmt2.setInt(2, userId);
                stmt2.executeUpdate();

                saveTransaction(userId, "Withdraw", amount);
                System.out.println("Withdraw Successfull!");
            }else{
                System.out.println("Insufficient Balance!");
            }
        }
    }catch(Exception e){
        e.printStackTrace();
    }
}

private void transactionHistory(int userId){
    try{
        String query = "SELECT * FROM transactions WHERE user_id = ? ORDER BY timestamp DESC LIMIT 5";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, userId);
        
        ResultSet rs = stmt.executeQuery();
        System.out.println("\n Last 5 Transactions: ");
        while(rs.next()){
            System.out.println(rs.getString("timestamp")+"|"+rs.getString("type")+"|"+rs.getDouble("amount"));
        }
    }catch(Exception e){
        e.printStackTrace();
    }
}

private void saveTransaction(int userId, String type, double amount){
    try{
        String query = "INSERT INTO transactions (user_id, type, amount) VALUES (?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1,userId);
        stmt.setString(2, type);
        stmt.setDouble(3, amount);
        stmt.executeUpdate();
    }catch(Exception e){
        e.printStackTrace();
    }
}

}