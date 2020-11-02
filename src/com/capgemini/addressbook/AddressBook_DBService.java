package com.capgemini.addressbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AddressBook_DBService {

	private static AddressBook_DBService addressBookDBService;

	static AddressBook_DBService getInstance() {
		if (addressBookDBService == null) {
			addressBookDBService = new AddressBook_DBService();
		}
		return addressBookDBService;
	}

	private AddressBook_DBService() {
	}

	public List<Address_Book_Data> readData() throws SQLException {
		String sql = "SELECT ab.firstName, ab.lastName, ab.address,ab.city, ab.state,"
				+ "ab.zip,ab.phoneNumber,ab.email,ab.Type,abn.addressBookName from "
				+ "address_book ab inner join address_book_name abn on ab.Type=abn.Type;";
		List<Address_Book_Data> addressBookList = new ArrayList<Address_Book_Data>();
		try (Connection connection = this.getConnection();) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				String firstName = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");
				String address = resultSet.getString("address");
				String city = resultSet.getString("city");
				String state = resultSet.getString("state");
				long zip = resultSet.getLong("zip");
				long phoneNumber = resultSet.getLong("phoneNumber");
				String email = resultSet.getString("email");
				String type = resultSet.getString("type");
				String addressBookName = resultSet.getString("addressBookName");
				addressBookList.add(new Address_Book_Data(firstName, lastName, address, city, state, zip, phoneNumber,
						email, type, addressBookName));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return addressBookList;
	}

	private Connection getConnection() throws SQLException {
		Connection connection;
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/address_book_service", "root",
				"Jashwini@2298");
		System.out.println("Connection successful: " + connection);
		return connection;
	}
}
