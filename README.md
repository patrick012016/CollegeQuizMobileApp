 
# College Quiz [Quizazu] - Android Client 📱

**Quizazu Android Client** is the mobile frontend for the **[College Quiz (Quizazu)](https://github.com/Lettulouz/CollegeQuiz)**. Designed as an interactive, real-time quiz platform, the client application enables synchronous participation in live quiz sessions, supports various question types, and real-time state management for competitive gameplay.

As part of the broader Quizazu architecture which integrates a web client (ReactJS/Razor) and a centralized backend service (ASP.NET C# MVC & WebAPI) - this repository contains the source code for the native Android application implemented in Java.


##  Features

* **Secure authentication & Auto-login:** Users log in via a REST API, utilizing JWT (JSON Web Tokens) to secure API calls and quiz room entries. The resulting token is securely stored locally to enable auto-login, bypassing the login screen on subsequent app launches.
* **Real-time gameplay sync:** Powered by Microsoft SignalR for low-latency bidirectional communication. The app reacts instantly to server hubs like `onGame`, `onQuestionTimer`, `onCorrectAnswer`, and `onQuestionResult`.
* **Dynamic game modes:** The app dynamically renders native UI fragments based on the incoming question type. Supported modes include:
    * `SINGLE_FOUR_ANSWERS` (4 Options)
    * `MULTIPLE_FOUR_ANSWERS` (Multiple Choice)
    * `SINGLE_SIX_ANSWERS` (6 Options)
    * `TRUE_FALSE` (True / False)
    * `RANGE` (Range Slider Questions)

* **Quick join via QR code:** Integrated with the `Quickie` library for fast lobby joining via camera.
* **Live timers & Results:** Synchronized countdown timers and immediate visual feedback on correct/incorrect answers directly on the device.

## Built with

### Core android

* **Java 11:** Main programming language.
* **Android SDK:** Minimum SDK 31, Target SDK 33.
* **AndroidX & Material Design:** For modern UI components (`ConstraintLayout`, `GridLayout`, `RangeSlider`, `CardView`).

### 3rd party libraries

* [**SignalR Client** (`com.microsoft.signalr:7.0.5`)](https://learn.microsoft.com/en-us/aspnet/core/signalr/java-client) - Full-duplex websocket communication for real-time game state updates.
* [**OkHttp3** (`com.squareup.okhttp3:okhttp:4.10.0`)](https://square.github.io/okhttp/) - HTTP client for initial REST API handshakes and joining rooms.
* [**Gson** (`com.google.code.gson:2.10.1`)](https://github.com/google/gson) - Serialization and deserialization of JSON web sockets payloads and DTOs.
* [**Quickie** (`io.github.g00fy2.quickie-bundled:1.6.0`)](https://github.com/G00fY2/quickie) - High-performance QR code scanning for joining quizzes.

## Architecture & App flow

The application flow is broken down into modular, core activities:

1. **`StartActivity`**: Displays a splash screen while the app initializes.
2. **`MainActivity`**: Manages user authorization. It utilizes `MotionLayout` for UI state transitions during network calls and handles invalid credentials.
3. **`MenuActivity`**: The main hub post-login. It hosts the `BottomNavigationView` to swap out main application fragments and manages the logout sequence.
4. **`LobbyActivity`**: Connects to the specific quiz room using a JWT-secured OkHttp request, then spins up the SignalR hub connection. It maintains a live countdown until the host starts the game.
5. **`Quiz_Activity`**: The core gameplay engine. It listens to websocket events and dynamically injects the appropriate UI Fragments (e.g., `FourAnswersFragment`, `TrueFalseFragment`, `SliderFragment`) into its frame layouts depending on the `QuizDto` payload.
6. **`ResultActivity`**: Shows scorecards with point gains and streaks between questions or at the end of the quiz, using `ResultDto` data.

## Prerequisites & Setup

To build and run this project, you will need:

* **Android Studio** (Electric Eel or newer recommended)
* **JDK 11**
* An active instance of the **Quizazu ASP.NET WebAPI** running locally or deployed (e.g., on MS Azure) to act as the SignalR broker.

1. Clone the repository:
    ```bash
    git clone https://github.com/patrick012016/CollegeQuizMobileApp.git
    ```


2. Open the project in Android Studio.
3. Ensure your backend environment variables in your `Constants` class point to your active ASP.NET server.
4. Sync Gradle and run the app on an emulator or physical device (API 31+).

## License

This project is licensed under the **[MIT License](https://choosealicense.com/licenses/mit/)**.
 
