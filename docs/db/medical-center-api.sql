-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: mariadb
-- Generation Time: Jun 12, 2025 at 02:30 PM
-- Server version: 10.11.2-MariaDB-1:10.11.2+maria~ubu2204
-- PHP Version: 8.1.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `medical-center-api`
--

-- --------------------------------------------------------

--
-- Table structure for table `appointment`
--

CREATE TABLE `appointment` (
  `id` bigint(20) NOT NULL,
  `doctor_id` bigint(20) NOT NULL,
  `patient_id` bigint(20) NOT NULL,
  `date` date NOT NULL,
  `time_start` time NOT NULL,
  `time_end` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `appointment`
--

INSERT INTO `appointment` (`id`, `doctor_id`, `patient_id`, `date`, `time_start`, `time_end`) VALUES
(1, 11, 14, '2024-05-29', '08:00:00', '09:00:00'),
(2, 13, 13, '2024-05-15', '12:50:00', '13:40:00'),
(24, 11, 14, '2024-05-15', '07:40:00', '08:30:00'),
(51, 11, 29, '2024-10-15', '11:10:00', '12:40:00'),
(68, 11, 28, '2024-10-15', '09:20:00', '10:30:00'),
(92, 12, 8, '2025-04-07', '08:40:00', '09:20:00'),
(94, 12, 7, '2025-04-07', '10:20:00', '10:50:00');

--
-- Triggers `appointment`
--
DELIMITER $$
CREATE TRIGGER `create_consultation_after_appointment` AFTER INSERT ON `appointment` FOR EACH ROW BEGIN
    INSERT INTO consultation (appointment_id)
    VALUES (NEW.id);
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `consultation`
--

CREATE TABLE `consultation` (
  `appointment_id` bigint(20) NOT NULL,
  `diagnosis` varchar(255) DEFAULT NULL,
  `medical_recommendations` varchar(1000) DEFAULT NULL,
  `symptoms` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `consultation`
--

INSERT INTO `consultation` (`appointment_id`, `diagnosis`, `medical_recommendations`, `symptoms`) VALUES
(1, 'Ідіопатичний тропічний енцефаліт', 'Пацієнта слід негайно госпіталізувати для проведення детального обстеження і моніторингу.', 'Головний біль, що прогресує і не знімається звичайними анальгетиками.\nПідвищена температура тіла (38-40°C), яка тримається більше трьох днів.\nСлабкість і млявість, що наростає.\nЗниження апетиту.'),
(2, NULL, NULL, NULL),
(24, NULL, NULL, NULL),
(51, '', NULL, NULL),
(68, 'test', NULL, NULL),
(92, NULL, NULL, NULL),
(94, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `day_of_week`
--

CREATE TABLE `day_of_week` (
  `day_number` int(11) NOT NULL,
  `name` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `day_of_week`
--

INSERT INTO `day_of_week` (`day_number`, `name`) VALUES
(1, 'Понеділок'),
(2, 'Вівторок'),
(3, 'Середа'),
(4, 'Четвер'),
(5, 'П\'ятниця'),
(6, 'Субота'),
(7, 'Неділя');

-- --------------------------------------------------------

--
-- Table structure for table `doctor`
--

CREATE TABLE `doctor` (
  `user_id` bigint(20) NOT NULL,
  `office_id` bigint(20) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `middle_name` varchar(255) NOT NULL,
  `birth_date` date NOT NULL,
  `medical_specialty` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `messenger_contact` varchar(255) DEFAULT NULL,
  `education` varchar(255) DEFAULT NULL,
  `qualification_category` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `doctor`
--

INSERT INTO `doctor` (`user_id`, `office_id`, `name`, `surname`, `middle_name`, `birth_date`, `medical_specialty`, `address`, `phone`, `messenger_contact`, `education`, `qualification_category`) VALUES
(2, 2, 'Надія', 'Панасюк', 'Іванівна', '1986-03-19', 'Терапевт', 'Вулиця Шевченка, будинок 10, квартира 35, Миколаїв,', '0671724134', '0962642447', NULL, 'Вища'),
(3, 2, 'Тимофій', 'Крамаренко', 'Євгенійович', '1994-05-15', 'Терапевт', 'Проспект Леніна, будинок 24А, Миколаїв', '0979246591', '0979246591', NULL, 'Перша'),
(4, 1, 'Ольга', 'Павлюк', 'Михайлівна', '2000-01-11', 'Гінеколог', 'Вулиця Гагаріна, будинок 7, квартира 18, Миколаїв', '0963657911', '0963657911', NULL, 'Друга'),
(5, 4, 'Сергій', 'Павлюк', 'Анатолійович', '1972-03-12', 'Хірург', 'Площа Олександрії, будинок 5, квартира 63, Миколаїв', '0954444580', '0954444580', NULL, 'Хірург'),
(6, 5, 'Олена', 'Дмитренко', 'Сергіївна', '1994-03-28', 'Уролог', 'Вулиця Лермонтова, будинок 25, квартира 42, Миколаїв', '0924084927', '0924084927', NULL, 'Друга'),
(7, 6, 'Юлія', 'Дмитренко', 'Валентинівна', '1996-11-06', 'Офтальмолог', 'Вулиця Київська, будинок 70, квартира 19, Миколаїв', '0997142306', '-', NULL, 'Вища'),
(8, 8, 'Денис', 'Кравченко', 'Анатолійович', '1989-09-09', 'Невролог', 'Проспект Миру, будинок 38, квартира 14, Миколаїв', '0680123832', '0680123832', NULL, 'Друга'),
(9, 9, 'Лариса', 'Микитюк', 'Йосипівна', '1989-05-09', 'Кардіолог', 'Проспект Олександра Поля, будинок 18, квартира 22, Миколаїв', '0963962953', '0931294595', NULL, 'Друга'),
(10, 10, 'Олександра', 'Бондаренко', 'Миколаївна', '1972-09-10', 'Ендокринолог', 'Проспект Олександра Поля, будинок 18, квартира 22, Миколаїв', '0948260335', '0948260335', NULL, 'Вища'),
(11, 11, 'Антон', 'Микитюк', 'Андрійович', '1987-01-14', 'Інфекціоніст', 'Вулиця Підмурна, будинок 8, квартира 17, Миколаїв', '0912762412', '0948170270', NULL, 'Перша'),
(12, 12, 'Микола', 'Васильєв', 'Михайлович', '1984-08-19', 'Рентгенолог', 'Вулиця Каразіна, будинок 27, квартира 11, Миколаїв', '0922199296', '0922199296', NULL, 'Вища'),
(13, 13, 'Тамара', 'Пономарчук', 'Олексіївна', '1978-07-02', 'Функціональний діагност', 'Вулиця Шевченка, будинок 54, квартира 11, Миколаїв', '0966398168', '0966398168', NULL, 'Вища'),
(14, 14, 'Єлизавета', 'Кравченко', 'Борисівна', '1993-03-12', 'УЗ-Діагност', 'Проспект Сталінський, будинок 36, квартира 22, Миколаїв', '0686300015', '0686300015', 'Запорізький державний медичний університет', 'Друга'),
(65, 9, 'Євген', 'Здражевський', 'Юрійович', '1990-03-10', 'Лікар кардіолог', 'вул. Космонавтів, буд. 42, кв. 15, м. Миколаїв, 54058, Україна', '+380965527526', '+380915448534', 'Київський національний медичний університет ім. О.О. Богомольця', 'Вища');

--
-- Triggers `doctor`
--
DELIMITER $$
CREATE TRIGGER `after_insert_doctor` AFTER INSERT ON `doctor` FOR EACH ROW BEGIN
    DECLARE day_count INT;
    DECLARE day_id INTEGER DEFAULT 1;

    SELECT COUNT(*) INTO day_count FROM day_of_week;

    WHILE day_id <= day_count DO
            INSERT INTO work_schedule (doctor_id, day_of_week_id, work_time_start, work_time_end)
            VALUES (NEW.user_id, day_id, null, null);
            SET day_id = day_id + 1;
    END WHILE;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `flyway_schema_history`
--

CREATE TABLE `flyway_schema_history` (
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `flyway_schema_history`
--

INSERT INTO `flyway_schema_history` (`installed_rank`, `version`, `description`, `type`, `script`, `checksum`, `installed_by`, `installed_on`, `execution_time`, `success`) VALUES
(1, '1', 'init', 'SQL', 'V1__init.sql', 1946029385, 'root', '2024-05-29 08:51:40', 56, 1),
(2, '2023.09.23.1', 'add messenger contact to patient', 'SQL', 'V2023.09.23.1__add_messenger_contact_to_patient.sql', 1678143669, 'root', '2024-05-29 08:51:40', 5, 1),
(3, '2023.09.23.2', 'add messenger contact to doctor', 'SQL', 'V2023.09.23.2__add_messenger_contact_to_doctor.sql', -152129812, 'root', '2024-05-29 08:51:40', 3, 1),
(4, '2023.09.23.3', 'add preferential category to patient', 'SQL', 'V2023.09.23.3__add_preferential_category_to_patient.sql', 1382898889, 'root', '2024-05-29 08:51:40', 4, 1),
(5, '2023.09.23.4', 'add qualification category to doctor', 'SQL', 'V2023.09.23.4__add_qualification_category_to_doctor.sql', -524055744, 'root', '2024-05-29 08:51:40', 3, 1),
(6, '2024.05.12.1', 'add flags to user', 'SQL', 'V2024.05.12.1__add_flags_to_user.sql', -1791535096, 'root', '2024-05-29 08:51:40', 3, 1),
(7, '2024.05.15.1', 'add medical recommendations to appointment', 'SQL', 'V2024.05.15.1__add_medical_recommendations_to_appointment.sql', -247517579, 'root', '2024-05-29 08:51:40', 4, 1),
(8, '2024.05.15.2', 'add symptoms to appointment', 'SQL', 'V2024.05.15.2__add_symptoms_to_appointment.sql', 1568259662, 'root', '2024-05-29 08:51:40', 5, 1),
(9, '2024.05.16.1', 'add birthdate to receptionist', 'SQL', 'V2024.05.16.1__add_birthdate_to_receptionist.sql', -59315621, 'root', '2024-05-29 08:51:40', 3, 1),
(10, '2024.05.25.1', 'create consultation table', 'SQL', 'V2024.05.25.1__create_consultation_table.sql', -1593669146, 'root', '2024-05-29 08:51:40', 3, 1),
(11, '2024.06.26.1', 'delete consultation data from appointment', 'SQL', 'V2024.06.26.1__delete_consultation_data_from_appointment.sql', -1211753815, 'root', '2024-06-26 14:40:53', 24, 1),
(12, '2024.10.24.1', 'create trigger consultation after appointment', 'SQL', 'V2024.10.24.1__create_trigger_consultation_after_appointment.sql', 1016637539, 'root', '2024-10-24 07:29:20', 8, 1),
(13, '2024.10.26.1', 'create trigger workschedule after doctor', 'SQL', 'V2024.10.26.1__create_trigger_workschedule_after_doctor.sql', -1484710566, 'root', '2024-10-26 13:04:23', 22, 1),
(14, '2025.03.31', 'add education to doctor', 'SQL', 'V2025.03.31__add_education_to_doctor.sql', 209663686, 'root', '2025-03-31 06:00:53', 47, 1);

-- --------------------------------------------------------

--
-- Table structure for table `office`
--

CREATE TABLE `office` (
  `id` bigint(20) NOT NULL,
  `number` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `office`
--

INSERT INTO `office` (`id`, `number`, `name`) VALUES
(1, 111, 'Оглядовий жіночий кабінет'),
(2, 112, 'Кабінет профілактичних оглядів'),
(3, 113, 'Кабінет долікарського прийому'),
(4, 211, 'Хірургічний кабінет'),
(5, 212, 'Урологічний кабінет'),
(6, 213, 'Офтальмологічний кабінет'),
(7, 214, 'Отоларингологічний кабінет'),
(8, 215, 'Неврологічний кабінет'),
(9, 216, 'Кардіологічний кабінет'),
(10, 217, 'Ендокринологічний кабінет'),
(11, 218, 'Кабінет інфекційних захворювань'),
(12, 311, 'Рентгенодіагностичний кабінет'),
(13, 312, 'Кабінет функціональної діагностики'),
(14, 313, 'Кабінет ультразвукової діагностики'),
(15, 314, 'Процедурний кабінет');

-- --------------------------------------------------------

--
-- Table structure for table `patient`
--

CREATE TABLE `patient` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `middle_name` varchar(255) NOT NULL,
  `birth_date` date NOT NULL,
  `address` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `messenger_contact` varchar(255) DEFAULT NULL,
  `preferential_category` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `patient`
--

INSERT INTO `patient` (`id`, `name`, `surname`, `middle_name`, `birth_date`, `address`, `phone`, `messenger_contact`, `preferential_category`) VALUES
(1, 'Олександр', 'Ковальчук', 'Ігорович', '1986-03-18', 'Площа Визвольна, будинок 10, квартира 28, Миколаїв', '0942438877', '0942438877', 'Учасник бойових дій'),
(2, 'Ірина', 'Петренко', 'Володимирівна', '1998-08-19', 'Вулиця Котляревського, будинок 29, квартира 12', '0681274672', '0681274672', ''),
(3, 'Тетяна', 'Сидоренко', 'Олександрівна', '1962-01-23', 'Вулиця Красноармійська, будинок 74, Миколаїв', '0933477313', '', 'Громадяни, які постраждали внаслідок Чорнобильскої катастрофи'),
(4, 'Віталій', 'Шевченко', 'Сергійович', '1991-09-04', 'Проспект Шкільний, будинок 16, квартира 8, Миколаїв', '0981586442', '0963400754', 'Багатодітні сім’ї'),
(5, 'Денис', 'Іваненко', 'Олегович', '2001-04-05', 'Вулиця Спартаківська, будинок 40, Миколаїв', '0926015133', '0926015133', ''),
(6, 'Людмила', 'Гончарук', 'Станіславівна', '1999-04-11', 'Площа Маршала Жукова, будинок 8, квартира 35, Миколаїв', '0998015287', '0998015287', 'Особи, які постраждали від торгівлі людьми'),
(7, 'Сергій', 'Кузьменко', 'Ігорович', '1984-12-18', 'Вулиця Березова, будинок 57, квартира 14, Миколаїв', '0686228757', '', ''),
(8, 'Лариса', 'Литвиненко', 'Петрівна', '1954-09-19', 'Вулиця Свободи, будинок 31, квартира 19, Миколаїв', '0961748799', '0961748799', 'Ветерани праці'),
(9, 'Євген', 'Кравченко', 'Володимирович', '2004-06-19', 'Проспект Гагаріна, будинок 14, квартира 25, Миколаїв', '0968131322', '0968131322', ''),
(10, 'Максим', 'Шаповалов', 'Олександрович', '1982-03-19', 'Вулиця Космічна, будинок 17, квартира 12, Миколаїв', '0922382567', '0922382567', ''),
(11, 'Оксана', 'Федорова', 'Михайлівна', '1974-12-19', 'Вулиця Миру, будинок 45, квартира 8, Миколаїв', '0966661948', '', 'Особи, які мають особливі заслуги перед Батьківщиною'),
(12, 'Андрій', 'Левченко', 'Віталійович', '2001-09-18', 'Вулиця Степова, будинок 38, квартира 14, Миколаїв', '0950055240', '0688424542', ''),
(13, 'Світлана', 'Мартиненко', 'Валеріївна', '1979-04-19', 'Проспект Шахтарський, будинок 48, квартира 17, Миколаїв', '0982814623', '0990695535', 'Ветерани податкової міліції'),
(14, 'Юлія', 'Лазаренко', 'Артемівна', '1987-04-19', 'Вулиця Краснофлотська, будинок 8, квартира 30, Миколаїв', '0632464249', '0632464249', ''),
(15, 'Михайло', 'Луценко', 'Володимирович', '1983-11-09', 'Площа Культури, будинок 8, квартира 16, Миколаїв', '0932582934', '0944598082', 'Почесний донор України'),
(24, 'Болеслав', 'Васильчук', 'Анатолійович', '1999-04-19', '18390, Миколаївська область, місто Миколаїв, просп. Мельникова, 41', '+380961605814', '+380681604362', ''),
(25, 'Андрій', 'Олійник', 'Миколайович', '1992-03-12', 'м. Миколаїв, вул. Соборна, 24', '+380 (63) 123-45-67', '+380 (50) 234-56-78', ''),
(26, 'Тетяна', 'Марченко', 'Вікторівна', '1985-07-09', 'м. Миколаїв, вул. Адміральська, 8', ' +380 (67) 987-65-43', '+380 (67) 987-65-43', 'Особи, які постраждали від торгівлі людьми'),
(27, 'Олександр', 'Грищенко', 'Петрович', '2000-12-25', 'м. Миколаїв, вул. Спаська, 17', '+380 (95) 111-22-33', '+380 (95) 111-22-33', ''),
(28, 'Сергій', 'Бойко', 'Анатолійович', '1990-10-03', 'м. Миколаїв, вул. Велика Морська, 30', '+380 (66) 555-66-77', '+380 (66) 555-66-77', ''),
(29, 'Катерина', 'Сидоренко', 'Вікторівна', '2003-02-18', 'м. Первомайськ, вул. Кузнечна, 12', '+380 (68) 876-54-32', '+380 (67) 444-55-11', ''),
(30, 'Віталій', 'Поліщук', 'Олексійович', '1975-07-30', 'м. Миколаїв, вул. Чкалова, 42', '+380 (67) 222-33-44', '+380 (67) 222-33-44', 'Громадяни, що постраждали від Чорнобильської катастрофи');

-- --------------------------------------------------------

--
-- Table structure for table `receptionist`
--

CREATE TABLE `receptionist` (
  `user_id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `middle_name` varchar(255) NOT NULL,
  `birth_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `receptionist`
--

INSERT INTO `receptionist` (`user_id`, `name`, `surname`, `middle_name`, `birth_date`) VALUES
(16, 'Марія', 'Броварчук', 'Миколаївна', '2001-02-08');

-- --------------------------------------------------------

--
-- Table structure for table `refresh_session`
--

CREATE TABLE `refresh_session` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `token` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `refresh_session`
--

INSERT INTO `refresh_session` (`id`, `user_id`, `token`) VALUES
(1, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MTY5NzQ2NDEsImV4cCI6MTcxOTY1MzA0MX0.OVHIf1KWtP6oe8hCneuL5eoFmwv3FyMTBEa_gvqw18w'),
(10, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MTcwNTg0NzgsImV4cCI6MTcxOTczNjg3OH0.LNvEbf1EWm3QpGQ8ATGH8-h9Y3SUZwlqBjXwf0Td-Qs'),
(18, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MTk0MTMwMTQsImV4cCI6MTcyMjA5MTQxNH0.EqFRyxFX_Vyc8r3CaYmWU2WMgE9if7ZizvvE5WExQG8'),
(48, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjYwNjI3MDAsImV4cCI6MTcyODc0MTEwMH0.aEvIJGmipRWRhHyEJRUvn8GanWPMs7w1pa6IVzkGDO0'),
(49, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjYwNjM2MjAsImV4cCI6MTcyODc0MjAyMH0.Pa-GDy3D0JKhfALb4qHEFCObdxZBczFGDZbqtGvP7m4'),
(50, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjYwNjUwOTMsImV4cCI6MTcyODc0MzQ5M30.YhAaBL-uRKhs7rU6p2fIMMJk8TGvD49FOKQIB8TQZWQ'),
(53, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjYzMzkxMDIsImV4cCI6MTcyOTAxNzUwMn0.xL-oCu6ColGaUJBDBqNKF04IFuLOW7eQDRELs8ap2C4'),
(54, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjY3Njc1OTAsImV4cCI6MTcyOTQ0NTk5MH0.v2SqAas4lzOEgd_fO0dw-l77da5r2iMp4WhYHizOvSw'),
(73, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjY5OTQ2MDUsImV4cCI6MTcyOTY3MzAwNX0.j6U_6tcuo2XXCEZKv4O-gkrI-IKqX--YInhafSzb650'),
(84, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3Mjc4MDQ0MDQsImV4cCI6MTczMDQ4MjgwNH0.j0xsPKiAz9I_AvuJ2U3JPW8rVTPCQjPodDs_JBNa-ak'),
(85, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3Mjc4NTA2MzUsImV4cCI6MTczMDUyOTAzNX0.smzOW1yBeyQ6ydSOTRDOnjdUUB7nzj-karYBpKZLy8I'),
(86, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjgxMTc0MjgsImV4cCI6MTczMDc5NTgyOH0.bnd78RiPVFYCGRGNmKCh8HF0Ap44IMhzDqyLrSE3KrE'),
(87, 11, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCaXhicjNrTiIsInVzZXJJZCI6MTEsInR5cGUiOiJSRUZSRVNIIiwiaWF0IjoxNzI4MTE3NjkwLCJleHAiOjE3MzA3OTYwOTB9.9iFbUHcAltm6QnLO497_GtIIsDOmOXBHZ6dZRaHZad8'),
(88, 11, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCaXhicjNrTiIsInVzZXJJZCI6MTEsInR5cGUiOiJSRUZSRVNIIiwiaWF0IjoxNzI4MTE3OTM3LCJleHAiOjE3MzA3OTYzMzd9.kgOCUPaLJCJx2oxeeq9cqZEccOIVgETEuJJZTiLgucI'),
(89, 11, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCaXhicjNrTiIsInVzZXJJZCI6MTEsInR5cGUiOiJSRUZSRVNIIiwiaWF0IjoxNzI4MTE4NjcyLCJleHAiOjE3MzA3OTcwNzJ9.ItcN2JPqQi25BtOrvU-AM8vJSSKegp1mgp5w3iKX4R0'),
(90, 11, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCaXhicjNrTiIsInVzZXJJZCI6MTEsInR5cGUiOiJSRUZSRVNIIiwiaWF0IjoxNzI4MTE5NzUxLCJleHAiOjE3MzA3OTgxNTF9.FwPlFIh2b7ijgHfK_7r3ZRDVbYBfXuUptXoOTY6_EJQ'),
(91, 11, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCaXhicjNrTiIsInVzZXJJZCI6MTEsInR5cGUiOiJSRUZSRVNIIiwiaWF0IjoxNzI4MTIyMDIyLCJleHAiOjE3MzA4MDA0MjJ9.o3DDLU29MGABK1nC0_gAdT51mCbauFjds_s9sTBP9HI'),
(95, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkwODA3MzksImV4cCI6MTczMTc1OTEzOX0.tcrBD9TXPuG3GWrAn5FCQ_yxtAO2cf-G04yQhz_-Il0'),
(96, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkwODA3NDUsImV4cCI6MTczMTc1OTE0NX0.yxR-yd4SILUHgvWAJuUhsb8r-guZWG32sOVNUSuSrVQ'),
(100, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkwODA5MzYsImV4cCI6MTczMTc1OTMzNn0.B6lrJk51MgjlauIu26PfbA2zdKMfCRXW4EIi-OSeA2k'),
(101, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkwODA5NjAsImV4cCI6MTczMTc1OTM2MH0.43_r4Wgnik9sLaU1fEbUlNfHiXCPnaJ57TTJGOVr2fI'),
(103, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkwODA5ODIsImV4cCI6MTczMTc1OTM4Mn0.bdoAqEEeCb5Ni5rCJvgtru-xw2m2_Vhv-cYCXIRLS8E'),
(105, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkwODEyMTEsImV4cCI6MTczMTc1OTYxMX0.YTDg96JdzAgksCW5jeog_ApxCSFqSnxy5mBpG7zRQis'),
(106, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkwODEyMTcsImV4cCI6MTczMTc1OTYxN30.aU7EPAHfFTI0Z6eAZMsyTvWyF-ptUJGdkMEnQhw6_gg'),
(125, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkwODE3MjYsImV4cCI6MTczMTc2MDEyNn0.yGYsjrDzgccWpRJVNvn6OUPozPvLPXzW1hZuwfTS4KI'),
(126, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkwODE3MjcsImV4cCI6MTczMTc2MDEyN30.dvwvE4_tY9My2rnUayvXw8XqHMw_jDHmGdnxr9A3MQY'),
(127, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkwODE3MzAsImV4cCI6MTczMTc2MDEzMH0.Iu3PfDBFNVtlULvVoF_-80I7ghhPe5iPDUYMnqBnASw'),
(128, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkwODE3MzEsImV4cCI6MTczMTc2MDEzMX0.5fQKnj0cizBg6BTyxWBYxmM62JGheSXpoLspr06yGEw'),
(129, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkwODE3MzIsImV4cCI6MTczMTc2MDEzMn0.2Yfg_9T3N9id5x0jLcskFUTGpoaONe1fM84_zhoF-Kk'),
(130, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkwODE3MzMsImV4cCI6MTczMTc2MDEzM30.73-RwHHXsKJ3uOe5cNEKybk4MEQ1xR6GCsulseKt5H4'),
(147, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkxODU1NDMsImV4cCI6MTczMTg2Mzk0M30.iNFvz2tiiaq8eQuy0Ki5g2T9q6g6qVWe2Dd0s6z6fOE'),
(149, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkzMzQzODIsImV4cCI6MTczMjAxMjc4Mn0.j1ceqr7-YT7nd5h4_RYBFAqWQO5se9zggcW8jDGu83g'),
(150, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkzMzQ4ODEsImV4cCI6MTczMjAxMzI4MX0.-Y_X0nKVntlB72sxn1DbIjXEnnUduYbA6T3Pnxq-YW0'),
(151, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkzMzQ5MDksImV4cCI6MTczMjAxMzMwOX0.fE9H5DO4kCO_-Zs72swGJ10GupBHQt6lH7GrP57bChU'),
(152, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkzMzQ5MjEsImV4cCI6MTczMjAxMzMyMX0.2hhI3ZMbkZRIbvQP8TYlpmq4ZwnLJlKPlWiD5jQU_0I'),
(153, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkzMzQ5MzYsImV4cCI6MTczMjAxMzMzNn0.B_xejQ9lsvyP8NMz6U0pTPaX-22VN1l79IYQpfghzIE'),
(154, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkzMzUwMTgsImV4cCI6MTczMjAxMzQxOH0.NVdPB8lq3vDgwc4HTEQTmRoOSrV24oa3yGjGf1hou-k'),
(155, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkzMzUwNzIsImV4cCI6MTczMjAxMzQ3Mn0.2kq0tyRn7mDZPUSdTcABAP7dgDdO89UnuhgnDLeSoPw'),
(156, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkzMzUxMjQsImV4cCI6MTczMjAxMzUyNH0.FzL2Iw9ZVkX7RXjjs-WjwW7mV2NGb-Cbjl2sF6dN6ag'),
(157, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkzMzUyMzMsImV4cCI6MTczMjAxMzYzM30.9zf0YNhV2v-SApUIgiJW9-o7oqDzLo9FRcrCdXR_pbE'),
(158, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkzMzUzNjgsImV4cCI6MTczMjAxMzc2OH0.4dMWRsLOhRT0-x1xm0sZbIVyJpnnAPTqSSwpZLtEykM'),
(159, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkzMzUzODAsImV4cCI6MTczMjAxMzc4MH0.iq48JaZtd1tEofNZ5Xc3VYSyiM0RHDbJJ6QjlzpssM4'),
(160, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkzMzU0MDcsImV4cCI6MTczMjAxMzgwN30.ehlFQbjltoFLk1bu2qV9IO8isgPQcFzV1cYJl8z2PNg'),
(161, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkzMzYyNjEsImV4cCI6MTczMjAxNDY2MX0.m3Ks-2Hl7UFYfnArJECDbwpWb9x-q2u5ZlqLWh5L0nw'),
(162, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkzMzYyNjUsImV4cCI6MTczMjAxNDY2NX0.WHhw0p0zke_oPVpLe9OkPzSRMWpIGqhNPiAFmVSf5ys'),
(163, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkzMzYyODQsImV4cCI6MTczMjAxNDY4NH0.qiF6liEtbWoyqMzHGJtExp4JPpXLGEkUeEGhXnmnV-Y'),
(164, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkzMzYyODcsImV4cCI6MTczMjAxNDY4N30.-m2FbfbiydR66_iztlw7bhp7JrKWBaxkN9yhonOGcEo'),
(169, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkzNDEwMzQsImV4cCI6MTczMjAxOTQzNH0.pvNWoNBq_fxQHPuHz1vqvTPvIz8KTsk2ebz6nQIJ3cM'),
(170, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkzNDEwNTEsImV4cCI6MTczMjAxOTQ1MX0.rkeM5ojchiSc8A4HO__96MHm0P_IQHKR4az57ZQXo_w'),
(171, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkzNDExMTIsImV4cCI6MTczMjAxOTUxMn0.z6PFKc5Vyl3VS_s31FoIBw82TLk6RHtotHGKIHItlp4'),
(172, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkzNDExMjIsImV4cCI6MTczMjAxOTUyMn0.EuePWgGmTHflqv-P0ynSKK8qBNewvpknoWOrRMWg-F8'),
(173, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkzNDEyMzksImV4cCI6MTczMjAxOTYzOX0.eCw9UnTHznMWueLqKJOWAoIWbxnRZarWnoycBDVX_TA'),
(174, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkzNDEyNDcsImV4cCI6MTczMjAxOTY0N30.NsuP5HRYLRxRqIYHQBeg-lLhiegZs4lO3InO3ltkXL4'),
(175, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkzNDEyNTIsImV4cCI6MTczMjAxOTY1Mn0.CCH1basBIVkRFlgTbX0r55-qoTwjxnJvTSqBCr7pYLI'),
(176, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkzNDEyODksImV4cCI6MTczMjAxOTY4OX0.5_ElVnzdOrjP2qDnwkf-d5rXffStOF2_T57eukCP6aw'),
(177, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkzNDEyOTEsImV4cCI6MTczMjAxOTY5MX0.ubdyzhyP-ImY1fPnL6whEpaZ3txaxxawkYax9akEnig'),
(178, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkzNDEzNzUsImV4cCI6MTczMjAxOTc3NX0.uUOy-JVQZ7stKPR6uThzfZ0Qe-Dh2RQy8B599eXCrAU'),
(179, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkzNDE2MjYsImV4cCI6MTczMjAyMDAyNn0.wp2KVnZeMl08DHfqJ3DmB3D7BGc7zPtiEMpE1n-XE8c'),
(180, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkzNDE4NzMsImV4cCI6MTczMjAyMDI3M30.jUJTR03Pw8YEDdanGVhrCHbdCWmDsy3nLfPHLFqiaV8'),
(181, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkzNDE4ODYsImV4cCI6MTczMjAyMDI4Nn0.OZdKs_jqcKykgkNqoj8htngVjtCX7MDQMemwc3X720A'),
(182, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkzNDE5MTIsImV4cCI6MTczMjAyMDMxMn0.BV_E8nmDjagtvtH9FvsDKXggzkR7G-iY1ifoB27XTy4'),
(183, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkzNDIyMzAsImV4cCI6MTczMjAyMDYzMH0.q2Mc_HBbIXnSuQbFrkzETUxy33VVR467Z-JBsgpXltc'),
(184, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkzNDI1NzMsImV4cCI6MTczMjAyMDk3M30.ij0DxWc6Fpa25UE68yssZwgHRBcRjbArXyFPIDfJVT8'),
(187, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MjkzNDQzMjcsImV4cCI6MTczMjAyMjcyN30.3U39pGfloO72f1sZXJ-B6BTk80DtKh5VbiJPCtuTMUc'),
(188, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3Mjk1ODI4MzksImV4cCI6MTczMjI2MTIzOX0.FI17EG8MMV3gqsXOlTwXjooC5Dq9M_ZLdDFnrLmjAJY'),
(189, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3Mjk0MzU5MTEsImV4cCI6MTczMjExNDMxMX0.HVvqFlZR3OgP9WfHujvJz2L6P1R284NksoOBl1rIyPY'),
(190, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3Mjk1MzExMDEsImV4cCI6MTczMjIwOTUwMX0.YvMiJ6P1pv-l-fUKYgl8mwWCo2_80QVS7C-Ua6ZsFwU'),
(191, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3Mjk1MzEzNzgsImV4cCI6MTczMjIwOTc3OH0.xFckCrisCyXWTvZbQ3VoMNaxzjPNSNvDuVMMAMmKrlI'),
(192, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3Mjk2NzgwNjMsImV4cCI6MTczMjM1NjQ2M30.8zIWu22W63wiAcQCpzEMlA4QxhnRxl0pXw1fhKEvJPs'),
(194, 11, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCaXhicjNrTiIsInVzZXJJZCI6MTEsInR5cGUiOiJSRUZSRVNIIiwiaWF0IjoxNzI5NjkzNzU3LCJleHAiOjE3MzIzNzIxNTd9.jCeD99JvAKlN-Ecw5B0G-SZjAwkg9_6u6L5DXnwUXto'),
(197, 11, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCaXhicjNrTiIsInVzZXJJZCI6MTEsInR5cGUiOiJSRUZSRVNIIiwiaWF0IjoxNzI5Njk1OTA1LCJleHAiOjE3MzIzNzQzMDV9.86jZGigUbCzdzW2Zwn-fEorusMJFDxkFkvR7zGH0VHY'),
(198, 11, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCaXhicjNrTiIsInVzZXJJZCI6MTEsInR5cGUiOiJSRUZSRVNIIiwiaWF0IjoxNzI5Njk3MDcwLCJleHAiOjE3MzIzNzU0NzB9.J6qMIhe5WGeJR78mB2mc3FPz3Y-GElk5XJI2Dqfp2aA'),
(199, 11, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCaXhicjNrTiIsInVzZXJJZCI6MTEsInR5cGUiOiJSRUZSRVNIIiwiaWF0IjoxNzI5Njk4MzA0LCJleHAiOjE3MzIzNzY3MDR9.VpZxd0Xk2An2Q53FCCtMW4Dmu2FkCZ7vJeHiicvppuk'),
(205, 11, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCaXhicjNrTiIsInVzZXJJZCI6MTEsInR5cGUiOiJSRUZSRVNIIiwiaWF0IjoxNzI5NzkwMTExLCJleHAiOjE3MzI0Njg1MTF9.CJ_r7gt4AJ0fW2OUwsnwPINYQHiLLOi7A6rt8yutQnk'),
(206, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3Mjk3OTAxMzcsImV4cCI6MTczMjQ2ODUzN30.gFfCe3qxUBvlU3fWF4VoZ1Awrw_l5KfhljSQTKy31Ns'),
(207, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3Mjk3OTE2MzYsImV4cCI6MTczMjQ3MDAzNn0.sgybldL_R-2FXioMdCNv5Gz0o_oFkp9vJa4m-wyf_6I'),
(212, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3Mjk5NDU3MjAsImV4cCI6MTczMjYyNDEyMH0.kWVoB0iSppko30h8YR5iCP2hqPgQe7U5IQp_g8NMOZM'),
(213, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3Mjk5NDYwNjAsImV4cCI6MTczMjYyNDQ2MH0.VAD-yVdoPFoUL7j0Q3ugxtHgFLEa9t95qpT08D27yV8'),
(214, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3Mjk5NTI1NjcsImV4cCI6MTczMjYzMDk2N30.C1DRfH8271NgounjzGJo-ineT8PSNGvK1Whu5fg3eT8'),
(215, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MzAxMTEwNjgsImV4cCI6MTczMjc4OTQ2OH0.J1_R7lJVCPHGwMu3UN9pu3JaApSylYD0RSOTBb2YeLo'),
(243, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MzE3NTMxODUsImV4cCI6MTczNDQzMTU4NX0.uQvoAMtMwcRCHkh1GwPsNpsw0H10PXwFKF-UT25ehmM'),
(245, 3, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoWjRuamxoMSIsInVzZXJJZCI6MywidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MzUyODkzMDksImV4cCI6MTczNzk2NzcwOX0.P4Xky1NH8QRMO6fschKC1vSWU2uzNb5HVeUxz_o5_0U'),
(261, 3, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoWjRuamxoMSIsInVzZXJJZCI6MywidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MzUzMTE3NjQsImV4cCI6MTczNzk5MDE2NH0.1crMdskoewsCy8-CdRDmP3ePOEmmzn_nOlaK5MEEcXU'),
(267, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3Mzk1Mzk3MzksImV4cCI6MTc0MjIxODEzOX0.GOiWsV4UddZqDfmCfDGq3rdldpIg0cda200HMmOOsWg'),
(268, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3NDI5MDk0MTAsImV4cCI6MTc0NTU4NzgxMH0.2vLH0my9q-G9p-6MRD3a9C9MLIsa1RnlbVrOUSdkJFo'),
(269, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3NDI5MTU4MjgsImV4cCI6MTc0NTU5NDIyOH0.L-pAk9pMriMVHYbJ_9dm0yp7GjN0IKF61T3lhp56X08'),
(270, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3NDMwMDE3NDAsImV4cCI6MTc0NTY4MDE0MH0.hxpjk9UNb05ssf6z9sD181TXknX-uiS3fUqihtevR2o'),
(271, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3NDMwMDQ5MDYsImV4cCI6MTc0NTY4MzMwNn0.2wD0LqYS8sIuSw__YkBVZ9txhHDAAcJbWcXauKJIhdY'),
(272, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3NDMwMDQ5MDYsImV4cCI6MTc0NTY4MzMwNn0.2wD0LqYS8sIuSw__YkBVZ9txhHDAAcJbWcXauKJIhdY'),
(273, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3NDMwMDQ5MTYsImV4cCI6MTc0NTY4MzMxNn0.9hxbqH3ZWy5b-D8VxjWIWWwYA5wDmc86mbgLlBP0Mz4'),
(274, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3NDMwMDUwMzcsImV4cCI6MTc0NTY4MzQzN30.8Dc5dz7BsTSU77YDHFMIMziSTLTPAhOCF6xlBre_ICg'),
(275, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3NDMwMDU0ODUsImV4cCI6MTc0NTY4Mzg4NX0.Wn5N_bo5XOlN3Klikz7dXEPgqmmz1NbERd5urcUjgt0'),
(276, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3NDMwMDU4MDQsImV4cCI6MTc0NTY4NDIwNH0.6dLCLKWj8JgDw3AP7xtSp02y-Ji6gfy8Wg0tA8NoZrk'),
(277, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3NDMwMDcwMDMsImV4cCI6MTc0NTY4NTQwM30.h232sVIMxVHQqPxd6ii5Y81n-Hd2k-UnvMGB6bbggqY'),
(278, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3NDMyNDYyMzUsImV4cCI6MTc0NTkyNDYzNX0.nndVx6aX_BfvM-zoAMTljqKBuy-6j84PLPnsgqdtYGw'),
(280, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3NDM0MDIwNTQsImV4cCI6MTc0NjA4MDQ1NH0.Bm2dFfucVecwmANrsX55aZNnYIlTPN1cc-ocx1SIrKs'),
(281, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3NDM0MDIxNjAsImV4cCI6MTc0NjA4MDU2MH0.jobKD_yOgycr2Z_-0wSsWYUsVSwpBM45KHMvCw4MqqM'),
(290, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3NDY0Mjg5MzMsImV4cCI6MTc0OTEwNzMzM30.twlLHBZj-GUp5eOPC74KSp2wzGZrN78aOM0i8DFfmuY');

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `name`) VALUES
(1, 'ROLE_DOCTOR'),
(2, 'ROLE_RECEPTIONIST'),
(3, 'ROLE_ADMIN');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `account_non_expired` tinyint(1) NOT NULL DEFAULT 1,
  `account_non_locked` tinyint(1) NOT NULL DEFAULT 1,
  `credentials_non_expired` tinyint(1) NOT NULL DEFAULT 1,
  `enabled` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `account_non_expired`, `account_non_locked`, `credentials_non_expired`, `enabled`) VALUES
(1, 'admin', '$2a$10$npm97S4jfaOoVcb578hcPO7oxWFeaX.TPHNfijbqR/yyNVlW8A8Sy', 1, 1, 1, 1),
(2, 'e3e0Tdza', '$2a$10$aVQUV7FRE4pj.W27lRskL.2Chz2X/RzxQbinQLDizKiP2sjX8tcd6', 1, 1, 1, 1),
(3, 'hZ4njlh1', '$2a$10$sLzofKOXVQ530mXnKR1NOuwCFSvf55CG33TgWqTslQ1vVoiq4zZFO', 1, 1, 1, 1),
(4, '8JBoe5QS', '$2a$10$UW73jpi8mGVYM29fxLSD5.R3NtRqQoPMSjqRrWgAuzKnrWsEGCZOm', 1, 1, 1, 1),
(5, 'CENRprjM', '$2a$10$lMHWRRaLgwzsOdUSH/eDX.WKG.Mvf0UhLT3JFcr.QJA8ZycrlUQEy', 1, 1, 1, 1),
(6, 'VIiz3InL', '$2a$10$h82huPI01FnO7cxW1IYx8OTgB8X.Wb39mzFW6lLS/e51vFFAhYzEa', 1, 1, 1, 1),
(7, 'RBFGAJYM', '$2a$10$i/o/.ed62vFe2FlOpTsLYOg10OyAfWj31vRj5ku/OcdbSAF9btKvS', 1, 1, 1, 1),
(8, 'WXDPjmlZ', '$2a$10$ge3l1O7Tpa.jx7xYhRq63OZYXnMoKIAvnMInChHLzrQds9EGTcAnW', 1, 1, 1, 1),
(9, '6w2fCaKZ', '$2a$10$i1jrzYuLm1Now8uA7MYNieFoBRkwPmjILgp2psuDcoBw6FH01J9D2', 1, 1, 1, 1),
(10, 'KWCFq0kX', '$2a$10$HDUxE13Dde.eoxi2yLFW0.PwpnCeJ1ccH9sST.qM6Fn9XxG2MIziO', 1, 1, 1, 1),
(11, 'Bixbr3kN', '$2a$10$jIRXj/pOO.CukfLrWpTuu.qCZX6GeYoO2d3xgvfWg1BJC4qWG.C9m', 1, 1, 1, 1),
(12, 'IYjuCjNy', '$2a$10$1d/IfnUw.AKVg0J/IUgACeCVbHJ.0kN51TaRiudlmZAIoDmkNj9RS', 1, 1, 1, 1),
(13, 'oBJN9f48', '$2a$10$e/CvL9h66c.9LG9EQSNsZ.eH.kGk077unt6ReK4qtnsxo0jnBszba', 1, 1, 1, 1),
(14, 'Jng75te5', '$2a$10$TU4W6OeNTtC/MPzx8uiFKehq2Ew9VsAr.lou.Rgx3sdP5KuM7u69O', 1, 1, 1, 1),
(16, 'Maocmd0p', '$2a$10$/9F3oi4vrP3iNCB6ciDHg.QhRPcIRKzAIhjI/E0gs7.yWYSHcMVLK', 1, 1, 1, 1),
(37, 'z73VOsGW', '$2a$10$njgFnvrhmulB4O7Umo7cWO5F8UFpqKlYPd/8ecbmIfrvbqfzxEuYO', 1, 1, 1, 1),
(38, 'MjJFs3wM', '$2a$10$5NINp4iP734OmS0HkWlvc.y5goXjctuygReQK721D6jvs51jqRyaC', 1, 1, 1, 1),
(65, 'sJpkU8Za', '$2a$10$JTgSDu3jI.2TippzUQxiRelRlKyeDvxyp4HAB2gjZicQBlNXjMQbC', 1, 1, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `user_roles`
--

CREATE TABLE `user_roles` (
  `role_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_roles`
--

INSERT INTO `user_roles` (`role_id`, `user_id`) VALUES
(3, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(1, 9),
(1, 10),
(1, 11),
(1, 12),
(1, 13),
(1, 14),
(2, 16),
(1, 37),
(1, 38),
(1, 65);

-- --------------------------------------------------------

--
-- Table structure for table `work_schedule`
--

CREATE TABLE `work_schedule` (
  `id` bigint(20) NOT NULL,
  `doctor_id` bigint(20) NOT NULL,
  `day_of_week_id` int(11) NOT NULL,
  `work_time_start` time DEFAULT NULL,
  `work_time_end` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `work_schedule`
--

INSERT INTO `work_schedule` (`id`, `doctor_id`, `day_of_week_id`, `work_time_start`, `work_time_end`) VALUES
(1, 2, 1, NULL, NULL),
(2, 2, 2, NULL, NULL),
(3, 2, 3, NULL, NULL),
(4, 2, 4, NULL, NULL),
(5, 2, 5, NULL, NULL),
(6, 2, 6, NULL, NULL),
(7, 2, 7, NULL, NULL),
(8, 3, 1, '09:00:00', '17:20:00'),
(9, 3, 2, '09:00:00', '17:00:00'),
(10, 3, 3, '08:00:00', '16:00:00'),
(11, 3, 4, '09:00:00', '16:30:00'),
(12, 3, 5, NULL, NULL),
(13, 3, 6, NULL, NULL),
(14, 3, 7, NULL, NULL),
(15, 4, 1, NULL, NULL),
(16, 4, 2, NULL, NULL),
(17, 4, 3, NULL, NULL),
(18, 4, 4, NULL, NULL),
(19, 4, 5, NULL, NULL),
(20, 4, 6, NULL, NULL),
(21, 4, 7, NULL, NULL),
(22, 5, 1, '09:00:00', '17:00:00'),
(23, 5, 2, '08:00:00', '16:30:00'),
(24, 5, 3, '09:00:00', '17:30:00'),
(25, 5, 4, '08:00:00', '16:00:00'),
(26, 5, 5, NULL, NULL),
(27, 5, 6, NULL, NULL),
(28, 5, 7, NULL, NULL),
(29, 6, 1, NULL, NULL),
(30, 6, 2, NULL, NULL),
(31, 6, 3, NULL, NULL),
(32, 6, 4, NULL, NULL),
(33, 6, 5, NULL, NULL),
(34, 6, 6, NULL, NULL),
(35, 6, 7, NULL, NULL),
(36, 7, 1, NULL, NULL),
(37, 7, 2, NULL, NULL),
(38, 7, 3, NULL, NULL),
(39, 7, 4, NULL, NULL),
(40, 7, 5, NULL, NULL),
(41, 7, 6, NULL, NULL),
(42, 7, 7, NULL, NULL),
(43, 8, 1, NULL, NULL),
(44, 8, 2, NULL, NULL),
(45, 8, 3, NULL, NULL),
(46, 8, 4, NULL, NULL),
(47, 8, 5, NULL, NULL),
(48, 8, 6, NULL, NULL),
(49, 8, 7, NULL, NULL),
(50, 9, 1, NULL, NULL),
(51, 9, 2, NULL, NULL),
(52, 9, 3, NULL, NULL),
(53, 9, 4, NULL, NULL),
(54, 9, 5, NULL, NULL),
(55, 9, 6, NULL, NULL),
(56, 9, 7, NULL, NULL),
(57, 10, 1, NULL, NULL),
(58, 10, 2, NULL, NULL),
(59, 10, 3, NULL, NULL),
(60, 10, 4, NULL, NULL),
(61, 10, 5, NULL, NULL),
(62, 10, 6, NULL, NULL),
(63, 10, 7, NULL, NULL),
(64, 11, 1, '08:00:00', '16:20:00'),
(65, 11, 2, '09:00:00', '16:00:00'),
(66, 11, 3, '07:30:00', '18:20:00'),
(67, 11, 4, '09:00:00', '14:00:00'),
(68, 11, 5, NULL, NULL),
(69, 11, 6, '13:00:00', '19:00:00'),
(70, 11, 7, NULL, NULL),
(71, 12, 1, '08:00:00', '15:00:00'),
(72, 12, 2, '09:00:00', '16:00:00'),
(73, 12, 3, '09:00:00', '17:00:00'),
(74, 12, 4, '08:00:00', '18:00:00'),
(75, 12, 5, NULL, NULL),
(76, 12, 6, NULL, NULL),
(77, 12, 7, NULL, NULL),
(78, 13, 1, '11:00:00', '14:00:00'),
(79, 13, 2, NULL, NULL),
(80, 13, 3, '12:00:00', '18:00:00'),
(81, 13, 4, '09:00:00', '14:00:00'),
(82, 13, 5, NULL, NULL),
(83, 13, 6, NULL, NULL),
(84, 13, 7, NULL, NULL),
(85, 14, 1, '11:00:00', '16:30:00'),
(86, 14, 2, '09:00:00', '17:00:00'),
(87, 14, 3, '08:00:00', '13:00:00'),
(88, 14, 4, '09:00:00', '15:00:00'),
(89, 14, 5, '08:00:00', '12:00:00'),
(90, 14, 6, NULL, NULL),
(91, 14, 7, NULL, NULL),
(337, 65, 1, NULL, NULL),
(338, 65, 2, NULL, NULL),
(339, 65, 3, NULL, NULL),
(340, 65, 4, NULL, NULL),
(341, 65, 5, NULL, NULL),
(342, 65, 6, NULL, NULL),
(343, 65, 7, NULL, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `appointment`
--
ALTER TABLE `appointment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_appointment_doctor` (`doctor_id`),
  ADD KEY `fk_appointment_user` (`patient_id`);

--
-- Indexes for table `consultation`
--
ALTER TABLE `consultation`
  ADD PRIMARY KEY (`appointment_id`);

--
-- Indexes for table `day_of_week`
--
ALTER TABLE `day_of_week`
  ADD PRIMARY KEY (`day_number`);

--
-- Indexes for table `doctor`
--
ALTER TABLE `doctor`
  ADD PRIMARY KEY (`user_id`),
  ADD KEY `fk_office` (`office_id`);

--
-- Indexes for table `flyway_schema_history`
--
ALTER TABLE `flyway_schema_history`
  ADD PRIMARY KEY (`installed_rank`),
  ADD KEY `flyway_schema_history_s_idx` (`success`);

--
-- Indexes for table `office`
--
ALTER TABLE `office`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `patient`
--
ALTER TABLE `patient`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `receptionist`
--
ALTER TABLE `receptionist`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `refresh_session`
--
ALTER TABLE `refresh_session`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_refresh_session_user` (`user_id`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD KEY `fk_role` (`role_id`),
  ADD KEY `fk_user` (`user_id`);

--
-- Indexes for table `work_schedule`
--
ALTER TABLE `work_schedule`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_day_of_week` (`day_of_week_id`),
  ADD KEY `fk_work_schedule_doctor` (`doctor_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `appointment`
--
ALTER TABLE `appointment`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=95;

--
-- AUTO_INCREMENT for table `office`
--
ALTER TABLE `office`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `patient`
--
ALTER TABLE `patient`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT for table `receptionist`
--
ALTER TABLE `receptionist`
  MODIFY `user_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- AUTO_INCREMENT for table `refresh_session`
--
ALTER TABLE `refresh_session`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=291;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=66;

--
-- AUTO_INCREMENT for table `work_schedule`
--
ALTER TABLE `work_schedule`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=344;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `appointment`
--
ALTER TABLE `appointment`
  ADD CONSTRAINT `fk_appointment_doctor` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`user_id`),
  ADD CONSTRAINT `fk_appointment_user` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`);

--
-- Constraints for table `consultation`
--
ALTER TABLE `consultation`
  ADD CONSTRAINT `fk_appointment` FOREIGN KEY (`appointment_id`) REFERENCES `appointment` (`id`);

--
-- Constraints for table `doctor`
--
ALTER TABLE `doctor`
  ADD CONSTRAINT `fk_doctor_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `fk_office` FOREIGN KEY (`office_id`) REFERENCES `office` (`id`);

--
-- Constraints for table `receptionist`
--
ALTER TABLE `receptionist`
  ADD CONSTRAINT `fk_receptionist_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `refresh_session`
--
ALTER TABLE `refresh_session`
  ADD CONSTRAINT `fk_refresh_session_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `fk_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  ADD CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `work_schedule`
--
ALTER TABLE `work_schedule`
  ADD CONSTRAINT `fk_day_of_week` FOREIGN KEY (`day_of_week_id`) REFERENCES `day_of_week` (`day_number`),
  ADD CONSTRAINT `fk_work_schedule_doctor` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
