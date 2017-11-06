/*
 * @(#)FileManagerControllerTest.java 1.0 1 04/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.filemanager.restapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
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
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringContextTestConfiguration.class, WebConfig.class})
public class FileManagerControllerTest extends AbstractIntegrationTest {
	
	@Autowired
    private WebApplicationContext context;
	
	protected MockMvc mockMvc;
	
	@Before
	public void setup() {
		mockMvc = webAppContextSetup(context).build();
	}
	
	@Test
	public void saveFileTest() throws Exception {
		final String json = getFileContentAsString("save-scene-image.json");
		final ResultActions result =mockMvc.perform(post("/file")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(json)).andExpect(status().isOk());
		
		final String resourceLocation = result.andReturn().getResponse().getHeader("resource-location");

		final File file = getFileMock("/drive/scene00.jpg");
		assertTrue(file.exists());
		assertEquals(287005L, file.length());
		assertTrue(resourceLocation.contains("/drive/scene00.jpg"));
		deleteFileFromResource();
	}
	
	@Test
	public void deleteFileTest() throws Exception {
		createFileOnResource();
		mockMvc.perform(delete("/file/text-test.txt")).andExpect(status().isOk());
		final File file = getFileMock("/drive/text-test.txt");
		assertFalse(file.exists());
	}
	
	@Test
	public void deleteFileExceptionTest() throws Exception {
		final MvcResult result = mockMvc.perform(delete("/file/text-test.txt"))
				.andExpect(status().isNotFound()).andReturn();
		final String message = result.getResolvedException().getMessage();
		assertEquals("File with name: text-test.txt not found from server", message);
	}
		
	public void deleteFileFromResource() throws Exception {
		final File file = getFileMock("/drive/scene00.jpg");
		if(file.exists()) {
			file.delete();
		}
	}
	
	private void createFileOnResource() throws Exception {
		final File file = getFileMock("/drive/text-test.txt");
		file.createNewFile();
		assertTrue(file.exists());
	}
	
	private File getFileMock(final String fileName) {
		final String path = context.getServletContext().getRealPath(fileName);
		return new File(path);
	}
}