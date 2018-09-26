package com.game.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GameServerMain
 */
@WebServlet("/GameServerMain")
public class GameServerMain extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static RequestTable myRequestTable = new RequestTable();

	private static String CLIENT = "localhost";
    private static String SERVER = "localhost";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GameServerMain() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Logger.log("Received Get: " + request.getPathInfo());
		response.setHeader("Access-Control-Allow-Origin", CLIENT);
		response.setHeader("Access-Control-Allow-Origin", SERVER);
		myRequestTable.handleRequestGet(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Logger.log("Received Post: " + request.getPathInfo());
		response.setHeader("Access-Control-Allow-Origin", CLIENT);
		response.setHeader("Access-Control-Allow-Origin", SERVER);
		myRequestTable.handleRequestPost(request, response);
	}
	
}