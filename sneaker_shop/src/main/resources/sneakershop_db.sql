/*
SQLyog Community v13.3.0 (64 bit)
MySQL - 8.0.42 : Database - sneaker_shop_db
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`sneaker_shop_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `sneaker_shop_db`;

/*Table structure for table `cart` */

DROP TABLE IF EXISTS `cart`;

CREATE TABLE `cart` (
  `cart_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int DEFAULT '1',
  `added_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`cart_id`),
  KEY `user_id` (`user_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `cart` */

/*Table structure for table `order_items` */

DROP TABLE IF EXISTS `order_items`;

CREATE TABLE `order_items` (
  `item_id` int NOT NULL AUTO_INCREMENT,
  `order_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`item_id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `order_items` */

insert  into `order_items`(`item_id`,`order_id`,`product_id`,`quantity`,`price`) values 
(1,2,1,2,8999.00),
(2,3,1,2,8999.00),
(3,4,1,1,8999.00),
(4,4,2,1,6499.00),
(5,5,2,1,6499.00),
(6,5,3,1,5499.00),
(7,6,6,1,12000.00),
(8,7,2,4,6499.00),
(9,7,7,2,2000.00),
(10,8,1,1,8999.00);

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `total_amount` decimal(10,2) DEFAULT NULL,
  `payment_status` varchar(30) DEFAULT NULL,
  `razorpay_order_id` varchar(100) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `order_status` varchar(30) DEFAULT 'Processing',
  PRIMARY KEY (`order_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `orders` */

insert  into `orders`(`order_id`,`user_id`,`total_amount`,`payment_status`,`razorpay_order_id`,`created_at`,`order_status`) values 
(1,7,17998.00,'PAID','order_TJd4LG7hPOiuLh','2026-07-30 12:50:17','Processing'),
(2,7,17998.00,'PAID','order_TJd9Uc31AjT8Gf','2026-07-30 12:54:46','Processing'),
(3,7,17998.00,'PAID','order_TJdEsAXOx28DL9','2026-07-30 12:59:47','Processing'),
(4,7,15498.00,'PAID','order_TJdTKuG5FxLCBE','2026-07-30 13:13:59','Processing'),
(5,7,11998.00,'PAID','order_TJdciu4vUxkLK9','2026-07-30 13:22:23','Processing'),
(6,7,12000.00,'PAID','order_TJdwh4fqQQ7L4p','2026-07-30 13:41:23','Processing'),
(7,5,29996.00,'PAID','order_TJea7ctXG40qpu','2026-07-30 14:19:25','Shipped'),
(8,1,8999.00,'PAID','order_TLkqSiuP0LrTSe','2026-08-04 21:44:30','Processing');

/*Table structure for table `products` */

DROP TABLE IF EXISTS `products`;

CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `category` varchar(50) NOT NULL,
  `description` text,
  `price` decimal(10,2) NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `products` */

insert  into `products`(`id`,`name`,`category`,`description`,`price`,`image_url`) values 
(1,'AirFlex Runner','Running','Lightweight running sneakers designed for speed and comfort. Breathable mesh and durable sole.',8999.00,'https://images.unsplash.com/photo-1579338559194-a162d19bf842?q=80&w=687&auto=format&fit=crop'),
(2,'Urban Street Pro','Lifestyle','Minimalist sneakers for everyday wear. Premium leather with a modern urban look.',6499.00,'https://images.unsplash.com/photo-1608667508764-33cf0726b13a?q=80&w=880&auto=format&fit=crop'),
(3,'Classic Court 90s','Retro','Retro-inspired sneakers with a tennis court vibe. Perfect balance between comfort and style.',5499.00,'https://images.unsplash.com/photo-1465453869711-7e174808ace9?q=80&w=1176&auto=format&fit=crop'),
(4,'Volt Edge','Performance','Performance sneakers with bold details. Responsive cushioning for all-day energy.',9999.00,'https://images.unsplash.com/photo-1512374382149-233c42b6a83b?q=80&w=735&auto=format&fit=crop'),
(5,'Zenith Flow','Premium','Premium lifestyle sneakers blending high-quality knit material and futuristic design.',10999.00,'https://images.unsplash.com/photo-1608231387042-66d1773070a5?q=80&w=1074&auto=format&fit=crop'),
(6,'Street Vibe Low','Casual','Casual low-top sneakers with a timeless silhouette. Built for versatility and comfort.',12000.00,'https://images.unsplash.com/photo-1511556532299-8f662fc26c06?q=80&w=1170&auto=format&fit=crop'),
(7,'Nova Horizon','Streetwear','High-top sneakers crafted with suede and mesh. Perfect mix of streetwear and performance.',2000.00,'https://images.unsplash.com/photo-1516767254874-281bffac9e9a?q=80&w=1170&auto=format&fit=crop'),
(8,'Pulse React','Training','Dynamic sneakers with responsive cushioning. Designed for training and everyday comfort.',1000.00,'https://images.unsplash.com/photo-1560769629-975ec94e6a86?q=80&w=764&auto=format&fit=crop'),
(9,'Core Street Retro','Retro','Old-school sneakers inspired by 80s basketball. Durable construction with vintage vibes.',7999.00,'https://images.unsplash.com/photo-1621315271772-28b1f3a5df87?q=80&w=687&auto=format&fit=crop'),
(10,'AeroFlex Lite','Running','Ultra-light sneakers designed for everyday mobility. Breathable and flexible design.',7950.00,'https://images.unsplash.com/photo-1496202703211-aa28e9500c30?q=80&w=1170&auto=format&fit=crop'),
(11,'Nova 90','Retro','Classic retro sneakers featuring a timeless silhouette, premium leather upper, and cushioned sole for everyday comfort',2400.00,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRZxVR77e7UsGoSpDLTdQQ6ok3xcngLmt62ZRm5TnooKsIB5gIDUoRldrEg&s=10'),
(12,'Nomad','Casual','Lightweight casual sneakers designed for all-day comfort. Breathable knit upper and flexible rubber outsole.',2100.00,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQF5J4ylAFjR9yRuWK2DQW-79pf-uU1WrudevBz3qn1DA&s'),
(13,'Forge','Training','Versatile training sneakers built for stability and performance. Supportive fit with responsive cushioning and superior grip.',7000.00,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSB8ghjx5jF-G9F9Kq8tn1BZQg7Ihz2SfE8a1rn51YCohaSuXnZ_6keTE0&s=10'),
(14,'Velocity','Performance','High-performance sneakers engineered for speed and agility. Lightweight mesh upper with responsive cushioning and durable traction.',3000.00,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS89_pAd_Wyx_wTgcYBNoZCj3lU0_qpokodGtVrw24shxggMW8xQkG6ow2m&s=10'),
(15,'Echelon','Premium','Premium sneakers crafted with refined materials and modern elegance. Plush cushioning, premium leather, and exceptional comfort.',7500.00,'https://www.aroundalways.com/cdn/shop/files/Kusumsneaker_1.jpg?v=1777632475'),
(16,'Rogue','Streetwear','Bold streetwear sneakers combining urban style with everyday comfort. Layered upper, cushioned midsole, and durable rubber outsole.',2500.00,'https://m.media-amazon.com/images/I/81lGCh1vqVL._AC_UY1000_.jpg');

/*Table structure for table `ratings` */

DROP TABLE IF EXISTS `ratings`;

CREATE TABLE `ratings` (
  `rating_id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `user_id` int NOT NULL,
  `rating` int NOT NULL,
  `review` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`rating_id`),
  UNIQUE KEY `unique_rating` (`product_id`,`user_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `ratings_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `ratings_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `ratings_chk_1` CHECK ((`rating` between 1 and 5))
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `ratings` */

insert  into `ratings`(`rating_id`,`product_id`,`user_id`,`rating`,`review`,`created_at`) values 
(1,1,1,5,'','2026-07-25 14:17:42'),
(2,1,6,3,'','2026-07-25 15:41:28'),
(3,1,7,5,'','2026-07-25 15:44:30'),
(4,2,7,5,'this is amazing','2026-07-27 14:43:57'),
(5,6,5,5,'','2026-07-30 14:20:04');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `pin_code` varchar(10) DEFAULT NULL,
  `role` enum('USER','ADMIN') DEFAULT 'USER',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `users` */

insert  into `users`(`id`,`username`,`password`,`first_name`,`last_name`,`address`,`city`,`pin_code`,`role`) values 
(1,'nora','toij2+wQ0hKZ4zeCQh42Kw==:/I5rgOkVjfIEewzPUtFZuCwZVB5jETWQmnyJpslhBXA=','Signora','Das','3/60','Kolkata','700080','USER'),
(2,'shankha','fTNQF0sAVjJhoI7nL8p9Rg==:O9Zzib8MonzyW19nPObu9TjCy/CRZ8+ovVR2s0DcGqY=','Shankha','Das','3/60','Kolkata','700080','USER'),
(5,'rutha','Ny8lrNK7eSgvltPHDyZ57g==:+/ycdnN8FTIXbDLjETxHxAEju7oPpLkfuvLB1pocsMA=','Rutha','Sahoo','3/60','Kolkata','700080','USER'),
(6,'arongis','bqDDx9x/BZxo8qg2hFb96Q==:0v7glwHFDpdb2/Q9CgNXbnwyFGxlpwaaH5V+9GEdsvQ=','Signora','Signora','3/60','Kolkata','700080','USER'),
(7,'bochi','NV1Bc0z4GY+5YS83ve+OcQ==:+w6XrIGDM5A46d3WeIPXdZt11o5Nmf8AZaBTR3R/rYs=','bochi','Signora','3/60','Kolkata','700080','USER'),
(8,'admin','fxLEOp598L3xNQbdkwqo2A==:/l93sqsTPogH1OTR3U3fk18tYZP17jCnf6bndssnbk8=','ADMIN','admin','3/60','Kolkata','700080','ADMIN');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
