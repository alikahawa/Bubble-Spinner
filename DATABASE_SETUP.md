# Bubble-Spinner Database Setup Guide

This guide explains how to set up the database for the Bubble-Spinner JavaFX game. The application uses MariaDB/MySQL to store user accounts and game scores.

## 🎯 Quick Start

### Option 1: Automated Setup (Recommended)
```bash
# Make the script executable (if not already)
chmod +x setup_database.sh

# Install MariaDB (if not installed)
./setup_database.sh --install

# Set up the database
./setup_database.sh --setup

# Test the connection
./setup_database.sh --test
```

### Option 2: Docker Setup (Easiest for Development)
```bash
# Start the database using Docker Compose
docker-compose up -d

# Check if containers are running
docker-compose ps

# View logs
docker-compose logs mariadb

# Access phpMyAdmin at http://localhost:8080
# Username: bubble_spinner
# Password: bubble_game_2025!
```

### Option 3: Manual Setup
```bash
# Install MariaDB (macOS)
brew install mariadb
brew services start mariadb

# Install MariaDB (Ubuntu/Debian)
sudo apt-get update
sudo apt-get install mariadb-server
sudo systemctl start mariadb

# Run the SQL script
mysql -u root -p < database_setup.sql
```

## 🗄️ Database Structure

### Tables

#### PLAYER Table
Stores user account information:
```sql
CREATE TABLE PLAYER (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

#### SCORE Table
Stores game scores:
```sql
CREATE TABLE SCORE (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userName VARCHAR(50) NOT NULL,
    score INT NOT NULL DEFAULT 0,
    achieved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## ⚙️ Configuration

### Database Environments
The application supports multiple environments configured in `src/main/resources/config/database.properties`:

- **local**: Local development database
- **test**: Testing database
- **prod**: Production database (current remote setup)

### Default Credentials

#### Local Development
- **Host**: localhost:3306
- **Database**: SEM
- **Username**: bubble_spinner
- **Password**: bubble_game_2025!

#### Docker Setup
- **Host**: localhost:3306
- **Database**: SEM
- **Username**: bubble_spinner
- **Password**: bubble_game_2025!
- **Root Password**: root_password_2025

#### Demo Account (Always Available)
- **Username**: demo
- **Password**: demo

## 🚀 Application Integration

### Using the DatabaseConnection Class

The original `DatabaseConnection.java` class is still functional, but we've also created `DatabaseConnectionModern.java` with enhanced features:

```java
// Authentication
boolean isValid = DatabaseConnection.authentication("username", "password");

// Registration
boolean registered = DatabaseConnection.registerUser("username", "password", "nickname");

// Save score
DatabaseConnection.writeScore("username", 15420);

// Get high scores
ResultSet scores = DatabaseConnection.retrieveScoreBoard();

// Get player's max score
String maxScore = DatabaseConnection.retrieveMaxScoreOfPlayer("username");
```

### Switching Database Configurations

To switch between environments, modify `database.properties`:
```properties
# Set the environment: local, test, or prod
db.environment=local
```

## 🛠️ Troubleshooting

### Common Issues

#### 1. Connection Refused
```bash
# Check if MariaDB is running
brew services list | grep mariadb  # macOS
systemctl status mariadb          # Linux
docker-compose ps                  # Docker
```

#### 2. Access Denied
```bash
# Reset MySQL root password (if needed)
sudo mysql_secure_installation

# Or for Docker
docker-compose exec mariadb mysql -u root -p
```

#### 3. Database Doesn't Exist
```bash
# Run the setup script
./setup_database.sh --setup

# Or manually create
mysql -u root -p < database_setup.sql
```

#### 4. Port Already in Use
```bash
# Check what's using port 3306
lsof -i :3306

# For Docker, change the port in docker-compose.yml
ports:
  - "3307:3306"  # Use port 3307 instead
```

### Testing Database Connection

```bash
# Test connection with setup script
./setup_database.sh --test

# Manual test
mysql -h localhost -u bubble_spinner -p -e "SELECT COUNT(*) FROM SEM.PLAYER;"

# Docker test
docker-compose exec mariadb mysql -u bubble_spinner -p -e "USE SEM; SELECT * FROM PLAYER;"
```

## 🔧 Development Tools

### phpMyAdmin (Docker Only)
Access the web-based database management tool:
- **URL**: http://localhost:8080
- **Username**: bubble_spinner
- **Password**: bubble_game_2025!

### Command Line Tools
```bash
# Connect to database
mysql -h localhost -u bubble_spinner -p SEM

# View tables
SHOW TABLES;

# View users
SELECT username, nickname FROM PLAYER;

# View high scores
SELECT userName, score FROM SCORE ORDER BY score DESC LIMIT 5;
```

## 📊 Sample Data

The setup script automatically creates:
- 5 demo user accounts
- Sample high scores for testing
- Proper database indexes for performance

## 🔒 Security Considerations

### For Production
1. **Change default passwords** in `database.properties`
2. **Use environment variables** for sensitive data
3. **Enable SSL/TLS** for database connections
4. **Implement password hashing** (currently plain text)
5. **Restrict database user permissions**
6. **Regular backups** and monitoring

### Password Security
Current implementation stores passwords in plain text. For production, consider:
```java
// Use BCrypt or similar for password hashing
String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
boolean isValid = BCrypt.checkpw(password, hashedPassword);
```

## 📝 Maintenance

### Backup
```bash
# Create backup
mysqldump -u root -p SEM > bubble_spinner_backup.sql

# Restore backup
mysql -u root -p SEM < bubble_spinner_backup.sql
```

### Docker Maintenance
```bash
# View logs
docker-compose logs mariadb

# Restart database
docker-compose restart mariadb

# Stop and remove
docker-compose down -v  # -v removes volumes (data)
```

## 📞 Support

If you encounter any issues:
1. Check the troubleshooting section above
2. Verify your MariaDB installation
3. Ensure all credentials are correct
4. Check database logs for specific error messages
5. Test with the demo account first

## 🔄 Migration from Remote Database

To migrate from the current remote database (84.104.121.142) to local:
1. Export data from remote database
2. Set up local database using this guide
3. Import data to local database
4. Update `database.properties` to use local environment
