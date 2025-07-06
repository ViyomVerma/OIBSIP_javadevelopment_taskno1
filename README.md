# 🚄 Online Train Reservation System (Java + Swing + MySQL)

A simple GUI-based train reservation system built using **Java Swing** for the frontend and **MySQL** for the backend. It allows users to register, login, book train tickets, view bookings, and cancel reservations.

---

## 📋 Features

- User Registration & Login
- Book Train Tickets
- View Your Bookings
- Cancel Booked Tickets
- Uses MySQL as backend DB
- Beautiful Java Swing UI

---

## 💻 Tech Stack

- Java (JDK 8+)
- Java Swing (GUI)
- JDBC (MySQL Connectivity)
- MySQL Database

---

## 🗃️ Database Structure

### `users` Table
```sql
id INT PRIMARY KEY AUTO_INCREMENT  
username VARCHAR(50)  
password VARCHAR(50)  
fullname VARCHAR(100)  

pnr BIGINT PRIMARY KEY  
username VARCHAR(50)  
passenger_name VARCHAR(100)  
train_number VARCHAR(100)  
class_type VARCHAR(20)  
journey_date DATE  
from_station VARCHAR(100)  
to_station VARCHAR(100)  
booking_time TIMESTAMP  

cancel_id INT PRIMARY KEY AUTO_INCREMENT  
pnr BIGINT  
cancellation_time TIMESTAMP  
