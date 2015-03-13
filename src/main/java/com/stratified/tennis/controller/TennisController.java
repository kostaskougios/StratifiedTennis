package com.stratified.tennis.controller;

import com.stratified.tennis.json.GameInitiate;
import com.stratified.tennis.json.GameInitiateResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("/tennis")
public class TennisController {

	@RequestMapping(value = "/initiate", method = RequestMethod.POST)
	public GameInitiateResponse inititate(@RequestBody GameInitiate initiate) {
		return new GameInitiateResponse("the-id");
	}
}
