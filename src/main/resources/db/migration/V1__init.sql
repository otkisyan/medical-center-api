CREATE TABLE role
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE user
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    username VARCHAR(255)          NOT NULL,
    password VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE user_roles
(
    role_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES role (id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE refresh_session
(
    id      BIGINT AUTO_INCREMENT NOT NULL,
    user_id BIGINT NOT NULL,
    token VARCHAR(255) NOT NULL,
    CONSTRAINT pk_refresh_session PRIMARY KEY (id),
    CONSTRAINT fk_refresh_session_user FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE patient
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    name        VARCHAR(255)          NOT NULL,
    surname     VARCHAR(255)          NOT NULL,
    middle_name VARCHAR(255)          NOT NULL,
    birth_date  DATE                  NOT NULL,
    address     VARCHAR(255)          NOT NULL,
    phone       VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_patient PRIMARY KEY (id)
);

CREATE TABLE office
(
    id     BIGINT AUTO_INCREMENT NOT NULL,
    number INTEGER               NOT NULL,
    name   VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_office PRIMARY KEY (id)
);

CREATE TABLE doctor
(
    user_id           BIGINT       NOT NULL,
    office_id         BIGINT       NULL,
    name              VARCHAR(255) NOT NULL,
    surname           VARCHAR(255) NOT NULL,
    middle_name       VARCHAR(255) NOT NULL,
    birth_date        DATE         NOT NULL,
    medical_specialty VARCHAR(255) NOT NULL,
    address           VARCHAR(255) NOT NULL,
    phone             VARCHAR(255) NOT NULL,
    CONSTRAINT pk_doctor PRIMARY KEY (user_id),
    CONSTRAINT fk_doctor_user FOREIGN KEY (user_id) REFERENCES user (id),
    CONSTRAINT fk_office FOREIGN KEY (office_id) REFERENCES office (id)
);

CREATE TABLE receptionist
(
    user_id     BIGINT AUTO_INCREMENT NOT NULL,
    name        VARCHAR(255)          NOT NULL,
    surname     VARCHAR(255)          NOT NULL,
    middle_name VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_receptionist PRIMARY KEY (user_id),
    CONSTRAINT fk_receptionist_user FOREIGN KEY (user_id) REFERENCES user (id)
);
CREATE TABLE appointment
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    doctor_id  BIGINT                NOT NULL,
    patient_id BIGINT                NOT NULL,
    diagnosis  VARCHAR(255)          NULL,
    date       DATE                  NOT NULL,
    time_start TIME                  NOT NULL,
    time_end   TIME                  NOT NULL,
    CONSTRAINT pk_appointment PRIMARY KEY (id),
    CONSTRAINT fk_appointment_doctor FOREIGN KEY (doctor_id) REFERENCES doctor (user_id),
    CONSTRAINT fk_appointment_user FOREIGN KEY (patient_id) REFERENCES patient (id)
);

CREATE TABLE day_of_week
(
    day_number INTEGER     NOT NULL,
    name       VARCHAR(30) NOT NULL,
    CONSTRAINT pk_day PRIMARY KEY (day_number)
);

CREATE TABLE work_schedule
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    doctor_id       BIGINT                NOT NULL,
    day_of_week_id  INTEGER               NOT NULL,
    work_time_start TIME                  NULL,
    work_time_end   TIME                  NULL,
    CONSTRAINT pk_work_schedule PRIMARY KEY (id),
    CONSTRAINT fk_day_of_week FOREIGN KEY (day_of_week_id) REFERENCES day_of_week (day_number),
    CONSTRAINT fk_work_schedule_doctor FOREIGN KEY (doctor_id) REFERENCES doctor (user_id)
);

INSERT INTO day_of_week (day_number, name)
VALUES (1, 'Понеділок');
INSERT INTO day_of_week (day_number, name)
VALUES (2, 'Вівторок');
INSERT INTO day_of_week (day_number, name)
VALUES (3, 'Середа');
INSERT INTO day_of_week (day_number, name)
VALUES (4, 'Четвер');
INSERT INTO day_of_week (day_number, name)
VALUES (5, 'П''ятниця');
INSERT INTO day_of_week (day_number, name)
VALUES (6, 'Субота');
INSERT INTO day_of_week (day_number, name)
VALUES (7, 'Неділя');


INSERT INTO role (name)
VALUES ('ROLE_DOCTOR');
INSERT INTO role (name)
VALUES ('ROLE_RECEPTIONIST');
INSERT INTO role (name)
VALUES ('ROLE_ADMIN');

INSERT INTO user (username, password)
VALUES ('admin', '$2a$10$kwmUaK83Oy1VseVK05ADpugfzRMQIaRo0Ig5kUw6aC7Rz/bGbC2Ta');
SET @user_id = LAST_INSERT_ID();
INSERT INTO user_roles (role_id, user_id)
VALUES ((SELECT id FROM role WHERE name = 'ROLE_ADMIN'), @user_id);

