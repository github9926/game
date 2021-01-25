# Wallet demo
Wallet demo

# How to test
1. Start wallet server by running

	mvnw spring-boot:run

2. Use curl (or similar) to test the rest-api.

	E.g. (in Windows command prompt):
	
	* Deposit 10 (euros, cents, or some imaginary currency)
	curl --header "Content-Type: application/json" --request POST --data "{\"eventId\":1,\"playerId\":1,\"amount\":10}" https://localhost:8080/deposit -k
	=> {"balance":10}
	
	* Repeat same event again, and check that balance is unchanged
	curl --header "Content-Type: application/json" --request POST --data "{\"eventId\":1,\"playerId\":1,\"amount\":10}" https://localhost:8080/deposit -k 
	=> {"balance":10}

	* Buy a game for 5 currency units
	curl --header "Content-Type: application/json" --request POST --data "{\"eventId\":2,\"playerId\":1,\"amount\":5}" https://localhost:8080/buy -k
	=> {"balance":5}
	
	* Try to buy a game for 10 currency units
	curl --header "Content-Type: application/json" --request POST --data "{\"eventId\":3,\"playerId\":1,\"amount\":10}" https://localhost:8080/buy -k
	=> Insufficient funds! Account only has 5, but tried to buy 10
	
See api.raml for more information about the rest end-points.

# Things that might make this demo better

	* Stand-alone database (instead of memory-only db started with the app
	* Db setup scripts (instead of Hibernate auto-generated db tables)
	* Docker compose setup for running the app, db and maybe some testing tools in containers
	* Better error handling
	* More automated tests
	
Thanks for reading! :)