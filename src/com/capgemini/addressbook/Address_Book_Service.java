package com.capgemini.addressbook;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

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

	public long countEntries() {
		return addressBookList.size();
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

	public void addContactToAddressBook(String firstName, String lastName, String address, LocalDate date_added,
			String city, String state, long zip, long phoneNumber, String email, String Type) {
		addressBookList.add(addressBookDBService.addContact(firstName, lastName, address, date_added, city, state, zip,
				phoneNumber, email, Type));

	}

	public void addContact(List<Address_Book_Data> addressList) {
		addressList.forEach(address -> {
			System.out.println("Employee being added : " + address.firstName);
			this.addContactToAddress(address.firstName, address.lastName, address.address, address.date_added,
					address.city, address.state, address.zip, address.phoneNumber, address.email,
					address.addressBookName);
			System.out.println("Employee added : " + address.firstName);
		});
		System.out.println("" + this.addressBookList);
	}

	public void addEmployeeToPayrollWithThreads(List<Address_Book_Data> addressList) {
		Map<Integer, Boolean> employeeAdditionStatus = new HashMap<>();
		addressList.forEach(address -> {
			Runnable task = () -> {
				employeeAdditionStatus.put(address.hashCode(), false);
				System.out.println("Employee being added : " + Thread.currentThread().getName());
				this.addContactToAddress(address.firstName, address.lastName, address.address, address.date_added,
						address.city, address.state, address.zip, address.phoneNumber, address.email, address.Type);
				employeeAdditionStatus.put(address.hashCode(), true);
				System.out.println("Employee added : " + Thread.currentThread().getName());
			};
			Thread thread = new Thread(task, address.firstName);
			thread.start();
		});
		while (employeeAdditionStatus.containsValue(false)) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		System.out.println("" + this.addressBookList);
	}

	private void addContactToAddress(String firstName, String lastName, String address, LocalDate date_added,
			String city, String state, long zip, long phoneNumber, String email, String Type) {
		addressBookList.add(addressBookDBService.addContact(firstName, lastName, address, date_added, city, state, zip,
				phoneNumber, email, Type));

	}
}
