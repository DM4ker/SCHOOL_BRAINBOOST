# BrainBoost

**BrainBoost is a learning flashcard platform designed to help users create, study, and share responive flashcards.**
**The system consists of a Spring Boot backend (using Hibernate & PostgreSQL) and a Angular frontend.**

--------------------------------------------------------------------------------------------------------------------------------------

# Installation 

**In order to provide a wide default structured and standardized system please assure that following versions are correctly installed and deployed.**

<details><summary>Click to expand</summary>

# Tech Stack & Requirements 

**Backend:**

- Spring Boot
- Hibernate (JPA)
- PostgreSQL
- REST API

**Frontend:**

- Angular v19.2.17
- HTTP communication with backend services

**IDE:**
- Intellij IDEA v2025.2 Ultimate 
- PostgreSQL selfhosted in Docker 

**Operating System:**
The Project is being developed and tested in Windows. We cannot assure a full functionality in other operating systems

# Steps to Start the Application 
<details><summary>Click to expand</summary>

This guide will help you set up and run the BrainBoost application on your computer using Docker. The application consists of three services: Backend (Spring Boot), Frontend (Angular), and PostgreSQL (database).

---

## Prerequisites

Ensure the following tools are installed on your computer:
1. **Docker**: [Install Docker](https://docs.docker.com/get-docker/)
2. **Docker Compose**: Comes pre-installed with Docker Desktop.

---

## Project Structure

The project is structured as follows:
```
BrainBoostBackend/
├── BrainBoostFrontend/
│   └── Dockerfile
├── docker-compose.yml
└── Dockerfile
```

---

## Setup

1. **Clone the Repository**  
   Clone the project repository to your local machine:
   ```bash
   git clone <repository-url>
   cd project-root
   ```

2. **Build the Docker Images**  
   Build the Docker images for all services:
   ```bash
   docker-compose build
   ```

3. **Start the Application**  
   Run the following command to start all services:
   ```bash
   docker-compose up
   ```

4. **Access the Application**  
   - **Frontend**: Open your browser and navigate to `http://localhost`
   - **Backend**: Accessible at `http://localhost:8080`
   - **PostgreSQL**: Database runs on `localhost:5432` (if needed)

---

## Troubleshooting

- If you encounter any issues, check the logs for each service:
  ```bash
  docker-compose logs <service-name>
  ```
  Replace `<service-name>` with `frontend`, `backend`, or `db`.

- Ensure ports `80`, `8080`, and `5432` are not in use by other applications.

---

## Stopping the Application

To stop the application, press `Ctrl+C` in the terminal where `docker-compose up` is running. Then, remove the containers:
```bash
docker-compose down
```

---
</details>

# BrainBoostFrontend
<details><summary>Click to expand</summary>
This project was generated using [Angular CLI](https://github.com/angular/angular-cli) version 20.3.13.

## Development server

To start a local development server, run:

```bash
ng serve
```

Once the server is running, open your browser and navigate to `http://localhost:4200/`. The application will automatically reload whenever you modify any of the source files.

## Code scaffolding

Angular CLI includes powerful code scaffolding tools. To generate a new component, run:

```bash
ng generate component component-name
```

For a complete list of available schematics (such as `components`, `directives`, or `pipes`), run:

```bash
ng generate --help
```

## Building

To build the project run:

```bash
ng build
```

This will compile your project and store the build artifacts in the `dist/` directory. By default, the production build optimizes your application for performance and speed.

## Running unit tests

To execute unit tests with the [Karma](https://karma-runner.github.io) test runner, use the following command:

```bash
ng test
```

## Running end-to-end tests

For end-to-end (e2e) testing, run:

```bash
ng e2e
```

Angular CLI does not come with an end-to-end testing framework by default. You can choose one that suits your needs.

## Additional Resources

For more information on using the Angular CLI, including detailed command references, visit the [Angular CLI Overview and Command Reference](https://angular.dev/tools/cli) page.
</details>

</details>

--------------------------------------------------------------------------------------------------------------------------------------

# Project Overview

**BrainBoost's current focus includes:**

- Register and log in to access personal flashcard sets
- Create individual flashcards with identifiers, a title, content, and an answer
- Track the last learned timestamp
- Share flashcards through generated links—accessible even for non-registered users
- Organize flashcards into flashcard sets

<details><summary>Click to expand</summary>



## Members and Responsability

- Leon Lukas Michelson - Backend (Spring Boot) / Frontend (Architecture)
- Vico Reinecke - Frontend (Angular) / Projectmanagement 

## Stakeholders

- Users
- Unsinged User 
- Flashcards

## Flashcard Structure:

- ID
- Title
- Content
- LastLearned
- CreatorID
- Answer

## Functional Requirements

- Create account
- Log in

## Flashcard Management

- Create flashcards (ID, Title, Content, Answer)
- Group flashcards into sets

## Sharing

- Share flashcards via public link
- Allow access without requiring a login

## Non-Functional Requirements

- Display Last Learned date on each flashcard
- Display flashcard creator
- Provide a functional and user-friendly UI

## Data Flow Overview

- User creates a flashcard via the Angular UI
- Angular sends the data via HTTP request
- Spring Boot receives the request, creates the flashcard, and stores it in PostgreSQL
- Flashcards can then be retrieved, displayed, and shared through unique links

## Roadmap

**User and Moderator ability:**

- Ability to edit / delete account
- Admin account for management 
 

**Point ranking system:**

- Feature to track your own progress
- Learning point system

**Password Stroage:**

- Storaging currently simplyfied with encrypting 
- For future public releases the password should be stored as a hash with salting (such as bycrypt)

</details>

--------------------------------------------------------------------------------------------------------------------------------------



