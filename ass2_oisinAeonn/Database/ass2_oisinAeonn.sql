CREATE DATABASE ass2_oisinAeonn;
USE ass2_oisinAeonn;

CREATE TABLE users (
    userId INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    firstName VARCHAR(255) NOT NULL,
    lastName VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    userType VARCHAR(255) DEFAULT NULL
);

CREATE TABLE posts (
    postId INT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    author VARCHAR(255) NOT NULL,
    likes INT NOT NULL DEFAULT 0,
    shares INT NOT NULL DEFAULT 0,
    dateTime TIMESTAMP NOT NULL,
    image VARCHAR(255) NOT NULL,
    FOREIGN KEY (author) REFERENCES users(username)
);
