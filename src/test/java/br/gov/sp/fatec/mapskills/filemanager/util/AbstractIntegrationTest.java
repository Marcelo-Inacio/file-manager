/*
 * @(#)AbstractIntegrationTest.java 1.0 1 03/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.filemanager.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * A classe {@link AbstractIntegrationTest}
 *
 * @author Marcelo Inacio
 * @version 1.0 03/09/2017
 */
public abstract class AbstractIntegrationTest {
	
	protected String getFileContentAsString(final String filePath) throws IOException {
    	final InputStream inputStream = getClass()
    			.getResourceAsStream(String.format("/br/gov/sp/fatec/mapskills/filemanager/json/%s", filePath));

    	if (inputStream == null) {
    		throw new FileNotFoundException(
    				new StringBuilder()
    						.append("File ")
    						.append(filePath)
    						.append(" not found. A file named ")
    						.append(filePath)
    						.append(" must be present ")
    						.append("in the src/test/resources folder of the project whose class matches being tested.")
    						.toString());
    	}

    	final Scanner scanner = new Scanner(inputStream, "UTF-8");
    	scanner.useDelimiter("\\A");
    	final String contentAsString = scanner.next();
    	scanner.close();
    	return contentAsString;
    }

}