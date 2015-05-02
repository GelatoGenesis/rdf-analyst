-- This should be executed as root (change the password!!! (and other credentials if you wish))
create user 'rdfanalyst'@'localhost' identified by 'rdfanalyst';
create database rdfanalyst default character set = "utf8";
grant all privileges on rdfanalyst.* to 'rdfanalyst'@'localhost';

-- This should be executed as the newly created user. If you changed the database name on creation then change it here on the first line to. Don't change anything else!
use rdfanalyst;
create table query (

);