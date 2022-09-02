package by.epam.shaturko.service.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import by.epam.shaturko.dao.DAOProvider;
import by.epam.shaturko.dao.GettingDataDAO;
import by.epam.shaturko.dao.SaveUserDAO;
import by.epam.shaturko.dao.TransactionalDAO;
import by.epam.shaturko.dao.exception.DAOException;
import by.epam.shaturko.dao.exception.TransactionException;
import by.epam.shaturko.entity.Message;
import by.epam.shaturko.entity.tour.City;
import by.epam.shaturko.entity.tour.Country;
import by.epam.shaturko.entity.tour.Hotel;
import by.epam.shaturko.entity.tour.Tour;
import by.epam.shaturko.entity.tour.TourOrder;
import by.epam.shaturko.entity.user.User;
import by.epam.shaturko.service.ServiceGettingData;
import by.epam.shaturko.service.exception.ServiceException;

public class ServiceGettingDataImpl implements ServiceGettingData {
	private final static DAOProvider PROVIDER = DAOProvider.getInstance();
	private final static TransactionalDAO TRANSACTION = PROVIDER.getTransactionalDAO();
	private final static GettingDataDAO GETTING_DATA_DAO = PROVIDER.getGettingDataDAO();

	@Override
	public List<String> getListOfCountriesNames() throws ServiceException {
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			List<String> listOfCountriesNames = GETTING_DATA_DAO.getListOfCountriesNames(threadLocal);
			TRANSACTION.commitTransaction();
			return listOfCountriesNames;
		} catch (TransactionException e) {
			throw new ServiceException(e);
		} catch (DAOException e) {
			try {
				TRANSACTION.rollbackTransaction();
			} catch (TransactionException e1) {
				throw new ServiceException(e1);
			}
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Tour> getListOfChosenTours(Map<String, Object> requestParameters, User user) throws ServiceException {
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			List<Tour> toursList = GETTING_DATA_DAO.getListOfChosenTours(threadLocal, requestParameters, user);
			TRANSACTION.commitTransaction();
			return toursList;
		} catch (TransactionException e) {
			throw new ServiceException(e);
		} catch (DAOException e) {
			try {
				TRANSACTION.rollbackTransaction();
			} catch (TransactionException e1) {
				throw new ServiceException(e1);
			}
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Hotel> getListOfHotels(String[] citiesNames, List<City> citiesList) throws ServiceException {
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			List<Hotel> listOfHotels = GETTING_DATA_DAO.getListOfHotels(threadLocal, citiesNames, citiesList);
			TRANSACTION.commitTransaction();
			return listOfHotels;
		} catch (TransactionException e) {
			throw new ServiceException(e);
		} catch (DAOException e) {
			try {
				TRANSACTION.rollbackTransaction();
			} catch (TransactionException e1) {
				throw new ServiceException(e1);
			}
			throw new ServiceException(e);
		}
	}

	@Override
	public List<City> getListOfCities(String[] countryNames) throws ServiceException {
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			List<City> cities = GETTING_DATA_DAO.getListOfCities(threadLocal, countryNames);
			TRANSACTION.commitTransaction();
			return cities;
		} catch (TransactionException e) {
			throw new ServiceException(e);
		} catch (DAOException e) {
			try {
				TRANSACTION.rollbackTransaction();
			} catch (TransactionException e1) {
				throw new ServiceException(e1);
			}
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Country> getListOfCountries(String[] countryNames) throws ServiceException {
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			List<Country> countries = GETTING_DATA_DAO.getListOfCountries(threadLocal, countryNames);
			TRANSACTION.commitTransaction();
			return countries;
		} catch (TransactionException e) {
			throw new ServiceException(e);
		} catch (DAOException e) {
			try {
				TRANSACTION.rollbackTransaction();
			} catch (TransactionException e1) {
				throw new ServiceException(e1);
			}
			throw new ServiceException(e);
		}
	}

	@Override
	public List<User> getAllUsers() throws ServiceException {
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			List<User> listOfUsers = GETTING_DATA_DAO.getAllUsers(threadLocal);
			TRANSACTION.commitTransaction();
			return listOfUsers;
		} catch (TransactionException e) {
			throw new ServiceException(e);
		} catch (DAOException e) {
			try {
				TRANSACTION.rollbackTransaction();
			} catch (TransactionException e1) {
				throw new ServiceException(e1);
			}
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Tour> getListOfOrderedTours(User user) throws ServiceException {
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			List<Tour> listOfTours = GETTING_DATA_DAO.getListOfOrderedTours(threadLocal, user);
			TRANSACTION.commitTransaction();
			return listOfTours;
		} catch (TransactionException e) {
			throw new ServiceException(e);
		} catch (DAOException e) {
			try {
				TRANSACTION.rollbackTransaction();
			} catch (TransactionException e1) {
				throw new ServiceException(e1);
			}
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Message> getMessages(User user, Tour tour) throws ServiceException {
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			List<Message> messages = GETTING_DATA_DAO.getMessages(threadLocal, user, tour);
			TRANSACTION.commitTransaction();
			return messages;
		} catch (TransactionException e) {
			throw new ServiceException(e);
		} catch (DAOException e) {
			try {
				TRANSACTION.rollbackTransaction();
			} catch (TransactionException e1) {
				throw new ServiceException(e1);
			}
			throw new ServiceException(e);
		}
	}

	@Override
	public Optional<TourOrder> getOrderByTourId(Tour tour) throws ServiceException {
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			Optional<TourOrder> order = GETTING_DATA_DAO.getOrderByTourId(threadLocal, tour);
			TRANSACTION.commitTransaction();
			return order;
		} catch (TransactionException e) {
			throw new ServiceException(e);
		} catch (DAOException e) {
			try {
				TRANSACTION.rollbackTransaction();
			} catch (TransactionException e1) {
				throw new ServiceException(e1);
			}
			throw new ServiceException(e);
		}
	}

	@Override
	public Optional<TourOrder> getOrderByTourIdUserId(Tour tour, User user) throws ServiceException {
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			Optional<TourOrder> order = GETTING_DATA_DAO.getOrderByTourIdUserId(threadLocal, tour, user);
			TRANSACTION.commitTransaction();
			return order;
		} catch (TransactionException e) {
			throw new ServiceException(e);
		} catch (DAOException e) {
			try {
				TRANSACTION.rollbackTransaction();
			} catch (TransactionException e1) {
				throw new ServiceException(e1);
			}
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Tour> getListOfVisitedTours(User user) throws ServiceException {
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			List<Tour> visitedTours = GETTING_DATA_DAO.getListOfVisitedTours(threadLocal, user);
			TRANSACTION.commitTransaction();
			return visitedTours;
		} catch (TransactionException e) {
			throw new ServiceException(e);
		} catch (DAOException e) {
			try {
				TRANSACTION.rollbackTransaction();
			} catch (TransactionException e1) {
				throw new ServiceException(e1);
			}
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Tour> getToursBySOId(User user, int SOId) throws ServiceException {
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			List<Tour> tours = GETTING_DATA_DAO.getToursBySOId(threadLocal, user, SOId);
			TRANSACTION.commitTransaction();
			return tours;
		} catch (TransactionException e) {
			throw new ServiceException(e);
		} catch (DAOException e) {
			try {
				TRANSACTION.rollbackTransaction();
			} catch (TransactionException e1) {
				throw new ServiceException(e1);
			}
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Tour> getAllSOTours(User user) throws ServiceException {
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			List<Tour> tours = GETTING_DATA_DAO.getAllSOTours(threadLocal, user);
			TRANSACTION.commitTransaction();
			return tours;
		} catch (TransactionException e) {
			throw new ServiceException(e);
		} catch (DAOException e) {
			try {
				TRANSACTION.rollbackTransaction();
			} catch (TransactionException e1) {
				throw new ServiceException(e1);
			}
			throw new ServiceException(e);
		}
	}

}
