CREATE DATABASE IF NOT EXISTS `ip-tracer`;
USE `ip-tracer`;
DROP TABLE IF EXISTS country_data;
CREATE TABLE  country_data (
    country_code VARCHAR(3) NOT NULL PRIMARY KEY,
    name VARCHAR(45),
    languages BLOB,
    currency_codes BLOB,
    timezones BLOB,
    location BLOB);


DROP TABLE IF EXISTS trace_data;

CREATE TABLE trace_data (
    id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
    ip VARCHAR(17),
    country VARCHAR(45),
    iso_code VARCHAR(3),
    distanceToBsAs DOUBLE,
    languages BLOB,
    currency_data BLOB,
    timezones BLOB,
    date DATETIME
);
