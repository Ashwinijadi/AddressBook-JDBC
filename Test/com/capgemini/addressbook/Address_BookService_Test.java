package com.capgemini.addressbook;

import java.sql.SQLException;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class Address_BookService_Test {

	@Test
	public void givenAddressBookInDB_WhenRetrieved_ShouldMatchAddressBookCount() throws SQLException {
		Address_Book_Service addressBookService = new Address_Book_Service();
		List<Address_Book_Data> addressBookData = addressBookService.readData();
		System.out.println(addressBookData);
		Assert.assertEquals(4, addressBookData.size());
	}

	@Test
	public void givenNewCityForPerson_WhenUpdatedUsingPreparedStatement_ShouldSyncWithDB() throws SQLException {
		Address_Book_Service addressBookService = new Address_Book_Service();
		List<Address_Book_Data> addressBookData = addressBookService.readData();
		addressBookService.update("Sneha","hyd");
		boolean result = addressBookService.checkContactInSyncWithDB("Sneha");
		Assert.assertTrue(result);
		System.out.println(addressBookData);
	}

}
