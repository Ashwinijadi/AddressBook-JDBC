package com.capgemini.addressbook;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Address_Book_Service {

	private List<Address_Book_Data> addressBookList;
	private AddressBook_DBService addressBookDBService;
	private Map<String, Integer> addressByCity;

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

	public Map<String, Integer> getAddressByCityOrState() {
		this.addressByCity = addressBookDBService.getAddressByCity();
		return addressByCity;
	}

	public void addContactToAddressBook(String firstName, String lastName, String address, String city, String state,
			LocalDate date_added, long zip, long phoneNumber, String email, String Type, String addressBookName) {
		addressBookList.add(addressBookDBService.addContact(firstName, lastName, address, city, state, date_added, zip,
				phoneNumber, email, Type, addressBookName));

	}
}
