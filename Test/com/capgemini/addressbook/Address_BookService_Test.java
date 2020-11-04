package com.capgemini.addressbook;

import java.sql.SQLException;
import java.time.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class Address_BookService_Test {

	@Test
	public void givenAddressBookInDB_WhenRetrieved_ShouldMatchAddressBookCount() throws SQLException {
		Address_Book_Service addressBookService = new Address_Book_Service();
		List<Address_Book_Data> addressBookData = addressBookService.readData();
		System.out.println(addressBookData);
		Assert.assertEquals(3, addressBookData.size());
	}

	@Test
	public void givenNewCityForPerson_WhenUpdatedUsingPreparedStatement_ShouldSyncWithDB() throws SQLException {
		Address_Book_Service addressBookService = new Address_Book_Service();
		List<Address_Book_Data> addressBookData = addressBookService.readData();
		addressBookService.update("Ashu", "hyderabad");
		boolean result = addressBookService.checkContactInSyncWithDB("Ashu");
		Assert.assertTrue(result);
		System.out.println(addressBookData);
	}

	@Test
	public void givenDateRange_WhenRetrieved_ShouldMatchEmployeeCount() throws SQLException {
		Address_Book_Service addressBookService = new Address_Book_Service();
		addressBookService.readData();
		LocalDate startDate = LocalDate.of(2018, 01, 01);
		LocalDate endDate = LocalDate.now();
		List<Address_Book_Data> addressBookData = addressBookService.readContactDataForDateRange(startDate, endDate);
		Assert.assertEquals(2, addressBookData.size());
		System.out.println(addressBookData);
	}

	@Test
	public void givenAddressBook_RetrieveNumberOfContacts_ByCityOrState() throws SQLException {
		Address_Book_Service addressBookService = new Address_Book_Service();
		addressBookService.readData();
		Map<String, Integer> addressByCityMap = addressBookService.getAddressByCityOrState();
		Integer count = 1;
		Assert.assertEquals(count, addressByCityMap.get("Warangal"));
		System.out.println(addressByCityMap);
	}

	@Test
	public void givenNewContact_WhenAdded_ShouldSyncWithDB() throws SQLException {
		Address_Book_Service addressBookService = new Address_Book_Service();
		addressBookService.readData();
		LocalDate date = LocalDate.now();
		addressBookService.addContactToAddressBook("sneha", " A ", "ssr", date, "Adilabad", "Telangana", 500015,
				88569922, "sneha@gmail", "Friend");
		boolean result = addressBookService.checkContactInSyncWithDB("sneha");
		Assert.assertTrue(result);
	}

	@Test
	public void addingAddresses_WhenAddedToDB_ShouldMatchEmployeeEntries() throws SQLException {
		LocalDate date = LocalDate.now();
		Address_Book_Data[] arrayOfaddress = {

				new Address_Book_Data("Deeksha", "sai", "kkr", date, "Hyd", "Telangana", 500015, 88569922,
						"deeksh@gmail", "Friend"),
				new Address_Book_Data("pallavi", "kyama ", "dskr", date, "hyd", "Telangana", 500015, 88569922,
						"pallavikyama@gmail", "Friend"),
				new Address_Book_Data("Divya", " saginala ", "ssr", date, "Adilabad", "Telangana", 500015, 88569922,
						"divya@gmail", "Friend") };
		Address_Book_Service addressBookService = new Address_Book_Service();
		addressBookService.readData();
		Instant start = Instant.now();
		addressBookService.addContact(Arrays.asList(arrayOfaddress));
		Instant end = Instant.now();
		System.out.println("Duration without thread : " + Duration.between(start, end));
		Instant threadStart = Instant.now();
		addressBookService.addEmployeeToPayrollWithThreads(Arrays.asList(arrayOfaddress));
		Instant threadEnd = Instant.now();
		System.out.println("Duartion with Thread : " + Duration.between(threadStart, threadEnd));
		Assert.assertEquals(6, addressBookService.countEntries());
	}

}
