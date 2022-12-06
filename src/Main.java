import java.sql.*;

public class Main {
    public static void main(String[] args) {

        selectAllFromStudio();
    }

    private static Connection connect() {
        Connection connection = null;
        try {
            String url = "jdbc:sqlite:src/db/labb3.db";
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return connection;
    }

    private static void selectAllFromStudio() {
        String sql = "SELECT * FROM studio";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("studioId") + "\t" +
                        rs.getString("studioName"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
