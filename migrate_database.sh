#!/bin/bash

# ================================================
# Database Migration Script
# ================================================
# This script helps migrate from the remote database to local setup
# 
# WARNING: This script requires access to the remote database
# Use with caution and ensure you have proper permissions

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

# Remote database configuration (from original setup)
REMOTE_HOST="84.104.121.142"
REMOTE_USER="sem"
REMOTE_PASS="123456"
REMOTE_DB="SEM"

# Local database configuration
LOCAL_HOST="localhost"
LOCAL_USER="bubble_spinner"
LOCAL_PASS="bubble_game_2025!"
LOCAL_DB="SEM"

# Function to test remote connection
test_remote_connection() {
    print_info "Testing connection to remote database..."
    
    if mysql -h"$REMOTE_HOST" -u"$REMOTE_USER" -p"$REMOTE_PASS" -e "SELECT 1;" 2>/dev/null; then
        print_success "Remote database connection successful"
        return 0
    else
        print_error "Cannot connect to remote database"
        print_warning "This might be expected if the remote server is no longer accessible"
        return 1
    fi
}

# Function to test local connection
test_local_connection() {
    print_info "Testing connection to local database..."
    
    if mysql -h"$LOCAL_HOST" -u"$LOCAL_USER" -p"$LOCAL_PASS" -e "SELECT 1;" 2>/dev/null; then
        print_success "Local database connection successful"
        return 0
    else
        print_error "Cannot connect to local database"
        print_info "Please run './setup_database.sh --setup' first"
        return 1
    fi
}

# Function to export data from remote database
export_remote_data() {
    print_info "Exporting data from remote database..."
    
    # Export PLAYER table
    if mysql -h"$REMOTE_HOST" -u"$REMOTE_USER" -p"$REMOTE_PASS" -e "SELECT * FROM $REMOTE_DB.PLAYER;" > remote_players.sql 2>/dev/null; then
        print_success "Player data exported"
    else
        print_warning "Could not export player data"
        return 1
    fi
    
    # Export SCORE table
    if mysql -h"$REMOTE_HOST" -u"$REMOTE_USER" -p"$REMOTE_PASS" -e "SELECT * FROM $REMOTE_DB.SCORE;" > remote_scores.sql 2>/dev/null; then
        print_success "Score data exported"
    else
        print_warning "Could not export score data"
        return 1
    fi
    
    return 0
}

# Function to show migration options
show_migration_options() {
    echo "========================================"
    echo "Bubble-Spinner Database Migration"
    echo "========================================"
    echo
    print_info "Migration Options:"
    echo
    echo "1. Full Migration (recommended)"
    echo "   - Export all data from remote database"
    echo "   - Import to local database"
    echo "   - Update application configuration"
    echo
    echo "2. Fresh Local Setup (clean start)"
    echo "   - Set up new local database"
    echo "   - Use sample data only"
    echo "   - No data from remote database"
    echo
    echo "3. Hybrid Setup (keep both)"
    echo "   - Keep remote database for production"
    echo "   - Set up local database for development"
    echo "   - Easy switching via configuration"
    echo
    echo "4. Test Connection Only"
    echo "   - Test both remote and local connections"
    echo "   - No data migration"
    echo
}

# Function to perform full migration
full_migration() {
    print_info "Performing full migration..."
    
    # Test connections
    if ! test_remote_connection; then
        print_error "Cannot proceed without remote database access"
        return 1
    fi
    
    if ! test_local_connection; then
        print_error "Local database not ready"
        return 1
    fi
    
    # Create backup of current local data
    print_info "Creating backup of current local data..."
    mysqldump -h"$LOCAL_HOST" -u"$LOCAL_USER" -p"$LOCAL_PASS" "$LOCAL_DB" > local_backup_$(date +%Y%m%d_%H%M%S).sql
    
    # Export and import data
    if export_remote_data; then
        print_info "Importing data to local database..."
        # This would require more complex SQL transformation
        print_warning "Data import requires manual SQL review"
        print_info "Exported files: remote_players.sql, remote_scores.sql"
    fi
    
    print_success "Migration completed (with manual steps required)"
    return 0
}

# Function to set up fresh local database
fresh_local_setup() {
    print_info "Setting up fresh local database..."
    
    if [ ! -f "./setup_database.sh" ]; then
        print_error "Setup script not found"
        return 1
    fi
    
    # Run the setup script
    ./setup_database.sh --setup
    
    # Update configuration to use local database
    if [ -f "src/main/resources/config/database.properties" ]; then
        sed -i.bak 's/db.environment=prod/db.environment=local/' src/main/resources/config/database.properties
        print_success "Configuration updated to use local database"
    fi
    
    return 0
}

# Function to set up hybrid configuration
hybrid_setup() {
    print_info "Setting up hybrid configuration..."
    
    # Set up local database
    fresh_local_setup
    
    print_success "Hybrid setup completed"
    print_info "You can now switch between databases by changing the 'db.environment' property"
    print_info "Available environments: local, prod"
    
    return 0
}

# Function to test connections only
test_connections_only() {
    print_info "Testing database connections..."
    
    echo "Remote Database:"
    if test_remote_connection; then
        # Get some stats
        REMOTE_PLAYERS=$(mysql -h"$REMOTE_HOST" -u"$REMOTE_USER" -p"$REMOTE_PASS" -e "SELECT COUNT(*) FROM $REMOTE_DB.PLAYER;" 2>/dev/null | tail -n1 || echo "0")
        REMOTE_SCORES=$(mysql -h"$REMOTE_HOST" -u"$REMOTE_USER" -p"$REMOTE_PASS" -e "SELECT COUNT(*) FROM $REMOTE_DB.SCORE;" 2>/dev/null | tail -n1 || echo "0")
        print_info "Remote database has $REMOTE_PLAYERS players and $REMOTE_SCORES scores"
    fi
    
    echo
    echo "Local Database:"
    if test_local_connection; then
        LOCAL_PLAYERS=$(mysql -h"$LOCAL_HOST" -u"$LOCAL_USER" -p"$LOCAL_PASS" -e "SELECT COUNT(*) FROM $LOCAL_DB.PLAYER;" 2>/dev/null | tail -n1 || echo "0")
        LOCAL_SCORES=$(mysql -h"$LOCAL_HOST" -u"$LOCAL_USER" -p"$LOCAL_PASS" -e "SELECT COUNT(*) FROM $LOCAL_DB.SCORE;" 2>/dev/null | tail -n1 || echo "0")
        print_info "Local database has $LOCAL_PLAYERS players and $LOCAL_SCORES scores"
    fi
    
    return 0
}

# Main menu
main_menu() {
    show_migration_options
    
    echo -n "Please select an option (1-4): "
    read -r choice
    
    case $choice in
        1)
            full_migration
            ;;
        2)
            fresh_local_setup
            ;;
        3)
            hybrid_setup
            ;;
        4)
            test_connections_only
            ;;
        *)
            print_error "Invalid option"
            return 1
            ;;
    esac
}

# Script entry point
case "${1:-}" in
    --help|-h)
        show_migration_options
        ;;
    --full)
        full_migration
        ;;
    --fresh)
        fresh_local_setup
        ;;
    --hybrid)
        hybrid_setup
        ;;
    --test)
        test_connections_only
        ;;
    "")
        main_menu
        ;;
    *)
        print_error "Unknown option: $1"
        show_migration_options
        exit 1
        ;;
esac
