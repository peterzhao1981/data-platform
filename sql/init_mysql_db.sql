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
  `date` INT(6) NOT NULL COMMENT 'Date: 20160601, 20160602...',
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
  `date` INT(6) NULL DEFAULT NULL COMMENT 'Daily date: 20160601, 20160602...',
  `new_user` INT(10) NULL DEFAULT NULL COMMENT 'Daily new users',
  `new_user_fb` INT(10) NULL DEFAULT NULL COMMENT 'Daily new facebook users',
  `new_user_yt` INT(10) NULL DEFAULT NULL COMMENT 'Daily new youtube users',
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
  `date` INT(6) NULL DEFAULT NULL COMMENT 'Weekly date: 20160605, 201606012...',
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
  `date` INT(6) NULL DEFAULT NULL COMMENT 'Monthly date: 201605, 201606...',
  `active_user` INT(10) NULL DEFAULT NULL COMMENT 'Monthly active users',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `date_idx` (`date` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
