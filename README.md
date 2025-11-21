# Cryptome – Android App [![CI](https://github.com/tranhuuluong/cryptome/actions/workflows/main.yml/badge.svg)](https://github.com/tranhuuluong/cryptome/actions/workflows/main.yml)

## 📄 Overview

Cryptome is a modern, modular Android application built entirely with **Jetpack Compose**.  
It showcases clean architecture, reactive state management, and a scalable multi-module structure.

This project also includes a custom **build-logic** module for shared Gradle configurations and a **GitHub Actions** workflow that runs linting, testing, and APK builds, helping maintain code quality as the app grows.

---

## Screenshots:
<table>
  <tr>
    <td align="center"><b>Home – Interaction Demo</b></td>
    <td align="center"><b>Home – Light Mode</b></td>
    <td align="center"><b>Home – Dark Mode</b></td>
  </tr>
  <tr>
    <td width="33%">
      <img src="screenshots/home.gif" alt="Home Interaction Demo" width="100%">
    </td>
    <td width="33%">
      <img src="screenshots/home_light.png" alt="Home Screen – Light Mode" width="100%">
    </td>
    <td width="33%">
      <img src="screenshots/home_dark.png" alt="Home Screen – Dark Mode" width="100%">
    </td>
  </tr>

  <tr>
    <td align="center"><b>Search – Interaction Demo</b></td>
    <td align="center"><b>Search – Light Mode</b></td>
    <td align="center"><b>Search – Dark Mode</b></td>
  </tr>
  <tr>
    <td width="33%">
      <img src="screenshots/search.gif" alt="Search Interaction Demo" width="100%">
    </td>
    <td width="33%">
      <img src="screenshots/search_light.png" alt="Search Screen – Light Mode" width="100%">
    </td>
    <td width="33%">
      <img src="screenshots/search_dark.png" alt="Search Screen – Dark Mode" width="100%">
    </td>
  </tr>
</table>

## ✨ Features
- View crypto/fiat currencies with price, percentage change, and sparkline charts
- Toggle between Crypto and Fiat, “Tradable / Not Tradable” status indicators
- Control Panel for quick actions (Insert Data, Clear Data)
- Real-time search with recent and popular queries

## 🛠 Tech Stack
- **[Jetpack Compose](https://developer.android.com/jetpack/compose)** – Declarative UI toolkit for Android
- **[Compose Material 3](https://developer.android.com/jetpack/androidx/releases/compose-material3)** – Modern Material Design components & theming
- **[Compose Navigation](https://developer.android.com/jetpack/compose/navigation)** – In-app navigation for Compose
- **[Koin](https://insert-koin.io/)** – Lightweight dependency injection
- **[Room](https://developer.android.com/jetpack/androidx/releases/room)** – Local database & persistence layer
- **[Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines)** & **[Flow](https://developer.android.com/kotlin/flow)** – Asynchronous programming and reactive streams
- **[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)** – Lifecycle-aware state management
- **[Turbine](https://github.com/cashapp/turbine)** – Flow testing utilities
- **[MockK](https://mockk.io/)** – Mocking framework for Kotlin
- **[Robolectric](https://robolectric.org/)** – JVM-based Android UI testing
- **Custom `build-logic` module** – Centralized Gradle conventions
- **GitHub Actions** – CI pipeline for linting, tests, and APK builds


## 🏛️ Project Structure
```plaintext
Cryptome
├── app                 # Main Android launch module
├── build-logic         # Custom Gradle convention plugins & shared config
├── core                
│   ├── common          # Utility classes & extensions
│   ├── database        # Room DB, DAOs, entities
│   ├── designsystem    # Reusable Compose UI components & theming
│   ├── domain          # Shared business models and use cases
│   ├── network         # DTOs, remote data sources
│   └── ui              # Shared UI resources, models, utilities
└── feature
    ├── home            # Home screen module
    └── search          # Search screen module