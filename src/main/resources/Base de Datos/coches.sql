DROP DATABASE IF EXISTS coches;
CREATE DATABASE coches;
USE coches;

CREATE TABLE `coche` (
                         `id` int unsigned NOT NULL AUTO_INCREMENT,
                         `matricula` varchar(32) NOT NULL,
                         `marca` varchar(32) DEFAULT NULL,
                         `modelo` varchar(32) DEFAULT NULL,
                         `tipo` varchar(32) DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `coche_pk` (`matricula`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS multa (
                                     id_multa integer unsigned NOT NULL AUTO_INCREMENT,
                                     precio DOUBLE NOT NULL,
                                     fecha DATE DEFAULT NULL,
                                     matricula varchar(7) NOT NULL,
                                     PRIMARY KEY (id_multa)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1;