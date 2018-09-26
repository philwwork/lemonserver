package com.game.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class RequestTable {

	// token -> user state
	private static Hashtable<String, UserAdapter> myUsers = new Hashtable<String, UserAdapter>();

	public void handleRequestGet(HttpServletRequest request, HttpServletResponse response) throws IOException
	{ 
	
		try {
			String token = request.getHeader("token");
			
			
			Logger.log("Received token: " + token);
			if (token==null)
			{
				handleBadToken(request, response);
			}
			
			UserAdapter myUser = myUsers.get(token);
			if (myUser==null)
			{
					handleBadToken(request, response);
			}
			
			String url = request.getPathInfo();
			switch (url)
			{
				case "/prices.json":
					handlePrices(request, response, myUser);
					break;
			
				case "/user.json":
					handleUserInfo(request, response, myUser);
					break;
					
					default: 
					handleBadUrl(request, response);
			}	
		
		} catch (IOException e) {
			
			Logger.log("No response written");
			e.printStackTrace();
			
			throw new IOException();
		}
	}
	

	public void handleRequestPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		
		Logger.log("Handling a post");
		try {
			String url = request.getPathInfo();
			switch (url)
			{
				case "/login.json":
					handleUserLogin(request, response);
					break;
					
				case "/turn.json":
					
					String token = request.getHeader("token");
					
					
					Logger.log("Received token: " + token);
					if (token==null)
					{
						handleBadToken(request, response);
					}
					
					UserAdapter myUser = myUsers.get(token);
					if (myUser==null)
					{
							handleBadToken(request, response);
					}
					
					handleTurn(request,response, myUser);
					break;
					
				default: 
					handleBadUrl(request, response);
			}	

		} catch (IOException e) {
			
			Logger.log("No response written");
			e.printStackTrace();
			throw new IOException();
		}
	}
	
	
	
	
	private void handleTurn(HttpServletRequest request, HttpServletResponse response, UserAdapter myUser) throws IOException
	{
		response.setStatus(200);
		response.setContentType("text/plain");
		
		BufferedReader myReader = request.getReader();
		String body = "";
		
		String line ="";
		while (line !=null)
		{
			body+=line;
			line = myReader.readLine();
		}
		
		Logger.log("Post body: " + body);
		// Purchase options, etc.
		String myTurn = body;
		
		
		String turnStateJson = myUser.processTurn(myTurn);
		response.getWriter().append(turnStateJson);
		
	}


	private void handleBadToken(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
			do404(request, response);
	}
	
	private void handleBadUrl(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		do404(request, response);
	}
	
	private void handlePrices(HttpServletRequest request, HttpServletResponse response, UserAdapter myUser) throws IOException
	{
		response.setStatus(200);
		response.setContentType("text/plain");
		
		BufferedReader myReader = request.getReader();
		String body = "";
		
		String line ="";
		while (line !=null)
		{
			body+=line;
			line = myReader.readLine();
		}
		
		Logger.log("Get body: " + body);
		// Purchase options, etc.
		String myTurn = body;
		
		
		String turnStateJson = myUser.processTurn(myTurn);
		response.getWriter().append(turnStateJson);
	}
	
	private void handleUserInfo(HttpServletRequest request, HttpServletResponse response,UserAdapter myUser) throws IOException
	{
		response.setStatus(200);
		response.setContentType("text/plain");
					
		String userString = myUser.getUsername();
		response.getWriter().append(userString);
	}
	
	private void handleUserLogin(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		
		Logger.log("User logging in");
		BufferedReader myReader = request.getReader();
		
		String body = "";
		
		String line ="";
		while (line !=null)
		{
			body+=line;
			line = myReader.readLine();
		}
		
		Logger.log("Post body: " + body);
		JSONObject myJson = new JSONObject(body);
		
		String username = myJson.getString("username");
		String password = myJson.getString("password");
		
		UserAdapter myUserState = new UserAdapter(username, password);
		
		String token = generateToken(myUserState);
        myUsers.put(token, myUserState);
		
        Logger.log("putting token: " + token + " into " + myUserState.getUsername());
		response.setStatus(200);
		response.setContentType("text/plain");
		response.getWriter().append(token);
		
	}
	
	private void do404(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.setStatus(404);
		response.setContentType("text/plain");
		response.getWriter().append("404 served at: ").append(request.getContextPath());
	}
	
	private String generateToken(Object anObject)
	{
		return "" + anObject.hashCode();
	}

}
