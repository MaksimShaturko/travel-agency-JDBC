package by.epam.shaturko.dao;

import java.sql.Connection;

import by.epam.shaturko.bean.Message;
import by.epam.shaturko.bean.tour.Tour;
import by.epam.shaturko.bean.user.User;
import by.epam.shaturko.dao.exception.DAOException;

public interface SaveMessageDAO {
	
	void saveMessage(Message message, ThreadLocal<Connection> threadLocal) throws DAOException;

}
