package com.stratified.tennis.di;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * configures beans related to databases
 *
 * @author kostas.kougios
 * Date: 13/03/15
 */
@Configuration
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
		String ddl = IOUtils.toString(getClass().getResourceAsStream("/database/ddl.sql"));
		jdbcTemplate().update(ddl);
	}
}
