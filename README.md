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
<img width="421" height="375" alt="Screenshot 2025-09-05 120702" src="https://github.com/user-attachments/assets/df910c7a-b53b-47b0-924f-7d4a1df5672d" />


<img width="465" height="368" alt="Screenshot 2025-09-05 120737" src="https://github.com/user-attachments/assets/47047674-135e-4a8c-94f0-e482d0e27861" />


<img width="313" height="285" alt="Screenshot 2025-09-05 120726" src="https://github.com/user-attachments/assets/4891abd2-3298-40aa-b6f7-0b765756647c" />


<img width="322" height="271" alt="Screenshot 2025-09-05 120715" src="https://github.com/user-attachments/assets/cffc8f9d-e880-4659-ba26-a8fe3d3b3f55" />


<img width="421" height="375" alt="Screenshot 2025-09-05 120702" src="https://github.com/user-attachments/assets/d741a94d-0718-41fe-849a-bd03f1dfcde9" />





