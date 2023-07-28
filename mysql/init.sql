CREATE DATABASE IF NOT EXISTS wordify;
USE wordify;

CREATE TABLE IF NOT EXISTS `collections` (
  `id` int(11) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `definition_id` int(11) UNSIGNED ZEROFILL NOT NULL,
  `collector_id` int(11) UNSIGNED ZEROFILL NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `definition_id` (`definition_id`,`collector_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `definitions` (
  `id` int(11) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `word_id` int(11) UNSIGNED ZEROFILL DEFAULT NULL,
  `phonetic_id` int(11) UNSIGNED ZEROFILL DEFAULT NULL,
  `author_id` int(11) UNSIGNED ZEROFILL DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `word_id` (`word_id`),
  KEY `phonetic_id` (`phonetic_id`),
  KEY `user_id` (`author_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `examples` (
  `id` int(11) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `definition_id` int(11) UNSIGNED ZEROFILL DEFAULT NULL,
  `example` text DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `definition_id` (`definition_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `meaning` (
  `definition_id` int(11) UNSIGNED ZEROFILL NOT NULL,
  `meaning` text DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`definition_id`),
  UNIQUE KEY `definition_id` (`definition_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `phonetics` (
  `id` int(11) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `phonetic` varchar(255) NOT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `phonetic` (`phonetic`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `tagging` (
  `definition_id` int(11) UNSIGNED ZEROFILL NOT NULL,
  `tag_id` int(11) UNSIGNED ZEROFILL NOT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`definition_id`,`tag_id`),
  KEY `tag_id` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `tags` (
  `id` int(11) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `tag` varchar(255) NOT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `tag` (`tag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) UNSIGNED ZEROFILL NOT NULL,
  `username` varchar(20) NOT NULL,
  `profile_name` varchar(50) NOT NULL,
  `icon` varchar(50),
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `user_auth` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `password` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `words` (
  `id` int(11) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `word` varchar(255) NOT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `word` (`word`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `refresh_tokens` (
  `token` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL,
  `expiration` datetime NOT NULL,
  `is_valid` tinyint(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


ALTER TABLE `collections`
  ADD CONSTRAINT `collection_idfk_1` FOREIGN KEY (`definition_id`) REFERENCES `definitions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `definitions`
  ADD CONSTRAINT `definitions_ibfk_1` FOREIGN KEY (`word_id`) REFERENCES `words` (`id`),
  ADD CONSTRAINT `definitions_ibfk_2` FOREIGN KEY (`phonetic_id`) REFERENCES `phonetics` (`id`),
  ADD CONSTRAINT `definitions_ibfk_3` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`);

ALTER TABLE `examples`
  ADD CONSTRAINT `examples_ibfk_1` FOREIGN KEY (`definition_id`) REFERENCES `definitions` (`id`);

ALTER TABLE `meaning`
  ADD CONSTRAINT `meaning_ibfk_1` FOREIGN KEY (`definition_id`) REFERENCES `definitions` (`id`);

ALTER TABLE `tagging`
  ADD CONSTRAINT `tagging_ibfk_1` FOREIGN KEY (`definition_id`) REFERENCES `definitions` (`id`),
  ADD CONSTRAINT `tagging_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`);

ALTER TABLE `users`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user_auth` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;




CREATE USER 'auth'@'%' IDENTIFIED BY 'auth_password';
GRANT SELECT, INSERT, UPDATE, DELETE ON wordify.user_auth TO 'auth'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON wordify.refresh_tokens TO 'auth'@'%';
GRANT SELECT ON wordify.refresh_tokens TO 'auth'@'%';
FLUSH PRIVILEGES;


CREATE USER 'api'@'%' IDENTIFIED BY 'api_password';
GRANT SELECT, INSERT, UPDATE, DELETE ON wordify.* TO 'api'@'%';
REVOKE INSERT,DELETE ON wordify.users FROM 'api'@'%';
REVOKE SELECT, INSERT, UPDATE, DELETE ON wordify.user_auth FROM 'api'@'%';
REVOKE SELECT, INSERT, UPDATE, DELETE ON wordify.refresh_tokens FROM 'api'@'%';
FLUSH PRIVILEGES;

INSERT INTO user_auth(`username`,`password`) VALUES ("developper","5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8");
