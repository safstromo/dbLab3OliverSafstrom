import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
connect();

    }
private static void connect(){
    Connection connection = null;
    try{
        String url = "jdbc:sqlite:/home/eox/Documents/Yrkesutbildning/ITHS/Databaser/SQLite/labb3.db";
        connection = DriverManager.getConnection(url);
        System.out.println("Database Connected...");
    }catch (SQLException e){
        System.out.println(e.getMessage());
    }
}


}
