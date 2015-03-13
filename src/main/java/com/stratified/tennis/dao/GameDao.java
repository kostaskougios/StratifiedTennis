package com.stratified.tennis.dao;

import com.stratified.tennis.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author kostas.kougios
 * Date: 13/03/15
 */
@Component
public class GameDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Game create(Game game) {
		List<Map<String, Object>> keys = new ArrayList<>(1);
		GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder(keys);
		PreparedStatementCreator psc = con -> {
			PreparedStatement st = con.prepareStatement("insert into game(player1,player2,start) values(?,?,?)");
			st.setString(1, game.getPlayer1());
			st.setString(2, game.getPlayer2());

			st.setDate(3, new java.sql.Date(game.getStart().getMillis()));
			return st;
		};

		jdbcTemplate.update(psc, generatedKeyHolder);
		int id = (int) keys.get(0).entrySet().iterator().next().getValue();
		return game.withId(id);
	}
}
