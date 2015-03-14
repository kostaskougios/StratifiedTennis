package com.stratified.tennis.di;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * configures beans related to databases and enables spring transaction management
 *
 * @author kostas.kougios
 * Date: 13/03/15
 */
@Configuration
@EnableTransactionManagement
public class Database {
	@Bean
	public DataSource dataSource() throws Exception {
		Properties properties = new Properties();
		properties.load(getClass().getResourceAsStream("/database/jdbc.h2.properties"));
		return BasicDataSourceFactory.createDataSource(properties);
	}

	@Bean
	public JdbcTemplate jdbcTemplate() throws Exception {
		return new JdbcTemplate(dataSource());
	}

	@PostConstruct
	private void initializeDb() throws Exception {
		// create the database schema
		try {
			for (String ddl : getDDL("/database/drop-ddl.sql"))
				jdbcTemplate().update(ddl);
		} catch (BadSqlGrammarException e) {
			// ignore because it is caused by missing tables
		}
		for (String ddl : getDDL("/database/ddl.sql"))
			jdbcTemplate().update(ddl);
	}

	private String[] getDDL(String resource) throws IOException {
		return IOUtils.toString(getClass().getResourceAsStream(resource)).split(";");
	}
}
