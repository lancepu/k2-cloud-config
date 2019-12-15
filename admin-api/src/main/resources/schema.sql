create schema if not exists secure_admin_api;

use secure_admin_api;

create table if not exists users(
	username varchar(50) not null primary key,
	password varchar(100) not null,
	enabled boolean not null
);

create table if not exists authorities (
	username varchar(50) not null,
	authority varchar(50) not null,
	constraint fk_authorities_users foreign key(username) references users(username));
	create unique index ix_auth_username on authorities (username,authority
);

/* Users */
insert into users (username, password, enabled) values ('employee',	'$2a$10$0cTGious45e2Vc87oxHWC.wL72Yq4rbJFVOXc000iELEbYO1mH6he', true);
insert into users (username, password, enabled)	values ('teamLead',	'$2a$10$0cTGious45e2Vc87oxHWC.wL72Yq4rbJFVOXc000iELEbYO1mH6he', true);
insert into users (username, password, enabled)	values ('manager', 	'$2a$10$0cTGious45e2Vc87oxHWC.wL72Yq4rbJFVOXc000iELEbYO1mH6he', true);
insert into users (username, password, enabled)	values ('admin',	'$2a$10$0cTGious45e2Vc87oxHWC.wL72Yq4rbJFVOXc000iELEbYO1mH6he', true);

/* Authorities */
insert into authorities (username, authority) values ('employee', 	'ROLE_EMPLOYEE');
insert into authorities (username, authority) values ('teamLead', 	'ROLE_EMPLOYEE');
insert into authorities (username, authority) values ('teamLead',	'ROLE_TEAMLEAD');
insert into authorities (username, authority) values ('manager', 	'ROLE_EMPLOYEE');
insert into authorities (username, authority) values ('manager', 	'ROLE_TEAMLEAD');
insert into authorities (username, authority) values ('manager', 	'ROLE_MANAGER');
insert into authorities (username, authority) values ('admin', 	'ROLE_EMPLOYEE');
insert into authorities (username, authority) values ('admin', 	'ROLE_TEAMLEAD');
insert into authorities (username, authority) values ('admin', 	'ROLE_MANAGER');
insert into authorities (username, authority) values ('admin', 	'ROLE_ADMIN');


