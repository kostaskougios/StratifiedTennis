package com.stratified.tennis.controller;

import com.stratified.tennis.json.GameInitiate;
import org.junit.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author kostas.kougios
 * Date: 14/03/15
 */
public class TennisControllerIntegrationTest extends IntegrationTestsBase {

	@Test
	public void initiate() throws Exception {
		postJson("/initiate", new GameInitiate("p1", "p2"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.gameId").value(1));
	}
}
