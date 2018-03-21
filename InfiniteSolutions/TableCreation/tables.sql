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