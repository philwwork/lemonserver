/**
 * 
 */


var MYAPP = MYAPP || {};
var serverAddress = "localhost:8080";



var endTurnButton = document.getElementById("end_turn");
endTurnButton.addEventListener("click", endTurn, false);

var resetButton = document.getElementById("reset");
resetButton.addEventListener("click", resetBuyHandler, false);

/* $("#end_turn").click(endTurn);
$("#reset").click(resetBuyHandler);*/





// Vanilla mouse wheel.
document.getElementById('lemons').addEventListener("mousewheel", changeOrder, false);

/*
$("#ice").on('mousewheel', null, 'hi', changeIce);
$("#water").on('mousewheel', null, 'hi', changeWater);
$("#sugar").on('mousewheel', null, 'hi', changeSugar);
$("#cups").on('mousewheel', null, 'hi', changeCups);
*/

var lemonsInput = document.getElementById("lemons");
var iceInput = document.getElementById("ice");
var waterInput = document.getElementById("water");
var sugarInput = document.getElementById("sugar");
var cupsInput = document.getElementById("cups");

var aFunction = new function() {	lemonsInput.select(); }

sugarInput.addEventListener("mouseover", function() {sugarInput.select();}, false);
lemonsInput.addEventListener("mouseover", function() {lemonsInput.select();}, false);
waterInput.addEventListener("mouseover", function() {waterInput.select();}, false);
cupsInput.addEventListener("mouseover",  function() {cupsInput.select();}, false);
iceInput.addEventListener("mouseover",function() {iceInput.select();}, false);

sugarInput.addEventListener("oninput", setTotalCost, false);
lemonsInput.addEventListener("oninput", setTotalCost, false);
waterInput.addEventListener("oninput", setTotalCost, false);
cupsInput.addEventListener("oninput",  setTotalCost, false);
iceInput.addEventListener("oninput", setTotalCost, false);





sugarInput.select();

var totalCost = document.getElementById("totalCost");




var leftContent = document.getElementById("left_content");

var lemonsPrice = document.getElementById("lemonsPrice");
var icePrice = document.getElementById("icePrice");
var waterPrice = document.getElementById("waterPrice");
var sugarPrice = document.getElementById("sugarPrice");
var cupsPrice = document.getElementById("cupsPrice");

var currentLemons = document.getElementById("currentLemons");
var currentIce = document.getElementById("currentIce");
var currentWater = document.getElementById("currentWater");
var currentSugar = document.getElementById("currentSugar");
var currentCups = document.getElementById("currentCups");

var currentMoney = document.getElementById("currentMoney");




/*
$("#lemons").mouseover(mouseOver);
$("#ice").mouseover(mouseOver);
$("#water").mouseover(mouseOver);
$("#sugar").mouseover(mouseOver);
$("#cups").mouseover(mouseOver);
/*

/*
lemons.onInput.listen(updateCost);
ice.onInput.listen(updateCost);
water.onInput.listen(updateCost);
sugar.onInput.listen(updateCost);
cups.onInput.listen(updateCost);
*/

window.onload=endTurn();


function endTurn()
{
	var xmlhttp=new XMLHttpRequest();
	
	xmlhttp.onreadystatechange=function()
	  {
		if (xmlhttp.readyState==4 && xmlhttp.status==500)
		{
			window.location="./error.html";
		}
		
		if (xmlhttp.readyState==4 && xmlhttp.status==404)
		{
			window.location="./invalid.html";
		}
		
	  if (xmlhttp.readyState==4 && xmlhttp.status==200)
	    {  
			var prices = JSON.parse(xmlhttp.responseText);
			
			
			
			leftContent.innerHTML = leftContent.innerHTML + "<p>" + prices["text"] + "</p>";

			/*
			leftContent.$("#left_content").append("<p>" + prices["text"] + "</p>");
			*/
			
			
			
			sugarPrice.innerText = prices.sugarPrice;
			lemonsPrice.innerText=prices.lemonsPrice;
			waterPrice.innerText=prices.waterPrice;
			cupsPrice.innerText=prices.cupsPrice;
			icePrice.innerText=prices.icePrice;
						
			/*
			$("#lemonsPrice").text(prices['lemonsPrice']);
			$("#icePrice").text(prices['icePrice']);
			$("#waterPrice").text(prices['waterPrice']);
			$("#sugarPrice").text(prices['sugarPrice']);
			$("#cupsPrice").text(prices['cupsPrice']);
			*/
			
			currentSugar.innerText = prices.sugar;
			currentLemons.innerText = prices.lemons;
			currentWater.innerText = prices.water;
			currentCups.innerText = prices.cups;
			currentIce.innerText = prices.ice;
			
			
			/*
			$("#currentLemons").text(prices['lemons']);
			$("#currentIce").text(prices['ice']);
			$("#currentWater").text(prices['water']);
			$("#currentSugar").text(prices['sugar']);
			$("#currentCups").text(prices['cups']);
			
			*/
			
			currentMoney.innerText = prices.money;
			
			/*
			$("#currentMoney").text(prices['money']);
			*/
			
	    }
	  }
	
	// Pass the login token back to the game server so it knows who you are.


	var stringOrder =JSON.stringify(getOrder());

	xmlhttp.open("POST","http://" + serverAddress + "/GameServer/Game/turn.json",true);
	xmlhttp.setRequestHeader("token", sessionStorage.getItem("token"));
	xmlhttp.send(stringOrder);
}

function resetBuyHandler()
{
	leftContent.innerHTML = leftContent.innerHTML + '<p>Reset buy.</p>';
	resetBuy();
}


function resetBuy()
{
	lemonsInput.value=0;
	iceInput.value=0;
	waterInput.value=0;
	sugarInput.value=0;
	cupsInput.value=0;
	
	totalCost.innerText="0";
	
}


function setTotalCost()
{
	totalCost.innerText="7";
	
	var total = lemonsInput.value + iceInput.value + waterInput.value + sugarInput.value + cupsInput.value;
	
	totalCost.innerText=total;
}







function getOrder()
{
	var order = {};
	
	 order.sugar = sugarInput.value;
	 order.lemons = lemonsInput.value;
	 order.water =  waterInput.value;
	 order.cups =  cupsInput.value;
	 order.ice =  iceInput.value;
	 
	 return order;
	  
}


function changeOrder()
{
	// Mousewheel 
	
}


