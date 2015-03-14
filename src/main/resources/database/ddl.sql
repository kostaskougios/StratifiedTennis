create table tennisgame (
	id serial not null,
	player1 varchar(100) not null,
	player2 varchar(100) not null,
	start timestamp not null,
	stop timestamp,
	primary key(id)
);
create table game (
	tennisGameId integer not null,
	gameOrder integer not null,
	player1Score integer not null,
	player2Score integer not null,
	constraint FK_TENNIS_ID foreign key(tennisGameId) references tennisgame(id)
);