package com.capgemini.addressbook;

import java.sql.SQLException;
import java.time.LocalDate;
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
		Assert.assertEquals(7, addressBookData.size());
	}

	@Test
	public void givenNewCityForPerson_WhenUpdatedUsingPreparedStatement_ShouldSyncWithDB() throws SQLException {
		Address_Book_Service addressBookService = new Address_Book_Service();
		List<Address_Book_Data> addressBookData = addressBookService.readData();
		addressBookService.update("Sneha", "hyd");
		boolean result = addressBookService.checkContactInSyncWithDB("Sneha");
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
		Assert.assertEquals(6, addressBookData.size());
		System.out.println(addressBookData);
	}

	@Test
	public void givenAddressBook_RetrieveNumberOfContacts_ByCityOrState() throws SQLException {
		Address_Book_Service addressBookService = new Address_Book_Service();
		addressBookService.readData();
		Map<String, Integer> addressByCityMap = addressBookService.getAddressByCityOrState();
		Integer count = 2;
		Assert.assertEquals(count, addressByCityMap.get("Warangal"));
		System.out.println(addressByCityMap);
	}

	@Test
	public void givenNewContact_WhenAdded_ShouldSyncWithDB() throws SQLException {
		Address_Book_Service addressBookService = new Address_Book_Service();
		addressBookService.readData();
		LocalDate date = LocalDate.now();
		addressBookService.addContactToAddressBook("Akhi", "k", "kkr", "Hyderabad", "Telangana", date, 500015,
				997778822, "arun@gmail", "Family", "Personal");
		boolean result = addressBookService.checkContactInSyncWithDB("Akhi");
		Assert.assertTrue(result);
	}

}
