import java.sql.*;
import java.util.Scanner;

public class Main {
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		boolean exit = false;
		selectAllFromStudio();

		while (!exit) {
			String menuInput = sc.nextLine();

			printMenu();
			switch (menuInput){
				case "1" -> add();
				case "2" -> show();
				case "3" -> update();
				case "4" -> remove();
				case "e,E" -> exit = true;
			}
		}

	}

	private static void add(){}

	private static void show() {}
	private static void update(){}
	private static void remove(){}

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

	private static void printMenu() {
		System.out.println("""
				1. Add
				2. Show
				3. Update
				4. Remove
				e. Exit
				""");
	}

}
