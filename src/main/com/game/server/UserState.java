package com.game.server;

import java.util.Hashtable;

public class UserState {

	String username;
	String password;
	
	// user state.name -> game state
	private GameState  myGameState;	
	
	public UserState(String username, String password) {
		this.username = username;
		this.password = password;
		myGameState = new GameState();
	}
	
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Hashtable<String,String> processTurn(Hashtable<String,String> turnRequestTable) {
		
				// TODO: process turn request values
				return myGameState.processTurn(turnRequestTable);
	}
}
