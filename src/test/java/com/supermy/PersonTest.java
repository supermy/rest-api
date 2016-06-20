package com.supermy;

//import org.junit.Test;
//import org.junit.runner.RunWith;
import com.supermy.db.DataBaseConfig;
import com.supermy.domain.Person;
import com.supermy.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.transaction.TransactionConfiguration;
//import org.springframework.test.context.web.WebAppConfiguration;


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = Application.class)
@Configuration
@EnableJpaRepositories
@Import({RepositoryRestMvcConfiguration.class,DataBaseConfig.class})
@PropertySource("classpath:application.properties")
@EnableAutoConfiguration
//@TransactionConfiguration
//@WebAppConfiguration
//@ActiveProfiles("test")
public class PersonTest {
	
	@Autowired
	private PersonRepository personRepository;
	
	//@Test
	public void test() {

        Person sp = new Person();
        sp.setFirstName("世界");
        sp.setLastName("你好");

        Person newSmartphone = personRepository.save(sp);
		
		//assertEquals(sp.getFirstName(), newSmartphone.getFirstName());
		//assertEquals(sp.getLastName(), newSmartphone.getLastName());
	}
	
	

}
