package com.hard.qroyal.auth.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class MyConfig {

	@Bean
	public CommonsMultipartResolver commonsMultipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setDefaultEncoding("UTF-8");
		return commonsMultipartResolver;
	}

	@Bean
	public Cloudinary cloudinary() {
		Cloudinary cloudinary = new Cloudinary(
				ObjectUtils.asMap("cloud_name", "dv29oltwi", "api_key", "956382576664388", "api_secret",
						"FQjd4FoX897cEZBIZv_xw97raQ8", "secure", true));
		return cloudinary;
	}
}
