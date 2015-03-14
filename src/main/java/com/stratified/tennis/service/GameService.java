package com.stratified.tennis.service;

import com.stratified.tennis.dao.GameDao;
import com.stratified.tennis.model.Game;
import com.stratified.tennis.util.FailFast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author kostas.kougios
 * Date: 13/03/15
 */
@Component
public class GameService {
	@Autowired
	private GameDao gameDao;

	public Game initiate(Game game) {
		FailFast.notNull(game, "game");
		return gameDao.create(game);
	}
}
