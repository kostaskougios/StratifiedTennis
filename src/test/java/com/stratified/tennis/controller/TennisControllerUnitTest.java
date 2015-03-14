package com.stratified.tennis.controller;

import com.stratified.tennis.controller.exceptions.GameIsCompletedException;
import com.stratified.tennis.controller.exceptions.GameNotFoundException;
import com.stratified.tennis.controller.exceptions.PlayerNameInvalidException;
import com.stratified.tennis.json.GameInitiate;
import com.stratified.tennis.json.GameInitiateResponse;
import com.stratified.tennis.json.ModelToJsonConverter;
import com.stratified.tennis.service.TennisGameService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.stratified.tennis.model.TestData.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TennisControllerUnitTest {

	@Mock
	private TennisGameService tennisGameService;
	@Mock
	private ModelToJsonConverter modelToJsonConverter;
	@InjectMocks
	private TennisController tennisController;

	@Test
	public void initiatePositive() {
		GameInitiate initiate = new GameInitiate("Kostas", "Nick");
		when(modelToJsonConverter.toGame(initiate)).thenReturn(TENNIS_GAME);
		when(tennisGameService.initiate(TENNIS_GAME)).thenReturn(TENNIS_GAME_5);
		GameInitiateResponse response = tennisController.initiate(initiate);
		assertEquals(5, response.getGameId());
	}

	@Test(expected = PlayerNameInvalidException.class)
	public void initiateNegativeDueToBlankPlayer1() {
		tennisController.initiate(new GameInitiate("", "Nick"));
	}

	@Test(expected = PlayerNameInvalidException.class)
	public void initiateNegativeDueToBlankPlayer2() {
		tennisController.initiate(new GameInitiate("Kostas", ""));
	}

	@Test(expected = PlayerNameInvalidException.class)
	public void initiateNegativeDueToSamePlayer() {
		tennisController.initiate(new GameInitiate("Kostas", "Kostas"));
	}

	@Test(expected = GameNotFoundException.class)
	public void wonGameNegativeDueToGameNotFound() {
		tennisController.wonGame(6, "x");
	}

	@Test(expected = PlayerNameInvalidException.class)
	public void wonGameNegativeDueToPlayerInvalid() {
		when(tennisGameService.getById(5)).thenReturn(TENNIS_GAME_5);
		tennisController.wonGame(5, "invalid player");
	}

	@Test(expected = GameIsCompletedException.class)
	public void wonGameNegativeDueToCompletedGame() {
		when(tennisGameService.getById(5)).thenReturn(TENNIS_GAME_COMPLETED);
		tennisController.wonGame(5, PLAYER1.getName());
	}

}