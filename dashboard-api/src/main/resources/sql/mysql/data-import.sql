insert into type(id,grp,name,detail) values(1,'EMPGRADE','C9','C9');
insert into type(id,grp,name,detail) values(2,'EMPGRADE','C10','C10');
insert into type(id,grp,name,detail) values(3,'EMPGRADE','C11','C11');
insert into type(id,grp,name,detail) values(4,'EMPGRADE','C12','C12');
insert into type(id,grp,name,detail) values(5,'EMPGRADE','C13','C13');
insert into type(id,grp,name,detail) values(6,'EMPTYPE','DH','Direct Hire');
insert into type(id,grp,name,detail) values(7,'EMPTYPE','VD','Vendor');
insert into type(id,grp,name,detail) values(8,'COMSITE','SH', 'Shang Hai');
insert into type(id,grp,name,detail) values(9,'COMSITE','DL', 'Da Lian');
insert into type(id,grp,name,detail) values(10,'COMSITE','GZ', 'Guang Zhou');

insert into user(id, name, login, password) values (1,'Roy','roy','spring');
insert into user(id, name, login, password) values (2,'Craig','craig','spring');
insert into user(id, name, login, password) values (3,'Greg','greg','spring');
 
insert into role(id, name) values (1,'ROLE_USER');
insert into role(id, name) values (2,'ROLE_ADMIN');
insert into role(id, name) values (3,'ROLE_GUEST'); 

insert into user_role(user_id, role_id) values (1,1);
insert into user_role(user_id, role_id) values (1,2);
insert into user_role(user_id, role_id) values (2,1);
insert into user_role(user_id, role_id) values (3,1);
