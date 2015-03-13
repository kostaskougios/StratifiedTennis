create table game (
	id serial not null,
	player1 varchar(100) not null,
	player2 varchar(100) not null,
	start timestamp not null,
	stop timestamp,
	primary key(id)
)