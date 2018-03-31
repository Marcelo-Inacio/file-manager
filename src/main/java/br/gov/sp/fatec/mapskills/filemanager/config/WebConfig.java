/*
 * @(#)WebConfig.java 1.0 1 04/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.filemanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * A classe {@link WebConfig} contem as configuracoes
 * para acesso aos arquivos salvos na aplicacao.
 *
 * @author Marcelo Inacio
 * @version 1.0 04/09/2017
 */

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {
	
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/drive/**").addResourceLocations("/drive/");
	}
	
	@Override
    public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
      configurer.favorPathExtension(false)
              .favorParameter(true)
              .useJaf(true)
              .mediaType("image/jpeg", MediaType.IMAGE_JPEG)
              .mediaType("image/png", MediaType.IMAGE_PNG)
              .mediaType("application/pdf", MediaType.APPLICATION_PDF)
              .mediaType("json", MediaType.APPLICATION_JSON);
   }
}