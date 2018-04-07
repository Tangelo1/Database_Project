CREATE TABLE IF NOT EXISTS address  (
  id INT AUTO_INCREMENT PRIMARY KEY,
  street VARCHAR(50),
  city VARCHAR(50),
  state VARCHAR(50),
  postal VARCHAR(8),
  country VARCHAR(50));

CREATE TABLE IF NOT EXISTS creditCard  (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50),
  number VARCHAR(16),
  exp_date VARCHAR(8),
  cvv VARCHAR(4)
);

CREATE TABLE IF NOT EXISTS location (
  location_id int AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50),
  type VARCHAR(1)
);

CREATE TABLE IF NOT EXISTS shippingCostMultipliers (
  multiplier VARCHAR(30) PRIMARY KEY,
  value DOUBLE
);

INSERT INTO shippingCostMultipliers VALUES ('Overnight', 2);
INSERT INTO shippingCostMultipliers VALUES ('Expedited', 1.75);
INSERT INTO shippingCostMultipliers VALUES ('Standard', 1);
INSERT INTO shippingCostMultipliers VALUES ('NoRush', .85);
INSERT INTO shippingCostMultipliers VALUES ('PerPound', 1);

CREATE TABLE IF NOT EXISTS account (
  id INT AUTO_INCREMENT PRIMARY KEY,
  type VARCHAR(1),
  name VARCHAR(50),
  phone VARCHAR(15),
  credit_card_id INT,
  FOREIGN KEY (credit_card_id) REFERENCES creditCard,
  billing_address_id INT,
  FOREIGN KEY (billing_address_id) REFERENCES address
);

CREATE TABLE IF NOT EXISTS package (
  tracking_id INT PRIMARY KEY,
  weight DOUBLE,
  type VARCHAR(10),
  speed VARCHAR(10),
  value DOUBLE,
  destination_addr_id INT,
  source_addr_id INT,
  isHazard BOOLEAN,
  isInternational BOOLEAN,
  FOREIGN KEY (destination_addr_id) REFERENCES address,
  FOREIGN KEY (source_addr_id) REFERENCES address
) ;

CREATE TABLE IF NOT EXISTS manifestItem (
  tracking_id INT PRIMARY KEY,
  FOREIGN KEY (tracking_id) REFERENCES package,
  name VARCHAR(75)
);

CREATE TABLE IF NOT EXISTS  trackingEvents(
  tracking_id INT,
  FOREIGN KEY (tracking_id) REFERENCES package,
  location_id INT,
  FOREIGN KEY (location_id) REFERENCES location,
  date TIMESTAMP,
  status VARCHAR(20));

CREATE TABLE IF NOT EXISTS shippingOrder (
  order_id INT AUTO_INCREMENT PRIMARY KEY,
  tracking_id INT,
  account_id INT,
  date TIMESTAMP,
  cost DOUBLE,
  FOREIGN KEY (tracking_id) REFERENCES package,
  FOREIGN KEY (account_id) REFERENCES  account
);
