# Cryptome – Android App [![CI](https://github.com/tranhuuluong/cryptome/actions/workflows/main.yml/badge.svg)](https://github.com/tranhuuluong/cryptome/actions/workflows/main.yml)

## 📄 Overview

Cryptome is a modern, modular Android application built with 100% **Jetpack Compose**.  
It showcases clean architecture, reactive state management, and a scalable multi-module structure.

This project also includes a custom **build-logic** module for shared Gradle configurations and a **GitHub Actions** workflow that runs linting, testing, and APK builds, helping maintain code quality as the app grows.

---

## 📚 Table of Contents
- [Overview](#-overview)
- [Features](#-features)
- [Screenshots](#screenshots)
- [Tech Stack](#-tech-stack)
- [Architecture & Module Structure](#-architecture--module-structure)

## 🚀 Features
- View crypto/fiat currencies with price, percentage change, and sparkline charts
- Toggle between Crypto and Fiat, “Tradable / Not Tradable” status indicators
- Control Panel for quick actions (Insert Data, Clear Data)
- Real-time search with recent and popular queries
- Detailed coin information with animated price chart build using custom Canvas drawings
- Light and Dark theme support

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

  <tr>
    <td align="center"><b>Coin Detail – Interaction Demo</b></td>
    <td align="center"><b>Coin Detail – Light Mode</b></td>
    <td align="center"><b>Coin Detail – Dark Mode</b></td>
  </tr>
  <tr>
    <td width="33%">
      <img src="screenshots/coin_detail.gif" alt="Coin Detail Interaction Demo" width="100%">
    </td>
    <td width="33%">
      <img src="screenshots/coin_detail_light.png" alt="Coin Detail Screen – Light Mode" width="100%">
    </td>
    <td width="33%">
      <img src="screenshots/coin_detail_dark.png" alt="Coin Detail Screen – Dark Mode" width="100%">
    </td>
  </tr>

</table>

## 🛠 Tech Stack
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-0095D5?logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Material 3](https://img.shields.io/badge/Material%203-6200EE?logo=materialdesign&logoColor=white)](https://developer.android.com/jetpack/androidx/releases/compose-material3)
[![Compose Navigation](https://img.shields.io/badge/Compose%20Navigation-1E88E5?logo=android&logoColor=white)](https://developer.android.com/jetpack/compose/navigation)
[![Koin](https://img.shields.io/badge/Koin-00ACC1?logo=koin&logoColor=white)](https://insert-koin.io/)
[![Ktor](https://img.shields.io/badge/Ktor-0288D1?logo=ktor&logoColor=white)](https://ktor.io/)
[![Room](https://img.shields.io/badge/Room-FF7043?logo=android&logoColor=white)](https://developer.android.com/jetpack/androidx/releases/room)
[![Coroutines](https://img.shields.io/badge/Coroutines-7C4DFF?logo=kotlinx.coroutines&logoColor=white)](https://github.com/Kotlin/kotlinx.coroutines)
[![Flow](https://img.shields.io/badge/Flow-26A69A?logo=kotlin&logoColor=white)](https://developer.android.com/kotlin/flow)
[![Turbine](https://img.shields.io/badge/Turbine-66BB6A?logo=testing-library&logoColor=white)](https://github.com/cashapp/turbine)
[![MockK](https://img.shields.io/badge/MockK-EC407A?logo=mockk&logoColor=white)](https://mockk.io/)
[![Robolectric](https://img.shields.io/badge/Robolectric-FFA726?logo=android&logoColor=white)](https://robolectric.org/)
[![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-2088FF?logo=githubactions&logoColor=white)](https://github.com/features/actions)
[![Gradle](https://img.shields.io/badge/Gradle-6BCB77?logo=gradle&logoColor=white)](#)

- **UI**: [Jetpack Compose](https://developer.android.com/jetpack/compose), [Material 3](https://developer.android.com/jetpack/androidx/releases/compose-material3), [Compose Navigation](https://developer.android.com/jetpack/compose/navigation)
- **DI**: [Koin](https://insert-koin.io/)
- **Networking**: [Ktor](https://ktor.io/)
- **Database**: [Room](https://developer.android.com/jetpack/androidx/releases/room)
- **Async**: [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://developer.android.com/kotlin/flow)
- **Testing**: [Turbine](https://github.com/cashapp/turbine), [MockK](https://mockk.io/), [Robolectric](https://robolectric.org/)
- **Custom `build-logic` module** – Centralized Gradle conventions
- **GitHub Actions** – CI pipeline for linting, tests, and APK builds

## 🏗 Architecture & Module Structure
Cryptome follows a Hybrid Modular Clean Architecture:
- Feature-first vertical slices: each feature contains its own `ui`, `domain`, `data`, and `di` layers.
- Core shared modules: reusable components such as design system, network, database, and shared domain logic.
- Build logic centralized in a dedicated Gradle conventions module.

### 📦 Module Overview
```plaintext
Cryptome
├── app                     # Main Android entry point, navigation host
├── build-logic             # Custom Gradle convention plugins
├── core
│   ├── common              # Utilities & extensions
│   ├── database            # Room DB, DAOs, entities
│   ├── designsystem        # Reusable Compose components & theming
│   ├── domain              # Shared domain models & use cases
│   ├── network             # DTOs, Ktor remote sources
│   └── ui                  # Shared UI models & helpers
└── feature                     
    ├── coin-detail             
    │   ├── di              # Koin modules for Coin Detail
    │   ├── domain          # Coin Detail-specific business logic
    │   ├── data            # Repositories, local/remote data sources
    │   └── ui              # Compose screens, state, events
    ├── home                    
    │   ├── di              # Koin modules for Home
    │   ├── domain          # Home-specific business logic
    │   ├── data            # Repositories, local/remote data sources
    │   └── ui              # Compose screens, state, events
    └── search                  
        ├── di              # Koin modules for Search
        ├── domain          # Search-specific business logic
        ├── data            # Repositories, local/remote data sources
        └── ui              # Compose screens, state, events
```