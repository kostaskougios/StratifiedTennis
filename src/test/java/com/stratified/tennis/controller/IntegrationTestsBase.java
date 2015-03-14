package com.stratified.tennis.controller;

import com.stratified.tennis.Application;
import com.stratified.tennis.util.JsonUtil;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * @author kostas.kougios
 * Date: 14/03/15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {Application.class})
public class IntegrationTestsBase {
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	protected ResultActions postJson(String uri, Object data) {
		try {
			return mockMvc.perform(
					post(uri)
							.contentType(MediaType.APPLICATION_JSON)
							.content(JsonUtil.toString(data))
							.accept(MediaType.APPLICATION_JSON)
			).andExpect(content().contentType("application/json;charset=UTF-8"));
		} catch (Exception e) {
			fail(e.getMessage());
			return null;
		}
	}
}
