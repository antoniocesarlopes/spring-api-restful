CREATE TABLE IF NOT EXISTS `solicitation_stage` (
	`id` BIGINT(19) NOT NULL AUTO_INCREMENT,
	`description` TEXT NULL DEFAULT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`realization_date` DATETIME(6) NOT NULL,
	`state` VARCHAR(12) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`user_id` BIGINT(19) NOT NULL,
	`solicitation_id` BIGINT(19) NOT NULL,
	PRIMARY KEY (`id`) USING BTREE,
	INDEX `FK_SOLICITATION_STAGE_USER` (`user_id`) USING BTREE,
	INDEX `FK_SOLICITATION_STAGE_SOLICITATION` (`solicitation_id`) USING BTREE,
	CONSTRAINT `FK_SOLICITATION_STAGE_SOLICITATION` FOREIGN KEY (`solicitation_id`) REFERENCES `solicitation` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT `FK_SOLICITATION_STAGE_USER` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION
)
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
;
