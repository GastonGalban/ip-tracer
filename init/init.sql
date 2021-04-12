CREATE DATABASE IF NOT EXISTS `ip-tracer`;
USE `ip-tracer`;
DROP TABLE IF EXISTS country_data;
CREATE TABLE  country_data (country_code VARCHAR(3) NOT NULL PRIMARY KEY,
    name VARCHAR(45),
    languages BLOB,
    currency_codes BLOB,
    timezones BLOB,
    location BLOB);
