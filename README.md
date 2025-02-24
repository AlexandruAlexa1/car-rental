# 🚗 Car Rental System - Full Stack Application  

A **comprehensive car rental management system** built using **Spring Boot** for the backend and **Angular** for the frontend. This application allows users to browse available cars, rent vehicles, manage locations, and handle rental operations efficiently.  

## ✨ Features  

### 🔹 Backend (Spring Boot)  
- **User Management** – Register, authenticate, and manage users with role-based access.  
- **Car Management** – Add, update, delete, and list available rental cars.  
- **Location Management** – Manage rental locations.  
- **Rent Management** – Handle the complete rental process, from booking to return.  
- **Security** – Implements **Spring Security with JWT authentication** for secure access.  
- **Testing** – Includes **unit and integration tests** to ensure reliability.  

### 🔹 Frontend (Angular)  
- **User Authentication** – Login and register securely.  
- **Car Listings** – Browse and filter available rental cars.  
- **Car Details** – View vehicle specifications and rental options.  
- **User Dashboard** – Manage personal rentals and account settings.  
- **Admin Dashboard** – Manage users, cars, and rental transactions.  
- **Custom UI** – Responsive design with modern styling.  

## 🛠 Technologies Used  
- **Backend:** Java, Spring Boot, Spring Security (JWT), MySQL, JPA/Hibernate.  
- **Frontend:** Angular, TypeScript, Bootstrap, Custom CSS.  

## 🚀 Installation & Running Instructions  

### 🔹 Prerequisites  
- **MySQL** installed and running.  
- **Java 17+** installed.  
- **Node.js & npm** installed (for frontend).  

### 🔹 Setup  

#### **1️⃣ Clone the Repository**  

#### **2️⃣ Database Setup**  
- Navigate to the root directory and execute the SQL script:  
  _(This will create the database, tables, and populate test data.)_

#### **3️⃣ Backend Setup**  
- Update `application.properties` with your MySQL credentials.  
- Run the backend application:  
  ```bash
  mvn spring-boot:run
  ```
- The backend will be accessible at **[http://localhost:8080](http://localhost:8080)**.  

#### **4️⃣ Frontend Setup**  
- Navigate to the frontend directory:  
  ```bash
  cd frontend
  ```
- Install dependencies:  
  ```bash
  npm install
  ```
- Start the Angular development server:  
  ```bash
  ng serve
  ```
- The frontend will be available at **[http://localhost:4200](http://localhost:4200)**.  

🚧 Project Status
"This project is currently in development, and new features are being added regularly."
