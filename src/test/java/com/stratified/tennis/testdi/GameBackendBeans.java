package com.stratified.tennis.testdi;

import com.stratified.tennis.dao.TennisGameDao;
import com.stratified.tennis.service.TennisGameService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * used only to create beans for testing purposes
 *
 * @author kostas.kougios
 * Date: 13/03/15
 */
@Configuration
public class GameBackendBeans {
	@Bean
	public TennisGameDao gameDao() {
		return new TennisGameDao();
	}

	@Bean
	public TennisGameService gameService() {
		return new TennisGameService();
	}
}
