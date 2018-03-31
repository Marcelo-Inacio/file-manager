/*
 * @(#)FileManagerControllerTest.java 1.0 1 04/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.filemanager.restapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.File;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
 * A classe {@link FileManagerControllerTest} contem testes de integracao
 * para os servicos da controller <b>FileManagerController</b>.
 * 
 *  @see FileManagerController
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
	
	@Value("${file.path.template}")
	private String pathTemplate;
	
	@Before
	public void setup() {
		mockMvc = webAppContextSetup(context).build();
	}
	
	@Test
	public void saveFileTest() throws Exception {
		final String json = getFileContentAsString("request/save-scene-image.json");
		final ResultActions result = mockMvc.perform(post("/file")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(json)).andExpect(status().isOk());
		
		final String resourceLocation = result.andReturn().getResponse().getHeader("resource-location");

		final File file = getFileMock("scene-test00.jpg");
		assertTrue(file.exists());
		assertEquals(287005L, file.length());
		assertTrue(resourceLocation.contains("/file/scene-test00.jpg"));
		deleteFileFromResource("scene-test00.jpg");
	}
	
	@Test
	public void saveFileNotAllowedExceptionTest() throws Exception {
		final String json = getFileContentAsString("request/save-invalid-file.json");
		final String jsonExpected = getFileContentAsString("expectations/file-precondition-failed.json");
		
		final MvcResult result = mockMvc.perform(post("/file")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(json)).andExpect(status().isPreconditionFailed()).andReturn();
		
		final String jsonResult = result.getResponse().getContentAsString();
		JSONAssert.assertEquals(jsonExpected, jsonResult, false);
	}
	
	@Test
	public void deleteFileTest() throws Exception {
		createFileOnResource("text-test.txt");
		mockMvc.perform(delete("/file/text-test.txt")).andExpect(status().isOk());
		final File file = getFileMock("text-test.txt");
		assertFalse(file.exists());
	}
	
	@Test
	public void deleteFileExceptionTest() throws Exception {
		final String jsonExpected = getFileContentAsString("expectations/file-delete-not-found.json");
		final MvcResult result = mockMvc.perform(delete("/file/text-test.txt"))
				.andExpect(status().isNotFound()).andReturn();
		final String jsonResult = result.getResponse().getContentAsString();
		assertEquals("File: text-test.txt not found from server", result.getResolvedException().getMessage());
		JSONAssert.assertEquals(jsonExpected, jsonResult, false);
	}
	
	@Test
	public void updateFileTest() throws Exception {
		createFileOnResource("scene-20171220.jpg");
		final String json = getFileContentAsString("request/update-scene-image.json");
		final ResultActions result = mockMvc.perform(put("/file/scene-20171220.jpg")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(json)).andExpect(status().isOk());
		
		final String resourceLocation = result.andReturn().getResponse().getHeader("resource-location");
		assertTrue(resourceLocation.contains("scene-updated00.jpg"));
		final File fileDeleted = getFileMock("scene-20171220.jpg");
		assertTrue(!fileDeleted.exists());
		deleteFileFromResource("scene-updated00.jpg");
	}
	
	@Test
	public void updateFileThatNotExistsTest() throws Exception {
		final String json = getFileContentAsString("request/update-scene-image.json");
		final ResultActions result = mockMvc.perform(put("/file/scene-20171220.jpg")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(json)).andExpect(status().isOk());
		
		final String resourceLocation = result.andReturn().getResponse().getHeader("resource-location");
		assertTrue(resourceLocation.contains("scene-updated00.jpg"));
		deleteFileFromResource("scene-updated00.jpg");
	}
	
	@Test
	public void getFileTest() throws Exception {
		createFileOnResource("scene-20171220.jpg");
		final MvcResult result = mockMvc.perform(get("/file/scene-20171220.jpg"))
				.andExpect(status().isOk()).andReturn();
		
		assertNotNull(result.getResponse().getContentAsByteArray());
		assertEquals("image/jpeg", result.getResponse().getContentType());
		deleteFileFromResource("scene-20171220.jpg");
	}
	
	@Test
	public void getFileOctetStreamTest() throws Exception {
		createFileOnResource("csv-file.csv");
		final MvcResult result = mockMvc.perform(get("/file/csv-file.csv"))
				.andExpect(status().isOk()).andReturn();
		
		assertNotNull(result.getResponse().getContentAsByteArray());
		assertEquals("application/octet-stream", result.getResponse().getContentType());
		deleteFileFromResource("csv-file.csv");
	}
	
	@Test
	public void getFileNotFoundExceptionTest() throws Exception {
		final String jsonExpected = getFileContentAsString("expectations/file-not-found.json");
		final MvcResult result = mockMvc.perform(get("/file/scene-20171220.jpg")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound()).andReturn();
		
		final String jsonResult = result.getResponse().getContentAsString();
		JSONAssert.assertEquals(jsonExpected, jsonResult, false);
	}
		
	private void deleteFileFromResource(final String filename) throws Exception {
		final File file = getFileMock(filename);
		if(file.exists()) {
			file.delete();
		}
		assertFalse(file.exists());
	}
	
	private void createFileOnResource(final String filename) throws Exception {
		final File file = getFileMock(filename);
		file.createNewFile();
		assertTrue(file.exists());
	}
	
	private File getFileMock(final String fileName) {
		return Paths.get(String.format(pathTemplate, fileName)).toFile();
	}
}