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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

	/**
	 * posts json data. It doesn't make any assumptions about the response
	 *
	 * @param uri    the uri
	 * @param data    the data (from json schema classes) which will be serialized to json
	 * @return ResultActions which can be used for further assertions
	 */
	protected ResultActions postJson(String uri, Object data) {
		try {
			return mockMvc.perform(
					post(uri)
							.contentType(MediaType.APPLICATION_JSON)
							.content(JsonUtil.toString(data))
							.accept(MediaType.APPLICATION_JSON)
			);
		} catch (Exception e) {
			fail(e.getMessage());
			return null;
		}
	}

	/**
	 * posts json and expects a valid response with http OK (200) code.
	 *
	 * @param uri    the uri to post the data to
	 * @param data    the data which will be serialized to json
	 * @param expectedResponseType    the expected response class
	 * @param <T>	the expected response type
	 * @return T as is deserialized from the response
	 */
	protected <T> T postJson(String uri, Object data, Class<T> expectedResponseType) {
		try {
			MvcResult mvcResult = postJson(uri, data)
					.andExpect(status().isOk())
					.andExpect(content().contentType("application/json;charset=UTF-8"))
					.andReturn();
			String contentAsString = mvcResult.getResponse().getContentAsString();
			return JsonUtil.to(expectedResponseType, contentAsString);
		} catch (Exception e) {
			fail(e.getMessage());
			return null;
		}
	}

	protected ResultActions getJson(String uriTemplate, Object... urlVariables) {
		try {
			return mockMvc.perform(get(uriTemplate, urlVariables).accept(MediaType.APPLICATION_JSON));
		} catch (Exception e) {
			fail(e.getMessage());
			return null;
		}
	}

	protected <T> T getJson(String uriTemplate, Class<T> expectedResponseType, Object... urlVariables) {
		try {
			MvcResult mvcResult = getJson(uriTemplate, urlVariables).andExpect(status().isOk()).andReturn();
			String contentAsString = mvcResult.getResponse().getContentAsString();
			return JsonUtil.to(expectedResponseType, contentAsString);
		} catch (Exception e) {
			fail(e.getMessage());
			return null;
		}
	}
}
