package com.stratified.tennis.service;

import com.stratified.tennis.dao.GameDao;
import com.stratified.tennis.model.Game;
import com.stratified.tennis.util.FailFast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * The service layer for our API. Might be an overhead here as it propagates calls to the dao,
 * but it will be useful later on if we were extending the game to do more things and also we
 * mark transaction boundaries which is a best practise in a layered architecture.
 *
 * @author kostas.kougios
 * Date: 13/03/15
 */
@Component
@Transactional
public class GameService {
	@Autowired
	private GameDao gameDao;

	public Game initiate(Game game) {
		FailFast.notNull(game, "game");
		return gameDao.create(game);
	}

	public Game getById(int id) {
		return gameDao.retrieve(id);
	}
}
