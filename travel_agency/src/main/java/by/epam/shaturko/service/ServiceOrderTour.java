package by.epam.shaturko.service;

import by.epam.shaturko.entity.tour.Tour;
import by.epam.shaturko.entity.user.User;
import by.epam.shaturko.service.exception.ServiceException;

public interface ServiceOrderTour {
	
	void orderTour(User user, Tour tour) throws ServiceException;

}
