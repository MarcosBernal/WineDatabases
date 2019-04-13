CREATE DATABASE cataVino;

CREATE TABLE cataVino.`user` (
user_id INT UNIQUE NOT NULL,
name VARCHAR(150),
PRIMARY KEY(`user_id`));


CREATE TABLE cataVino.`grape_variety` (
grape_variety_id INT UNIQUE NOT NULL,
name VARCHAR(150) NOT NULL,
PRIMARY KEY (grape_variety_id));


CREATE TABLE cataVino.`winery` (
winery_id INT UNIQUE NOT NULL,
name VARCHAR(150),
PRIMARY KEY (winery_id));


CREATE TABLE cataVino.`taster` (
taster_id INT UNIQUE NOT NULL,
name VARCHAR(150) NOT NULL,
twitter_handle VARCHAR(150),
PRIMARY KEY (taster_id));


CREATE TABLE cataVino.`country` (
country_id INT UNIQUE NOT NULL,
name_es VARCHAR(128) NOT NULL,
name_en VARCHAR(128) NOT NULL,
name_fr VARCHAR(128) NOT NULL,
iso_code_2 VARCHAR(2),
iso_code_3 VARCHAR(3),
phone_prefix INT,
PRIMARY KEY (country_id));


CREATE TABLE cataVino.`region` (
region_id INT UNIQUE NOT NULL,
name VARCHAR(150) NOT NULL,
area VARCHAR(150),
province VARCHAR(150),
country_id INT NOT NULL,
PRIMARY KEY (region_id));


CREATE TABLE cataVino.`wine` (
wine_id INT UNIQUE NOT NULL,
name VARCHAR(150),
title VARCHAR(255),
description TEXT,
grape_variety_id INT NOT NULL,
designation VARCHAR(255),
winery_id INT NOT NULL,
region_id INT,
PRIMARY KEY (wine_id),
CONSTRAINT FK_grape_variety_grape_variety_id FOREIGN KEY (grape_variety_id) REFERENCES grape_variety(grape_variety_id),
CONSTRAINT FK_winery_winery_id FOREIGN KEY (winery_id) REFERENCES winery(winery_id),
CONSTRAINT FK_region_region_id FOREIGN KEY (region_id) REFERENCES region(region_id)
);
-- CONSTRAINT NOT_DUPLICATES UNIQUE (title, grape_variety_id, winery_id, region_id)

CREATE TABLE cataVino.`wine_user_review` (
wine_id INT NOT NULL,
user_id INT NOT NULL,
date DATETIME,
score DECIMAL(10,2),
PRIMARY KEY (wine_id, user_id),
CONSTRAINT FK_user_user_id FOREIGN KEY(user_id) REFERENCES user(user_id),
CONSTRAINT FK_user_wine_id FOREIGN KEY(wine_id) REFERENCES wine(wine_id)
);

CREATE TABLE cataVino.`wine_scoring_guide` (
wine_id INT NOT NULL,
taster_id INT NOT NULL,
date DATETIME,
score DECIMAL(10,2) NOT NULL,
price DECIMAL(10,2),
PRIMARY KEY(wine_id, taster_id),
CONSTRAINT FK_wine_wine_id FOREIGN KEY (wine_id) REFERENCES wine(wine_id),
CONSTRAINT FK_taster_taster_id FOREIGN KEY (taster_id) REFERENCES taster(taster_id)
);