CREATE TABLE IF NOT EXISTS `solicitation` (
	`id` BIGINT(19) NOT NULL AUTO_INCREMENT,
	`creation_date` DATETIME(6) NOT NULL,
	`description` TEXT NULL DEFAULT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`state` VARCHAR(12) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`subject` VARCHAR(75) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`user_id` BIGINT(19) NOT NULL,
	PRIMARY KEY (`id`) USING BTREE,
	INDEX `FK_SOLICITATION_USER` (`user_id`) USING BTREE,
	CONSTRAINT `FK_SOLICITATION_USER` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION
)
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
;