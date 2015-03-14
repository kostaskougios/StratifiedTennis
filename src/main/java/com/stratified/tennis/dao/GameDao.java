package com.stratified.tennis.dao;

import com.stratified.tennis.model.Player;
import com.stratified.tennis.model.TennisGame;
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
	private MappingSqlQueryWithParameters<TennisGame> gameSqlQuery;

	@PostConstruct
	private void init() {
		createSqlUpdate = new SqlUpdate(dataSource, "insert into game(player1,player2,start) values(?,?,?)", new int[]{Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP});
		createSqlUpdate.setReturnGeneratedKeys(true);

		gameSqlQuery = new MappingSqlQueryWithParameters<TennisGame>(dataSource, "select id,player1,player2,start,stop from game where id=?") {
			@Override
			protected TennisGame mapRow(ResultSet rs, int rowNum, Object[] parameters, Map context) throws SQLException {
				Timestamp stop = rs.getTimestamp(5);
				TennisGame tennisGame = TennisGame.newBuilder().id(rs.getInt(1))
						.player1(Player.of(rs.getString(2)))
						.player2(Player.of(rs.getString(3)))
						.start(new DateTime(rs.getTimestamp(4)))
						.stop(stop == null ? null : new DateTime(stop))
						.build();
				return tennisGame;
			}
		};
		gameSqlQuery.setParameters(new SqlParameter[]{new SqlParameter(Types.INTEGER)});
	}

	public TennisGame create(TennisGame tennisGame) {
		FailFast.notNull(tennisGame, "game");

		GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		createSqlUpdate.update(new Object[]{tennisGame.getPlayer1().getName(), tennisGame.getPlayer2().getName(), new java.sql.Date(tennisGame.getStart().getMillis())}, generatedKeyHolder);
		int id = generatedKeyHolder.getKey().intValue();
		return tennisGame.withId(id);
	}

	public TennisGame retrieve(int id) {
		List<TennisGame> tennisGames = gameSqlQuery.execute(id);
		if (tennisGames.isEmpty()) return null;
		if (tennisGames.size() != 1) throw new IllegalStateException("database corrupted, 2 games with id " + id);
		return tennisGames.get(0);
	}
}
