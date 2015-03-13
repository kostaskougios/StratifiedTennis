package com.stratified.tennis;

import com.mangofactory.swagger.plugin.EnableSwagger;
import com.stratified.tennis.di.Database;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Starts the web server, useful during dev.
 *
 * @author kostas.kougios
 * Date: 13/03/15
 */
@SpringBootApplication
@EnableSwagger
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(new Object[]{Application.class, Database.class}, args);
	}
}
