package com.supermy;

import com.supermy.db.DataBaseConfig;
//import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
//import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import com.supermy.db.UploadConfig;
import com.supermy.web.FileUploadController;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


//import javax.servlet.*;
//import java.util.Enumeration;

@Configuration
@EnableMongoRepositories
@EnableJpaRepositories
@Import({RepositoryRestMvcConfiguration.class,DataBaseConfig.class})
@PropertySource("classpath:application.properties")
@EnableAutoConfiguration
@EnableWebMvc
//@ComponentScan({"**.service","hello","**.web"})  //有问题
@ComponentScan
public class Application  extends WebMvcConfigurerAdapter {
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(Application.class);

//	@Bean
//	@ConditionalOnMissingBean(SingleSignOutHttpSessionListener.class)
//	public SingleSignOutHttpSessionListener requestContextListener() {
//		return new SingleSignOutHttpSessionListener();
//	}
//
//	/**
//	 * 配置单点登录的filter
//	 * @return
//     */
//	@Bean
//	public FilterRegistrationBean ssoFilterRegistration() {
//
//		FilterRegistrationBean registration = new FilterRegistrationBean();
//		registration.setFilter(ssoFilter());
//		registration.addInitParameter("serverName", "133.224.220.66:80");
//		registration.addInitParameter("casServerUrlPrefix", "http://133.224.220.66/cas");
//		registration.addInitParameter("casServerLoginUrl", "http://133.224.220.66/cas/login");
//		registration.addInitParameter("singleSignOut", "true");
//		registration.addInitParameter("skipUrls", "/out.jsp,.*\\.(css|js|jpg|jpeg|bmp|png|gif|ico)$");
//		//out.jsp,/index.jsp,/struts/.*,/resources/*,.*\.(css|js|jpg|jpeg|bmp|png|gif|ico|less|woff|tff|woff2)$
//		registration.addInitParameter("loginUserHandle", "com.xxxx.sso.client.impl.DemoAuthHandleImpl");
//		registration.addInitParameter("encoding", "UTF-8");
//
//		registration.addUrlPatterns("/*");
//
//		registration.setName("ssoFilter");
//		return registration;
//	}
//
//	@Bean(name = "ssoFilter")
//	public Filter ssoFilter() {
//		return new com.xxxx.sso.client.SSOFilter();
//	}

	/**
	 * 静态资源权限配置
	 */
	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
			"classpath:/META-INF/resources/", "classpath:/resources/",
			"classpath:/static/", "classpath:/public/" ,"public","resources","static","html"};


	/**
	 * 特殊资源权限配置 WebMvcConfigurerAdapter
	 *
	 * @param registry
     */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		if (!registry.hasMappingForPattern("/webjars/**")) {
			registry.addResourceHandler("/webjars/**").addResourceLocations(
					"classpath:/META-INF/resources/webjars/");
		}
		if (!registry.hasMappingForPattern("/**")) {
			registry.addResourceHandler("/**").addResourceLocations(
					CLASSPATH_RESOURCE_LOCATIONS);
		}
	}



	public static void main(String[] args) {

		//SpringApplication.run(Application.class, args);

		ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);

		RepositoryRestConfiguration restConfiguration = ctx.getBean(RepositoryRestConfiguration.class);

		restConfiguration.setReturnBodyOnCreate(true);
		restConfiguration.setReturnBodyOnUpdate(true);

	}


	@Value("${bi.pool}")
	private int bipool;

	@Bean(name = "bipool")
	public ExecutorService executorService() {
		return Executors.newFixedThreadPool(bipool);
	}


	@Bean
	public CommandLineRunner UploadConfig(){
		return new UploadConfig();
	}


}
