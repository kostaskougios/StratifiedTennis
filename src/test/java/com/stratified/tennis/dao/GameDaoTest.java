package com.stratified.tennis.dao;

import com.stratified.tennis.di.Database;
import com.stratified.tennis.model.Game;
import com.stratified.tennis.testdi.GameBackendBeans;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Database.class, GameBackendBeans.class})
public class GameDaoTest {
	@Autowired
	private GameDao gameDao;

	@Test
	public void create() {
		Game game = Game.newGame("p1", "p2");
		Game created = gameDao.create(game);
		assertTrue(created.getId() > 0);
		assertEquals("p1", created.getPlayer1());
		assertEquals("p2", created.getPlayer2());
	}

	@Test
	public void retrievePositive() {
		Game game = Game.newGame("p1", "p2");
		Game created = gameDao.create(game);

		Game retrieved = gameDao.retrieve(created.getId());
		assertEquals(created, retrieved);
	}

	@Test
	public void retrieveNegative() {
		assertNull(gameDao.retrieve(6));
	}

}