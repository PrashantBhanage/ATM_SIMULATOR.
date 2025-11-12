ATM System (JavaFX + MySQL)

A simple JavaFX-based ATM application connected to a MySQL database.
It allows login, balance check, deposit, withdrawal, and viewing transaction history.

Setup
Requirements

JDK 17 or later

MySQL Server

JavaFX SDK

MySQL Connector JAR

Database
CREATE DATABASE atm_db;
USE atm_db;

CREATE TABLE users(
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50),
  pin VARCHAR(20),
  balance DOUBLE DEFAULT 0
);

CREATE TABLE transactions(
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT,
  type VARCHAR(20),
  amount DOUBLE,
  timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY(user_id) REFERENCES users(id)
);

INSERT INTO users(username, pin, balance)
VALUES('prashant', '12345', 5000);

DB Connection
private static final String URL = "jdbc:mysql://localhost:3306/atm_db?serverTimezone=UTC";
private static final String USER = "prashant";
private static final String PASSWORD = "12345";

How to Run

Open the project in VS Code

Make sure your launch.json includes the JavaFX and MySQL connector paths

Run Launch ATM GUI

Login with:

Username: prashant

PIN: 12345

Project Files
ATM.java          // JavaFX GUI and logic
DBConnection.java // Database connection
Main.java         // Entry point (launches GUI)

Author

Prashant – BCA Final Year Student
