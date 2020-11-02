package com.capgemini.addressbook;

import java.sql.SQLException;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.capgemini.addressbook.Address_Book_Service.IOService;

class Address_BookService_Test {

	@Test
	public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() throws SQLException {
		Address_Book_Service addressBookService = new Address_Book_Service();
		List<Address_Book_Data> addressBookData = addressBookService.readData(IOService.DB_IO);
		System.out.println(addressBookData);
		Assert.assertEquals(4, addressBookData.size());

	}

}
