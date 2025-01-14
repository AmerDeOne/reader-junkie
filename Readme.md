# Reader-Junkie

Reader-Junkie is an Android app built for book lovers to explore, review, and track their reading progress. It is designed using modern Android development tools such as Kotlin, Jetpack Compose, Firebase, and Firestore for a seamless and responsive experience.

## Tools & Technologies Used

- **Android Studio**: The official IDE for Android development.
- **Kotlin**: A modern programming language for Android development.
- **Jetpack Compose**: A modern toolkit for building native UIs in Android.
- **Firebase**: A comprehensive platform for building and managing mobile apps.
- **Firestore**: Firebase's NoSQL cloud database to store user data and app content.
- **Material Design**: For consistent and beautiful UI components.

## Features

- **User Authentication**: Sign up and log in with Firebase Authentication.
- **Book Reviews**: View, write, and rate book reviews.
- **Reading Progress Tracker**: Keep track of books you are reading and progress updates.
- **Firestore Database**: User data and book reviews are stored securely in Firestore.

## Prerequisites

Before running the app, ensure you have the following installed:

- **Android Studio** (latest stable version)
- **Java Development Kit (JDK)** 8 or higher
- **Firebase Project**: You need to set up a Firebase project and connect it to the app (instructions below).

## Setup Instructions

### 1. Clone the Repository

Clone this repository to your local machine:

git clone https://github.com/AmerDeOne/reader-junkie.git

Launch Android Studio.
Select Open an existing project.
Navigate to the folder where you cloned the repository and open it.

#### Set Up Firebase
To configure Firebase for your project, follow these steps:

Go to the Firebase Console.
Create a new Firebase project or select an existing one.
In the Firebase console, navigate to Project Settings > General.
Add your Android app’s package name to the Your apps section.
Download the google-services.json file and add it to your project’s app/ folder.
In Android Studio, ensure Firebase is configured:

Firebase Authentication: Enable email/password authentication in the Firebase console.
Firestore: Set up Firestore and enable read/write access as needed.
#### Gradle Sync
Once you've added the google-services.json file, sync the project with Gradle files:

In Android Studio, click on File > Sync Project with Gradle Files.

#### Run the App
To run the app:

Connect an Android device or start an emulator.
Click on the Run button in Android Studio.
The app should build and launch on the connected device/emulator.
