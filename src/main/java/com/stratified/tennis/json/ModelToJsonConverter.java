package com.stratified.tennis.json;

import com.stratified.tennis.model.Game;
import com.stratified.tennis.model.Player;
import com.stratified.tennis.model.TennisGame;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * converts json schema classes to domain model classes.
 *
 * @author kostas.kougios
 * Date: 13/03/15
 */
@Component
public class ModelToJsonConverter {
	public TennisGame toGame(TennisGameInitiate tennisGameInitiate) {
		return TennisGame.newGame(Player.of(tennisGameInitiate.getPlayer1()), Player.of(tennisGameInitiate.getPlayer2()));
	}

	public TennisGameStatus toTennisGameStatus(TennisGame tennisGame) {
		List<GameStatus> gameStatuses = new ArrayList<>(2);
		gameStatuses.addAll(tennisGame.getGames().stream().map(this::toGameStatus).collect(Collectors.toList()));

		return new TennisGameStatus(
				tennisGame.getPlayer1().getName(),
				tennisGame.getPlayer2().getName(),
				tennisGame.getStatus().getDescription(),
				Seconds.secondsBetween(tennisGame.getStart(), DateTime.now()).getSeconds(),
				gameStatuses
		);
	}

	private GameStatus toGameStatus(Game game) {
		return new GameStatus(game.getPlayer1Score(), game.getPlayer2Score());
	}
}
