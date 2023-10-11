CREATE TABLE `dishes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `description` varchar(600) DEFAULT NULL,
  `is_roll` tinyint(1) DEFAULT NULL,
  `is_wok` tinyint(1) DEFAULT NULL,
  `is_pizza` tinyint(1) DEFAULT NULL,
  `price` int DEFAULT NULL,
  `image` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `addresses` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(1000) DEFAULT NULL,
  `front_door` int DEFAULT NULL,
  `floor` int DEFAULT NULL,
  `apartment` int DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `users` (
  `id_phone` varchar(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_phone`)
);

CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address_id` int NOT NULL,
  `comment` varchar(1000) DEFAULT NULL,
  `added_datetime` datetime DEFAULT NULL,
  `expiration_datetime` datetime DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `customer_phone` varchar(20) NOT NULL,
  `customer_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `address_id` (`address_id`),
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`)
);

CREATE TABLE `order_points` (
  `dish_id` int NOT NULL,
  `order_id` int NOT NULL,
  `price` int DEFAULT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `dish_id` (`dish_id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `order_points_ibfk_1` FOREIGN KEY (`dish_id`) REFERENCES `dishes` (`id`),
  CONSTRAINT `order_points_ibfk_2` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
);