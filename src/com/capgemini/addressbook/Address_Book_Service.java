package com.capgemini.addressbook;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Address_Book_Service {

	private List<Address_Book_Data> addressBookList;
	private AddressBook_DBService addressBookDBService;

	public Address_Book_Service(List<Address_Book_Data> addressBookList) {
		this.addressBookList = addressBookList;
	}

	public Address_Book_Service() {
		addressBookDBService = addressBookDBService.getInstance();
	}

	public List<Address_Book_Data> readData() throws SQLException {
		this.addressBookList = addressBookDBService.readData();

		return addressBookList;

	}

	public void update(String name, String address) {
		int result = addressBookDBService.updateAddressBook(name, address);
		if (result == 0)
			return;
		Address_Book_Data addressbookData = this.readData(name);
		if (addressbookData != null)
			addressbookData.address = address;
	}

	private Address_Book_Data readData(String name) {
		return this.addressBookList.stream().filter(contact -> contact.firstName.equals(name)).findFirst().orElse(null);
	}

	public boolean checkContactInSyncWithDB(String name) {
		List<Address_Book_Data> addressBookList = addressBookDBService.getAddressbookDataByName(name);
		return addressBookList.get(0).equals(readData(name));
	}

	public List<Address_Book_Data> readContactDataForDateRange(LocalDate startDate, LocalDate endDate) {
		this.addressBookList = addressBookDBService.getDetailsForDateRange(startDate, endDate);
		return addressBookList;
	}
}
