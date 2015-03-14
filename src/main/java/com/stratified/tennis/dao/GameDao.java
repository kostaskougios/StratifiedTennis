package com.stratified.tennis.dao;

import com.stratified.tennis.model.Game;
import com.stratified.tennis.util.FailFast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Types;

/**
 * @author kostas.kougios
 * Date: 13/03/15
 */
@Component
public class GameDao {
	@Autowired
	private DataSource dataSource;

	private SqlUpdate createSqlUpdate;

	@PostConstruct
	private void init() {
		createSqlUpdate = new SqlUpdate(dataSource, "insert into game(player1,player2,start) values(?,?,?)", new int[]{Types.VARCHAR, Types.VARCHAR, Types.DATE});
		createSqlUpdate.setReturnGeneratedKeys(true);
	}

	public Game create(Game game) {
		FailFast.notNull(game, "game");

		GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		createSqlUpdate.update(new Object[]{game.getPlayer1(), game.getPlayer2(), new java.sql.Date(game.getStart().getMillis())}, generatedKeyHolder);
		int id = generatedKeyHolder.getKey().intValue();
		return game.withId(id);
	}
}
