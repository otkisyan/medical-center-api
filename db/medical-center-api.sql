-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: mariadb
-- Generation Time: May 29, 2024 at 10:05 AM
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
  `symptoms` varchar(255) DEFAULT NULL,
  `diagnosis` varchar(255) DEFAULT NULL,
  `medical_recommendations` varchar(1000) DEFAULT NULL,
  `date` date NOT NULL,
  `time_start` time NOT NULL,
  `time_end` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `appointment`
--

INSERT INTO `appointment` (`id`, `doctor_id`, `patient_id`, `symptoms`, `diagnosis`, `medical_recommendations`, `date`, `time_start`, `time_end`) VALUES
(1, 11, 14, NULL, NULL, NULL, '2024-05-29', '08:00:00', '09:00:00');

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
(1, 'Ідіопатичний тропічний енцефаліт', 'Пацієнта слід негайно госпіталізувати для проведення детального обстеження і моніторингу.', 'Головний біль, що прогресує і не знімається звичайними анальгетиками.\nПідвищена температура тіла (38-40°C), яка тримається більше трьох днів.\nСлабкість і млявість, що наростає.\nЗниження апетиту.');

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
  `qualification_category` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `doctor`
--

INSERT INTO `doctor` (`user_id`, `office_id`, `name`, `surname`, `middle_name`, `birth_date`, `medical_specialty`, `address`, `phone`, `messenger_contact`, `qualification_category`) VALUES
(2, 2, 'Надія', 'Панасюк', 'Іванівна', '1986-03-19', 'Терапевт', 'Вулиця Шевченка, будинок 10, квартира 35, Миколаїв,', '0671724134', '0962642447', 'Вища'),
(3, 2, 'Тимофій', 'Крамаренко', 'Євгенійович', '1994-05-15', 'Терапевт', 'Проспект Леніна, будинок 24А, Миколаїв', '0979246591', '0979246591', 'Перша'),
(4, 1, 'Ольга', 'Павлюк', 'Михайлівна', '2000-01-11', 'Гінеколог', 'Вулиця Гагаріна, будинок 7, квартира 18, Миколаїв', '0963657911', '0963657911', 'Друга'),
(5, 4, 'Сергій', 'Павлюк', 'Анатолійович', '1972-03-12', 'Хірург', 'Площа Олександрії, будинок 5, квартира 63, Миколаїв', '0954444580', '0954444580', 'Хірург'),
(6, 5, 'Олена', 'Дмитренко', 'Сергіївна', '1994-03-28', 'Уролог', 'Вулиця Лермонтова, будинок 25, квартира 42, Миколаїв', '0924084927', '0924084927', 'Друга'),
(7, 6, 'Юлія', 'Дмитренко', 'Валентинівна', '1996-11-06', 'Офтальмолог', 'Вулиця Київська, будинок 70, квартира 19, Миколаїв', '0997142306', '-', 'Вища'),
(8, 8, 'Денис', 'Кравченко', 'Анатолійович', '1989-09-09', 'Невролог', 'Проспект Миру, будинок 38, квартира 14, Миколаїв', '0680123832', '0680123832', 'Друга'),
(9, 9, 'Лариса', 'Микитюк', 'Йосипівна', '1989-05-09', 'Кардіолог', 'Проспект Олександра Поля, будинок 18, квартира 22, Миколаїв', '0963962953', '0931294595', 'Друга'),
(10, 10, 'Олександра', 'Бондаренко', 'Миколаївна', '1972-09-10', 'Ендокринолог', 'Проспект Олександра Поля, будинок 18, квартира 22, Миколаїв', '0948260335', '0948260335', 'Вища'),
(11, 11, 'Антон', 'Микитюк', 'Андрійович', '1987-01-14', 'Інфекціоніст', 'Вулиця Підмурна, будинок 8, квартира 17, Миколаїв', '0912762412', '0948170270', 'Перша'),
(12, 12, 'Микола', 'Васильєв', 'Михайлович', '1984-08-19', 'Рентгенолог', 'Вулиця Каразіна, будинок 27, квартира 11, Миколаїв', '0922199296', '0922199296', 'Вища'),
(13, 13, 'Тамара', 'Пономарчук', 'Олексіївна', '1978-07-02', 'Функціональний діагност', 'Вулиця Шевченка, будинок 54, квартира 11, Миколаїв', '0966398168', '0966398168', 'Вища'),
(14, 14, 'Єлизавета', 'Кравченко', 'Борисівна', '1993-03-12', 'УЗ-Діагност', 'Проспект Сталінський, будинок 36, квартира 22, Миколаїв', '0686300015', '0686300015', 'Друга'),
(15, 15, 'Катерина', 'Васильчук', 'Тарасівна', '1986-12-05', 'Медична сестра', 'Площа Революції, будинок 14, квартира 30, Миколаїв', '0912135565', '0972121545', '');

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
(1, '1', 'init', 'SQL', 'V1__init.sql', -1009776848, 'root', '2024-05-29 08:51:40', 56, 1),
(2, '2023.09.23.1', 'add messenger contact to patient', 'SQL', 'V2023.09.23.1__add_messenger_contact_to_patient.sql', -2110755124, 'root', '2024-05-29 08:51:40', 5, 1),
(3, '2023.09.23.2', 'add messenger contact to doctor', 'SQL', 'V2023.09.23.2__add_messenger_contact_to_doctor.sql', -997457407, 'root', '2024-05-29 08:51:40', 3, 1),
(4, '2023.09.23.3', 'add preferential category to patient', 'SQL', 'V2023.09.23.3__add_preferential_category_to_patient.sql', -1810608255, 'root', '2024-05-29 08:51:40', 4, 1),
(5, '2023.09.23.4', 'add qualification category to doctor', 'SQL', 'V2023.09.23.4__add_qualification_category_to_doctor.sql', -1543685400, 'root', '2024-05-29 08:51:40', 3, 1),
(6, '2024.05.12.1', 'add flags to user', 'SQL', 'V2024.05.12.1__add_flags_to_user.sql', 1919532162, 'root', '2024-05-29 08:51:40', 3, 1),
(7, '2024.05.15.1', 'add medical recommendations to appointment', 'SQL', 'V2024.05.15.1__add_medical_recommendations_to_appointment.sql', 1658151702, 'root', '2024-05-29 08:51:40', 4, 1),
(8, '2024.05.15.2', 'add symptoms to appointment', 'SQL', 'V2024.05.15.2__add_symptoms_to_appointment.sql', 950911140, 'root', '2024-05-29 08:51:40', 5, 1),
(9, '2024.05.16.1', 'add birthdate to receptionist', 'SQL', 'V2024.05.16.1__add_birthdate_to_receptionist.sql', -615284442, 'root', '2024-05-29 08:51:40', 3, 1),
(10, '2024.05.25.1', 'create consultation table', 'SQL', 'V2024.05.25.1__create_consultation_table.sql', 1460094026, 'root', '2024-05-29 08:51:40', 3, 1);

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
(15, 'Михайло', 'Луценко', 'Володимирович', '1983-11-09', 'Площа Культури, будинок 8, квартира 16, Миколаїв', '0932582934', '0944598082', 'Почесний донор України');

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
(4, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwidHlwZSI6IlJFRlJFU0giLCJpYXQiOjE3MTY5NzYwNDUsImV4cCI6MTcxOTY1NDQ0NX0.JY4zcLG08j3TYongpWoFhpUIZQmpvgUD9h6fnO3JjfE');

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
(1, 'admin', '$2a$10$kwmUaK83Oy1VseVK05ADpugfzRMQIaRo0Ig5kUw6aC7Rz/bGbC2Ta', 1, 1, 1, 1),
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
(15, 'B9vqXxq5', '$2a$10$nWZvsfJg5x5k0f0RlbwXT.QehYWRIxwwD4dJrybBaA0dZUfatQDji', 1, 1, 1, 1),
(16, 'Maocmd0p', '$2a$10$/9F3oi4vrP3iNCB6ciDHg.QhRPcIRKzAIhjI/E0gs7.yWYSHcMVLK', 1, 1, 1, 1);

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
(1, 15),
(2, 16);

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
(64, 11, 1, '08:00:00', '17:00:00'),
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
(89, 14, 5, NULL, NULL),
(90, 14, 6, NULL, NULL),
(91, 14, 7, NULL, NULL),
(92, 15, 1, NULL, NULL),
(93, 15, 2, NULL, NULL),
(94, 15, 3, NULL, NULL),
(95, 15, 4, NULL, NULL),
(96, 15, 5, NULL, NULL),
(97, 15, 6, NULL, NULL),
(98, 15, 7, NULL, NULL);

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
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `office`
--
ALTER TABLE `office`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `patient`
--
ALTER TABLE `patient`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `receptionist`
--
ALTER TABLE `receptionist`
  MODIFY `user_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `refresh_session`
--
ALTER TABLE `refresh_session`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `work_schedule`
--
ALTER TABLE `work_schedule`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=99;

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
