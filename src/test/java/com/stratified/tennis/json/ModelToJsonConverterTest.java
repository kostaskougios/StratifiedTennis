package com.stratified.tennis.json;

import com.stratified.tennis.model.TennisGame;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ModelToJsonConverterTest {
	private ModelToJsonConverter converter = new ModelToJsonConverter();

	@Test
	public void testToGame() {
		TennisGame tennisGame = converter.toGame(new GameInitiate("Kostas", "Nick"));
		assertEquals("Kostas", tennisGame.getPlayer1().getName());
		assertEquals("Nick", tennisGame.getPlayer2().getName());
	}
}