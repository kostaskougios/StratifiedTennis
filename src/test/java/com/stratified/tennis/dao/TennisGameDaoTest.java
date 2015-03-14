package com.stratified.tennis.dao;

import com.stratified.tennis.di.Database;
import com.stratified.tennis.model.TennisGame;
import com.stratified.tennis.model.TestData;
import com.stratified.tennis.testdi.GameBackendBeans;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Database.class, GameBackendBeans.class})
public class TennisGameDaoTest {
	@Autowired
	private GameDao gameDao;

	private TennisGame tennisGame = TestData.TENNIS_GAME;

	@Test
	public void create() {
		TennisGame created = gameDao.create(tennisGame);
		assertTrue(created.getId() > 0);
		assertEquals(tennisGame.getPlayer1(), created.getPlayer1());
		assertEquals(tennisGame.getPlayer2(), created.getPlayer2());
	}

	@Test
	public void retrievePositive() {
		TennisGame created = gameDao.create(tennisGame);

		TennisGame retrieved = gameDao.retrieve(created.getId());
		assertEquals(created, retrieved);
	}

	@Test
	public void retrieveNegative() {
		assertNull(gameDao.retrieve(6));
	}

	@Test
	public void updateGamesWith1Game() {
		TennisGame created = gameDao.create(tennisGame);
		TennisGame gameAfterWins = created.win(created.getPlayer2()).win(created.getPlayer2());
		gameDao.update(gameAfterWins);
		assertEquals(gameAfterWins, gameDao.retrieve(created.getId()));
	}

	@Test
	public void updateGamesWith2Games() {
		TennisGame created = gameDao.create(tennisGame);
		TennisGame gameAfterWins = created;
		for (int i = 0; i < 10; i++)
			gameAfterWins = gameAfterWins.win(created.getPlayer2());
		gameDao.update(gameAfterWins);
		TennisGame retrieved = gameDao.retrieve(created.getId());
		assertEquals(gameAfterWins, retrieved);
	}

	@Test
	public void updateGamesWith2GamesGameEnded() {
		TennisGame created = gameDao.create(tennisGame);
		TennisGame gameAfterWins = created;
		for (int i = 0; i < 10; i++)
			gameAfterWins = gameAfterWins.win(created.getPlayer2());
		gameDao.update(gameAfterWins);
		TennisGame retrieved = gameDao.retrieve(created.getId());
		assertEquals(TennisGame.Status.COMPLETED, retrieved.getStatus());
	}
}