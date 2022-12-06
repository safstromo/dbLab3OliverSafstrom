import java.sql.*;
import java.util.Scanner;

public class Main {
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		boolean exit = false;

		while (!exit) {
			printMenu();
			String menuInput = sc.nextLine();

			switch (menuInput) {
				case "1" -> add();
				case "2" -> show();
				case "3" -> update();
				case "4" -> remove();
				case "e,E" -> exit = true;
			}
		}

	}

	private static void add() {
		printAddMenu();
		String input = sc.nextLine();
		boolean exit = false;

		switch (input) {
			case "1" -> addGame();
			case "2" -> addStudio();
			case "e,E" -> exit = true;
		}


	}

	private static void addGame() {
		System.out.println("Enter game name: ");
		String gameName = sc.nextLine();
		System.out.println("Enter price: ");
		double price = sc.nextDouble();
		insertGame(gameName,price);



	}

	private static void addStudio() {
		System.out.println("Enter studio name: ");
		String studioName = sc.nextLine();
		insertStudio(studioName);
	}

	private static void printAddMenu() {
		System.out.println("""
				1. Add game
				2. Add studio
				3. Back to menu
				""");
	}

	private static void show() {
	}

	private static void update() {
	}

	private static void remove() {
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

	private static void insertStudio(String studioName) {
		String sql = "INSERT INTO studio(studioName) VALUES(?)";

		try (Connection conn = connect();
			 PreparedStatement querty = conn.prepareStatement(sql)) {
			querty.setString(1, studioName);
			querty.executeUpdate();
			System.out.println(studioName + " added");

		}catch (SQLException e){
			System.out.println(e.getMessage());
		}
	}
	private static void insertGame(String gameName,double price) {
		String sql = "INSERT INTO game(gameName, gamePrice) VALUES(?,?)";

		try (Connection conn = connect();
			 PreparedStatement query = conn.prepareStatement(sql)) {
			query.setString(1, gameName);
			query.setDouble(2,price);
			query.executeUpdate();
			System.out.println(gameName + " added");

		}catch (SQLException e){
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
