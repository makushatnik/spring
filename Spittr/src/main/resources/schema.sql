drop table if exists spittle;
drop table if exists spitter;

create table spitter (
  id int(5) identity,
  username varchar(25) not null,
  password varchar(25) not null,
  first_name varchar(30) not null,
  last_name varchar(30) not null,
  email varchar(50) not null,
  role varchar(15) not null,
  active boolean default true,
  blocked boolean default false,
  photo_url varchar(50),
  date_created datetime default now()
);

create table spittle (
  id int(5) identity primary key,
  spitter_id int(5) not null,
  message varchar(2000) not null,
  posted_time datetime not null,
  latitude double(11,5) default 0,
  longitude double(11,5) default 0,
  foreign key (spitter_id) references spitter(id)
);
