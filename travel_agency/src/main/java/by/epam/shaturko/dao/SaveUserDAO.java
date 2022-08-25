package by.epam.shaturko.dao;

import java.sql.Connection;

import by.epam.shaturko.dao.exception.DAOException;
import by.epam.shaturko.entity.user.Details;
import by.epam.shaturko.entity.user.User;

public interface SaveUserDAO {
	
	boolean saveUser(User user, Details details, ThreadLocal<Connection> threadLocal) throws DAOException;
	
	boolean checkLogin(String login, ThreadLocal<Connection> threadLocal) throws DAOException;
	
	boolean checkEmail(String email, ThreadLocal<Connection> threadLocal) throws DAOException;

}
