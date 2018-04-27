package com.spring.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.csvfactory.CsvFactory;
import com.spring.model.Login;
import com.spring.model.User;

@Service
public class UserServiceImpl implements UserService {
	private static Logger LOG = Logger.getLogger(UserServiceImpl.class);

	@Autowired
	private CsvFactory csvFactory;

	public void register(User user) {
		try {
			csvFactory.write(user);
		} catch (Exception ex) {
			ex.printStackTrace();
			LOG.error("Error while registering user !!", ex);
		}
	}

	public User validateUser(Login login) {
		try {
			return csvFactory.validateUser(login);
		} catch (Exception ex) {
			ex.printStackTrace();
			LOG.error("Error while validating user !!", ex);
		}

		return null;
	}

}
