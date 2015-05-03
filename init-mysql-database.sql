-- This should be executed as root (change the password!!! (and other credentials if you wish))
create user 'rdfanalyst'@'localhost' identified by 'rdfanalyst';
create database rdfanalyst default character set = "utf8";
grant all privileges on rdfanalyst.* to 'rdfanalyst'@'localhost';

-- This should be executed as the newly created user. If you changed the database name on creation then change it here on the first line to. Don't change anything else!

use rdfanalyst;

create table query (
    topic varchar(50) primary key,
    query varchar(5000) not null
);

create table rdf_triple (
    id int(20) unsigned auto_increment primary key,
    topic varchar(50) not null,
    received_ms numeric(21,0) not null,
    subject blob,
    predicate blob,
    object blob,
    foreign key (topic) references query(topic)
);

create index triple_topic on rdf_triple(topic);
create index triple_received on rdf_triple(received_ms desc);