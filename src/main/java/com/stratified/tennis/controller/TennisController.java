package com.stratified.tennis.controller;

import com.stratified.tennis.controller.exceptions.PlayerNameInvalidException;
import com.stratified.tennis.json.GameInitiate;
import com.stratified.tennis.json.GameInitiateResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kostas.kougios
 * Date: 13/03/15
 */
@RestController
public class TennisController {

	@RequestMapping(value = "/initiate", method = RequestMethod.POST)
	public GameInitiateResponse initiate(@RequestBody GameInitiate initiate) {
		if (StringUtils.isBlank(initiate.getPlayer1())) throw new PlayerNameInvalidException("player1 can't be blank");
		if (StringUtils.isBlank(initiate.getPlayer2())) throw new PlayerNameInvalidException("player2 can't be blank");
		if (initiate.getPlayer1().equals(initiate.getPlayer2()))
			throw new PlayerNameInvalidException("player can't play against himself");
		return new GameInitiateResponse("the-id");
	}
}
