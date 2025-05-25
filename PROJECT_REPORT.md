# JavaFX Todo List Application Project Report

## Team Information
- Name: [Your Name]
- Student ID: [Your Student ID]
- GitHub Repository: [Your Repository URL]

## Project Overview
This project implements a Todo List application using JavaFX, providing a modern user interface for task management with authentication and data persistence capabilities. The application follows the Model-View-Controller (MVC) architectural pattern and utilizes file-based storage for maintaining user data and tasks.

## Development Environment & Technologies
- Java 24 & JavaFX 24.0.1
- Maven for dependency management
- FXML for UI layouts
- CSS for styling
- File I/O for data persistence

## Core Features & Implementation (70 Points)

### 1. Basic Functionality (30 points)
- **Task Management**
  - Create, read, update, and delete tasks
  - Task properties: title, description, due date, priority
  - Visual priority indicators (High: Red, Medium: Yellow, Low: Green)
  - Task completion tracking
  - Gold-themed task cards for better visibility

- **User Interface**
  - Responsive grid layout
  - Modern and intuitive design
  - Custom styling for controls
  - User-friendly form validation

### 2. Authentication System (20 points)
- User registration with username/password
- Secure login/logout functionality
- Session management
- Local user data storage
- Input validation and error handling

### 3. File Processing (20 points)
- Separate task files for each user
- Real-time data persistence
- Automatic task loading on login
- File format: `title|description|dueDate|priority|completed`

## Technical Architecture

### MVC Pattern Implementation
1. **Model**
   - `Task.java`: Task data structure
   - `TaskFileHandler.java`: Data persistence

2. **View**
   - `login.fxml`: Authentication interface
   - `main.fxml`: Task management interface
   - `addTask.fxml`: Task creation form
   - `main.css`: Application styling

3. **Controller**
   - `LoginController.java`: Authentication logic
   - `MainController.java`: Task operations
   - `AddTaskController.java`: Task creation

[Page 2]

## File Structure
```
todo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── controllers/
│   │   │   ├── models/
│   │   │   └── utils/
│   │   └── resources/
│   │       ├── views/
│   │       └── styles/
├── tasks/
└── pom.xml
```

## Testing & Quality Assurance

### Functional Testing
1. **Authentication**
   - Registration and login validation
   - Session management verification
   - Error handling for invalid credentials

2. **Task Management**
   - Task CRUD operations
   - Data persistence verification
   - UI responsiveness testing

3. **File Operations**
   - Data saving/loading
   - File access error handling
   - Data format validation

## Challenges & Solutions

1. **Task Persistence**
   - Challenge: Reliable file-based storage
   - Solution: Implemented robust TaskFileHandler with error handling

2. **User Session Management**
   - Challenge: Maintaining user context
   - Solution: Static user tracking with session management

3. **UI Design**
   - Challenge: Responsive task display
   - Solution: GridPane with ScrollPane implementation

## Future Improvements
1. Cloud synchronization
2. Task categories and tags
3. Search functionality
4. Email notifications
5. Enhanced security features

## Feature Implementation Table

| Feature | Status | Source Files |
|---------|--------|--------------|
| Basic Functionality | ✓ | MainController.java, Task.java |
| Authentication | ✓ | LoginController.java |
| File Processing | ✓ | TaskFileHandler.java |
| UI Design | ✓ | main.css, *.fxml |

## Screenshots
[Insert 2-3 key screenshots showing main features]

## Declaration
I declare that this project is my own work and has not been submitted in any form for another assignment. I have acknowledged all information sources used in this report.

Signature: _________________
Date: [Current Date] 