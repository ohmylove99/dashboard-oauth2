create database oauth;
CREATE USER 'oauth'@'localhost' IDENTIFIED BY 'oauth';
grant all on oauth.* to oauth@localhost identified by 'oauth';
GRANT ALL PRIVILEGES ON oauth.* TO 'oauth'@'%' IDENTIFIED BY 'oauth' WITH GRANT OPTION; 
set password for 'oauth'@'localhost' =password('oauth');
flush privileges;