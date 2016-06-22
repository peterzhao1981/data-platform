-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema mode
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mode
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mode_bi` DEFAULT CHARACTER SET utf8mb4 ;
-- -----------------------------------------------------
-- Schema new_schema1
-- -----------------------------------------------------
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
  `date` INT(6) NULL DEFAULT NULL COMMENT 'Date: 20160601, 20160602...',
  `new_reg` INT(10) NULL DEFAULT NULL COMMENT 'Daily new registration users',
  `new_reg_fb` INT(10) NULL DEFAULT NULL COMMENT 'Daily new facebook registration users',
  `new_reg_yt` INT(10) NULL DEFAULT NULL COMMENT 'Daily new youtube registration users',
  `total_reg` INT(10) NULL DEFAULT NULL COMMENT 'Total registration users',
  `actives` INT(10) NULL DEFAULT NULL COMMENT 'Daily active users',
  `orders` INT(10) NULL DEFAULT NULL COMMENT 'Daily order count',
  `total_orders` INT(10) NULL DEFAULT NULL COMMENT 'Total order count',
  `gmv` FLOAT NULL DEFAULT NULL COMMENT 'Daily GMV',
  `total_gmv` FLOAT NULL DEFAULT NULL COMMENT 'Total GMV',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `date_idx` (`date` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
