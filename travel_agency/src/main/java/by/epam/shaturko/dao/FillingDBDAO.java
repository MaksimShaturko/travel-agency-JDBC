package by.epam.shaturko.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import by.epam.shaturko.dao.exception.DAOException;
import by.epam.shaturko.entity.tour.City;
import by.epam.shaturko.entity.tour.Country;
import by.epam.shaturko.entity.tour.Hotel;

public interface FillingDBDAO {
	
	void fillInCategories(List<String> categoryNames, 
			ThreadLocal<Connection> threadLocal) throws DAOException;
	
	void fillInCountries (List<Country> countryList, ThreadLocal<Connection> threadLocal) throws DAOException;
	
	void fillInCities (List<City> cityList, ThreadLocal<Connection> threadLocal) throws DAOException;	

	void fillInHotels(List<Hotel> hotelList, ThreadLocal<Connection> threadLocal) throws DAOException;
	
	void fillInCitiesHasCategories(List<Integer> cityCategoryList, ThreadLocal<Connection> threadLocal) throws DAOException;

}
