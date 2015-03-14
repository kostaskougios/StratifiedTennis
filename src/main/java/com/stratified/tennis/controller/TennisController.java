package com.stratified.tennis.controller;

import com.stratified.tennis.controller.exceptions.GameIsCompletedException;
import com.stratified.tennis.controller.exceptions.GameNotFoundException;
import com.stratified.tennis.controller.exceptions.PlayerNameInvalidException;
import com.stratified.tennis.json.GameInitiate;
import com.stratified.tennis.json.GameInitiateResponse;
import com.stratified.tennis.json.ModelToJsonConverter;
import com.stratified.tennis.model.TennisGame;
import com.stratified.tennis.service.TennisGameService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author kostas.kougios
 * Date: 13/03/15
 */
@RestController
public class TennisController {
	@Autowired
	private TennisGameService tennisGameService;
	@Autowired
	private ModelToJsonConverter modelToJsonConverter;

	@RequestMapping(value = "/initiate", method = RequestMethod.POST)
	@ResponseBody
	public GameInitiateResponse initiate(@RequestBody GameInitiate initiate) {
		if (StringUtils.isBlank(initiate.getPlayer1())) throw new PlayerNameInvalidException("player1 can't be blank");
		if (StringUtils.isBlank(initiate.getPlayer2())) throw new PlayerNameInvalidException("player2 can't be blank");
		if (initiate.getPlayer1().equals(initiate.getPlayer2()))
			throw new PlayerNameInvalidException("player can't play against himself");

		TennisGame tennisGame = tennisGameService.initiate(modelToJsonConverter.toGame(initiate));
		return new GameInitiateResponse(tennisGame.getId());
	}

	@RequestMapping(value = "/won/{gameId}/{playerName}", method = RequestMethod.GET)
	public void wonGame(@PathVariable int gameId, @PathVariable String playerName) {
		TennisGame tennisGame = getGame(gameId);
		if (!tennisGame.isPlayer(playerName))
			throw new PlayerNameInvalidException("player not part of this game :" + playerName);
		if (tennisGame.getStatus() == TennisGame.Status.COMPLETED)
			throw new GameIsCompletedException("game " + gameId + " is completed");
	}

	private TennisGame getGame(int gameId) {
		TennisGame tennisGame = tennisGameService.getById(gameId);
		if (tennisGame == null) throw new GameNotFoundException("game with id " + gameId + " not found");
		return tennisGame;
	}
}
