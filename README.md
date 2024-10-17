
# Library Management System

## Overview

This is a Library Management System built with Spring Boot that allows users to view available books, borrow books, and return them. The system distinguishes between different user roles (Admin and User) to manage access rights effectively.

## Features

1. **User Authentication**: Role-based access control to differentiate between Admin and User roles.
2. **Book Management**:
   - Users can view a list of available books in the library.
   - Admins can add new books to the library.
3. **Borrowing Books**:
   - Users can borrow books, with a limit of 2 books at a time.
   - Users cannot borrow a book if no copies are available.
   - Only one copy of a book can be borrowed by a user at a time.
4. **Returning Books**: Users can return books, which updates the inventory accordingly.

## Technology Stack

- **Backend**: Spring Boot
- **Database**: H2 Database (or any other relational database)
- **Security**: Spring Security for user authentication and authorization
- **JPA**: Spring Data JPA for database interaction

## Getting Started

### Prerequisites

- Java 17 or higher
- Gradle
- IDE (IntelliJ IDEA)

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/Krutika1996/LibraryManagementApp/tree/master
