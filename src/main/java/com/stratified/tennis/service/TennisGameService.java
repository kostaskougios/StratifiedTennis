package com.stratified.tennis.service;

import com.stratified.tennis.dao.GameDao;
import com.stratified.tennis.model.Player;
import com.stratified.tennis.model.TennisGame;
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
public class TennisGameService {
	@Autowired
	private GameDao gameDao;

	public TennisGame initiate(TennisGame tennisGame) {
		FailFast.notNull(tennisGame, "game");
		return gameDao.create(tennisGame);
	}

	public TennisGame getById(int id) {
		return gameDao.retrieve(id);
	}

	public TennisGame win(TennisGame tennisGame, Player player) {
		FailFast.notNull(tennisGame, "game");
		FailFast.notNull(player, "player");

		if (!tennisGame.isPlayer(player))
			throw new IllegalArgumentException("Player " + player + " not taking part in game " + tennisGame);

		return tennisGame.win(player);
	}
}
