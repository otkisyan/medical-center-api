
<h1 align="center">
Medical Center
</h1>
<p align="center">
This web application was created using the Spring framework for the medical center reception desk to provide convenient and efficient work with patients and patient appointments with doctors. The application has a convenient and pleasant user interface.
</p>

## Table of Contents
* [Features](#features)
* [Getting Started](#getting-started)
    * [Prerequisites](#prerequisites)
    * [Installation](#installation)
    * [Important Endpoints](#important-endpoints)

## Features
- View data on all patients, doctors, appointments, offices and departments.
- View data about a specific patient, i.e. his/her data and appointments.
- View data about a specific doctor, i.e. his/her data and appointments.
- View data about a specific office.
- View data about a specific department.
- Add new, edit and delete existing patients.
- Add new, edit and delete existing doctors.
- Add new, edit and delete existing appointments.
- Add new, edit and delete existing offices of the polyclinic. 
- Add new, edit and delete existing departments of the polyclinic.

| Method 	| Path          	| Description                          	|
|--------	|---------------	|--------------------------------------	|
| `GET`    	| `/patients`     	| Get data of all patients             	|
| `GET`    	| `/patients/{id}` 	| Get a specified patient data         	|
| `PUT`    	| `/patients/{id}` 	| Save the data of a specified patient 	|
| `POST`   	| `/patients`     	| Save a new patient                   	|
| `DELETE`  | `/patients/{id}`     | Remove a specified patient            |

*Similarly for other entities*

## Getting Started
### Prerequisites:
- Docker and Docker Compose

### Installation
**Before you start:** Change environment variable values in `.env` file for more security or leave it as it is.
1. Clone the repo

```bash
> git clone https://github.com/otkisyan/medical-center-api.git
> cd medical-center-api
```
2. Run MariaDB and phpMyAdmin
```bash
> docker-compose up -d mariadb phpmyadmin
```

3. Run Redis and RedisInsight
```bash
> docker-compose up -d redis-2 redisinsight-2
```

4. Compile and package the code
```bash
> ./mvnw clean package
```

5. Run the Application
```bash
> docker-compose up -d medical-center-api
```

### Important Endpoints:
* http://localhost:8080 - Application
* http://localhost:8081 - phpMyAdmin (server: `mariadb`, username: `root`, password: `$MARIADB_ROOT_PASSWORD`)
* http://localhost:3306 - MariaDB
* http://localhost:6380 - Redis
* http://localhost:5541 - RedisInsight (host: `redis-2`, port: `6380`, password: `$REDIS_PASSWORD`)
