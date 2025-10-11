create table if not exists games (
    id bigint not null primary key auto_increment,
    name varchar(100) not null,
    console varchar(50) not null,
    year int not null
);