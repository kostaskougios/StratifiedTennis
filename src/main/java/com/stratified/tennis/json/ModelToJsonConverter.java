package com.stratified.tennis.json;

import com.stratified.tennis.model.Player;
import com.stratified.tennis.model.TennisGame;
import org.springframework.stereotype.Component;

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

}
