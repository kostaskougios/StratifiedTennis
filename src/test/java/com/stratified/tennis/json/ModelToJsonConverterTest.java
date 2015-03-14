package com.stratified.tennis.json;

import com.stratified.tennis.model.Game;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ModelToJsonConverterTest {
	private ModelToJsonConverter converter = new ModelToJsonConverter();

	@Test
	public void testToGame() {
		Game game = converter.toGame(new GameInitiate("Kostas", "Nick"));
		assertEquals("Kostas", game.getPlayer1().getName());
		assertEquals("Nick", game.getPlayer2().getName());
	}
}