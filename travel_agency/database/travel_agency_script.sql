-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema travel_agency
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema travel_agency
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `travel_agency` DEFAULT CHARACTER SET utf8 ;
USE `travel_agency` ;

-- -----------------------------------------------------
-- Table `travel_agency`.`categories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travel_agency`.`categories` (
  `category_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`category_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travel_agency`.`special_offers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travel_agency`.`special_offers` (
  `special_offer_id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  `description` VARCHAR(1000) NOT NULL,
  `discount` INT NOT NULL,
  PRIMARY KEY (`special_offer_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travel_agency`.`countries`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travel_agency`.`countries` (
  `country_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `flag_image_path` VARCHAR(300) NOT NULL,
  `description` VARCHAR(2000) NULL,
  PRIMARY KEY (`country_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travel_agency`.`cities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travel_agency`.`cities` (
  `city_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(2000) NOT NULL,
  `image_path` VARCHAR(300) NOT NULL,
  `country_id` INT NOT NULL,
  PRIMARY KEY (`city_id`),
  INDEX `fk_cities_countries1_idx` (`country_id` ASC) VISIBLE,
  CONSTRAINT `fk_cities_countries1`
    FOREIGN KEY (`country_id`)
    REFERENCES `travel_agency`.`countries` (`country_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travel_agency`.`hotels`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travel_agency`.`hotels` (
  `hotel_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `description` VARCHAR(2000) NOT NULL,
  `image_path` VARCHAR(120) NOT NULL,
  `stars` INT NOT NULL,
  `city_id` INT NOT NULL,
  PRIMARY KEY (`hotel_id`),
  INDEX `fk_hotels_cities1_idx` (`city_id` ASC) VISIBLE,
  CONSTRAINT `fk_hotels_cities1`
    FOREIGN KEY (`city_id`)
    REFERENCES `travel_agency`.`cities` (`city_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travel_agency`.`tours`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travel_agency`.`tours` (
  `tour_id` INT NOT NULL AUTO_INCREMENT,
  `price_start` DECIMAL(9,2) NOT NULL,
  `date_of_departure` DATE NOT NULL,
  `date_of_return` DATE NOT NULL,
  `status` ENUM('ordered', 'free', 'visited') NOT NULL,
  `type_of_food` ENUM('RO', 'BB', 'HB', 'FB', 'AI', 'UAI') NOT NULL,
  `type_of_placement` ENUM('SINGLE', 'DBL', 'TRIPLE', 'QUADRIPLE') NOT NULL,
  `type_of_room` ENUM('STANDARD', 'SUPERIOR', 'STUDIO', 'FAMILY') NOT NULL,
  `category_id` INT NOT NULL,
  `hotel_id` INT NOT NULL,
  `special_offers_id` INT NULL,
  PRIMARY KEY (`tour_id`),
  INDEX `category_id_idx` (`category_id` ASC) VISIBLE,
  INDEX `fk_tours_special_offers1_idx` (`special_offers_id` ASC) VISIBLE,
  INDEX `fk_tours_hotels1_idx` (`hotel_id` ASC) VISIBLE,
  CONSTRAINT `category_id`
    FOREIGN KEY (`category_id`)
    REFERENCES `travel_agency`.`categories` (`category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tours_special_offers1`
    FOREIGN KEY (`special_offers_id`)
    REFERENCES `travel_agency`.`special_offers` (`special_offer_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tours_hotels1`
    FOREIGN KEY (`hotel_id`)
    REFERENCES `travel_agency`.`hotels` (`hotel_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travel_agency`.`user_details`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travel_agency`.`user_details` (
  `details_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `surname` VARCHAR(45) NULL,
  `phone_number` VARCHAR(25) NULL,
  `number_of_visited_tours` INT NULL,
  `loyality_discount` INT NOT NULL,
  `agency_additional_discount` INT NOT NULL,
  `image_avatar_path` VARCHAR(300) NOT NULL,
  PRIMARY KEY (`details_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travel_agency`.`user_roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travel_agency`.`user_roles` (
  `role_id` INT NOT NULL,
  `role_name` VARCHAR(45) NULL,
  PRIMARY KEY (`role_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travel_agency`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travel_agency`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(100) NOT NULL,
  `login` VARCHAR(25) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `user_status` ENUM('active', 'deleted') NOT NULL,
  `user_details_id` INT NULL,
  `user_role_id` INT NOT NULL,
  PRIMARY KEY (`user_id`),
  INDEX `fk_users_user_details1_idx` (`user_details_id` ASC) VISIBLE,
  INDEX `fk_users_user_roles1_idx` (`user_role_id` ASC) VISIBLE,
  CONSTRAINT `fk_users_user_details1`
    FOREIGN KEY (`user_details_id`)
    REFERENCES `travel_agency`.`user_details` (`details_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_user_roles1`
    FOREIGN KEY (`user_role_id`)
    REFERENCES `travel_agency`.`user_roles` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travel_agency`.`tour_orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travel_agency`.`tour_orders` (
  `order_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `tour_id` INT NOT NULL,
  `confirmation_date` DATETIME NOT NULL,
  `order_status` ENUM('ACTIVE', 'CANCELED', 'COMPLETED') NOT NULL,
  PRIMARY KEY (`order_id`),
  INDEX `client_id_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_tour_orders_tours1_idx` (`tour_id` ASC) VISIBLE,
  CONSTRAINT `client_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `travel_agency`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tour_orders_tours1`
    FOREIGN KEY (`tour_id`)
    REFERENCES `travel_agency`.`tours` (`tour_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travel_agency`.`messages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travel_agency`.`messages` (
  `message_id` INT NOT NULL AUTO_INCREMENT,
  `tour_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `message` VARCHAR(2000) NOT NULL,
  `message_time` DATETIME NOT NULL,
  PRIMARY KEY (`message_id`),
  INDEX `tour_id_idx` (`tour_id` ASC) VISIBLE,
  INDEX `fk_messages_users1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `tour_id`
    FOREIGN KEY (`tour_id`)
    REFERENCES `travel_agency`.`tours` (`tour_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_messages_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `travel_agency`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travel_agency`.`visited_tours`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travel_agency`.`visited_tours` (
  `user_id` INT NOT NULL,
  `tour_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `tour_id`),
  INDEX `fk_users_has_tours_tours1_idx` (`tour_id` ASC) VISIBLE,
  INDEX `fk_users_has_tours_users1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_users_has_tours_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `travel_agency`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_tours_tours1`
    FOREIGN KEY (`tour_id`)
    REFERENCES `travel_agency`.`tours` (`tour_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travel_agency`.`cities_has_categories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travel_agency`.`cities_has_categories` (
  `city_id` INT NOT NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`city_id`, `category_id`),
  INDEX `fk_cities_has_categories_categories1_idx` (`category_id` ASC) VISIBLE,
  INDEX `fk_cities_has_categories_cities1_idx` (`city_id` ASC) VISIBLE,
  CONSTRAINT `fk_cities_has_categories_cities1`
    FOREIGN KEY (`city_id`)
    REFERENCES `travel_agency`.`cities` (`city_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cities_has_categories_categories1`
    FOREIGN KEY (`category_id`)
    REFERENCES `travel_agency`.`categories` (`category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

INSERT into travel_agency.user_details (details_id, name, surname, phone_number, number_of_visited_tours, loyality_discount, agency_additional_discount, image_avatar_path) VALUES (1, 'Maksim', 'Shaturko', '+375291933686', 0, 0, 0, '');

INSERT into travel_agency.user_roles (role_id, role_name) VALUES (1, 'ADMIN');
INSERT into travel_agency.user_roles (role_id, role_name) VALUES (2, 'CLIENT');

INSERT into travel_agency.users (email , login, password, user_status, user_role_id, user_details_id) VALUES ('shaturko.maksim@gmail.com','admin', 'admin', 'active', 1, 1);

INSERT into travel_agency.special_offers(title, description, discount) VALUES ('Горящий_Стандартный', '30% скидка всем турам за неделю до отправления', 30);
