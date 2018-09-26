package com.game.server;

import java.util.Hashtable;

import org.json.JSONObject;

public class UserAdapter {


	
	private UserState myUserState;
	
	public UserAdapter(String username, String password) {
		this.myUserState = new UserState(username, password);
	}
	
	
	public String getUsername() {
		return myUserState.getUsername();
	}

	public String getPassword() {
		return myUserState.getPassword();
	}
	
	public String processTurn(String turnRequestString)
	{
			if (turnRequestString.equals(""))
			{
				String results = tableToJsonString(myUserState.processTurn(new Hashtable<String,String>()));
				Logger.log(results);
				return results;
			}
			
			Hashtable<String,String> myTurnRequest = jsonStringToTable(turnRequestString);
			
			// return current state after turn is processed.
			Hashtable<String,String> myTurnResults = myUserState.processTurn(myTurnRequest);
			

			return tableToJsonString(myTurnResults);
	}		
	
	
	private String tableToJsonString(Hashtable<String,String> myTable)
	{
		Object wrappedTable = JSONObject.wrap(myTable);
		return wrappedTable.toString();
	}

	
	private Hashtable<String,String> jsonStringToTable(String jsonString)
	{
		JSONObject myJson = new JSONObject(jsonString);
		String[] myNames = JSONObject.getNames(myJson);
		Hashtable<String,String> toTable = new Hashtable<String,String>();
		
		for (String name: myNames)
		{
			toTable.put(name,  myJson.getString(name));
		}
		
		return toTable;
	}
}
