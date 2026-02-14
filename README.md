# SmartTourism - Interactive Travel & Discovery Platform

**SmartTourism** is a native Android application designed to transform how travelers discover and navigate new cities. By integrating real-time geospatial data with a rich, interactive user interface, it provides users with curated travel guides, navigation assistance, and dynamic content.

## 🚀 Key Features

-   **Location-Aware Discovery**: Seamless integration with **Google Maps SDK** to visualize points of interest and provide turn-by-turn navigation.
-   **Dynamic Content Engine**: Robust parsing of complex JSON datasets to render city details, ratings, and best visit times dynamically.
-   **Interactive Media**: Custom-built **ViewPager2** carousels with auto-slide functionality for an immersive visual experience.
-   **City & Place Filtering**: Efficient search and filtering algorithms to help users find specific destinations instantly.
-   **Smart Recommendations**: Curated lists of top cities and nearby attractions based on user location and preferences.

## 🛠️ Tech Stack

-   **Language**: Java / Android SDK
-   **Architecture**: MVVM (Model-View-ViewModel) pattern
-   **Maps & Location**: Google Maps Android SDK, Google Places API
-   **UI Components**: Material Design, RecyclerView, ViewPager2, CardView
-   **Data Handling**: JSON Parsing, Asset Management

## 📂 Project Structure

-   `MapActivity`: Handles Google Maps integration and marker rendering.
-   `MainActivity`: Core navigation hub featuring the media carousel and search.
-   `DetailsActivity`: Displays comprehensive information about specific tourist spots.
-   `adapters/`: Custom adapters for efficient list and carousel rendering.
-   `models/`: Data models reflecting the JSON structure for type safety.

## 📦 Setup & Installation

1.  **Clone the Repository**:
    ```bash
    git clone https://github.com/Vamshikrishna779/SmartTourism.git
    ```
2.  **Open in Android Studio**:
    -   Select "Open an existing Android Studio project".
    -   Navigate to the cloned directory.
3.  **Configure Maps API Key**:
    -   Obtain an API Key from the Google Cloud Console.
    -   Add it to your `local.properties` or `AndroidManifest.xml` (ensure this file is not committed if using `local.properties`).
4.  **Build & Run**:
    -   Sync Gradle files.
    -   Run on an Emulator or Physical Device.

## 🤝 Contributing

Contributions to improve data accuracy or add new features are welcome. Please submit a Pull Request.

## 📄 License

This project is open-source and available under the MIT License.
