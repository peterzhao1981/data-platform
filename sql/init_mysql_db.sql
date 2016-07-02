-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `mode_bi` DEFAULT CHARACTER SET utf8mb4 ;

USE `mode_bi` ;

-- -----------------------------------------------------
-- Table `mode_bi`.`md_calendar`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mode_bi`.`md_calendar` ;

CREATE TABLE IF NOT EXISTS `mode_bi`.`md_calendar` (
	`id` INT(10) NOT NULL AUTO_INCREMENT,
  `date` INT(8) NOT NULL COMMENT 'Date: 20160601, 20160602...',
  `weekend` INT(6) NULL DEFAULT NULL COMMENT 'Weed end of the current date: ',
  `month` INT(6) NULL DEFAULT NULL COMMENT 'Month of the date: 201601, 201602...',
  `quarter` varchar(6) NULL DEFAULT NULL COMMENT 'Quarter of the date: 2016Q1, 2016Q2',
  `year` INT(6) NULL DEFAULT NULL COMMENT 'Year of the date: 2016. 2015...',
  `start_ts` BIGINT(13) NULL DEFAULT NULL COMMENT 'China timezone start timestamp',
  `end_ts` BIGINT(13) NULL DEFAULT NULL COMMENT 'China timezone end timestamp',
	 PRIMARY KEY (`id`),
   UNIQUE INDEX `date_idx` (`date` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `mode_bi`.`md_stats_daily`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mode_bi`.`md_stats_daily` ;

CREATE TABLE IF NOT EXISTS `mode_bi`.`md_stats_daily` (
  `id` INT(10) NOT NULL AUTO_INCREMENT,
  `date` INT(8) NULL DEFAULT NULL COMMENT 'Daily date: 20160601, 20160602...',
  `new_user` INT(10) NULL DEFAULT NULL COMMENT 'Daily new users',
  `new_user_fb` INT(10) NULL DEFAULT NULL COMMENT 'Daily new facebook users',
  `new_user_yt` INT(10) NULL DEFAULT NULL COMMENT 'Daily new youtube users',
  `new_user_ins` INT(10) NULL DEFAULT NULL COMMENT 'Daily new instagram users',
  `total_user` INT(10) NULL DEFAULT NULL COMMENT 'Total users',
  `active_user` INT(10) NULL DEFAULT NULL COMMENT 'Daily active users',
  `order` INT(10) NULL DEFAULT NULL COMMENT 'Daily order count',
  `total_order` INT(10) NULL DEFAULT NULL COMMENT 'Total order count',
  `gmv` FLOAT NULL DEFAULT NULL COMMENT 'Daily GMV',
  `total_gmv` FLOAT NULL DEFAULT NULL COMMENT 'Total GMV',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `date_idx` (`date` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `mode_bi`.`md_stats_weekly`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mode_bi`.`md_stats_weekly` ;

CREATE TABLE IF NOT EXISTS `mode_bi`.`md_stats_weekly` (
  `id` INT(10) NOT NULL AUTO_INCREMENT,
  `date` INT(8) NULL DEFAULT NULL COMMENT 'Weekly date: 20160605, 201606012...',
  `active_user` INT(10) NULL DEFAULT NULL COMMENT 'Weekly active users',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `date_idx` (`date` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `mode_bi`.`md_stats_monthly`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mode_bi`.`md_stats_monthly` ;

CREATE TABLE IF NOT EXISTS `mode_bi`.`md_stats_monthly` (
  `id` INT(10) NOT NULL AUTO_INCREMENT,
  `date` INT(8) NULL DEFAULT NULL COMMENT 'Monthly date: 201605, 201606...',
  `active_user` INT(10) NULL DEFAULT NULL COMMENT 'Monthly active users',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `date_idx` (`date` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `mode_bi`.`md_stats_hourly_request`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mode_bi`.`md_stats_hourly_request` ;

CREATE TABLE IF NOT EXISTS `mode_bi`.`md_stats_hourly_request` (
  `id` INT(10) NOT NULL AUTO_INCREMENT,
  `date` INT(8) NULL DEFAULT NULL COMMENT 'Daily date: 20160601, 20160602...',
  `h1` INT(10) NULL DEFAULT NULL COMMENT '00:00 to 01:00',
  `h2` INT(10) NULL DEFAULT NULL COMMENT '01:00 to 02:00',
  `h3` INT(10) NULL DEFAULT NULL COMMENT '02:00 to 03:00',
  `h4` INT(10) NULL DEFAULT NULL COMMENT '03:00 to 04:00',
  `h5` INT(10) NULL DEFAULT NULL COMMENT '04:00 to 05:00',
  `h6` INT(10) NULL DEFAULT NULL COMMENT '05:00 to 06:00',
  `h7` INT(10) NULL DEFAULT NULL COMMENT '06:00 to 07:00',
  `h8` INT(10) NULL DEFAULT NULL COMMENT '07:00 to 08:00',
  `h9` INT(10) NULL DEFAULT NULL COMMENT '08:00 to 09:00',
  `h10` INT(10) NULL DEFAULT NULL COMMENT '09:00 to 10:00',
  `h11` INT(10) NULL DEFAULT NULL COMMENT '10:00 to 11:00',
  `h12` INT(10) NULL DEFAULT NULL COMMENT '11:00 to 12:00',
  `h13` INT(10) NULL DEFAULT NULL COMMENT '12:00 to 13:00',
  `h14` INT(10) NULL DEFAULT NULL COMMENT '13:00 to 14:00',
  `h15` INT(10) NULL DEFAULT NULL COMMENT '14:00 to 15:00',
  `h16` INT(10) NULL DEFAULT NULL COMMENT '15:00 to 16:00',
  `h17` INT(10) NULL DEFAULT NULL COMMENT '16:00 to 17:00',
  `h18` INT(10) NULL DEFAULT NULL COMMENT '17:00 to 18:00',
  `h19` INT(10) NULL DEFAULT NULL COMMENT '18:00 to 19:00',
  `h20` INT(10) NULL DEFAULT NULL COMMENT '19:00 to 20:00',
  `h21` INT(10) NULL DEFAULT NULL COMMENT '20:00 to 21:00',
  `h22` INT(10) NULL DEFAULT NULL COMMENT '21:00 to 22:00',
  `h23` INT(10) NULL DEFAULT NULL COMMENT '22:00 to 23:00',
  `h24` INT(10) NULL DEFAULT NULL COMMENT '23:00 to 24:00',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `date_idx` (`date` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `mode_bi`.`md_stats_country`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mode_bi`.`md_stats_country` ;

CREATE TABLE IF NOT EXISTS `mode_bi`.`md_stats_country` (
  `country` VARCHAR(30) NOT NULL COMMENT 'Country name',
  `total` INT(10) NULL DEFAULT NULL COMMENT 'Total user count')
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
