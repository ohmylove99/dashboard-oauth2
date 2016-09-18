create database staffdb;
CREATE USER 'staffdb'@'localhost' IDENTIFIED BY 'staffdb';
grant all on staffdb.* to staffdb@localhost identified by 'staffdb';
GRANT ALL PRIVILEGES ON staffdb.* TO 'staffdb'@'%' IDENTIFIED BY 'staffdb' WITH GRANT OPTION; 
set password for 'staffdb'@'localhost' =password('staffdb');
flush privileges;