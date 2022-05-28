CREATE TABLE IF NOT EXISTS `history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `added` date DEFAULT NULL,
  `palavra_id` BIGINT(20) DEFAULT NULL,
	PRIMARY KEY (`id`),
   FOREIGN KEY (`palavra_id`) REFERENCES `tab_palavras` (`id`)
) ENGINE=InnoDB