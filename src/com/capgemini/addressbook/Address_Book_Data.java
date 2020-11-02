package com.capgemini.addressbook;

public class Address_Book_Data {
	String firstName;
	String lastName;
	String address;
	String city;
	String state;
	long zip;
	long phoneNumber;
	String email;
	String type;
	String addressBookName;

	public Address_Book_Data(String firstName, String lastName, String address, String city, String state, long zip,
			long phoneNumber, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}

	public Address_Book_Data(String firstName, String lastName, String address, String city, String state, long zip,
			long phoneNumber, String email, String type,String addressBookName) {
		this(firstName, lastName, address, city, state, zip, phoneNumber, email);
		this.type = type;
		this.addressBookName=addressBookName;
	}
	public Address_Book_Data() {
		
	}

	@Override
	public String toString() {
		return "Address_Book_Data [firstName=" + firstName + ", lastName=" + lastName + ", address=" + address
				+ ", city=" + city + ", state=" + state + ", zip=" + zip + ", phoneNumber=" + phoneNumber + ", email="
				+ email + ", type=" + type + ", addressBookName=" + addressBookName + "]";
	}


}
