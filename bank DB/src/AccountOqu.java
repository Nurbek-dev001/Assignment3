import java.sql.*;

public class AccountOqu {
    public static void createAccount(String accountType, double balance, int customerId) {
        String query = "INSERT INTO Account (AccountType, Balance, CustomerID) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, accountType);
            statement.setDouble(2, balance);
            statement.setInt(3, customerId);
            statement.executeUpdate();
            System.out.println("Account satti quryldy.");
        } catch (SQLException e) {
            System.err.println("Qate: " + e.getMessage());
        }
    }

    public static void readAccounts() {
        String query = "SELECT * FROM Account";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            System.out.println("Account tizbegi:");
            while (resultSet.next()) {
                System.out.println("AccountID: " + resultSet.getInt("AccountID") +
                        ", AccountType: " + resultSet.getString("AccountType") +
                        ", Balance: " + resultSet.getDouble("Balance") +
                        ", CustomerID: " + resultSet.getInt("CustomerID"));
            }
        } catch (SQLException e) {
            System.err.println("Qate: " + e.getMessage());
        }
    }

    public static void updateAccount(int accountId, double newBalance) {
        String query = "UPDATE Account SET Balance = ? WHERE AccountID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, newBalance);
            statement.setInt(2, accountId);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Account satti zhanartyldy.");
            } else {
                System.out.println("Account ID " + accountId + "tabylmady.");
            }
        } catch (SQLException e) {
            System.err.println("Qate: " + e.getMessage());
        }
    }

    public static void deleteAccount(int accountId) {
        String query = "DELETE FROM Account WHERE AccountID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, accountId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Account satti oshirildi.");
            } else {
                System.out.println("Account ID " + accountId + " tabylmady.");
            }
        } catch (SQLException e) {
            System.err.println("Qate: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        createAccount("Agymda", 5000.75, 1);
        readAccounts();
        updateAccount(1, 12000.00);
        deleteAccount(2);
    }
}