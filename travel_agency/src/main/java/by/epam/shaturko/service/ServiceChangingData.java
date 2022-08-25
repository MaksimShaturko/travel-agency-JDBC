package by.epam.shaturko.service;

import java.sql.Connection;

import by.epam.shaturko.bean.tour.Tour;
import by.epam.shaturko.bean.tour.TourOrder;
import by.epam.shaturko.bean.user.User;
import by.epam.shaturko.dao.ChangingDataDAO;
import by.epam.shaturko.dao.DAOProvider;
import by.epam.shaturko.dao.TransactionalDAO;
import by.epam.shaturko.service.exception.ServiceException;

public interface ServiceChangingData {
	
	void changeAgencyDiscount(int newDiscount, int userId) throws ServiceException;
	
	void setTourAsVisited(TourOrder order) throws ServiceException;
	
	void cancelTour(Tour tour, User user) throws ServiceException;
}
