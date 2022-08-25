package by.epam.shaturko.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import by.epam.shaturko.bean.Message;
import by.epam.shaturko.bean.tour.City;
import by.epam.shaturko.bean.tour.Country;
import by.epam.shaturko.bean.tour.Hotel;
import by.epam.shaturko.bean.tour.Tour;
import by.epam.shaturko.bean.tour.TourOrder;
import by.epam.shaturko.bean.user.User;
import by.epam.shaturko.service.exception.ServiceException;

public interface ServiceGettingData {

	List<String> getListOfCountriesNames() throws ServiceException;
	
	List<Tour> getListOfChosenTours(Map<String, Object> requestParrameters, User user) throws ServiceException;
	
	List<Hotel> getListOfHotels(String[] citiesNames, List<City> citiesList) throws ServiceException;
	
	List<City> getListOfCities(String[] countryNames) throws ServiceException;
	
	List<Country> getListOfCountries(String[] countryNames) throws ServiceException;
	
	List<User> getAllUsers() throws ServiceException;
	
	List<Tour> getListOfOrderedTours(User user) throws ServiceException;
	
	List<Message> getMessages(User user, Tour tour) throws ServiceException;
	
	Optional<TourOrder> getOrderByTourId(Tour tour) throws ServiceException;
	
	Optional<TourOrder> getOrderByTourIdUserId(Tour tour, User user) throws ServiceException;
	
	List<Tour> getListOfVisitedTours(User user) throws ServiceException;
	

}
