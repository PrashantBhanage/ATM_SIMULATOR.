# ATM Simulator

A Java-based ATM Simulator that demonstrates basic banking operations such as deposit, withdrawal, and balance inquiry.  
Includes MySQL database connectivity using JDBC for storing and retrieving account details.

---

## Features
- User login with PIN  
- Balance inquiry  
- Cash withdrawal  
- Deposit money  
- Transaction history stored in MySQL  

---

## Tech Stack
- Language: Java (Core Java, OOP)  
- Database: MySQL  
- JDBC Driver: mysql-connector-java  

---

## How to Run

1. Clone the repository  
   ```bash
   git clone https://github.com/your-username/ATM_Simulator.git
   cd ATM_Simulator
Compile the source code with JDBC driver

bash
Copy code
javac -cp ".;lib/mysql-connector-java-x.x.xx.jar" src/*.java
Run the application

bash
Copy code
java -cp ".;lib/mysql-connector-java-x.x.xx.jar;src" Main
(Replace x.x.xx with your actual JAR version.)

Project Structure
bash
Copy code
ATM_Simulator/
│── src/
│    ├── ATM.java          # ATM operations
│    ├── Dconnection.java  # Database connection
│    ├── Main.java         # Application entry point
│
│── lib/
│    └── mysql-connector-java-x.x.xx.jar
│
│── README.md



