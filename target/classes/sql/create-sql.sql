-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema railway
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema railway
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `railway` DEFAULT CHARACTER SET utf8 ;
USE `railway` ;

-- -----------------------------------------------------
-- Table `railway`.`detail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `railway`.`detail` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `railway`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `railway`.`role` (
  `id` INT NOT NULL,
  `role_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `role_name_UNIQUE` (`role_name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `railway`.`route`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `railway`.`route` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `start_station_id` INT NOT NULL,
  `end_station_id` INT NOT NULL,
  `depart_time` TIME NOT NULL,
  `time` TIME NOT NULL,
  `price` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `railway`.`station`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `railway`.`station` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `railway`.`route_has_station`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `railway`.`route_has_station` (
  `route_id` INT NOT NULL,
  `station_id` INT NOT NULL,
  PRIMARY KEY (`route_id`, `station_id`),
  INDEX `fk_route_has_station_station1_idx` (`station_id` ASC) VISIBLE,
  INDEX `fk_route_has_station_route_idx` (`route_id` ASC) VISIBLE,
  CONSTRAINT `fk_route_has_station_route`
    FOREIGN KEY (`route_id`)
    REFERENCES `railway`.`route` (`id`),
  CONSTRAINT `fk_route_has_station_station1`
    FOREIGN KEY (`station_id`)
    REFERENCES `railway`.`station` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `railway`.`trip`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `railway`.`trip` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `depart_date` DATE NOT NULL,
  `arrival_date` DATE NOT NULL,
  `available_places` INT NOT NULL,
  `route_id` INT NOT NULL,
  PRIMARY KEY (`id`, `route_id`),
  INDEX `fk_trip_route1_idx` (`route_id` ASC) VISIBLE,
  CONSTRAINT `fk_trip_route1`
    FOREIGN KEY (`route_id`)
    REFERENCES `railway`.`route` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `railway`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `railway`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `role_id` INT NOT NULL,
  `detail_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
  INDEX `fk_user_role1_idx` (`role_id` ASC) VISIBLE,
  INDEX `fk_user_detail1_idx` (`detail_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_detail1`
    FOREIGN KEY (`detail_id`)
    REFERENCES `railway`.`detail` (`id`),
  CONSTRAINT `fk_user_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `railway`.`role` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `railway`.`ticket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `railway`.`ticket` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `trip_id` INT NOT NULL,
  `seat` INT NOT NULL,
  PRIMARY KEY (`id`, `user_id`, `trip_id`),
  INDEX `fk_train_has_user_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_ticket_trip1_idx` (`trip_id` ASC) VISIBLE,
  CONSTRAINT `fk_ticket_trip1`
    FOREIGN KEY (`trip_id`)
    REFERENCES `railway`.`trip` (`id`),
  CONSTRAINT `fk_train_has_user_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `railway`.`user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
