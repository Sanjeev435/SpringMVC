package com.spring.csvfactory;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.spring.model.Login;
import com.spring.model.User;
import com.spring.test.config.AppConfigForTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfigForTest.class, loader = AnnotationConfigContextLoader.class)
public class CsvFactoryImplTest {

	@Autowired
	private CsvFactory csvFactory;

	@Before
	public void setUp() throws IOException {
		String success = csvFactory.write(getDummyUserWithBlankValues());
		Assert.assertEquals("success", success);
	}

	@Test
	public void write() throws IOException {
		String success = csvFactory.write(getDummyUser());
		Assert.assertEquals("success", success);
		
		String userExists = csvFactory.write(getDummyUser());
		Assert.assertEquals("User Exists", userExists);
	}

	@Test
	public void validateUser() throws IOException {
		String success = csvFactory.write(getDummyUser());
		Assert.assertEquals("success", success);

		Login login = new Login();
		login.setUsername("Sanjeev");
		login.setPassword("Wrong Password");
		User user = csvFactory.validateUser(login);
		Assert.assertEquals(null, user);
		
		login.setPassword("Sanjeev@Kumar");
		user = csvFactory.validateUser(login);
		Assert.assertEquals(getDummyUser().getUsername(), user.getUsername());
		Assert.assertEquals(getDummyUser().getPassword(), user.getPassword());
		Assert.assertEquals(getDummyUser().getFirstname(), user.getFirstname());
		Assert.assertEquals(getDummyUser().getLastname(), user.getLastname());
		Assert.assertEquals(getDummyUser().getEmail(), user.getEmail());
		Assert.assertEquals(getDummyUser().getAddress(), user.getAddress());
		Assert.assertEquals(getDummyUser().getPhone(), user.getPhone());
	}
	
	@Test
	public void read() throws IOException{
		String success = csvFactory.write(getDummyUser());
		Assert.assertEquals("success", success);

		User user = csvFactory.read(new User("Sanjeev", "Wrong Password"));
		Assert.assertEquals(null, user);
		
		user = csvFactory.read(new User("Sanjeev", "Sanjeev@Kumar"));
		Assert.assertEquals(getDummyUser().getUsername(), user.getUsername());
		Assert.assertEquals(getDummyUser().getPassword(), user.getPassword());
		Assert.assertEquals(getDummyUser().getFirstname(), user.getFirstname());
		Assert.assertEquals(getDummyUser().getLastname(), user.getLastname());
		Assert.assertEquals(getDummyUser().getEmail(), user.getEmail());
		Assert.assertEquals(getDummyUser().getAddress(), user.getAddress());
		Assert.assertEquals(getDummyUser().getPhone(), user.getPhone());
	}

	private User getDummyUserWithBlankValues() {
		User user = new User();

		user.setUsername(StringUtils.EMPTY);
		user.setPassword(StringUtils.EMPTY);
		user.setFirstname(StringUtils.EMPTY);
		user.setLastname(StringUtils.EMPTY);
		user.setEmail(StringUtils.EMPTY);
		user.setAddress(StringUtils.EMPTY);
		user.setPhone(999999999);

		return user;
	}

	private User getDummyUser() {
		User user = new User();

		user.setUsername("Sanjeev");
		user.setPassword("Sanjeev@Kumar");
		user.setFirstname("Sanjeev");
		user.setLastname("Kumar");
		user.setEmail("abc@xyz.com");
		user.setAddress("New Delhi");
		user.setPhone(999999999);

		return user;
	}
}
