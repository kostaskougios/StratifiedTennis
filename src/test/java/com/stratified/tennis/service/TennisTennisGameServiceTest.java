package com.stratified.tennis.service;

import com.stratified.tennis.dao.GameDao;
import com.stratified.tennis.model.TennisGame;
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
public class TennisTennisGameServiceTest {
	@Mock
	private GameDao gameDao;
	@InjectMocks
	private TennisGameService tennisGameService;

	private TennisGame tennisGame = TestData.TENNIS_GAME;
	private TennisGame tennisGame5 = tennisGame.withId(5);

	@Test
	public void testInitiate() {
		when(gameDao.create(tennisGame)).thenReturn(tennisGame5);
		TennisGame g = tennisGameService.initiate(tennisGame);
		assertEquals(5, g.getId());
		verify(gameDao).create(tennisGame);
	}

	@Test
	public void testGetById() {
		when(gameDao.retrieve(5)).thenReturn(tennisGame5);
		assertEquals(tennisGame5, tennisGameService.getById(5));
		verify(gameDao).retrieve(5);
	}
}