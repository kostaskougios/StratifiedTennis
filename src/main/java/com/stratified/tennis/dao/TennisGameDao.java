package com.stratified.tennis.dao;

import com.stratified.tennis.model.Game;
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
public class TennisGameDao {
	@Autowired
	private DataSource dataSource;

	private SqlUpdate tennisGameSqlInsert;
	private SqlUpdate tennisGameSqlUpdate;
	private SqlUpdate gameSqlInsert;
	private SqlUpdate gameSqlDelete;

	private MappingSqlQueryWithParameters<TennisGame> tennisGameSqlQuery;
	private MappingSqlQueryWithParameters<Game> gameSqlQuery;

	@PostConstruct
	private void init() {
		tennisGameSqlInsert = new SqlUpdate(dataSource, "insert into tennisgame(player1,player2,start) values(?,?,?)", new int[]{Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP});
		tennisGameSqlInsert.setReturnGeneratedKeys(true);

		tennisGameSqlUpdate = new SqlUpdate(dataSource, "update tennisgame set player1=?, player2=?, start=?, stop=? where id=?", new int[]{Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.TIMESTAMP, Types.INTEGER});

		gameSqlInsert = new SqlUpdate(dataSource, "insert into game(tennisGameId,gameOrder,player1Score,player2Score) values(?,?,?,?)", new int[]{Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER});
		gameSqlDelete = new SqlUpdate(dataSource, "delete from game where tennisGameId=? ", new int[]{Types.INTEGER});

		tennisGameSqlQuery = new MappingSqlQueryWithParameters<TennisGame>(dataSource, "select id,player1,player2,start,stop from tennisgame where id=?") {
			@Override
			protected TennisGame mapRow(ResultSet rs, int rowNum, Object[] parameters, Map context) throws SQLException {
				int id = rs.getInt(1);
				List<Game> games = gameSqlQuery.execute(id);

				Timestamp stop = rs.getTimestamp(5);

				TennisGame tennisGame = TennisGame.newBuilder().id(id)
						.player1(Player.of(rs.getString(2)))
						.player2(Player.of(rs.getString(3)))
						.start(new DateTime(rs.getTimestamp(4)))
						.stop(stop == null ? null : new DateTime(stop))
						.games(games)
						.build();

				return tennisGame;
			}
		};
		tennisGameSqlQuery.setParameters(new SqlParameter[]{new SqlParameter(Types.INTEGER)});

		gameSqlQuery = new MappingSqlQueryWithParameters<Game>(dataSource, "select player1Score,player2Score from game where tennisGameId=? order by gameOrder asc") {
			@Override
			protected Game mapRow(ResultSet rs, int rowNum, Object[] parameters, Map context) throws SQLException {
				return Game.of(rs.getInt(1), rs.getInt(2));
			}
		};
		gameSqlQuery.setParameters(new SqlParameter[]{new SqlParameter(Types.INTEGER)});

	}

	public TennisGame create(TennisGame tennisGame) {
		FailFast.notNull(tennisGame, "game");

		GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		tennisGameSqlInsert.update(new Object[]{tennisGame.getPlayer1().getName(), tennisGame.getPlayer2().getName(), new java.sql.Date(tennisGame.getStart().getMillis())}, generatedKeyHolder);
		int id = generatedKeyHolder.getKey().intValue();
		TennisGame tg = tennisGame.withId(id);
		updateGames(tg);
		return tg;
	}

	public TennisGame retrieve(int id) {
		List<TennisGame> tennisGames = tennisGameSqlQuery.execute(id);
		if (tennisGames.isEmpty()) return null;
		if (tennisGames.size() != 1) throw new IllegalStateException("database corrupted, 2 games with id " + id);
		return tennisGames.get(0);
	}

	/**
	 * updates the tennis game
	 *
	 * @param tennisGame    the TennisGame.
	 */
	public void update(TennisGame tennisGame) {
		if (tennisGame.getId() < 1) throw new IllegalArgumentException("game not persisted: " + tennisGame);
		updateGames(tennisGame);
		tennisGameSqlUpdate.update(
				tennisGame.getPlayer1().getName(),
				tennisGame.getPlayer2().getName(),
				new java.sql.Date(tennisGame.getStart().getMillis()),
				tennisGame.getStop() == null ? null : new java.sql.Date(tennisGame.getStop().getMillis()),
				tennisGame.getId()
		);
	}

	private void updateGames(TennisGame tennisGame) {
		gameSqlDelete.update(tennisGame.getId());
		List<Game> games = tennisGame.getGames();
		for (int order = 1; order <= games.size(); order++) {
			Game game = games.get(order - 1);
			gameSqlInsert.update(tennisGame.getId(), order, game.getPlayer1Score(), game.getPlayer2Score());
		}
	}
}
