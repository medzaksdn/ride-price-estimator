DROP TABLE IF EXISTS price;
DROP TABLE IF EXISTS ride_option;

CREATE TABLE ride_option (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(20) NOT NULL
);

CREATE TABLE price (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  distance FLOAT NOT NULL,
  price DOUBLE NOT NULL,
  ride_option_id INT NOT NULL,
  FOREIGN KEY (ride_option_id) REFERENCES ride_option(id)
);

INSERT INTO ride_option(id, name) VALUES
    (1, 'POOL'),
    (2, 'COMFORT'),
    (3, 'LUXURY');

INSERT INTO price(distance, price, ride_option_id) VALUES
    -- POOL
    (0, 10, 1), -- minimum price $ 10
    (4000, 3, 1), -- $ 3  per km between 0 and 4 km
    (8000, 2, 1), -- $ 2  per km between 4 and 8 km
    (15000, 1, 1) ,-- $ 1  per km between 8 and 15 km
    -- COMFORT
    (0, 12, 2),
    (4000, 3.5, 2),
    (8000, 2.5, 2),
    (15000, 1.5, 2),
    -- LUXURY
    (0, 14, 3),
    (4000, 4, 3),
    (8000, 3, 3),
    (15000, 2, 3);