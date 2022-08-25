package by.epam.shaturko.dao;

import java.sql.Connection;

import by.epam.shaturko.bean.user.Details;
import by.epam.shaturko.bean.user.User;
import by.epam.shaturko.dao.exception.DAOException;

public interface SaveUserDAO {
	
	boolean saveUser(User user, Details details, ThreadLocal<Connection> threadLocal) throws DAOException;
	
	boolean checkLogin(String login, ThreadLocal<Connection> threadLocal) throws DAOException;
	
	boolean checkEmail(String email, ThreadLocal<Connection> threadLocal) throws DAOException;

}
