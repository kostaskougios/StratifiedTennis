package com.stratified.tennis.json;

import com.stratified.tennis.model.Game;
import org.springframework.stereotype.Component;

/**
 * converts json schema classes to domain model classes.
 *
 * @author kostas.kougios
 * Date: 13/03/15
 */
@Component
public class ModelToJsonConverter {
	public Game toGame(GameInitiate gameInitiate) {
		return Game.newGame(gameInitiate.getPlayer1(), gameInitiate.getPlayer2());
	}

}
