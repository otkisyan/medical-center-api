
<h1 align="center">
Medical Center
</h1>
<div align="center">

![ci](https://github.com/otkisyan/medical-center-api/actions/workflows/ci.yml/badge.svg)

</div>
<p align="center">
This web application was created using the Spring framework for the medical center reception desk to provide convenient and efficient work with patients and patient appointments with doctors. The application has a convenient and pleasant user interface.
</p>

## Table of Contents
* [Overview](#overview)
  * [Features](#features) 
* [Getting Started](#getting-started)
    * [Prerequisites](#prerequisites)
    * [Installation](#installation)
    * [Important Endpoints](#important-endpoints)

## Overview

### Features

When working with the application, a **receptionist** has the ability to perform the following tasks:

- View lists of all patients, doctors, appointments, and offices.
- View data about a specific patient, including their personal data and appointments.
- View data about a specific doctor, including their personal data, appointments, and work schedule.
- View data about a specific office.
- View data about a specific patient appointment with a doctor.
- Add, edit, and delete doctors' work schedules for each day of the week.
- Add new patients, edit, and delete data about existing patients.
- Schedule, edit, and cancel patient appointments with doctors (without the ability to edit consultation information, such as: symptoms, diagnoses and medical recommendations).
- Add new offices, edit, and delete data about existing offices.

A **doctor** has the ability to perform the following tasks:

- Add new patients, edit, and delete data about existing patients.
- View lists of all patients and appointments.
- View data about a specific patient, including their personal data and appointments.
- View data about a specific patient appointment with a doctor.
- Schedule new appointments, edit and cancel his appointments
- View information about appointment consultation in all appointments, such as: symptoms, diagnosis, medical recommendations. 
- Enter information about the appointments consultation (only in his own appointments)

An **administrator** inherits all the functionality of a receptionist and has the following additional functions:

- Add new receptionists (including creating a receptionist account), edit, and delete data about existing receptionists.
- Add new doctors (including creating a doctor account), edit, and delete data about existing doctors.
- View list of all receptionists.
- View data about a specific receptionist.
- View information about appointment consultation in all appointments.

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
* http://localhost:8084 - phpMyAdmin (server: `mariadb`, username: `root`, password: `$MARIADB_ROOT_PASSWORD`)
* http://localhost:3307 - MariaDB
* http://localhost:6380 - Redis
* http://localhost:5541 - RedisInsight (host: `redis-2`, port: `6380`, password: `$REDIS_PASSWORD`)
