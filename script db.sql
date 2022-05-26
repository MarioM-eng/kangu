-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema kangu
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema kangu
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `kangu` DEFAULT CHARACTER SET utf8 ;
USE `kangu` ;

-- -----------------------------------------------------
-- Table `kangu`.`persons`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kangu`.`persons` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `dni` VARCHAR(45) NOT NULL,
  `created_at` DATE NULL,
  `updated_at` DATE NULL,
  `deleted_at` DATE NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idUsers_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `dni_UNIQUE` (`dni` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `kangu`.`profiles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kangu`.`profiles` (
  `idProfiles` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idProfiles`),
  UNIQUE INDEX `idprofile_UNIQUE` (`idProfiles` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `kangu`.`modules`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kangu`.`modules` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idmodules_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `kangu`.`module_profile`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kangu`.`module_profile` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `profile_id` INT NOT NULL,
  `module_id` INT NOT NULL,
  INDEX `fk_profile_has_modules_modules1_idx` (`module_id` ASC) VISIBLE,
  INDEX `fk_profile_has_modules_profile1_idx` (`profile_id` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idProfile_has_modules_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `fk_profile_has_modules_profile1`
    FOREIGN KEY (`profile_id`)
    REFERENCES `kangu`.`profiles` (`idProfiles`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_profile_has_modules_modules1`
    FOREIGN KEY (`module_id`)
    REFERENCES `kangu`.`modules` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `kangu`.`actions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kangu`.`actions` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `modules_idModules` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idactions_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_actions_modules1_idx` (`modules_idModules` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  CONSTRAINT `fk_actions_modules1`
    FOREIGN KEY (`modules_idModules`)
    REFERENCES `kangu`.`modules` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `kangu`.`patients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kangu`.`patients` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `person_id` INT NOT NULL,
  `age` VARCHAR(3) NOT NULL,
  `date_birth` DATE NULL,
  `sex` CHAR(1) NULL,
  `diagnosis` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idpatients_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_patients_persons1_idx` (`person_id` ASC) VISIBLE,
  CONSTRAINT `fk_patients_persons1`
    FOREIGN KEY (`person_id`)
    REFERENCES `kangu`.`persons` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `kangu`.`responsibles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kangu`.`responsibles` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `cel` VARCHAR(10) NULL,
  `person_id` INT NOT NULL,
  `address` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idresponsibles_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_responsibles_persons1_idx` (`person_id` ASC) VISIBLE,
  CONSTRAINT `fk_responsibles_persons1`
    FOREIGN KEY (`person_id`)
    REFERENCES `kangu`.`persons` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `kangu`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kangu`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `person_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idusers_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_users_persons1_idx` (`person_id` ASC) VISIBLE,
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  CONSTRAINT `fk_users_persons1`
    FOREIGN KEY (`person_id`)
    REFERENCES `kangu`.`persons` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `kangu`.`patient_responsible`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kangu`.`patient_responsible` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `patient_id` INT NOT NULL,
  `responsible_id` INT NOT NULL,
  INDEX `fk_patients_has_responsibles_responsibles1_idx` (`responsible_id` ASC) VISIBLE,
  INDEX `fk_patients_has_responsibles_patients1_idx` (`patient_id` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idPatients_has_responsibles_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `fk_patients_has_responsibles_patients1`
    FOREIGN KEY (`patient_id`)
    REFERENCES `kangu`.`patients` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_patients_has_responsibles_responsibles1`
    FOREIGN KEY (`responsible_id`)
    REFERENCES `kangu`.`responsibles` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `kangu`.`action_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kangu`.`action_user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `action_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  INDEX `fk_actions_has_users_users1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_actions_has_users_actions1_idx` (`action_id` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idActions_has_users_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `fk_actions_has_users_actions1`
    FOREIGN KEY (`action_id`)
    REFERENCES `kangu`.`actions` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_actions_has_users_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `kangu`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `kangu`.`user_profile`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kangu`.`user_profile` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `profile_id` INT NOT NULL,
  INDEX `fk_users_has_profiles_profiles1_idx` (`profile_id` ASC) VISIBLE,
  INDEX `fk_users_has_profiles_users1_idx` (`user_id` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idUsers_has_profiles_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `fk_users_has_profiles_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `kangu`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_profiles_profiles1`
    FOREIGN KEY (`profile_id`)
    REFERENCES `kangu`.`profiles` (`idProfiles`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `kangu`.`consults`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kangu`.`consults` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `reason` VARCHAR(45) NOT NULL,
  `assessment` VARCHAR(45) NOT NULL,
  `observation` VARCHAR(45) NOT NULL,
  `patient_id` INT NOT NULL,
  `created_at` DATE NULL,
  `updated_at` DATE NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idconsults_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_consults_patients1_idx` (`patient_id` ASC) VISIBLE,
  CONSTRAINT `fk_consults_patients1`
    FOREIGN KEY (`patient_id`)
    REFERENCES `kangu`.`patients` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

DELIMITER $$
DROP PROCEDURE IF EXISTS add_person$$
CREATE PROCEDURE add_person(IN nombre VARCHAR(45),IN dni VARCHAR(45),IN created_at DATE)
BEGIN
    INSERT INTO persons (name,dni,created_at,updated_at,deleted_at) 
	 VALUES(nombre,dni,created_at,null,null);
END$$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS add_user$$
CREATE PROCEDURE add_user(IN nombre VARCHAR(45),IN dni VARCHAR(45),IN created_at DATE,IN username VARCHAR(45),IN password VARCHAR(45), OUT id INT)
BEGIN
	DECLARE idP INT;#Variable donde se almacenará el id de la persona
	/*Handler para error SQL*/
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		SELECT 'Ocurrió un error, la operación se revirtió y el procedimiento almacenado finalizó' as error;
		ROLLBACK;
	END;

	/*Handler para error SQL*/
	DECLARE EXIT HANDLER FOR SQLWARNING
	BEGIN
		SELECT 'Ocurrió un error, la operación se revirtió y el procedimiento almacenado finalizó' as error;
		ROLLBACK;
	END;
    START TRANSACTION;
	CALL add_person(nombre,dni,created_at,updated_at);
    SET idP = (SELECT persons.id FROM persons WHERE persons.dni = dni);
    INSERT INTO users (username,password,person_id) 
	 VALUES(username,password,idP);
     SET id = (SELECT users.id FROM users WHERE users.person_id = idP);
     COMMIT;
END$$
DELIMITER ;


DELIMITER $$
DROP PROCEDURE IF EXISTS add_patient$$
CREATE PROCEDURE add_patient(IN nombre VARCHAR(45),IN dni VARCHAR(45),IN created_at DATE,IN age int,IN date_birth date,IN sex char ,IN diagnosis varchar(45), OUT id int)
BEGIN
	DECLARE idP int;#Variable donde se almacenará el id de la persona
	/*Handler para error SQL*/
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		ROLLBACK;
        SELECT 'Ocurrió un error, la operación se revirtió y el procedimiento almacenado finalizó' as error;
	END;

	/*Handler para error SQL*/
	DECLARE EXIT HANDLER FOR SQLWARNING
	BEGIN
		ROLLBACK;
        SELECT 'Ocurrió un error, la operación se revirtió y el procedimiento almacenado finalizó' as error;
	END;
    /*Inicio de la transacción*/
    START TRANSACTION;
	CALL add_person(nombre,dni,created_at);
    SET idP = (SELECT persons.id FROM persons WHERE persons.dni = dni);
    INSERT INTO patients (person_id,age,date_birth,sex,diagnosis) 
	 VALUES(idP,age,date_birth,sex,diagnosis);
     SET id = (select patients.id from patients where patients.person_id = idP);
     /*Fin de la transacción*/
     commit;
END$$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS add_responsible$$
CREATE PROCEDURE add_responsible(IN nombre VARCHAR(45),IN dni VARCHAR(45),IN created_at DATE,IN cel VARCHAR(45),IN address VARCHAR(45), OUT id INT)
BEGIN
	DECLARE idP INT;#Variable donde se almacenará el id de la persona
	/*Handler para error SQL*/
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		SELECT 'Ocurrió un error, la operación se revirtió y el procedimiento almacenado finalizó' as error;
		ROLLBACK;
	END;

	/*Handler para error SQL*/
	DECLARE EXIT HANDLER FOR SQLWARNING
	BEGIN
		SELECT 'Ocurrió un error, la operación se revirtió y el procedimiento almacenado finalizó' as error;
		ROLLBACK;
	END;
    START TRANSACTION;
	CALL add_person(nombre,dni,created_at);
    SET idP = (SELECT persons.id FROM persons WHERE persons.dni = dni);
    INSERT INTO responsibles (cel,person_id,address) 
	 VALUES(cel,idP,address);
     SET id = (SELECT responsibles.id FROM responsibles WHERE responsibles.person_id = idP);
     commit;
END$$
DELIMITER ;

CALL add_user("Mario",1,now(),"Mario_kangu","j7w9UTDgcgI=",@id);
