# Akka-spring-query
Spring project that uses Akka internally 

## Value
This application is an example of a Spending Management System.
You can use it to record your breakfast, Lunch and Dinner expenses!

## Simple explanation of the code
User sends a request (restfull) to save a record, the controller then creates a command asking the writeWorker to write this record to db.
Note: command is the record to be saved (for simplicity)

For Reading, it's the same except it does not work well since there is no way to listen to messages from the readWorker (currently)

It has two working API's
* <host>/api/expenses/write?name=<name-of-the-food>&amount=<paid-amount>
* <host>/api/expenses/read?id=<record-id>

Here is an example usage
* http://localhost:8080/api/expenses/write?name=Dostoros&amount=9.50
* http://localhost:8080/api/expenses/read?id=1

You will need to look into the code (akka package specifically) to know what is going on.

# pre-requisite
* java 8
* gradle 2.4

# Running
* gradle build
* gradle bootRun
