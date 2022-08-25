package by.epam.shaturko.service;

import by.epam.shaturko.entity.user.Details;
import by.epam.shaturko.entity.user.User;
import by.epam.shaturko.service.exception.ServiceException;

public interface RegistrationService{
	
	boolean registerUser(User user, Details details) throws ServiceException;
	
	boolean checkLogin(String login) throws ServiceException;
	
	boolean checkEmail(String email) throws ServiceException;

}
