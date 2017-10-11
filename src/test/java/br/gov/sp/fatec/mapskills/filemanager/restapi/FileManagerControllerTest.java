/*
 * @(#)FileManagerControllerTest.java 1.0 1 04/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.filemanager.restapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import br.gov.sp.fatec.mapskills.filemanager.config.SpringContextTestConfiguration;
import br.gov.sp.fatec.mapskills.filemanager.config.WebConfig;
import br.gov.sp.fatec.mapskills.filemanager.util.AbstractIntegrationTest;

/**
 * A classe {@link FileManagerControllerTest}
 *
 * @author Marcelo Inacio
 * @version 1.0 04/09/2017
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringContextTestConfiguration.class, WebConfig.class})
public class FileManagerControllerTest extends AbstractIntegrationTest {
	
	@Autowired
    private WebApplicationContext context;
	
	protected MockMvc mockMvc;
	
	@Before
	public void setup() {
		mockMvc = webAppContextSetup(context).build();
	}
	
	@After
	public void down() throws Exception {
		final File file = getFileMock();
		if(file.exists()) {
			file.delete();
		}
	}
	
	@Test
	public void saveFile() throws Exception {
		final String json = getFileContentAsString("save-scene-image.json");
		mockMvc.perform(post("/file").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(json)).andExpect(status().isOk());
		
		final File file = getFileMock();
		assertTrue(file.exists());
		assertEquals(287005L, file.length());
	}
	
	private File getFileMock() {
		final String path = context.getServletContext().getRealPath("/drive/scene00.jpg");
		return new File(path);
	}

}