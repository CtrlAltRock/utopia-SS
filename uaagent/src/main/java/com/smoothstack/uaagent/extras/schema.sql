CREATE DATABASE  IF NOT EXISTS utopia_airlines ;
USE utopia_airlines;
-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: utopia_airlines
-- ------------------------------------------------------
-- Server version	8.0.28

--
-- Table structure for table `airplane`
--

DROP TABLE IF EXISTS airplane;
CREATE TABLE airplane (
  id int unsigned NOT NULL AUTO_INCREMENT,
  type_id int unsigned NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY id_UNIQUE (id),
  KEY fk_airplane_airplane_model1_idx (type_id),
  CONSTRAINT fk_airplane_airplane_model1 FOREIGN KEY (type_id) REFERENCES airplane_type (id)
);

--
-- Table structure for table `airplane_type`
--

DROP TABLE IF EXISTS airplane_type;
CREATE TABLE airplane_type (
  id int unsigned NOT NULL AUTO_INCREMENT,
  max_capacity int unsigned NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY id_UNIQUE (id)
);
--
-- Table structure for table `airport`
--

DROP TABLE IF EXISTS airport;
CREATE TABLE airport (
  iata_id char(3) NOT NULL,
  city varchar(45) NOT NULL,
  PRIMARY KEY (iata_id),
  UNIQUE KEY iata_id_UNIQUE (iata_id)
);

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS booking;
CREATE TABLE booking (
  id int unsigned NOT NULL AUTO_INCREMENT,
  is_active tinyint NOT NULL DEFAULT '1',
  confirmation_code varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY id_UNIQUE (id)
);

--
-- Table structure for table `booking_agent`
--

DROP TABLE IF EXISTS booking_agent;
CREATE TABLE booking_agent (
  booking_id int unsigned NOT NULL,
  agent_id int unsigned NOT NULL,
  PRIMARY KEY (booking_id),
  UNIQUE KEY booking_id_UNIQUE (booking_id),
  KEY fk_booking_booker_user1_idx (agent_id),
  CONSTRAINT fk_booking_booker_booking1 FOREIGN KEY (booking_id) REFERENCES booking (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_booking_booker_user1 FOREIGN KEY (agent_id) REFERENCES `user` (id) ON DELETE CASCADE ON UPDATE CASCADE
) ;
--
-- Table structure for table `booking_guest`
--

DROP TABLE IF EXISTS booking_guest;
CREATE TABLE booking_guest (
  booking_id int unsigned NOT NULL,
  contact_email varchar(255) NOT NULL,
  contact_phone varchar(45) NOT NULL,
  PRIMARY KEY (booking_id),
  UNIQUE KEY booking_id_UNIQUE (booking_id),
  CONSTRAINT fk_booking_guest_booking1 FOREIGN KEY (booking_id) REFERENCES booking (id) ON DELETE CASCADE ON UPDATE CASCADE
) ;

--
-- Table structure for table `booking_payment`
--

DROP TABLE IF EXISTS booking_payment;
CREATE TABLE booking_payment (
  booking_id int unsigned NOT NULL,
  stripe_id varchar(255) NOT NULL,
  refunded tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (booking_id),
  UNIQUE KEY booking_id_UNIQUE (booking_id),
  KEY fk_booking_payment_booking1_idx (booking_id),
  CONSTRAINT fk_booking_payment_booking1 FOREIGN KEY (booking_id) REFERENCES booking (id) ON DELETE CASCADE ON UPDATE CASCADE
) ;

--
-- Table structure for table `booking_user`
--

DROP TABLE IF EXISTS booking_user;
CREATE TABLE booking_user (
  booking_id int unsigned NOT NULL,
  user_id int unsigned NOT NULL,
  PRIMARY KEY (booking_id),
  UNIQUE KEY booking_id_UNIQUE (booking_id),
  KEY fk_user_bookings_booking1_idx (booking_id),
  KEY fk_user_bookings_user1_idx (user_id),
  CONSTRAINT fk_user_bookings_booking1 FOREIGN KEY (booking_id) REFERENCES booking (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_user_bookings_user1 FOREIGN KEY (user_id) REFERENCES `user` (id) ON DELETE CASCADE ON UPDATE CASCADE
) ;

--
-- Table structure for table `flight`
--

DROP TABLE IF EXISTS flight;
CREATE TABLE flight (
  id int unsigned NOT NULL,
  route_id int unsigned NOT NULL,
  airplane_id int unsigned NOT NULL,
  departure_time datetime NOT NULL,
  reserved_seats int unsigned NOT NULL,
  seat_price float NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY id_UNIQUE (id),
  KEY fk_tbl_flight_tbl_route1_idx (route_id),
  KEY fk_flight_airplane1_idx (airplane_id),
  CONSTRAINT fk_flight_airplane1 FOREIGN KEY (airplane_id) REFERENCES airplane (id),
  CONSTRAINT fk_tbl_flight_tbl_route1 FOREIGN KEY (route_id) REFERENCES route (id) ON DELETE CASCADE ON UPDATE CASCADE
) ;

--
-- Table structure for table `flight_bookings`
--

DROP TABLE IF EXISTS flight_bookings;
CREATE TABLE flight_bookings (
  flight_id int unsigned NOT NULL,
  booking_id int unsigned NOT NULL,
  PRIMARY KEY (booking_id,flight_id),
  KEY fk_flight_bookings_booking (booking_id),
  KEY fk_flight_bookings_flight (flight_id),
  CONSTRAINT fk_flight_bookings_booking FOREIGN KEY (booking_id) REFERENCES booking (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_flight_bookings_flight FOREIGN KEY (flight_id) REFERENCES flight (id) ON DELETE CASCADE ON UPDATE CASCADE
) ;


--
-- Table structure for table `route`
--

DROP TABLE IF EXISTS route;
CREATE TABLE route (
  id int unsigned NOT NULL AUTO_INCREMENT,
  origin_id char(3) NOT NULL,
  destination_id char(3) NOT NULL,
  PRIMARY KEY (id,origin_id,destination_id),
  UNIQUE KEY unique_route (origin_id,destination_id),
  UNIQUE KEY id_UNIQUE (id),
  KEY fk_route_airport1_idx (origin_id),
  KEY fk_route_airport2_idx (destination_id),
  CONSTRAINT fk_route_airport1 FOREIGN KEY (origin_id) REFERENCES airport (iata_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_route_airport2 FOREIGN KEY (destination_id) REFERENCES airport (iata_id) ON DELETE CASCADE ON UPDATE CASCADE
) ;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS user;
CREATE TABLE `user` (
  id int unsigned NOT NULL AUTO_INCREMENT,
  role_id int unsigned NOT NULL,
  given_name varchar(255) NOT NULL,
  family_name varchar(255) NOT NULL,
  username varchar(45) NOT NULL,
  email varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  phone varchar(45) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY id_UNIQUE (id),
  UNIQUE KEY username_UNIQUE (username),
  UNIQUE KEY email_UNIQUE (email),
  UNIQUE KEY phone_UNIQUE (phone),
  KEY fk_user_user_role1_idx (role_id),
  CONSTRAINT fk_user_user_role1 FOREIGN KEY (role_id) REFERENCES user_role (id)
) ;



--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS user_role;
CREATE TABLE user_role (
  id int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY id_UNIQUE (id),
  UNIQUE KEY name_UNIQUE (`name`)
) ;

-- Dump completed
