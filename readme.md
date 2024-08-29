# Employee Management System

This is a simple Employee Management System built using Java, JDBC, and SQL. The system manages employee information, department details, job roles, and project assignments.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Database Schema](#database-schema)
- [Installation](#installation)
  
## Overview

The Employee Management System is designed to help manage various aspects of a company's workforce. It includes functionalities to manage employees, departments, job roles, and projects, providing a comprehensive tool for human resource management.

## Features

- Add, update, delete, and view employee details.
- Manage departments and assign employees to different departments.
- Define job roles and assign them to employees.
- Track employee participation in different projects.

## Database Schema

The system uses a relational database to store data. Below are the main tables:

1. **Employee**
   - `employee_id` INT (Primary Key)
   - `first_name` VARCHAR(50)
   - `last_name` VARCHAR(50)
   - `email` VARCHAR(100)
   - `phone_number` VARCHAR(15)
   - `hire_date` DATE
   - `job_id` INT (Foreign Key)
   - `department_id` INT (Foreign Key)
   - `salary` DECIMAL(10, 2)

2. **Department**
   - `department_id` INT (Primary Key)
   - `department_name` VARCHAR(100)
   - `manager_id` INT (Foreign Key)

3. **Job**
   - `job_id` INT (Primary Key)
   - `job_title` VARCHAR(100)
   - `min_salary` DECIMAL(10, 2)
   - `max_salary` DECIMAL(10, 2)

4. **Project**
   - `project_id` INT (Primary Key)
   - `project_name` VARCHAR(100)
   - `start_date` DATE
   - `end_date` DATE
   - `budget` DECIMAL(10, 2)

## Installation

1. **Clone the repository:**

   ```bash
   git clone https://github.com/RahulSingh044/Employee-Management-System.git
