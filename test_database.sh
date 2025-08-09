#!/bin/bash

# ================================================
# Database Connection Test Script
# ================================================
# This script tests the database connection and functionality
# for the Bubble-Spinner application

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

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

# Test database connection
test_database_connection() {
    print_info "Testing database connection..."
    
    DB_HOST="localhost"
    DB_PORT="3306"
    DB_NAME="SEM"
    DB_USER="bubble_spinner"
    DB_PASS="bubble_game_2025!"
    
    # Test connection
    if mysql -h"$DB_HOST" -P"$DB_PORT" -u"$DB_USER" -p"$DB_PASS" -e "SELECT 1;" 2>/dev/null; then
        print_success "Database connection successful"
    else
        print_error "Database connection failed"
        return 1
    fi
    
    # Test tables exist
    if mysql -h"$DB_HOST" -P"$DB_PORT" -u"$DB_USER" -p"$DB_PASS" -e "USE $DB_NAME; SHOW TABLES;" 2>/dev/null | grep -E "(PLAYER|SCORE)" >/dev/null; then
        print_success "Required tables found"
    else
        print_error "Required tables not found"
        return 1
    fi
    
    # Test sample data
    PLAYER_COUNT=$(mysql -h"$DB_HOST" -P"$DB_PORT" -u"$DB_USER" -p"$DB_PASS" -e "USE $DB_NAME; SELECT COUNT(*) FROM PLAYER;" 2>/dev/null | tail -n1)
    SCORE_COUNT=$(mysql -h"$DB_HOST" -P"$DB_PORT" -u"$DB_USER" -p"$DB_PASS" -e "USE $DB_NAME; SELECT COUNT(*) FROM SCORE;" 2>/dev/null | tail -n1)
    
    print_info "Players in database: $PLAYER_COUNT"
    print_info "Scores in database: $SCORE_COUNT"
    
    if [ "$PLAYER_COUNT" -gt 0 ] && [ "$SCORE_COUNT" -gt 0 ]; then
        print_success "Sample data found"
    else
        print_warning "No sample data found - this might be expected for a fresh setup"
    fi
    
    return 0
}

# Test application build
test_application_build() {
    print_info "Testing application build..."
    
    if ./gradlew clean build --quiet; then
        print_success "Application builds successfully"
    else
        print_error "Application build failed"
        return 1
    fi
    
    return 0
}

# Test database configuration files
test_configuration_files() {
    print_info "Checking configuration files..."
    
    # Check database.properties
    if [ -f "src/main/resources/config/database.properties" ]; then
        print_success "Database configuration file found"
    else
        print_error "Database configuration file missing"
        return 1
    fi
    
    # Check database setup script
    if [ -f "database_setup.sql" ]; then
        print_success "Database setup script found"
    else
        print_error "Database setup script missing"
        return 1
    fi
    
    # Check docker-compose
    if [ -f "docker-compose.yml" ]; then
        print_success "Docker Compose file found"
    else
        print_warning "Docker Compose file missing"
    fi
    
    return 0
}

# Main test execution
main() {
    echo "========================================"
    echo "Bubble-Spinner Database Test Suite"
    echo "========================================"
    echo
    
    # Track test results
    TOTAL_TESTS=0
    PASSED_TESTS=0
    
    # Test configuration files
    TOTAL_TESTS=$((TOTAL_TESTS + 1))
    if test_configuration_files; then
        PASSED_TESTS=$((PASSED_TESTS + 1))
    fi
    echo
    
    # Test application build
    TOTAL_TESTS=$((TOTAL_TESTS + 1))
    if test_application_build; then
        PASSED_TESTS=$((PASSED_TESTS + 1))
    fi
    echo
    
    # Test database connection
    TOTAL_TESTS=$((TOTAL_TESTS + 1))
    if test_database_connection; then
        PASSED_TESTS=$((PASSED_TESTS + 1))
    fi
    echo
    
    # Results
    echo "========================================"
    echo "Test Results"
    echo "========================================"
    print_info "Total tests: $TOTAL_TESTS"
    print_success "Passed: $PASSED_TESTS"
    
    if [ $PASSED_TESTS -eq $TOTAL_TESTS ]; then
        print_success "All tests passed! ✅"
        echo
        print_info "Your Bubble-Spinner database setup is ready!"
        print_info "You can now run the application with: ./gradlew run --args='--enable-preview'"
        return 0
    else
        FAILED_TESTS=$((TOTAL_TESTS - PASSED_TESTS))
        print_error "Failed: $FAILED_TESTS ❌"
        echo
        print_info "Some tests failed. Please check the output above for issues."
        print_info "Refer to DATABASE_SETUP.md for troubleshooting help."
        return 1
    fi
}

# Run main function
main "$@"
