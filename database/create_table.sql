create database jdbc_project1;
use jdbc_project1;

create user 'webadmin'@'localhost' identified by 'webadmin';
grant all privileges on * . * to 'webadmin'@'localhost';

create table user(
	id bigint not null auto_increment primary key,
    username varchar(150) not null,
    password varchar(150) not null,
    fullname varchar(150) not null,
    email varchar(150) not null,
    phoneNumber varchar(20) not null,
    address varchar(150) null,
    status int not null, -- 1: active, 2: no active
    role_id bigint not null
);

create table role(
	id bigint not null auto_increment primary key,
    role varchar(150) not null
);

alter table user add constraint fk_user_role
foreign key(role_id) references role(id);

insert into role(id, role) values
(1, "ADMIN"), (2, "USER");
select * from role;


insert into user(id, username, password, fullname, email, phoneNumber, address, status, role_id) values
(1, 'admin', 'webadmin', 'Quản trị', 'admin@example.com', '123', null, 1, 1),
(2, 'user', 'webuser', 'Người dùng', 'user@example.com', '456', null, 1, 2);
insert into user(id, username, password, fullname, email, phoneNumber, address, status, role_id) values
(1303, 'diepsang', 'webdiepsang', 'Diệp Sáng', 'diepsang@gmail.com', '0987654321', null, 1, 2),
(1304, 'tranthu', 'webtranthu', 'Trần Thư', 'tranthu@gmail.com', '0987654321', null, 1, 2),
(1305, 'phamhung', 'webphamhung', 'Phạm Hưng', 'phamhung@gmail.com', '0987654321', null, 1, 2),
(1306, 'khuongtran', 'webkhuongtran', 'Khương Trần', 'khuongtran@example.com', '0987654321', null, 1, 2),
(1307, 'minhthu', 'webminhthu', 'Minh Thư', 'minhthu@gmail.com', '0987654321', null, 1, 2),
(1308, 'nhathoang', 'webnhathoang', 'Nhật Hoàng', 'nhathoang@gmail.com', '0987654321', null, 1, 2),
(1309, 'quocky', 'webquocky', 'Quốc Kỳ', 'quocky@gmail.com', '0987654321', null, 1, 2),
(1310, 'minhviet', 'webminhviet', 'Minh Việt', 'minhviet@gmail.com', '0987654321', null, 1, 2),
(1311, 'thaotrang', 'webthaotrang', 'Thảo Trang', 'thaotrang@gmail.com', '0987654321', null, 1, 2);
select * from user;