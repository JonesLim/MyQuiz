# **Quiz Application**

## **Overview**

This project is a **Quiz Application** where teachers can create accounts, manage student groups, and create quizzes with timed multiple-choice questions. Students can join quizzes using a unique Quiz ID, participate in them, and view their scores and rankings compared to other participants.

## **Table of Contents**

1. [Features](#features)
2. [Architecture](#architecture)
3. [UI/UX Design](#uiux-design)
4. [Setup Instructions](#setup-instructions)
5. [Usage Instructions](#usage-instructions)
6. [CSV Import](#csv-import)
7. [Screenshots](#screenshots)
9. [Project Structure](#project-structure)
9. [Technologies Used](#technologies-used)

## **Features**

### **User Registration and Roles**

- **Teacher and Student Roles:** Users can register and choose a role as either a teacher or a student.
- **Login and Authentication:** Secure authentication system allowing users to log in and manage their activities.

### **Teacher Dashboard**

- **Create Quizzes:** Teachers can create quizzes with unique Quiz IDs.
- **Manage Student Groups:** Teachers can create and manage groups of students.
- **Set Quiz Timings:** Teachers can set and update quiz timings, ensuring timed assessments.

### **Student Home Screen**

- **Join Quiz:** Students can join quizzes using a unique Quiz ID shared by the teacher.
- **View Upcoming Quizzes:** Students can see the next quiz time on their home screen.

### **Quiz Participation**

- **Timed Quizzes:** Students participate in timed quizzes with multiple-choice questions.
- **Auto Submission:** If the time runs out, the quiz is automatically submitted.
- **Score and Ranking:** After completing the quiz, students can view their score and rank compared to other participants.

### **CSV Import**

- **Bulk Quiz Creation:** Teachers can import quiz data from CSV files, including quiz names, descriptions, time limits, and more.

## **Architecture**

The app follows the **MVVM (Model-View-ViewModel)** architectural pattern to ensure separation of concerns, modularity, and scalability.

### **Data Model**

- **User:** Represents user data, including role (Teacher/Student), name, email, etc.
- **Quiz:** Represents quiz data, including questions, answers, time limits, etc.

### **Data Management**

- **Firebase Firestore:** For managing user data, quizzes, and real-time synchronization.

### **Dependency Injection**

- **Hilt:** Used for dependency injection to improve the modularity of the app.

## **UI/UX Design**

The app's design follows **Material Design** guidelines for a clean and intuitive interface. The design prioritizes user-friendly navigation and responsiveness across different devices.

**Figma Prototype**: https://www.figma.com/design/PMgm3uMRousE2T8c8wUhLK/Quiz-App?node-id=0-1&m=dev&t=YezlR4UOReO041sp-1

## **Setup Instructions**

### **Prerequisites**

- **Android Studio:** Make sure you have the latest version installed.
- **Firebase Project:** Create a Firebase project and connect it to the app.
- **Git:** Ensure Git is installed for version control.

### **Installation**

1. **Clone the repository:**

    ```bash
    git clone https://github.com/username/quiz-application.git
    ```

2. **Open in Android Studio:**

    Open the project in Android Studio.

3. **Connect Firebase:**

    Add the `google-services.json` file to your project.
    Set up Firebase Authentication and Firestore.

4. **Build the project:**

    Sync Gradle files and build the project.

## **Usage Instructions**

- **Registration:** Register as a teacher or a student.
- **Teacher Dashboard:**
  - Create quizzes.
  - Manage student groups.
  - Set quiz timings.
  - Import quizzes using CSV files.
- **Student Home Screen:**
  - Join quizzes using a Quiz ID.
  - View upcoming quizzes.
- **Participate in Quizzes:** Answer multiple-choice questions within the time limit.
- **View Results:** See scores and rankings after quiz submission.

## **CSV Import**

To import quizzes via CSV:

1. **Prepare the CSV file** with columns for quiz details such as name, description, time limits, and questions.
2. **Upload the CSV** from the teacher dashboard.
3. **Automatic Parsing:** The app will parse the CSV and create quizzes accordingly.

## **Screenshots**

Screenshots of the app to demonstrate the UI and functionality.

**Teacher Role:**
    
<p align="left">
  <img src="https://github.com/user-attachments/assets/11e4c42d-d619-4d62-aeff-8dfb91764a58" alt="Splash Screen" width="200"/>
  <img src="https://github.com/user-attachments/assets/c8754a2c-fb47-494e-b21c-b14aad5ea1aa" alt="Register Screen" width="200"/>
  <img src="https://github.com/user-attachments/assets/d815d51a-1998-417d-8833-4be8e5e0848e" alt="Login Screen" width="200"/>    
  <img src="https://github.com/user-attachments/assets/644591d7-ed93-4a38-9d63-ee597480f100" alt="Teacher Dashboard Screen" width="200"/>
  <img src="https://github.com/user-attachments/assets/d07186f2-d4ed-4e72-bff5-9de44a45e6d0" alt="Add Quiz Screen" width="200"/>
  <img src="https://github.com/user-attachments/assets/36bf9bdd-ca7d-48b0-8b12-138e89e86a27" alt="Leaderboard Screen" width="200"/>
  <img src="https://github.com/user-attachments/assets/5dd48251-f027-41f3-9e38-07319611c1b0" alt="Profile Screen" width="200"/>
</p>
    
**Student Role:**
    
<p align="left">
  <img src="https://github.com/user-attachments/assets/11e4c42d-d619-4d62-aeff-8dfb91764a58" alt="Splash Screen" width="200"/>
  <img src="https://github.com/user-attachments/assets/c8754a2c-fb47-494e-b21c-b14aad5ea1aa" alt="Register Screen" width="200"/>
  <img src="https://github.com/user-attachments/assets/d815d51a-1998-417d-8833-4be8e5e0848e" alt="Login Screen" width="200"/>    
  <img src="https://github.com/user-attachments/assets/86edae24-907e-4861-8142-93bd18c001f2" alt="Student Dashboard Screen" width="200"/>
  <img src="https://github.com/user-attachments/assets/f2efb9b8-92be-4bf2-ae84-7cf67a8f78dc" alt="Join Quiz Screen" width="200"/>
  <img src="https://github.com/user-attachments/assets/47a22e8b-ca0c-454f-b800-dc09fddfd2fb" alt="Quiz Screen" width="200"/>
  <img src="https://github.com/user-attachments/assets/99d08db2-d859-41e1-8fb2-dca7b3a7e8db" alt="Score Screen" width="200"/>
  <img src="https://github.com/user-attachments/assets/36bf9bdd-ca7d-48b0-8b12-138e89e86a27" alt="Leaderboard Screen" width="200"/>
  <img src="https://github.com/user-attachments/assets/c7704fe8-b486-4a22-a337-3975e60f3af3" alt="Profile Screen" width="200"/>
</p>

## **Project Structure**

- **app/:** Contains the source code.
- **data/:** Contains data models and repository classes.
- **ui/:** Contains UI-related files, including fragments and activities.
- **viewmodel/:** Contains ViewModel classes for managing UI-related data.

## **Technologies Used**

- **Kotlin**
- **Firebase Firestore**
- **Hilt for Dependency Injection**
- **MVVM Architecture**
- **Material Design**
- **CSV Parsing**
- **Lottie for Animations**
