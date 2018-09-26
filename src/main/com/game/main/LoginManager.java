package com.game.main;

import java.util.Hashtable;

public class LoginManager {

	UserTable myUserTable;
	Hashtable<String, String> loggedInUsers;
	Hashtable<String, String> tokensToUsers;
	
	public LoginManager()
	{
		myUserTable = new UserTable();
	
		loggedInUsers = new Hashtable<String,String>();
		tokensToUsers = new Hashtable<String,String>();
		
		mockUser();
	}
	
	private void mockUser()
	{
		// Create a mock table with an already persisted user.
		String persistedUser = "phil";
		String persistedPassword = "fluffy";
		String persistedEmail = "phil@aol.com";
		String persistedBio = "A large amount of grape.";
		
		try {
			myUserTable.createUser(persistedUser, persistedPassword, persistedEmail, persistedBio);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String login(String username, String password) {

		if (!myUserTable.isValid(username, password))
		{
			return null;
		}

		if (getCurrentToken(username)!=null)
		{
			// reuse current session token
			return getCurrentToken(username);
		}
		
		return loginUserGetNewToken(username);
	}
	public boolean isValid(String token) {
		
		return tokensToUsers.get(token)!=null;
	}

	private String loginUserGetNewToken(String username)
	{
		String token = generateToken();
		loggedInUsers.put(username, token);
		tokensToUsers.put(token,  username);
		return token;
	}
	
	private String getCurrentToken(String username)
	{
		return loggedInUsers.get(username);
	}
	
	// Replace with GUID, obviously you're going to be handing these out faster than milliseconds.
	private String generateToken()
	{
		return "" + System.currentTimeMillis();
	}

	public String createUser(String username, String password, String email, String bio)  throws Exception {
		myUserTable.createUser(username, password, email, bio);
		return loginUserGetNewToken(username);
	}
}
