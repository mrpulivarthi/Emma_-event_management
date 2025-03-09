# Emma_-event_management
This project is a assignment of my coventry school, where I develop a event management platform for Emma using Java and weka api  

# To generate a fake csv data for training the model
I have used python's faker library to generate 100 rows of fake event data to work with and train my model. 

# created database and tables for data management using MYSQL. 
CREATE DATABASE event_management;
USE event_management;

CREATE TABLE event_types (
    type_id INT AUTO_INCREMENT PRIMARY KEY,
    type_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE events (
    event_id INT AUTO_INCREMENT PRIMARY KEY,
    event_name VARCHAR(100) NOT NULL,
    event_date DATE NOT NULL,
    location VARCHAR(100) NOT NULL,
    description TEXT,
    type_id INT,
    created_by VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (type_id) REFERENCES event_types(type_id)
);

CREATE TABLE rsvp (
    rsvp_id INT AUTO_INCREMENT PRIMARY KEY,
    event_id INT,
    user_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    attendance_status ENUM('Attending', 'Maybe', 'Not Attending') DEFAULT 'Attending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (event_id) REFERENCES events(event_id) ON DELETE CASCADE
);

-- Insert initial event types
INSERT INTO event_types (type_name) VALUES
('Conference'),
('Wedding'),
('Workshop'),
('Party');



# Created a maven project and used tomcat extension and configured the tomcat in to maven 

