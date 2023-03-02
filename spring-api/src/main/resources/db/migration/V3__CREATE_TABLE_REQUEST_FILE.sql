CREATE TABLE IF NOT EXISTS `solicitation_file` (
	`id` BIGINT(19) NOT NULL AUTO_INCREMENT,
	`location` TEXT NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`name` VARCHAR(100) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`solicitation_id` BIGINT(19) NOT NULL,
	PRIMARY KEY (`id`) USING BTREE,
	INDEX `FK_SOLICITATION_FILE_SOLICITATION` (`solicitation_id`) USING BTREE,
	CONSTRAINT `FK_SOLICITATION_FILE_SOLICITATION` FOREIGN KEY (`solicitation_id`) REFERENCES `solicitation` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION
)
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
;
