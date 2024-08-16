DROP DATABASE IF EXISTS hackathon_db;
CREATE DATABASE IF NOT EXISTS hackathon_db;
USE hackathon_db;

-- Crear tabla City (Ciudad)
CREATE TABLE city (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) 
);

-- Insertar datos en Ciudad
INSERT INTO city (name) VALUES ('Tarragona'), ('Reus');

-- Crear tabla Neighborhood (Barrio)
CREATE TABLE neighborhood (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    postal_code VARCHAR(5) NOT NULL,
    city_id BIGINT NOT NULL,
    FOREIGN KEY (city_id) REFERENCES city(id) ON DELETE CASCADE
);

-- Insertar datos en Barrio - Tarragona
INSERT INTO neighborhood (name, postal_code, city_id) VALUES 
('Parte Alta', '43003', 1),
('Serrallo', '43004', 1),
('Eixample', '43001', 1),
('Nou Eixample Nord', '43005', 1),
('Nou Eixample Sud', '43006', 1),
('Bonavista', '43100', 1),
('Campclar', '43007', 1),
('Torreforta', '43006', 1),
('Sant Pere i Sant Pau', '43007', 1),
('Sant Salvador', '43008', 1),
('La Móra - Tamarit', '43007', 1),
('Llevant', '43008', 1),
('Urbanitzacions de Ponent', '43110', 1),
('Barris Maritims', '43880', 1);

-- Insertar datos en Barrio - Reus
INSERT INTO neighborhood (name, postal_code, city_id) VALUES 
('Centre', '43201', 2),
('Sant Pere', '43202', 2),
('La Pastoreta', '43203', 2),
('Horts de Miró', '43204', 2),
('Mare Molas', '43205', 2),
('Mas Iglesias', '43206', 2),
('Sant Jordi', '43207', 2),
('Mas Abelló', '43204', 2),
('Poetes', '43203', 2),
('Niloga', '43204', 2),
('Montserrat', '43205', 2),
('Ponent', '43206', 2),
('La Mineta', '43203', 2);

-- Crear tabla Evento
CREATE TABLE event (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    image_url VARCHAR(255),
    name VARCHAR(255) NOT NULL,
    description TEXT,
    date DATE
);

-- Insertar datos en Evento
INSERT INTO event (image_url, name, description, date) VALUES 
('https://example.com/event1.jpg', 'Hackathon 2024', 'Annual hackathon event in Tarragona', '2024-09-15'),
('https://example.com/event2.jpg', 'Tech Meetup', 'Monthly tech meetup in Reus', '2024-08-20');

-- Crear tabla Evento-Barrio (relación muchos a muchos)
CREATE TABLE event_has_neighborhood (
    event_id BIGINT,
    neighborhood_id BIGINT,
    PRIMARY KEY (event_id, neighborhood_id),
    FOREIGN KEY (event_id) REFERENCES event(id) ON DELETE CASCADE,
    FOREIGN KEY (neighborhood_id) REFERENCES neighborhood(id) ON DELETE CASCADE
);

-- Insertar datos en Evento-Barrio
INSERT INTO event_has_neighborhood (event_id, neighborhood_id) VALUES 
(1, 1), (1, 2), (2, 15), (2, 16), (2, 17);

-- Crear tabla Categoría
CREATE TABLE category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- Insertar datos en Categoría
INSERT INTO category (name) VALUES ('Technology'), ('Education'), ('Community'), ('Health');

-- Crear tabla Evento-Categoría (relación muchos a muchos)
CREATE TABLE event_has_category (
    event_id BIGINT,
    category_id BIGINT,
    PRIMARY KEY (event_id, category_id),
    FOREIGN KEY (event_id) REFERENCES event(id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE
);

-- Insertar datos en Evento-Categoría
INSERT INTO event_has_category (event_id, category_id) VALUES 
(1, 1), (1, 2), (2, 1), (2, 3);



SELECT * FROM city;
SELECT * FROM neighborhood;
SELECT * FROM event;
SELECT * FROM event_has_neighborhood;
SELECT * FROM category;

SELECT * FROM event_has_neighborhood WHERE event_id = 2;