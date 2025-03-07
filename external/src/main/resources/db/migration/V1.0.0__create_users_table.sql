CREATE TABLE `Users` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '氏名',
  `age` int NOT NULL COMMENT '年齢',
  PRIMARY KEY (`id`)
) COMMENT='ユーザー';
