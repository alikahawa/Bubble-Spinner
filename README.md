# Bubble Spinner - Professional Edition 🎮

[![Version](https://img.shields.io/badge/version-2.0-blue.svg)](https://github.com/alikahawa/Bubble-Spinner)
[![JavaFX](https://img.shields.io/badge/JavaFX-17+-green.svg)](https://openjfx.io/)
[![Java](https://img.shields.io/badge/Java-12+-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/license-MIT-brightgreen.svg)](LICENSE)

A modern, professional bubble shooter game built with JavaFX, featuring beautiful UI design, advanced gameplay mechanics, and robust architecture.

## ✨ What's New in v2.0 - Professional Edition

This modernization brings the classic Bubble-Spinner game to professional standards with significant improvements across all aspects of the application.

### 🎨 **Visual & UI Improvements**

-   **Modern Material Design UI**: Complete visual overhaul with Material Design principles
-   **Professional CSS Theming**: Custom-designed modern theme with beautiful gradients and animations
-   **Responsive Layout**: Improved layouts that work well on different screen sizes
-   **Enhanced Typography**: Professional font choices and improved text hierarchy
-   **Smooth Animations**: Scale effects, hover states, and smooth transitions
-   **Glass Morphism Effects**: Modern glassmorphism design patterns
-   **Accessibility Features**: Better contrast, focus indicators, and keyboard navigation

### 🏗️ **Code Architecture & Quality**

-   **Comprehensive Documentation**: Extensive JavaDoc documentation for all classes and methods
-   **Modern Java Practices**: Updated code structure following current Java best practices
-   **Robust Error Handling**: Professional exception handling with logging
-   **Logging Framework**: Integrated Java logging for better debugging and monitoring
-   **Clean Code Principles**: Improved naming conventions and code organization
-   **Type Safety**: Enhanced type safety and null-safety patterns

### 🎮 **Game Features**

-   **Enhanced Game Controls**: Improved game interface with modern controls
-   **Better Visual Feedback**: Enhanced bubble animations and game state indicators
-   **Improved Scoreboard**: Modern leaderboard with statistics and filtering options
-   **Professional Game Flow**: Smooth transitions between game states
-   **Enhanced User Profiles**: Better player profile management and display

### 🛠️ **Technical Improvements**

-   **Resource Management**: Better handling of images, sounds, and other resources
-   **Scene Management**: Improved scene loading with fallback mechanisms
-   **Performance Optimization**: Better memory usage and performance improvements
-   **Modular Design**: More modular and maintainable code architecture

### 🗄️ **Database Integration**

-   **MariaDB/MySQL Integration**: Professional database setup for user accounts and scores
-   **Multi-Environment Support**: Development, testing, and production database configurations
-   **Docker Support**: Easy database setup using Docker Compose
-   **Automated Setup Scripts**: Shell scripts for quick database installation and configuration
-   **Connection Pooling Ready**: Architecture prepared for database connection pooling
-   **Comprehensive Database Documentation**: Complete setup and maintenance guides

---

## 🚀 Getting Started

### Prerequisites

-   **Java Development Kit (JDK) 12 or higher**
-   **JavaFX SDK** (included in project dependencies)
-   **Gradle** (wrapper included)
-   **MariaDB** (for database functionality)

### Installation & Running

1. **Clone the repository**

    ```bash
    git clone https://github.com/alikahawa/Bubble-Spinner.git
    cd Bubble-Spinner
    ```

2. **Build the project**

    ```bash
    ./gradlew build
    ```

3. **Run the game**

    ```bash
    ./gradlew run --args='--enable-preview'
    ```

    Or use the VS Code task: `Run Bubble-Spinner (Modern)`

### Development Setup

1. **VS Code Extensions** (recommended):

    - Extension Pack for Java
    - JavaFX Support
    - Gradle Language Support

2. **Database Setup**:
    
    **Option 1 - Quick Setup (Recommended):**
    ```bash
    # Install MariaDB and setup database
    ./setup_database.sh --install
    ./setup_database.sh --setup
    ```
    
    **Option 2 - Docker Setup (Easiest):**
    ```bash
    # Start database with Docker
    docker-compose up -d
    # Access phpMyAdmin at http://localhost:8080
    ```
    
    **Option 3 - Manual Setup:**
    ```bash
    # Install MariaDB (macOS)
    brew install mariadb
    brew services start mariadb
    
    # Run database script
    mysql -u root -p < database_setup.sql
    ```
    
    For detailed database setup instructions, see [DATABASE_SETUP.md](DATABASE_SETUP.md)
    - Create database schema as defined in the project
    - Update connection settings in `DatabaseConnection.java`

---

## 🎮 How to Play

### Game Objective

Clear all bubbles from the rotating grid by shooting bubbles of the same color to form groups of 3 or more.

### Controls

-   **Mouse Movement**: Aim the cannon
-   **Left Click**: Shoot bubble
-   **Space Bar**: Alternative shoot control
-   **P Key**: Pause/Resume game

### Game Mechanics

-   **Rotating Grid**: The bubble grid rotates, adding strategic complexity
-   **Physics**: Realistic bubble collision and physics
-   **Chain Reactions**: Clear multiple groups for higher scores
-   **Level Progression**: Multiple difficulty levels with increasing challenges

---

## 🏗️ Project Architecture

### Package Structure

```
src/main/java/
├── auth/                 # Authentication & Player Management
│   ├── DatabaseConnection.java
│   └── Player.java
├── game/                 # Core Game Logic
│   ├── Game.java
│   ├── Observer.java
│   ├── ScoreCalculator.java
│   └── UpdateType.java
├── grid/                 # Grid System & Collision Detection
│   ├── Grid.java
│   ├── GridCreator.java
│   ├── Collision.java
│   └── bubble/           # Bubble Types & Physics
└── gui/                  # User Interface Controllers
    ├── Main.java
    ├── SceneLoader.java
    ├── GameController.java
    ├── MainScreenController.java
    └── [Other Controllers...]

src/main/resources/
├── styles/               # Modern CSS Themes
│   └── modern-theme.css
├── *.fxml               # Original FXML Files
├── *Modern.fxml         # New Modern FXML Files
├── icons/               # Game Icons & Assets
├── sounds/              # Audio Assets
└── levels/              # Level Definitions
```

### Design Patterns Used

-   **Observer Pattern**: For game state updates and UI synchronization
-   **Factory Pattern**: For bubble creation and grid generation
-   **MVC Architecture**: Clear separation of Model, View, and Controller
-   **Scene Management**: Centralized scene loading and transition handling

---

## 🎨 Modern UI Features

### Color Palette

-   **Primary**: Modern Blue (#2196F3)
-   **Secondary**: Vibrant Orange (#FF9800)
-   **Backgrounds**: Gradient combinations with glass effects
-   **Text**: High contrast with accessibility considerations

### Component Library

-   **Buttons**: Multiple styles (Primary, Secondary, Outline)
-   **Cards**: Elevated surfaces with shadows
-   **Forms**: Modern input fields with floating labels
-   **Tables**: Professional data presentation
-   **Dialogs**: Consistent modal design

### Responsive Design

-   Flexible layouts that adapt to different window sizes
-   Scalable UI components
-   Optimized for both small and large screens

---

## 📊 Features

### 🔐 **Authentication System**

-   User login with database validation
-   Guest mode for quick play
-   Player profile management
-   Secure password handling

### 🎯 **Game Modes**

-   **Easy**: Perfect for beginners
-   **Normal**: Balanced challenge
-   **Hard**: For experienced players
-   **Extreme Bonus**: Ultimate challenge

### 📈 **Statistics & Leaderboards**

-   Personal best scores
-   Global leaderboard
-   Game statistics tracking
-   Achievement system (planned)

### 🎵 **Audio System**

-   Sound effects for game actions
-   Background music support
-   Volume controls
-   Audio feedback for UI interactions

### 💾 **Save System**

-   Automatic progress saving
-   Player preferences storage
-   Score history tracking
-   Settings persistence

---

## 🛠️ Development

### Building from Source

```bash
# Clean build
./gradlew clean build

# Run tests
./gradlew test

# Generate documentation
./gradlew javadoc

# Create distribution
./gradlew distZip
```

### Code Quality Tools

The project includes several code quality tools:

-   **Checkstyle**: Code style enforcement
-   **SpotBugs**: Static analysis for bug detection
-   **PMD**: Code quality analysis
-   **JaCoCo**: Test coverage reporting

Run quality checks:

```bash
./gradlew check
```

### Contributing

We welcome contributions! Please follow these guidelines:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

Please ensure your code follows the existing style and includes appropriate documentation.

---

## 📚 Documentation

### JavaDoc Documentation

Complete API documentation is available. Generate it locally:

```bash
./gradlew javadoc
open build/docs/javadoc/index.html
```

### Architecture Documentation

-   [Design Patterns Used](docs/architecture.md)
-   [Game Logic Flow](docs/game-logic.md)
-   [UI Component Guide](docs/ui-components.md)
-   [Database Schema](docs/database.md)

---

## 🐛 Known Issues & Troubleshooting

### Common Issues

1. **JavaFX Module Issues**

    - Ensure JavaFX is properly configured
    - Use `--enable-preview` flag when running

2. **Database Connection**

    - Verify MariaDB is running
    - Check connection settings in `DatabaseConnection.java`

3. **Performance**
    - Close unnecessary applications
    - Ensure adequate system memory

### Reporting Bugs

Please use the [GitHub Issues](https://github.com/alikahawa/Bubble-Spinner/issues) page to report bugs or request features.

---

## 🚀 Roadmap

### Upcoming Features (v2.1)

-   [ ] Online multiplayer support
-   [ ] Achievement system
-   [ ] Custom level editor
-   [ ] Power-ups and special bubbles
-   [ ] Daily challenges
-   [ ] Cloud save synchronization

### Long-term Goals (v3.0)

-   [ ] Mobile version (Android/iOS)
-   [ ] VR support
-   [ ] AI-powered difficulty adjustment
-   [ ] Tournaments and competitions

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👥 Contributors

### Original Development Team (v1.0)

-   **Team SEM-13** - _Initial development and core game mechanics_

### Modernization (v2.0)

-   **Ali Kahawati** - _Lead Developer - Professional Edition Modernization_
    -   UI/UX redesign and modernization
    -   Code architecture improvements
    -   Documentation and quality enhancements

---

## 🙏 Acknowledgments

-   JavaFX community for excellent documentation and examples
-   Material Design guidelines for UI inspiration
-   The original Bubble Shooter concept creators
-   All contributors and testers who helped improve the game

---

## 📞 Support

-   **GitHub Issues**: [Report bugs or request features](https://github.com/alikahawa/Bubble-Spinner/issues)
-   **Documentation**: Check our comprehensive docs in the `/docs` folder
-   **Email**: [Contact the development team](mailto:dev@bubble-spinner.com)

---

**Made with ❤️ by the Bubble-Spinner development team**

_Enjoy the game and happy bubble popping! 🎯_
