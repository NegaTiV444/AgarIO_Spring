CREATE DATABASE IF NOT EXISTS agario;
USE agario;
create table records (name varchar(255) not null, score float not null, primary key (name));
