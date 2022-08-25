package by.epam.shaturko.service;

import by.epam.shaturko.bean.tour.Tour;
import by.epam.shaturko.bean.user.User;
import by.epam.shaturko.service.exception.ServiceException;

public interface ServiceOrderTour {
	
	void orderTour(User user, Tour tour) throws ServiceException;

}
