package com.stratified.tennis.controller;

import com.stratified.tennis.controller.exceptions.GameIsCompletedException;
import com.stratified.tennis.controller.exceptions.GameNotFoundException;
import com.stratified.tennis.controller.exceptions.PlayerNameInvalidException;
import com.stratified.tennis.json.ModelToJsonConverter;
import com.stratified.tennis.json.TennisGameInitiate;
import com.stratified.tennis.json.TennisGameInitiateResponse;
import com.stratified.tennis.json.TennisGameStatus;
import com.stratified.tennis.model.Player;
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
	public TennisGameInitiateResponse initiate(@RequestBody TennisGameInitiate initiate) {
		if (StringUtils.isBlank(initiate.getPlayer1())) throw new PlayerNameInvalidException("player1 can't be blank");
		if (StringUtils.isBlank(initiate.getPlayer2())) throw new PlayerNameInvalidException("player2 can't be blank");
		if (initiate.getPlayer1().equals(initiate.getPlayer2()))
			throw new PlayerNameInvalidException("player can't play against himself");

		TennisGame tennisGame = tennisGameService.initiate(modelToJsonConverter.toGame(initiate));
		return new TennisGameInitiateResponse(tennisGame.getId());
	}

	@RequestMapping(value = "/won/{gameId}/{playerName}", method = RequestMethod.PUT)
	public void wonGame(@PathVariable int gameId, @PathVariable String playerName) {
		TennisGame tennisGame = getGame(gameId);
		if (!tennisGame.isPlayer(playerName))
			throw new PlayerNameInvalidException("player not part of this game :" + playerName);
		if (tennisGame.getStatus() == TennisGame.Status.COMPLETED)
			throw new GameIsCompletedException("game " + gameId + " is completed");
		Player player = tennisGame.findPlayer(playerName);
		tennisGameService.win(tennisGame, player);
	}

	@RequestMapping(value = "/status/{gameId}", method = RequestMethod.GET)
	public TennisGameStatus tennisGameStatus(@PathVariable int gameId) {
		TennisGame tennisGame = getGame(gameId);
		TennisGameStatus status = modelToJsonConverter.toTennisGameStatus(tennisGame);
		return status;
	}

	private TennisGame getGame(int gameId) {
		TennisGame tennisGame = tennisGameService.getById(gameId);
		if (tennisGame == null) throw new GameNotFoundException("game with id " + gameId + " not found");
		return tennisGame;
	}
}
