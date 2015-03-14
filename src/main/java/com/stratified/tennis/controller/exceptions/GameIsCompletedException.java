package com.stratified.tennis.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author kostas.kougios
 * Date: 13/03/15
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class GameIsCompletedException extends RuntimeException {
	public GameIsCompletedException(String message) {
		super(message);
	}
}
