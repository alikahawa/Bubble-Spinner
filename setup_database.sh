#!/bin/bash

# ================================================
# Bubble-Spinner Database Setup Script
# ================================================
# This script helps set up the database for the Bubble-Spinner game
# Supports MariaDB and MySQL on various platforms
# 
# Usage: ./setup_database.sh [options]
# Options:
#   --help       Show this help message
#   --install    Install MariaDB (macOS/Linux)
#   --setup      Set up the database schema
#   --reset      Reset database (WARNING: deletes all data)
#   --test       Test database connection
# ================================================

set -e  # Exit on any error

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Default configuration
DB_HOST="localhost"
DB_PORT="3306"
DB_NAME="SEM"
DB_USER="bubble_spinner"
DB_PASS="bubble_game_2025!"
DB_ROOT_USER="root"

# Function to print colored output
print_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Function to check if MariaDB/MySQL is installed
check_database_installed() {
    if command -v mysql >/dev/null 2>&1; then
        print_success "MySQL/MariaDB client found"
        return 0
    else
        print_error "MySQL/MariaDB client not found"
        return 1
    fi
}

# Function to install MariaDB
install_mariadb() {
    print_info "Installing MariaDB..."
    
    if [[ "$OSTYPE" == "darwin"* ]]; then
        # macOS
        if command -v brew >/dev/null 2>&1; then
            print_info "Installing MariaDB using Homebrew..."
            brew install mariadb
            print_info "Starting MariaDB service..."
            brew services start mariadb
        else
            print_error "Homebrew not found. Please install Homebrew first:"
            echo "/bin/bash -c \"\$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)\""
            exit 1
        fi
    elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
        # Linux
        if command -v apt-get >/dev/null 2>&1; then
            # Ubuntu/Debian
            print_info "Installing MariaDB using apt..."
            sudo apt-get update
            sudo apt-get install -y mariadb-server mariadb-client
            sudo systemctl start mariadb
            sudo systemctl enable mariadb
        elif command -v yum >/dev/null 2>&1; then
            # CentOS/RHEL
            print_info "Installing MariaDB using yum..."
            sudo yum install -y mariadb-server mariadb
            sudo systemctl start mariadb
            sudo systemctl enable mariadb
        else
            print_error "Unsupported Linux distribution"
            exit 1
        fi
    else
        print_error "Unsupported operating system: $OSTYPE"
        exit 1
    fi
    
    print_success "MariaDB installation completed"
    print_warning "Please run 'mysql_secure_installation' to secure your installation"
}

# Function to test database connection
test_connection() {
    print_info "Testing database connection..."
    
    # Test with application user
    if mysql -h"$DB_HOST" -P"$DB_PORT" -u"$DB_USER" -p"$DB_PASS" -e "SELECT 1;" 2>/dev/null; then
        print_success "Application user connection successful"
    else
        print_warning "Application user connection failed"
    fi
    
    # Test basic connection
    print_info "Please enter root password when prompted:"
    if mysql -h"$DB_HOST" -P"$DB_PORT" -u"$DB_ROOT_USER" -p -e "SHOW DATABASES;" 2>/dev/null; then
        print_success "Root user connection successful"
        return 0
    else
        print_error "Database connection failed"
        return 1
    fi
}

# Function to setup database
setup_database() {
    print_info "Setting up Bubble-Spinner database..."
    
    if [ ! -f "database_setup.sql" ]; then
        print_error "database_setup.sql file not found!"
        print_info "Please make sure you're running this script from the project root directory."
        exit 1
    fi
    
    print_info "Please enter MySQL root password when prompted:"
    if mysql -h"$DB_HOST" -P"$DB_PORT" -u"$DB_ROOT_USER" -p < database_setup.sql; then
        print_success "Database setup completed successfully!"
        print_info "Database: $DB_NAME"
        print_info "Application user: $DB_USER"
        print_info "Application password: $DB_PASS"
    else
        print_error "Database setup failed!"
        exit 1
    fi
}

# Function to reset database
reset_database() {
    print_warning "This will DELETE ALL DATA in the database!"
    read -p "Are you sure you want to continue? (y/N): " -n 1 -r
    echo
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        print_info "Resetting database..."
        setup_database
    else
        print_info "Reset cancelled."
    fi
}

# Function to show help
show_help() {
    echo "Bubble-Spinner Database Setup Script"
    echo "======================================"
    echo
    echo "Usage: $0 [option]"
    echo
    echo "Options:"
    echo "  --help       Show this help message"
    echo "  --install    Install MariaDB (macOS/Linux)"
    echo "  --setup      Set up the database schema and sample data"
    echo "  --reset      Reset database (WARNING: deletes all data)"
    echo "  --test       Test database connection"
    echo
    echo "Examples:"
    echo "  $0 --install    # Install MariaDB"
    echo "  $0 --setup      # Create database and tables"
    echo "  $0 --test       # Test connection"
    echo
    echo "Configuration:"
    echo "  Database Host: $DB_HOST"
    echo "  Database Port: $DB_PORT"
    echo "  Database Name: $DB_NAME"
    echo "  App User: $DB_USER"
    echo
}

# Main script logic
case "${1:-}" in
    --help|-h)
        show_help
        ;;
    --install)
        install_mariadb
        ;;
    --setup)
        check_database_installed || exit 1
        setup_database
        ;;
    --reset)
        check_database_installed || exit 1
        reset_database
        ;;
    --test)
        check_database_installed || exit 1
        test_connection
        ;;
    "")
        print_info "Bubble-Spinner Database Setup"
        print_info "Use --help for usage information"
        echo
        print_info "Quick setup:"
        print_info "1. $0 --install  (if MariaDB not installed)"
        print_info "2. $0 --setup    (create database)"
        print_info "3. $0 --test     (verify setup)"
        ;;
    *)
        print_error "Unknown option: $1"
        show_help
        exit 1
        ;;
esac
