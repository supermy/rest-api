package com;

import com.supermy.base.aop.MyInterceptor1;
import com.supermy.base.aop.MyInterceptor2;
import com.supermy.base.db.Cmd2Config;
import com.supermy.base.db.CmdConfig;
import com.supermy.base.db.DataBaseConfig;
//import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
//import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import com.supermy.base.db.UploadConfig;
import com.supermy.base.domain.BaseObj;
import com.supermy.base.repository.BaseRepositoryFactoryBean;
import com.supermy.security.domain.Avatar;
import com.supermy.security.domain.User;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


//import javax.servlet.*;
//import java.util.Enumeration;

/**
 //在 SpringBootApplication 上使用@ServletComponentScan 注解后，Servlet、Filter、Listener 可以直接通过 @WebServlet、@WebFilter、@WebListener 注解自动注册，无需其他代码。
 *
 */
@ServletComponentScan
@Configuration
@EnableMongoRepositories
//@EnableJpaRepositories
//@Import({RepositoryRestMvcConfiguration.class,DataBaseConfig.class})
@PropertySource("classpath:application.properties")
@EnableAutoConfiguration
@EnableWebMvc
//@ComponentScan({"**.service","hello","**.web"})  //有问题
@EnableJpaRepositories(repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)   //添加自定义方法
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
			"classpath:/META-INF/resources/",
			"classpath:/resources/",
			"classpath:/static/",
			"classpath:/public/" ,
			"public",
			"resources",
			"static",
			"html"};


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

	@Bean
	public CommandLineRunner CmdConfig(){
		return new CmdConfig();
	}

	@Bean
	public CommandLineRunner Cmd2Config(){
		return new Cmd2Config();
	}

	/**
	 * id 生成到json 中
	 * @return
     */
	@Bean
	public RepositoryRestConfigurer repositoryRestConfigurer() {

		return new RepositoryRestConfigurerAdapter() {
			@Override
			public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
				//必须是子类才有效
				config.exposeIdsFor(BaseObj.class,User.class,Avatar.class);

//				@Override
//				public boolean isIdExposedFor(Class<?> domainType) {
//					return this.exposeIdsFor.contains(domainType);
//				}

			}

		};

	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 多个拦截器组成一个拦截器链
		// addPathPatterns 用于添加拦截规则
		// excludePathPatterns 用户排除拦截
		registry.addInterceptor(new MyInterceptor1()).addPathPatterns("/rest/**");
		registry.addInterceptor(new MyInterceptor2()).addPathPatterns("/form/rest/**");
		super.addInterceptors(registry);
	}



}
