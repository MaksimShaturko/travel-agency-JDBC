package by.epam.shaturko.service;

import java.util.Optional;

import by.epam.shaturko.bean.user.User;
import by.epam.shaturko.service.exception.ServiceException;

public interface ServiceAuthorization {
	public Optional<User> logIn(String login, String password) throws ServiceException;
}
