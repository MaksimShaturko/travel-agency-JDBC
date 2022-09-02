package by.epam.shaturko.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import by.epam.shaturko.dao.exception.DAOException;
import by.epam.shaturko.entity.Message;
import by.epam.shaturko.entity.tour.Categories;
import by.epam.shaturko.entity.tour.City;
import by.epam.shaturko.entity.tour.Country;
import by.epam.shaturko.entity.tour.Hotel;
import by.epam.shaturko.entity.tour.Tour;
import by.epam.shaturko.entity.tour.TourOrder;
import by.epam.shaturko.entity.user.User;

public interface GettingDataDAO {
	List<String> getListOfCountriesNames(ThreadLocal<Connection> threadLocal) throws DAOException;

	List<Tour> getListOfChosenTours(ThreadLocal<Connection> threadLocal, Map<String, Object> requestParameters,
			User user) throws DAOException;

	List<Hotel> getListOfHotels(ThreadLocal<Connection> threadLocal, String[] citiesNames, List<City> citiesList)
			throws DAOException;

	List<City> getListOfCities(ThreadLocal<Connection> threadLocal, String[] countriesNames) throws DAOException;

	List<Country> getListOfCountries(ThreadLocal<Connection> threadLocal, String[] countriesNames) throws DAOException;

	List<User> getAllUsers(ThreadLocal<Connection> threadLocal) throws DAOException;

	List<Tour> getListOfOrderedTours(ThreadLocal<Connection> threadLocal, User user) throws DAOException;

	List<Message> getMessages(ThreadLocal<Connection> threadLocal, User user, Tour tour) throws DAOException;

	Optional<TourOrder> getOrderByTourId(ThreadLocal<Connection> threadLocal, Tour tour) throws DAOException;

	Optional<TourOrder> getOrderByTourIdUserId(ThreadLocal<Connection> threadLocal, Tour tour, User user)
			throws DAOException;

	List<Tour> getListOfVisitedTours(ThreadLocal<Connection> threadLocal, User user) throws DAOException;

	List<Tour> getToursBySOId(ThreadLocal<Connection> threadLocal, User user, int SOId) throws DAOException;

	List<Tour> getAllSOTours(ThreadLocal<Connection> threadLocal, User user) throws DAOException;
}
