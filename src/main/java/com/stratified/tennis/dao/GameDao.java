package com.stratified.tennis.dao;

import com.stratified.tennis.model.Game;
import com.stratified.tennis.util.FailFast;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQueryWithParameters;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.Map;

/**
 * @author kostas.kougios
 * Date: 13/03/15
 */
@Component
public class GameDao {
	@Autowired
	private DataSource dataSource;

	private SqlUpdate createSqlUpdate;
	private MappingSqlQueryWithParameters<Game> gameSqlQuery;

	@PostConstruct
	private void init() {
		createSqlUpdate = new SqlUpdate(dataSource, "insert into game(player1,player2,start) values(?,?,?)", new int[]{Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP});
		createSqlUpdate.setReturnGeneratedKeys(true);

		gameSqlQuery = new MappingSqlQueryWithParameters<Game>(dataSource, "select id,player1,player2,start,stop from game where id=?") {
			@Override
			protected Game mapRow(ResultSet rs, int rowNum, Object[] parameters, Map context) throws SQLException {
				Timestamp stop = rs.getTimestamp(5);
				Game game = Game.newBuilder().id(rs.getInt(1))
						.player1(rs.getString(2))
						.player2(rs.getString(3))
						.start(new DateTime(rs.getTimestamp(4)))
						.stop(stop == null ? null : new DateTime(stop))
						.build();
				return game;
			}
		};
		gameSqlQuery.setParameters(new SqlParameter[]{new SqlParameter(Types.INTEGER)});
	}

	public Game create(Game game) {
		FailFast.notNull(game, "game");

		GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		createSqlUpdate.update(new Object[]{game.getPlayer1(), game.getPlayer2(), new java.sql.Date(game.getStart().getMillis())}, generatedKeyHolder);
		int id = generatedKeyHolder.getKey().intValue();
		return game.withId(id);
	}

	public Game retrieve(int id) {
		List<Game> games = gameSqlQuery.execute(id);
		if (games.isEmpty()) return null;
		if (games.size() != 1) throw new IllegalStateException("database corrupted, 2 games with id " + id);
		return games.get(0);
	}
}
