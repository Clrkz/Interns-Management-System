-- phpMyAdmin SQL Dump
-- version 4.8.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 11, 2019 at 02:17 PM
-- Server version: 10.1.34-MariaDB
-- PHP Version: 7.2.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cdpo_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `audit`
--

CREATE TABLE `audit` (
  `audit_id` int(11) NOT NULL,
  `date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `u_information_id` int(11) NOT NULL,
  `audit_activity` longtext NOT NULL,
  `audit_description` longtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `company_archive_file`
--

CREATE TABLE `company_archive_file` (
  `c_requirement_file_id` int(11) NOT NULL,
  `c_requirement_id` int(11) NOT NULL,
  `c_information_id` int(11) NOT NULL,
  `pdf_doccument` longblob NOT NULL,
  `file_name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `company_branch`
--

CREATE TABLE `company_branch` (
  `c_branch_id` int(11) NOT NULL,
  `c_information_id` int(11) NOT NULL,
  `c_branch` varchar(100) NOT NULL,
  `c_address` longtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `company_division`
--

CREATE TABLE `company_division` (
  `c_division_id` int(11) NOT NULL,
  `c_division_title` varchar(100) NOT NULL,
  `status` int(11) NOT NULL COMMENT '0no:1yes'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `company_information`
--

CREATE TABLE `company_information` (
  `c_information_id` int(11) NOT NULL,
  `c_logo` longblob,
  `c_name` varchar(200) NOT NULL,
  `c_contact` varchar(50) NOT NULL,
  `c_sector_id` int(11) NOT NULL,
  `c_division_id` int(11) NOT NULL,
  `dateadded` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `company_requirements`
--

CREATE TABLE `company_requirements` (
  `c_requirement_id` int(11) NOT NULL,
  `c_requirement_title` varchar(150) NOT NULL,
  `file_handler` int(11) DEFAULT NULL,
  `status` int(11) NOT NULL COMMENT '0no:1yes'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `company_requirement_file`
--

CREATE TABLE `company_requirement_file` (
  `c_requirement_file_id` int(11) NOT NULL,
  `c_requirement_id` int(11) NOT NULL,
  `c_information_id` int(11) NOT NULL,
  `pdf_doccument` longblob,
  `file_name` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `company_sector`
--

CREATE TABLE `company_sector` (
  `c_sector_id` int(11) NOT NULL,
  `c_sector_title` varchar(100) NOT NULL,
  `status` int(11) NOT NULL COMMENT '0no:1yes'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `gender`
--

CREATE TABLE `gender` (
  `id` int(11) NOT NULL,
  `gender` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `gender`
--

INSERT INTO `gender` (`id`, `gender`) VALUES
(0, 'Male'),
(1, 'Female');

-- --------------------------------------------------------

--
-- Table structure for table `ojt_archive_file`
--

CREATE TABLE `ojt_archive_file` (
  `ojt_company_req_id` int(11) NOT NULL,
  `ojt_requirement_id` int(11) NOT NULL,
  `ojt_information_id` int(11) NOT NULL,
  `pdf_document` longblob NOT NULL,
  `file_name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ojt_hours`
--

CREATE TABLE `ojt_hours` (
  `ojt_hours_id` int(11) NOT NULL,
  `ojt_hours_title` varchar(50) NOT NULL,
  `status` int(11) NOT NULL COMMENT '0no:1yes'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Dumping data for table `ojt_hours`
--

INSERT INTO `ojt_hours` (`ojt_hours_id`, `ojt_hours_title`, `status`) VALUES (0, '0', 0);

-- --------------------------------------------------------

--
-- Table structure for table `ojt_information`
--

CREATE TABLE `ojt_information` (
  `ojt_information_id` int(11) NOT NULL,
  `c_information_id` int(11) NOT NULL,
  `ojt_department` varchar(150) NOT NULL,
  `ojt_supervisor` varchar(100) NOT NULL,
  `ojt_contact_number` varchar(100) NOT NULL,
  `ojt_allowance` varchar(100) NOT NULL,
  `ojt_hours_id` int(11) NOT NULL,
  `ojt_completed_status` int(11) NOT NULL COMMENT '0no:1yes'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ojt_requirements`
--

CREATE TABLE `ojt_requirements` (
  `ojt_requirement_id` int(11) NOT NULL,
  `ojt_requirement_title` varchar(150) NOT NULL,
  `file_handler` int(11) DEFAULT NULL,
  `status` int(11) NOT NULL COMMENT '0no:1yes'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ojt_requirement_file`
--

CREATE TABLE `ojt_requirement_file` (
  `ojt_company_req_id` int(11) NOT NULL,
  `ojt_requirement_id` int(11) NOT NULL,
  `ojt_information_id` int(11) NOT NULL,
  `pdf_document` longblob,
  `file_name` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `student_achievements`
--

CREATE TABLE `student_achievements` (
  `s_achievement_id` int(11) NOT NULL,
  `s_information_id` varchar(50) NOT NULL,
  `s_achievement_title` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `student_activities`
--

CREATE TABLE `student_activities` (
  `s_activity_id` int(11) NOT NULL,
  `s_records_id` int(11) NOT NULL,
  `s_activity_date_time` datetime NOT NULL,
  `s_activity_title` longtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `student_contact`
--

CREATE TABLE `student_contact` (
  `s_contact_id` int(11) NOT NULL,
  `s_information_id` varchar(50) NOT NULL,
  `s_contact_type_id` int(11) NOT NULL,
  `s_contact_info` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `student_contact_type`
--

CREATE TABLE `student_contact_type` (
  `s_contact_type_id` int(11) NOT NULL,
  `s_contact_type_title` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student_contact_type`
--

INSERT INTO `student_contact_type` (`s_contact_type_id`, `s_contact_type_title`) VALUES
(1, 'Number'),
(2, 'Email'),
(3, 'Social');

-- --------------------------------------------------------

--
-- Table structure for table `student_courses`
--

CREATE TABLE `student_courses` (
  `s_course_id` int(11) NOT NULL,
  `department_id` int(11) NOT NULL,
  `s_course_title` varchar(200) NOT NULL,
  `status` int(11) NOT NULL COMMENT '0no:1yes'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `student_departments`
--

CREATE TABLE `student_departments` (
  `department_id` int(11) NOT NULL,
  `department_title` varchar(100) NOT NULL,
  `dept_abbr` varchar(10) NOT NULL,
  `status` int(11) NOT NULL COMMENT '0no:1yes'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `student_information`
--

CREATE TABLE `student_information` (
  `s_student_no` varchar(100) NOT NULL,
  `u_information_id` int(11) NOT NULL,
  `s_firstname` varchar(100) NOT NULL,
  `s_middlename` varchar(100) NOT NULL,
  `s_lastname` varchar(100) NOT NULL,
  `s_suffixname` varchar(50) NOT NULL,
  `s_gender` int(11) NOT NULL COMMENT '0male:1female',
  `s_department_id` int(11) NOT NULL,
  `s_course_id` int(11) NOT NULL,
  `s_level_id` int(11) NOT NULL,
  `ojt_hours_id` int(11) NOT NULL,
  `record_date` date NOT NULL,
  `s_picture` longblob,
  `s_dateadded` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `student_levels`
--

CREATE TABLE `student_levels` (
  `s_level_id` int(11) NOT NULL,
  `s_level_title` varchar(50) NOT NULL,
  `status` int(11) NOT NULL COMMENT '0no:1yes'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student_levels`
--

INSERT INTO `student_levels` (`s_level_id`, `s_level_title`, `status`) VALUES
(1, 'EMPLOYED', 1);

-- --------------------------------------------------------

--
-- Table structure for table `student_logs`
--

CREATE TABLE `student_logs` (
  `s_log_id` int(11) NOT NULL,
  `s_records_id` int(11) NOT NULL,
  `s_log_date_time` datetime NOT NULL,
  `s_log_title` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `student_records`
--

CREATE TABLE `student_records` (
  `s_records_id` int(11) NOT NULL,
  `s_information_id` varchar(50) NOT NULL,
  `s_year_level_id` int(11) NOT NULL,
  `department_id` int(11) NOT NULL,
  `s_course_id` int(11) NOT NULL,
  `s_ojt_information_id` int(11) NOT NULL,
  `dateadded` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `system`
--

CREATE TABLE `system` (
  `system_id` int(11) NOT NULL,
  `sn` varchar(100) NOT NULL,
  `status` int(11) NOT NULL COMMENT '0no:1yes',
  `date_added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `u_information_id` int(11) NOT NULL,
  `u_firstname` varchar(100) NOT NULL,
  `u_middlename` varchar(100) NOT NULL,
  `u_lastname` varchar(100) NOT NULL,
  `u_suffixname` varchar(50) NOT NULL,
  `u_gender` int(11) NOT NULL COMMENT '0male:1female',
  `u_phone_number` varchar(100) NOT NULL,
  `u_email` varchar(100) NOT NULL,
  `u_picture` longblob,
  `u_role` int(11) NOT NULL COMMENT '0staff:1admin',
  `u_username` varchar(50) NOT NULL,
  `u_password` varchar(200) NOT NULL,
  `u_validated` int(11) NOT NULL COMMENT '0no:1yes',
  `u_status` int(11) NOT NULL COMMENT '0no:1yes',
  `u_dateadded` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`u_information_id`, `u_firstname`, `u_middlename`, `u_lastname`, `u_suffixname`, `u_gender`, `u_phone_number`, `u_email`, `u_picture`, `u_role`, `u_username`, `u_password`, `u_validated`, `u_status`, `u_dateadded`) VALUES
(0, '', '', '', '', 0, '', '', NULL, 0, 'Guest', '', 0, 0, '0000-00-00');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `audit`
--
ALTER TABLE `audit`
  ADD PRIMARY KEY (`audit_id`);

--
-- Indexes for table `company_branch`
--
ALTER TABLE `company_branch`
  ADD PRIMARY KEY (`c_branch_id`);

--
-- Indexes for table `company_division`
--
ALTER TABLE `company_division`
  ADD PRIMARY KEY (`c_division_id`);

--
-- Indexes for table `company_information`
--
ALTER TABLE `company_information`
  ADD PRIMARY KEY (`c_information_id`);

--
-- Indexes for table `company_requirements`
--
ALTER TABLE `company_requirements`
  ADD PRIMARY KEY (`c_requirement_id`);

--
-- Indexes for table `company_requirement_file`
--
ALTER TABLE `company_requirement_file`
  ADD PRIMARY KEY (`c_requirement_file_id`);

--
-- Indexes for table `company_sector`
--
ALTER TABLE `company_sector`
  ADD PRIMARY KEY (`c_sector_id`);

--
-- Indexes for table `ojt_hours`
--
ALTER TABLE `ojt_hours`
  ADD PRIMARY KEY (`ojt_hours_id`);

--
-- Indexes for table `ojt_information`
--
ALTER TABLE `ojt_information`
  ADD PRIMARY KEY (`ojt_information_id`);

--
-- Indexes for table `ojt_requirements`
--
ALTER TABLE `ojt_requirements`
  ADD PRIMARY KEY (`ojt_requirement_id`);

--
-- Indexes for table `ojt_requirement_file`
--
ALTER TABLE `ojt_requirement_file`
  ADD PRIMARY KEY (`ojt_company_req_id`);

--
-- Indexes for table `student_achievements`
--
ALTER TABLE `student_achievements`
  ADD PRIMARY KEY (`s_achievement_id`);

--
-- Indexes for table `student_activities`
--
ALTER TABLE `student_activities`
  ADD PRIMARY KEY (`s_activity_id`);

--
-- Indexes for table `student_contact`
--
ALTER TABLE `student_contact`
  ADD PRIMARY KEY (`s_contact_id`);

--
-- Indexes for table `student_contact_type`
--
ALTER TABLE `student_contact_type`
  ADD PRIMARY KEY (`s_contact_type_id`);

--
-- Indexes for table `student_courses`
--
ALTER TABLE `student_courses`
  ADD PRIMARY KEY (`s_course_id`),
  ADD UNIQUE KEY `s_course_title` (`s_course_title`);

--
-- Indexes for table `student_departments`
--
ALTER TABLE `student_departments`
  ADD PRIMARY KEY (`department_id`),
  ADD UNIQUE KEY `department_title` (`department_title`);

--
-- Indexes for table `student_information`
--
ALTER TABLE `student_information`
  ADD PRIMARY KEY (`s_student_no`);

--
-- Indexes for table `student_levels`
--
ALTER TABLE `student_levels`
  ADD PRIMARY KEY (`s_level_id`),
  ADD UNIQUE KEY `s_level_title` (`s_level_title`);

--
-- Indexes for table `student_logs`
--
ALTER TABLE `student_logs`
  ADD PRIMARY KEY (`s_log_id`);

--
-- Indexes for table `student_records`
--
ALTER TABLE `student_records`
  ADD PRIMARY KEY (`s_records_id`);

--
-- Indexes for table `system`
--
ALTER TABLE `system`
  ADD PRIMARY KEY (`system_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`u_information_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `audit`
--
ALTER TABLE `audit`
  MODIFY `audit_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT for table `company_branch`
--
ALTER TABLE `company_branch`
  MODIFY `c_branch_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT for table `company_division`
--
ALTER TABLE `company_division`
  MODIFY `c_division_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT for table `company_information`
--
ALTER TABLE `company_information`
  MODIFY `c_information_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT for table `company_requirements`
--
ALTER TABLE `company_requirements`
  MODIFY `c_requirement_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT for table `company_requirement_file`
--
ALTER TABLE `company_requirement_file`
  MODIFY `c_requirement_file_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT for table `company_sector`
--
ALTER TABLE `company_sector`
  MODIFY `c_sector_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT for table `ojt_hours`
--
ALTER TABLE `ojt_hours`
  MODIFY `ojt_hours_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT for table `ojt_information`
--
ALTER TABLE `ojt_information`
  MODIFY `ojt_information_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT for table `ojt_requirements`
--
ALTER TABLE `ojt_requirements`
  MODIFY `ojt_requirement_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT for table `ojt_requirement_file`
--
ALTER TABLE `ojt_requirement_file`
  MODIFY `ojt_company_req_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT for table `student_achievements`
--
ALTER TABLE `student_achievements`
  MODIFY `s_achievement_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT for table `student_activities`
--
ALTER TABLE `student_activities`
  MODIFY `s_activity_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT for table `student_contact`
--
ALTER TABLE `student_contact`
  MODIFY `s_contact_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT for table `student_contact_type`
--
ALTER TABLE `student_contact_type`
  MODIFY `s_contact_type_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `student_courses`
--
ALTER TABLE `student_courses`
  MODIFY `s_course_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT for table `student_departments`
--
ALTER TABLE `student_departments`
  MODIFY `department_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT for table `student_levels`
--
ALTER TABLE `student_levels`
  MODIFY `s_level_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `student_logs`
--
ALTER TABLE `student_logs`
  MODIFY `s_log_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT for table `student_records`
--
ALTER TABLE `student_records`
  MODIFY `s_records_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT for table `system`
--
ALTER TABLE `system`
  MODIFY `system_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `u_information_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
