package by.epam.shaturko.service;

import java.util.List;

import by.epam.shaturko.entity.tour.SpecialOffer;
import by.epam.shaturko.entity.tour.Tour;
import by.epam.shaturko.service.exception.ServiceException;

public interface ServiceGettingSOTours {
	
	public List<Tour> getToursBySOId(int SOId) throws ServiceException;
	
	public List<Tour> getAllSOTours() throws ServiceException;

}
