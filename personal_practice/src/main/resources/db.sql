
create table squeezeuser(
squeeze_id varchar2(20 char)primary key,
squeeze_pw varchar2(20 char)not null,
squeeze_date date not null,
squeeze_folder varchar(40 char) unique,
squeeze_email varchar(30 char)not null
);

select * from squeezeuser;


truncate table squeezeuser;
drop table squeezeuser cascade constraint purge;