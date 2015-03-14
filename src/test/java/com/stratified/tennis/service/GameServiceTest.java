package com.stratified.tennis.service;

import com.stratified.tennis.dao.GameDao;
import com.stratified.tennis.model.Game;
import com.stratified.tennis.model.TestData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {
	@Mock
	private GameDao gameDao;
	@InjectMocks
	private GameService gameService;

	private Game game = TestData.GAME;
	private Game game5 = game.withId(5);

	@Test
	public void testInitiate() {
		when(gameDao.create(game)).thenReturn(game5);
		Game g = gameService.initiate(game);
		assertEquals(5, g.getId());
		verify(gameDao).create(game);
	}

	@Test
	public void testGetById() {
		when(gameDao.retrieve(5)).thenReturn(game5);
		assertEquals(game5, gameService.getById(5));
		verify(gameDao).retrieve(5);
	}
}