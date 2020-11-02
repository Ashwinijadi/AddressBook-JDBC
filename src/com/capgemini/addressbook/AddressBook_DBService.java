package com.capgemini.addressbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AddressBook_DBService {

	private static AddressBook_DBService addressBookDBService;
	private PreparedStatement AddressBookContactStatement;

	static AddressBook_DBService getInstance() {
		if (addressBookDBService == null) {
			addressBookDBService = new AddressBook_DBService();
		}
		return addressBookDBService;
	}

	private AddressBook_DBService() {
	}

	public List<Address_Book_Data> readData() {
		String sql = "SELECT ab.firstName, ab.lastName, ab.address,ab.city, ab.state,"
				+ "ab.zip,ab.phoneNumber,ab.email,ab.Type,abn.addressBookName from "
				+ "address_book ab inner join address_book_name abn on ab.Type=abn.Type;";
		return this.getContactDetailsUsingSqlQuery(sql);
	}

	private List<Address_Book_Data> getContactDetailsUsingSqlQuery(String sql) {
		List<Address_Book_Data> addressBookList = null;
		try (Connection connection = this.getConnection();) {
			PreparedStatement preparedstatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedstatement.executeQuery(sql);
			addressBookList = this.readAddressBookData(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return addressBookList;
	}

	public List<Address_Book_Data> readAddressBookData(ResultSet resultSet) throws SQLException {
		List<Address_Book_Data> addressBookList = new ArrayList<>();
		try {
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

	public int updateAddressBook(String name, String address) {
		return this.updateAddressBookUsingPreparedStatement(name, address);
	}

	private int updateAddressBookUsingPreparedStatement(String firstName, String address) {

		try (Connection connection = this.getConnection();) {
			String sql = "update address_book set address = ? Where firstName= ?";
			PreparedStatement preparedstatement = connection.prepareStatement(sql);
			preparedstatement.setString(1, address);
			preparedstatement.setString(2, firstName);
			int update = preparedstatement.executeUpdate();
			return update;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	private void preparedStatementForContactData() {
		try {
			Connection connection = this.getConnection();
			String sql = "SELECT ab.firstName, ab.lastName, ab.address,ab.city, ab.state,"
					+ "ab.zip,ab.phoneNumber,ab.email,ab.Type,abn.addressBookName from "
					+ "address_book ab inner join address_book_name abn on ab.Type=abn.Type  WHERE firstName=? ;";

			AddressBookContactStatement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Address_Book_Data> getAddressbookDataByName(String name) {
		List<Address_Book_Data> addressBookList = null;
		if (this.AddressBookContactStatement == null)
			this.preparedStatementForContactData();
		try {
			AddressBookContactStatement.setString(1, name);
			ResultSet resultSet = AddressBookContactStatement.executeQuery();
			addressBookList = this.readAddressBookData(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return addressBookList;
	}

}
