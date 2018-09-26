package com.game.main;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestLoginManager {

	@Test
	public void testLogin() {
		// This will need to be concurrent eventually to simulate multiple people logging in/creating users simultaneously..
		
		// *If a user logs in multiple times before timing out, should they keep getting the]
		// same user token back?  Obviously they will get a new one after timing out/logging in. Presume yes for now.
		LoginManager myManager = new LoginManager();
		
		// Hardcoded test user.
		String username = "phil";
		String password = "fluffy";
		
		String userToken = testLoginPositive(username, password, myManager);

		// Test token
		testTokenPositive(userToken, myManager);


		// Test fake
		String fakeToken = "asdf";
		testTokenNegative(fakeToken, myManager);
		
		String fakeuser = "nonsense";
		String fakepass = "asdf";
		
		// Test various invalid username/password combinations.
		testLoginNegative(fakeuser, fakepass, myManager);
		testLoginNegative(username, fakepass, myManager);
		testLoginNegative(fakeuser, password, myManager);
		testLoginNegative(password, username, myManager);

	
		// Test token again
		testTokenPositive(userToken, myManager);
		
		// Test fake again
		testTokenNegative(fakeToken, myManager);

		// Test the login again
		String aUserToken = testLoginPositive(username, password, myManager);
		
		// Tokens should be the same*
		if (aUserToken!=userToken)
			fail("New user token given before time out.");
		

		// Test token again
		testTokenPositive(userToken, myManager);

		// Test fake again
		testTokenNegative(fakeToken, myManager);

	}

	// Pass along the login manager to make sure that multiple attempts do  not interfere with each other.
	private String testLoginPositive(String username, String password, LoginManager myManager)
	{
		String token = myManager.login(username, password);
		
		if (token==null)
			fail("no token received for valid username and password.");
	
		return token;
	}

	// Pass along the login manager to make sure that multiple attempts do  not interfere with each other.
	private void testLoginNegative(String username, String password, LoginManager myManager)
	{
		String token = myManager.login(username, password);

		if (token!=null)
			fail("Invalid username or password received a token when it should not have.");
		
	}
	
	private void testTokenPositive(String token, LoginManager myManager)
	{
		if (!myManager.isValid(token))
		{
			fail ("Token '" + token + "' given is not valid");
		}
	}
	
	private void testTokenNegative(String token, LoginManager myManager)
	{
		if (myManager.isValid(token))
		{
			fail("Token given is valid when it should not be.");
		}
	}
	
	
	@Test
	public void testCreateUser()
	{
		LoginManager myManager = new LoginManager();
		
		String username = "phillip";
		String password = "whatever";
		String email = "phil7scott7@gmail.com";
		String bio = "game test";
		
		// The controller will have to handle the exceptions such as already existing user and so on.
		String newToken = null;
		try
		{
			newToken = myManager.createUser(username, password, email, bio);
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
			// already existing user, invalid fields, etc.
			fail ("Problem creating user " + e.getMessage());
		}
		
		if (newToken==null)
		{
			fail("Null token returned from valid create user input.");
		}
		
		testTokenPositive(newToken, myManager);

		// After creating a user, test general functionality using hard coded test user.
		String otherUsername = "phil";
		String otherPassword = "fluffy";
		
		String userToken = testLoginPositive(otherUsername, otherPassword, myManager);
		testTokenPositive(userToken, myManager);

		String fakeToken ="afsdljasdfkj"; 
		testTokenNegative(fakeToken, myManager);
	}
}
	
	
	
