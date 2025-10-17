package com.mudson;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ToluProjectBankApplicationTests {

	@Autowired
	private ApplicationContext context;

	// Provide a mock JavaMailSender so beans depending on it can be created during tests
	@MockBean
	private JavaMailSender mailSender;

	@Test
	void contextLoads() {
		assertNotNull(context, "ApplicationContext should have been loaded");
	}

	@Test
	void applicationBeanPresent() {
		// Ensure the main application class is present as a bean
		assertNotNull(context.getBean(ToluProjectBankApplication.class));
	}

}
