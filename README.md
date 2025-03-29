# <img src="https://notion-emojis.s3-us-west-2.amazonaws.com/prod/svg-twitter/1f3e8.svg" style="width:50px"/> Hotel API

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)
	![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)

This project is a RESTful API developed using Java, Spring Boot, MySQL, Spring Security, and JWT for authentication.

It is an API for managing hotels, rooms, and reservations. It provides endpoints for CRUD operations related to hotels, along with hotel-specific functionalities.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Example Flow](#example-flow)
- [Contributions and Feedback](#contributions-and-feedback)

## Installation

### Prerequisites

- Java 17+
- Maven
- [Bruno](https://www.usebruno.com/)

### Steps

1. Clone the repository.

```bash
 git clone https://github.com/lucasamantino/Hotel-API.git
```

2. Install dependencies with Maven.
3. Install [MySQL]("https://www.mysql.com/")

#### 4. Asymmetric Encryption Keys

- Open the terminal in `/src/main/resources/`
- Generate a private key.

```bash
 openssl genrsa > private.key
```

- Generate a public key.

```bash
 openssl rsa -in private.key -pubout -out public.key
```

- Result

```plaintext
    📂 hotel-api     
    └── 📁 /src
       └── 📁/main
          └── 📁/resources
            ├── 📄 public.key  
            ├── 📄 private.key  
```

## Usage

### Running the API
1. Configured environment variables (create a `.env` file based on [.env.example](./.env.example))

2. Start the application using Maven Wrapper:

   ```bash
   ./mvnw spring-boot:run  # Linux/Mac
   ```

   or

   ```bash
   mvnw.cmd spring-boot:run  # Windows
   ```

2. The API will be available at:
   `http://localhost:${SERVER_PORT}` (default port: 8080)

### Testing the API
1. Download [Bruno](https://www.usebruno.com/) (open-source API client).

2. Import the [Bruno Collection](./Bruno%20collection.json):
    - Click **Import** in Bruno and select the JSON file.
   
3. Explore all endpoints.

## API Endpoints

```markdown

### AUTHENTICATION

| Method | Endpoint               | Description                         | Authorization                   |
|--------|------------------------|-------------------------------------|---------------------------------|
| `POST` | `/auth/login`          | User login                         | `Public`                        |
| `POST` | `/auth/register`       | User registration                   | `Public`                        |

### ROOMS

| Method | Endpoint               | Description                         | Authorization                   |
|--------|------------------------|-------------------------------------|---------------------------------|
| `GET`  | `/rooms/all`           | List all rooms                      | `RECEPTIONIST`, `ADMIN`         |
| `GET`  | `/rooms/free`          | List available rooms                 | `RECEPTIONIST`, `ADMIN`         |
| `GET`  | `/rooms/search`        | List rooms by type                   | `RECEPTIONIST`, `ADMIN`         |
| `POST` | `/rooms`               | Register a new room                  | `ADMIN`                         |
| `PUT`  | `/rooms/{id}`          | Update a room                        | `ADMIN`                         |
| `DELETE`| `/rooms/{id}`         | Delete a room                        | `ADMIN`                         |

### RESERVATIONS

| Method | Endpoint               | Description                         | Authorization                   |
|--------|------------------------|-------------------------------------|---------------------------------|
| `POST` | `/reservations`        | Create a reservation                | `BASIC`, `RECEPTIONIST`, `ADMIN` |
| `PUT`  | `/reservations/{id}`   | Update a reservation                | `BASIC`, `RECEPTIONIST`, `ADMIN` |
| `DELETE`| `/reservations/{id}`  | Cancel a reservation                | `BASIC`, `RECEPTIONIST`, `ADMIN` |
| `GET`  | `/reservations/{id}`   | Get reservation details             | `BASIC`, `RECEPTIONIST`, `ADMIN` |

### CHECK-IN/CHECK-OUT

| Method | Endpoint               | Description                         | Authorization                   |
|--------|------------------------|-------------------------------------|---------------------------------|
| `POST` | `/checkin/{id}`        | Perform check-in                    | `RECEPTIONIST`, `ADMIN`         |
| `POST` | `/checkout/{id}`       | Perform check-out                   | `RECEPTIONIST`, `ADMIN`         |

### USER MANAGEMENT

| Method | Endpoint               | Description                         | Authorization                   |
|--------|------------------------|-------------------------------------|---------------------------------|
| `PUT`  | `/user`                | Modify user role                    | `ADMIN`                         |

### **Role Legend**
- `BASIC`: Regular user (guest)
- `RECEPTIONIST`: Receptionist (staff)
- `ADMIN`: Administrator
```

## Example Flow

```markdown
1. `POST /auth/register` → Creates a `BASIC` user and obtains JWT token
2. `POST /auth/login` → Logs in
3. `GET /rooms/free` → Lists available rooms
4. `POST /reservations` → Creates a reservation (with token in header)
5. `POST /checkin/{id}` → Receptionist registers check-in
```

## Contributions and Feedback

Found a bug or have suggestions? Open an **issue** or submit a **pull request**!\
Developed by [Lucas Henrique](https://github.com/lucasamantino).
