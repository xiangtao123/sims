CREATE SCHEMA `jsrush` DEFAULT CHARACTER SET utf8 ;


use jsrush;

create table test (
 id  int not null primary key auto_increment,
 text varchar(20)
);

insert into test(text) values('权限管理');

select * from test;


