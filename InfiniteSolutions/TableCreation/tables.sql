//State can also be a province or a county
//Street can also be PO box
CREATE TABLE address (
  id INT PRIMARY KEY,
  street VARCHAR(50),
  city VARCHAR(50),
  state VARCHAR(50),
  zip INT,
  country VARCHAR(50));
//
CREATE TABLE credit_card (
  name VARCHAR(50),
  number VARCHAR(16) PRIMARY KEY,
  exp_date DATE,
  cvv VARCHAR(4)
);
//
CREATE TABLE location (
  location_id int PRIMARY KEY,
  name VARCHAR(50),
  type VARCHAR(1)
);
//
CREATE TABLE shippingCostMultipliers (
  multiplier VARCHAR(30) PRIMARY KEY,
  value DOUBLE,
);
//
CREATE TABLE account (
  id INT PRIMARY KEY,
  type VARCHAR(1),
  name VARCHAR(50),
  phone VARCHAR(15),
  credit_card_id INT,
  FOREIGN KEY (credit_card_id) REFERENCES credit_card,
  billing_address_id INT,
  FOREIGN KEY (billing_address_id) REFERENCES address);
//
CREATE TABLE package (
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
  FOREIGN KEY (source_addr_id) REFERENCES address,
) ;
//
CREATE TABLE manifestItem (
  tracking_id INT,
  FOREIGN KEY (tracking_id) REFERENCES package);
//
CREATE TABLE trackingEvents (
  tracking_id INT,
  FOREIGN KEY (tracking_id) REFERENCES package,
  location_id INT,
  FOREIGN KEY (location_id) REFERENCES location,
  date TIMESTAMP,
  status VARCHAR(20));
//
CREATE TABLE shipping_order (
  order_id INT PRIMARY KEY,
  tracking_id INT,
  account_id INT,
  date TIMESTAMP,
  cost DOUBLE,
  FOREIGN KEY (tracking_id) REFERENCES package,
  FOREIGN KEY (account_id) REFERENCES  account
);
//