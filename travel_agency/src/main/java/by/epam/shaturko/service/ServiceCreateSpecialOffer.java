package by.epam.shaturko.service;

import java.util.List;

import by.epam.shaturko.entity.tour.SpecialOffer;
import by.epam.shaturko.entity.tour.Tour;
import by.epam.shaturko.service.exception.ServiceException;

public interface ServiceCreateSpecialOffer {

	void createSO(SpecialOffer so) throws ServiceException;

	void createSOByCountries(SpecialOffer so, String[] countriesNames, String soTitle) throws ServiceException;

	void createSOByCities(SpecialOffer so, String[] citiesNames, String soTitle) throws ServiceException;
	
	void createSOByHotels(SpecialOffer so, String[] hotelsNames, String soTitle) throws ServiceException;
	
	void createSOByTours(SpecialOffer so, List<Tour> tours, String soTitle) throws ServiceException;
	
	List<SpecialOffer> getAllSO() throws ServiceException;
}
