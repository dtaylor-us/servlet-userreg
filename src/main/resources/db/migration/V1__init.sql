CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(20) NOT NULL,
  `email_verification_hash` varchar(45) DEFAULT NULL,
  `email_verification_attempts` int(11) DEFAULT NULL,
  `status` varchar(15) DEFAULT 'new',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='User Table';