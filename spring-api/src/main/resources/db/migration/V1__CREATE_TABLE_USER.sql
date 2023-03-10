CREATE TABLE IF NOT EXISTS `user` (
	`id` BIGINT(19) NOT NULL AUTO_INCREMENT,
	`email` VARCHAR(75) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`name` VARCHAR(75) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`password` VARCHAR(100) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`role` VARCHAR(20) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	PRIMARY KEY (`id`) USING BTREE,
	UNIQUE INDEX `UK_USER` (`email`) USING BTREE
)
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
;