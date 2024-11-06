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
                                     id_coche INT NOT NULL,
                                     PRIMARY KEY (id_multa)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1;

INSERT INTO coche (matricula, marca, modelo, tipo)
VALUES
    ('1122BBC', 'Renault', 'Clio', 'Familiar'),
    ('2233DDB', 'Seat', 'Ibiza', 'Familiar'),
    ('3344FFC', 'Seat', 'Leon', 'Deportivo'),
    ('4455BBZ', 'Ford', 'S-Max', 'Monovolumen'),
    ('5566CCR', 'Ford', 'Kuga', 'SUV'),
    ('6677FFD', 'Nissan', 'Micra', 'Familiar'),
    ('7788JJZ', 'Volkswagen', 'Passat', 'Familiar'),
    ('8899BBV', 'Volkswagen', 'T-ROC', 'SUV'),
    ('9911FFG', 'Volkswagen', 'Touran', 'Monovolumen'),
    ('8855GFR', 'Renault', 'Scenic', 'Monovolumen');

INSERT INTO multa (precio, fecha, id_coche)
VALUES
    (100.50, '2024-11-06', 1),
    (120.75, '2024-11-07', 1),
    (200.30, '2024-11-08', 1),
    (150.00, '2024-11-09', 4),
    (180.90, '2024-11-10', 4),
    (90.25, '2024-11-11',  8),
    (110.00, '2024-11-12', 8),
    (130.50, '2024-11-13', 8),
    (170.20, '2024-11-14', 9),
    (160.00, '2024-11-15', 10);