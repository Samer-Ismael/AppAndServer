[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/MYVtI0hB)
[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-7f7980b617ed060a017424585567c406b6ee15c891e84e1186181d67ecf80aa0.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=11360346)

# Kafka Chat Server

## Description

The Kafka Chat Server is a critical component of the Chat App infrastructure, 
responsible for handling JSON-formatted messages and facilitating communication between users. 
This README provides an overview of the server, its functionalities, and instructions on setting it up.
## Table of Contents 

1. [Introduction](#introduction)
2. [Prerequisites](#prerequisites)
3. [Installation](#installation)
    - [Option 1: Manual Installation](#option-1-manual-installation)
    - [Option 2: Docker Installation](#option-2-docker-installation)
4. [Usage](#usage)
5. [Credits](#Credits)
6. [License](#license)
7. [Diagram](#diagram)

## Introduction

The Kafka Chat Server is designed to act as a message broker for the Chat App, 
allowing users to send and receive messages in real-time. 
It integrates seamlessly with a MySQL database running on localhost:8080, 
ensuring data persistence and reliability.

Key features of the Kafka Chat Server include:

- Handling JSON-formatted messages.
- Storing messages in a MySQL database for historical reference.


## Prerequisites

Before setting up the Kafka Chat Server, ensure you have installed the following:

- [Apache Kafka](https://kafka.apache.org/) - The messaging system for message distribution.
- [MySQL Database](https://www.mysql.com/) - A local MySQL database configured and running on port 8080.


## Installation

### Option 1: Manual Installation

To install and run the Kafka Chat Server, follow these steps:

**Note:** You need to have Java installed on your system to be able to run the server. 
You can confirm this by running `java --version` in the CMD. You must have Java 17 or higher. 
If you don't have it, you can download it from 
[https://www.oracle.com/java/technologies/downloads/](https://www.oracle.com/java/technologies/downloads/).


1. **Download Kafka Server:**
    - Download the Apache Kafka distribution from [https://kafka.apache.org/downloads](https://kafka.apache.org/downloads).
    - Unzip the downloaded file to a location of your choice.

2. **Open the Project:**
    - Open the project in your preferred IDE, such as IntelliJ IDEA.

3. **Configure Kafka Server Path:**
   In `ServerApplication.java` located at `src/main/java/com/chat/Server/`, replace the placeholder `ServerPath` with the actual path to your Kafka Server installation.

4. **Configure Database:**
    - Navigate to the application properties.
    - follow the instruction you will find there to replace your database name, username and password.

5. **Start the Server:**
    - Start the Kafka Chat Server application.

### Option 2: Docker Installation

If you prefer a containerized setup, you can use Docker to simplify the installation process.
Make sure you have Docker installed on your system. You can confirm this by running `docker version` in your terminal.

1. **Clone the repository:**
    - You can clone the repository to your local machine. Alternatively, you can download the 'docker-compose.yml' and 'ChatApp.jar' files without cloning.
    - Open a terminal window and navigate to the project directory or the folder where you have the downloaded files.
    - Run the following command to start the Docker services:

     ```bash
     docker-compose up -d
     ```

   - Wait for all the services to start.
   - Check the Docker Desktop application to ensure that all services are running. If any service is stopped, click the start button to start it.


2. **Run the User App:**
   - Open a new terminal window, or in the same one because the server is running in detached mood.
   - Run the following command to start the Chat Application:

     ```bash
     javaw -jar ChatApp.jar
     ```

   - The Chat Application should start, and you'll be prompted to enter your username.
   - Enter your desired username, and you'll be connected to the chat.
   - You can repeat the above steps to start multiple instances of the chat application with different usernames.


The Kafka Chat Server will now be running in a Docker container.
- You can access the server at `http://localhost:8080/chat/room/send` to send json message from psotman.
- You can access the database on `localhost:3307`. If you have another database server running you can leave it on, because it will be on 3306.
- Kafka server will be running on `localhost:9092`. If you have another server running on this port, you may need to close it!

## Usage only for Manual Installation

1. **Start the Chat App:**
   - Launch the Chat App on your local machine.

2. **Test the Chat App:**
   - To test the Chat App functionality, you can use the following steps:
      - Open your preferred IDE, such as IntelliJ IDEA.
      - Look for options beside the start button and you will find three user profiles.
      - Start each user profile one by one.
      - Assign different names to each user.
      - Start chatting by sending messages between the user profiles.

   This allows you to simulate a multi-user chat environment for testing and development purposes.


## Credits

Collaborators:
- [Marcus](https://github.com/marcusjobb)
- [Samer](https://github.com/Samer-Ismael)
- Marcus Henriksson

## License

This project is licensed under the [MIT License](https://choosealicense.com/licenses/mit/).

## Diagram
## Server

![Class_Diagram](Diagram%20and%20rapport%2FServer.png)
## User App

![ChatApp1.png](Diagram%20and%20rapport%2FChatApp1.png)
