package com.capgemini.addressbook;

import java.sql.SQLException;
import java.util.List;

public class Address_Book_Service {
	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO
	}

	private List<Address_Book_Data> addressBookList;
	private AddressBook_DBService addressBookDBService;

	public Address_Book_Service(List<Address_Book_Data> addressBookList) {
		this.addressBookList = addressBookList;
	}

	public Address_Book_Service() {
		addressBookDBService = addressBookDBService.getInstance();
	}

	public void writeData(IOService ioService) {
		if (ioService.equals(IOService.CONSOLE_IO))
			System.out.println("address to Console\n" + addressBookList);
	}

	public List<Address_Book_Data> readData(IOService ioService) throws SQLException {
		if (ioService.equals(IOService.FILE_IO))
			return addressBookDBService.readData();
		if (ioService.equals(IOService.DB_IO))
			return addressBookDBService.readData();
		else
			return null;
	}
}
