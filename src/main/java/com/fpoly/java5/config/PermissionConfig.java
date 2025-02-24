package com.fpoly.java5.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fpoly.java5.component.PermissionComponents;

@Configuration
public class PermissionConfig implements WebMvcConfigurer{
	
	@Autowired
	  PermissionComponents permissionComponents;

	  @Override
	  public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(permissionComponents)
	    .addPathPatterns("/admin/*", "/user/*") // Nhận những đường dẫn được đi qua interceptor
	    .excludePathPatterns("/css/**", "/js/**", "/img/**");
	  }
}
