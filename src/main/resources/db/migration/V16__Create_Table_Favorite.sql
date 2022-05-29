CREATE TABLE IF NOT EXISTS `favorite` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `added` datetime DEFAULT NULL,
  `palavra_id` BIGINT(20) DEFAULT NULL,
  `user_id` BIGINT(20) DEFAULT NULL,
   PRIMARY KEY (`id`),
   FOREIGN KEY (`palavra_id`) REFERENCES `tab_palavras` (`id`),
   FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB