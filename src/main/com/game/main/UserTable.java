package com.game.main;

import java.util.Hashtable;

public class UserTable {

	Hashtable<String, String> userTable = new Hashtable<String,String>();
	
	public void createUser(String username, String password, String email, String bio) throws Exception {
		
		String aPassword = userTable.get(username);
		if (aPassword!=null)
			throw new Exception("User already exists.");
		
		validateUsername(username);
		validatePassword(password);
		
		validateEmail(email);
		persist(username, password, email, bio);
	}

	private void persist(String username, String password, String email, String bio) {
		userTable.put(username, password);
	}

	private void validateEmail(String email) throws Exception {
		// TODO Auto-generated method stub
	}

	private void validatePassword(String password) throws Exception {
		// TODO Auto-generated method stub
		
	}

	private void validateUsername(String username) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public boolean isValid(String username, String password) {

		String realPassword = userTable.get(username);
		return  (realPassword!=null && realPassword == password);
	}

}
