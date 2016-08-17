insert into ss_type(id,grp,name,detail) values(1,'EMPGRADE','C9','C9');
insert into ss_type(id,grp,name,detail) values(2,'EMPGRADE','C10','C10');
insert into ss_type(id,grp,name,detail) values(3,'EMPGRADE','C11','C11');
insert into ss_type(id,grp,name,detail) values(4,'EMPGRADE','C12','C12');
insert into ss_type(id,grp,name,detail) values(5,'EMPGRADE','C13','C13');
insert into ss_type(id,grp,name,detail) values(6,'EMPTYPE','DH','Direct Hire');
insert into ss_type(id,grp,name,detail) values(7,'EMPTYPE','VD','Vendor');
insert into ss_type(id,grp,name,detail) values(8,'COMSITE','SH', 'Shang Hai');
insert into ss_type(id,grp,name,detail) values(9,'COMSITE','DL', 'Da Lian');
insert into ss_type(id,grp,name,detail) values(10,'COMSITE','GZ', 'Guang Zhou');

insert into ss_user(id, name, login_name, password) values (1,'Admin','admin','JIkCExpzJiiu9uKHKCfbEN98B78=');
insert into ss_user(id, name, login_name, password) values (2,'ss_user','ss_user','JIkCExpzJiiu9uKHKCfbEN98B78=');
insert into ss_user(id, name, login_name, password) values (3,'Guest','guest','JIkCExpzJiiu9uKHKCfbEN98B78=');
 
insert into ss_role(id, name) values (1,'ss_user');
insert into ss_role(id, name) values (2,'ADMIN');
insert into ss_role(id, name) values (3,'GUEST'); 

insert into ss_user_role(user_id, role_id) values (1,1);
insert into ss_user_role(user_id, role_id) values (1,2);
insert into ss_user_role(user_id, role_id) values (2,1);
insert into ss_user_role(user_id, role_id) values (3,1);

insert into ss_employee(id,name,emp_type, emp_grade) values (1,'Jason',6,5);
insert into ss_employee(id,name,emp_type) values (2,'Peter',6);
insert into ss_employee(id,name,emp_grade) values (3,'Ive',5);
insert into ss_employee(id,name) values (4,'Jonathan');

insert into ss_employee2(id,name,emp_type, emp_grade) values (1,'Jason',6,5);
insert into ss_employee2(id,name,emp_type) values (2,'Peter',6);
insert into ss_employee2(id,name,emp_grade) values (3,'Ive',5);
insert into ss_employee2(id,name) values (4,'Jonathan');