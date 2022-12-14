package by.epam.shaturko.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import by.epam.shaturko.dao.exception.DAOException;
import by.epam.shaturko.entity.tour.Tour;
import by.epam.shaturko.entity.tour.TourOrder;
import by.epam.shaturko.entity.user.User;

public interface ChangingDataDAO {

	void changeAgencyDiscount(int newDiscount, int detailsId, ThreadLocal<Connection> threadLocal) throws DAOException;
	
	void setTourAsVisited(TourOrder order, ThreadLocal<Connection> threadLocal) throws DAOException;
	
	void cancelTour(ThreadLocal<Connection> threadLocal, Tour tour, User user) throws DAOException;
}
