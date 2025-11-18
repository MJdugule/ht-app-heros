# 📱 Hello Android Heros
A multi‑module Android application fully written in Kotlin, following the MVI (Model–View–Intent) architecture pattern. 
This project demonstrates clean separation of concerns, scalability, and maintainability for modern Android development.
<br /><br />


# 🚀 Features
- Multi‑module setup for clear boundaries and reusability
  
- MVI architecture ensuring predictable state management
  
- Kotlin-first implementation with coroutines and Flow
  
- Jetpack libraries for modern Android development
  
- Proto DataStore for type‑safe persistence
  
- Local & remote data sources with repository pattern
<br /><br />


# 🏗️ Project Structure
|: Module :|: Responsibility :|
<br /><br />


🧩 Architecture Overview
The project follows MVI (Model–View–Intent):

- **Model** → Represents the state of the UI.

- **View** → Displays the state and forwards user interactions.

- **Intent** → Captures user actions and translates them into events.

- **Domain layer** → Encapsulates business logic with use cases.

- **Data layer** → Provides data from local (Room, DataStore) and remote (API) sources.
<br /><br />


# 🛠️ Tech Stack
- **Language:** Kotlin

- **UI:** Jetpack Compose / XML (depending on your setup)

- **Architecture:** MVI + Clean Architecture

- **Async:** Kotlin Coroutines + Flow

- **Persistence:** Room, Proto DataStore

- **Networking:** Retrofit / OkHttp

- **Dependency Injection:** Hilt / Koin

- **Testing:** JUnit, Espresso, MockK
<br /><br />


# 📦 Getting Started
> Prerequisites

- Android Studio (latest stable version)

- JDK 17+

- Gradle (bundled with Android Studio)
<br /><br />

# Setup
1. Clone the repository:

```
git clone https://github.com/yourusername/yourproject.git
```
2. Open in Android Studio.

3. Sync Gradle and build the project.

4. Run the app on an emulator or device.
<br /><br />

# 🧪 Testing
Run unit and instrumentation tests:
```
./gradlew test
./gradlew connectedAndroidTest
```
<br /><br />

# 📂 Module Dependency Graph
```
graph TD
    app --> presentation
    app --> theme
    presentation --> domain
    domain --> data
```
<br /><br />


# 🤝 Contributing
Contributions are welcome!

- Fork/Clone the repo

- Create a feature branch

- Submit a pull request
<br /><br />
