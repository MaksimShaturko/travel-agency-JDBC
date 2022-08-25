package by.epam.shaturko.service;

import by.epam.shaturko.service.exception.ServiceException;

public interface FillingDBService {
	
	void fillInCategories() throws ServiceException;
	
	void fillInCountries(String fileCountriesSource) throws ServiceException;
	
	void fillInCities(String fileCitiesSource) throws ServiceException;
	
	void fillInHotels(String fileHotelsSource) throws ServiceException;
	
	void fillInCitiesHasCategories(String fileCHCSource) throws ServiceException;

}
