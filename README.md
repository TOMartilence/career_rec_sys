## **User Management System**  
A Java-based user management system using **JDBC** and **MySQL**, allowing different user roles (**Student, Employee, Counselor, and Administrator**) to register, authenticate, and interact with a database.  

---

### **Features**
✅ Object-Oriented Design with Abstract Classes & Interfaces  
✅ JDBC-based MySQL Database Connectivity  
✅ User Role Management (Student, Employee, Counselor, Administrator)  
✅ CRUD Operations (Insert, Retrieve, Update)  
✅ Email Interaction via Desktop Mail Client  

---

### **Tech Stack**  
- **Java** (Core, OOP, JDBC)  
- **MySQL** (Database)  
- **Swing / AWT** (For UI, if extended)  

---

### **Installation & Setup**  

#### **1️⃣ Clone the Repository**
```sh
git clone https://github.com/your-username/User-Management-System.git
cd User-Management-System
```

#### **2️⃣ Set Up MySQL Database**
- Create a database **`oops`**  
- Use the provided SQL script to create tables:

```sql
CREATE TABLE student (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    password VARCHAR(255),
    email VARCHAR(255),
    phoneNumber VARCHAR(15),
    standard INT
);

CREATE TABLE employee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    password VARCHAR(255),
    email VARCHAR(255),
    phoneNumber VARCHAR(15),
    salary DOUBLE
);

CREATE TABLE counselor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    password VARCHAR(255),
    email VARCHAR(255),
    phoneNumber VARCHAR(15),
    educationQualification VARCHAR(255),
    experience INT
);

CREATE TABLE administrator (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    password VARCHAR(255),
    email VARCHAR(255),
    phoneNumber VARCHAR(15),
    salary DOUBLE
);
```

#### **3️⃣ Update Database Credentials**  
Modify the `JDBC URL`, `username`, and `password` in the Java files:  
```java
Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/oops", "root", "your-password");
```

#### **4️⃣ Compile & Run the Program**  
```sh
javac *.java  
java MainClass  
```

---

### **Usage**
- **Register Users:** Each role can insert records into the database.  
- **Authenticate Users:** Login validation via MySQL.  
- **Manage Data:** Counselors can edit `Courses` and `Universities`.  
- **Email Interaction:** Employees can retrieve and send emails to students.  

---

### **Contributing**
1. Fork the repository  
2. Create a new branch (`feature-xyz`)  
3. Commit your changes  
4. Push and open a PR  

---

### **License**
-NA-
