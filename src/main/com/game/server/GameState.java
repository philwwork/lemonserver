package com.game.server;

import java.util.Hashtable;
import java.util.Random;

public class GameState {

	private static Random myRand = new Random();
	
	private int turn = 0;
	
	private final static String LEMONS="lemons";
	private final static String ICE="ice";
	private final static String WATER="water";
	private final static String SUGAR="sugar";
	private final static String CUPS="cups";
	private final static String MONEY="money";

	private final static String LEMONS_PRICE="lemonsPrice";
	private final static String ICE_PRICE="icePrice";
	private final static String WATER_PRICE="waterPrice";
	private final static String SUGAR_PRICE="sugarPrice";
	private final static String CUPS_PRICE="cupsPrice";
	
	private int lemons = 1;
	private int ice = 1;
	private int water = 1;
	private int sugar = 1;
	private int cups = 1;
	
	private int money = 1000;
	/**
	 * Notes:
	 * Turn based system will have prices loaded for each turn on a given day.
	 * Since the turns will be limited to 5 or so per day, the players should they play
	 * every day will get the same prices.
	 */
	
	
	// Should come from server that has prices queued up per turn.
	public Hashtable<String,String> getTurnPrices() {
		
		Hashtable<String,String> priceTable = new Hashtable<String,String>();
		
		priceTable.put(LEMONS_PRICE,  ""+getLemonsPrice());
		priceTable.put(ICE_PRICE,  ""+getIcePrice());
		priceTable.put(WATER_PRICE,  ""+getWaterPrice());
		priceTable.put(SUGAR_PRICE,  ""+getSugarPrice());
		priceTable.put(CUPS_PRICE,  ""+getCupsPrice());
		return priceTable;
	}
	
	public Hashtable<String,String> getStateTable() {
		
		Hashtable<String,String> turnTable = new Hashtable<String,String>();
		
		turnTable.putAll(getTurnPrices());
		turnTable.putAll(getStats());
		return turnTable;
		
	}
	
	public Hashtable<String,String> getStats()
	{
		
		Hashtable<String,String> statsTable = new Hashtable<String,String>();
		
		statsTable.put(MONEY,  getMoney());
		
		statsTable.put(LEMONS, getLemons());
		statsTable.put(ICE, getIce());
		statsTable.put(WATER,  getWater());
		statsTable.put(SUGAR, getSugar());
		statsTable.put(CUPS,  getCups());
		
		return statsTable;
	}

	private String getCups() {
		return "" + cups;
	}

	private String getSugar() {
		return "" + sugar;
	}

	private String getWater() {
		return "" + water;
	}

	private String getIce() {
		return "" + ice;
	}

	private String getLemons() {
		return "" + lemons;
	}

	private String getMoney() {
		return "" + money;
	}
	
	public Hashtable<String,String> processTurn(Hashtable<String,String> myTurnRequest)
	{
		
		
		// validate, execute, mutate stats with request
		// then return the updated stats
		
		doOrder(myTurnRequest);
		
		
		Hashtable<String,String> responseTable = getStateTable();
		
		if (turn==0)
		{
			responseTable.put("text", getFirstText());
		}
		
		else
		{
			responseTable.put("text", getTurnText());
		}
		turn++;
		return responseTable;
	}
	
	
	private void doOrder(Hashtable<String,String> order)
	{
		int lemonsOrder =Integer.parseInt(order.get("lemons"));
		int iceOrder =Integer.parseInt(order.get("ice"));
		int waterOrder =Integer.parseInt(order.get("water"));
		int sugarOrder =Integer.parseInt(order.get("sugar"));
		int cupsOrder =Integer.parseInt(order.get("cups"));
		
		money-= lemonsOrder * getLemonsPrice();
		money-= iceOrder * getIcePrice();
		money-= waterOrder * getWaterPrice();
		money-= sugarOrder * getSugarPrice();
		money-= cupsOrder * getCupsPrice();
		
		
		
		lemons+=lemonsOrder;
		ice+=iceOrder;
		water+=waterOrder;
		sugar+=sugarOrder;
		cups+=cupsOrder;
	}
	
	private String getFirstText()
	{
		String text = "Welcome to the game. You start with &cent;1000 to buy your supplies, and some stuff you grabbed from the fridge.";
		return text;
	}
	
	private String getTurnText()
	{
		String text =  "Begin turn (" + turn + ").";
		return text;
	}
	
	
	// TODO: Change these to get the per turn value instead.

	// 30 to 64 cents each.
	private int getLemonsPrice()
	{
		int price = 50 + myRand.nextInt(50) + 1;
		return price;
	}
	
	
	private int getRand(int low, int high)
	{
		return low + myRand.nextInt(high - low + 1);
	}
	
	// 73 to 126 cents
	private int getIcePrice()
	{
		return getRand(73, 126);
	}
	
	// 73 to 126 cents
	private int getWaterPrice()
	{
		return getRand(73, 126);
	}
	
	// 148 to 199 cents
	private int getSugarPrice()
	{
		return getRand(148,199);
	}

	// 248 to 350 cents
	private int getCupsPrice()
	{
		return getRand(248, 350);
	}

}
