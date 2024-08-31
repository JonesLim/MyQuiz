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

### **Wireframes and Prototypes**

Wireframes and prototypes were created to visualize the layout and user flow before implementation.

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

Include screenshots or GIFs of the app to demonstrate the UI and functionality.

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
