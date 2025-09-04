# ToriNotes App 📒

A simple yet modern **Notes application** written in **Kotlin**, built with **minimal dependencies**.  
The app demonstrates **Clean Architecture**, **Jetpack Compose UI**, and persistence with **Room + Paging 3**.

---

## ✨ Features
- 📄 **List Notes** with pagination using [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3).
- ➕ **Create a new note**.
- 📝 **Update existing notes**.
- ❌ **Delete notes**.
- 💾 Notes are stored locally in a **Room database**.

---

## 📱 Screenshots
<img width="270" height="600" alt="Image" src="https://github.com/phamthombg/tori_note/blob/main/screenshots/note_list.png" />

<img width="270" height="600" alt="Image" src="https://github.com/phamthombg/tori_note/blob/main/screenshots/note_detail.png" />

---

## Installation note

- If you're facing this error while installing dependencies: **_The project is using an incompatible version (AGP 8.13.0) of the Android Gradle plugin**
-> Please update your Android Studio to **latest version**!

---

## 🛠 Tech Stack
- **Language**: [Kotlin](https://kotlinlang.org/)
- **UI**: [Jetpack Compose](https://developer.android.com/jetpack/compose)
- **Architecture**: [Clean Architecture](https://developer.android.com/topic/architecture) (Domain, Data, Presentation layers)
- **Persistence**: [Room](https://developer.android.com/training/data-storage/room)
- **Pagination**: [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3)
- **Coroutines & Flow**: For async + reactive streams
- **Testing**: JUnit, kotlinx-coroutines-test, MockK (no extra DI frameworks)

---

## 📐 Project Structure

- **Data layer**: Handles local DB with Room, and provides `Flow<PagingData<Note>>`.
- **Domain layer**: Defines use cases (`AddNote`, `DeleteNote`, `UpdateNote`, `GetNotes`, etc.).
- **Presentation layer**: Implements ViewModels and Compose UI screens.

<img src="https://developer.android.com/static/topic/libraries/architecture/images/mad-arch-overview-data.png" alt="Demo Note" width="300"/>

---

## ✅ Unit Testing
The project includes **unit tests** for:
- Use cases, Repositories (proving Clean Architecture separation).
- ViewModels (proving MVI state & effect correctness).

Tests are written with:
- **JUnit** for assertions
- **MockK** for mocking dependencies
- **kotlinx-coroutines-test** for coroutine + Flow testing

---

## 🔑 Key Decisions
- **DI framework**: Use Hilt.
- **MVVM**: Ensures predictable, testable UI state management.
- **Paging 3 + Room**: Efficiently loads notes with built-in paging support.
