-- ================================================
-- Bubble-Spinner Game Database Setup Script
-- ================================================
-- This script creates the required database and tables for the Bubble-Spinner game
-- Supports both MariaDB and MySQL
-- 
-- Author: Ali Kahawati (Database Setup)
-- Date: August 2025
-- Version: 2.0
-- ================================================

-- Create the database
DROP DATABASE IF EXISTS SEM;
CREATE DATABASE SEM CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE SEM;

-- ================================================
-- PLAYER TABLE
-- ================================================
-- Stores user account information
DROP TABLE IF EXISTS PLAYER;
CREATE TABLE PLAYER (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    -- Indexes for performance
    INDEX idx_username (username),
    INDEX idx_created_at (created_at)
);

-- ================================================
-- SCORE TABLE  
-- ================================================
-- Stores game scores for each player
DROP TABLE IF EXISTS SCORE;
CREATE TABLE SCORE (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userName VARCHAR(50) NOT NULL,
    score INT NOT NULL DEFAULT 0,
    achieved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- Foreign key relationship (optional, for data integrity)
    -- FOREIGN KEY (userName) REFERENCES PLAYER(username) ON DELETE CASCADE,
    
    -- Indexes for performance
    INDEX idx_username_score (userName, score DESC),
    INDEX idx_score_desc (score DESC),
    INDEX idx_achieved_at (achieved_at)
);

-- ================================================
-- SAMPLE DATA
-- ================================================
-- Insert some demo users and scores for testing

-- Demo user for testing
INSERT INTO PLAYER (username, password, nickname) VALUES 
    ('demo', 'demo', 'Demo Player'),
    ('testuser', 'password123', 'Test User'),
    ('alice', 'alice123', 'Alice Wonder'),
    ('bob', 'bob456', 'Bob Builder'),
    ('charlie', 'charlie789', 'Charlie Brown');

-- Sample high scores
INSERT INTO SCORE (userName, score) VALUES 
    ('demo', 15420),
    ('demo', 12800),
    ('demo', 9650),
    ('testuser', 18900),
    ('testuser', 14200),
    ('alice', 22150),
    ('alice', 16800),
    ('bob', 11900),
    ('bob', 8750),
    ('charlie', 25600),
    ('charlie', 19400),
    ('charlie', 13200);

-- ================================================
-- CREATE USER AND GRANT PERMISSIONS
-- ================================================
-- Create a dedicated user for the application
-- (Replace with your desired username/password)

-- For production, use a strong password!
DROP USER IF EXISTS 'bubble_spinner'@'%';
CREATE USER 'bubble_spinner'@'%' IDENTIFIED BY 'bubble_game_2025!';

-- Grant necessary permissions
GRANT SELECT, INSERT, UPDATE ON SEM.PLAYER TO 'bubble_spinner'@'%';
GRANT SELECT, INSERT ON SEM.SCORE TO 'bubble_spinner'@'%';

-- For localhost access
DROP USER IF EXISTS 'bubble_spinner'@'localhost';
CREATE USER 'bubble_spinner'@'localhost' IDENTIFIED BY 'bubble_game_2025!';
GRANT SELECT, INSERT, UPDATE ON SEM.PLAYER TO 'bubble_spinner'@'localhost';
GRANT SELECT, INSERT ON SEM.SCORE TO 'bubble_spinner'@'localhost';

-- Refresh privileges
FLUSH PRIVILEGES;

-- ================================================
-- VERIFICATION QUERIES
-- ================================================
-- Run these to verify the setup worked correctly

SELECT 'Database Setup Complete!' as Status;

SELECT 'Player Table:' as Info, COUNT(*) as RecordCount FROM PLAYER;
SELECT 'Score Table:' as Info, COUNT(*) as RecordCount FROM SCORE;

-- Show sample high scores
SELECT 'Top 5 High Scores:' as Info;
SELECT userName, score, achieved_at 
FROM SCORE 
ORDER BY score DESC 
LIMIT 5;

-- Show all users
SELECT 'Registered Users:' as Info;
SELECT username, nickname, created_at 
FROM PLAYER 
ORDER BY created_at;

-- ================================================
-- NOTES
-- ================================================
-- 1. Change the database user password in production
-- 2. Consider using password hashing for user passwords
-- 3. Adjust permissions as needed for security
-- 4. Make sure to backup your database regularly
-- ================================================
