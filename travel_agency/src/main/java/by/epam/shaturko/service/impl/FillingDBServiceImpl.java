package by.epam.shaturko.service.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import by.epam.shaturko.dao.DAOProvider;
import by.epam.shaturko.dao.FillingDBDAO;
import by.epam.shaturko.dao.TransactionalDAO;
import by.epam.shaturko.dao.exception.DAOException;
import by.epam.shaturko.dao.exception.TransactionException;
import by.epam.shaturko.entity.tour.Categories;
import by.epam.shaturko.entity.tour.City;
import by.epam.shaturko.entity.tour.Country;
import by.epam.shaturko.entity.tour.Hotel;
import by.epam.shaturko.service.FillingDBService;
import by.epam.shaturko.service.exception.ServiceException;

public class FillingDBServiceImpl implements FillingDBService {
	private final static DAOProvider PROVIDER = DAOProvider.getInstance();
	private final static TransactionalDAO TRANSACTION = PROVIDER.getTransactionalDAO();
	private final static FillingDBDAO FILLING_DB_DAO = PROVIDER.getFillingDBDAO();
	private final static String EMPTY_STRING = "";
	private final static String END = "end";
	private final static String NAME = "name";
	private final static String DESCRIPTION = "description";
	private final static String PATH = "path";
	private final static String UTF_8 = "UTF-8";

	@Override
	public void fillInCategories() throws ServiceException {
		List<String> categoryList = new ArrayList<>();
		Categories[] categories = Categories.values();
		for (Categories category : categories) {
			categoryList.add(category.name());
		}
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			FILLING_DB_DAO.fillInCategories(categoryList, threadLocal);
			TRANSACTION.commitTransaction();
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
	public void fillInCountries(String countriesSource) throws ServiceException {
		URL url = FillingDBServiceImpl.class.getClassLoader().getResource(countriesSource);
		List<Country> countryList = createCountryList(url);
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			FILLING_DB_DAO.fillInCountries(countryList, threadLocal);
			TRANSACTION.commitTransaction();
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
	public void fillInCities(String fileCitiesSource) throws ServiceException {
		URL url = FillingDBServiceImpl.class.getClassLoader().getResource(fileCitiesSource);
		List<City> cityList = createCityList(url);
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			FILLING_DB_DAO.fillInCities(cityList, threadLocal);
			TRANSACTION.commitTransaction();
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
	public void fillInHotels(String fileHotelsSource) throws ServiceException {
		URL url = FillingDBServiceImpl.class.getClassLoader().getResource(fileHotelsSource);
		List<Hotel> hotelList = createHotelList(url);
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			FILLING_DB_DAO.fillInHotels(hotelList, threadLocal);
			TRANSACTION.commitTransaction();
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
	public void fillInCitiesHasCategories(String fileCHCSource) throws ServiceException {
		URL url = FillingDBServiceImpl.class.getClassLoader().getResource(fileCHCSource);
		List<Integer> cityCategoryList = createCitiesHasCategoriesList(url);
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			FILLING_DB_DAO.fillInCitiesHasCategories(cityCategoryList, threadLocal);
			TRANSACTION.commitTransaction();
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
	
	private List<Country> createCountryList(URL url) throws ServiceException {
		List<Country> countryList = new ArrayList<>();
		String countryName = EMPTY_STRING;
		String countryDescription = EMPTY_STRING;
		String image = EMPTY_STRING;
		try (Scanner sc = new Scanner(
				new BufferedReader(new InputStreamReader(new FileInputStream(url.getPath()), UTF_8)))) {
			while (!sc.hasNext(END)) {
				if (sc.hasNext(NAME)) {
					sc.nextLine();
					countryName = sc.nextLine();
				}
				if (sc.hasNext(DESCRIPTION)) {
					sc.nextLine();
					while (!sc.hasNext(PATH)) {
						countryDescription = countryDescription + sc.nextLine();
					}
					sc.nextLine();
					image = sc.nextLine();
					Country country = new Country(countryName, countryDescription, image);
					countryList.add(country);
					countryDescription = EMPTY_STRING;
				}
				sc.nextLine();
			}
			return countryList;
		} catch (UnsupportedEncodingException e1) {
			throw new ServiceException(e1);
		} catch (IOException e2) {
			throw new ServiceException(e2);
		}
	}
	
	private List<City> createCityList(URL url) throws ServiceException {
		List<City> cityList = new ArrayList<>();
		String countryName = EMPTY_STRING;
		String cityName = EMPTY_STRING;
		String description = EMPTY_STRING;
		String image = EMPTY_STRING;
		try (Scanner sc = new Scanner(
				new BufferedReader(new InputStreamReader(new FileInputStream(url.getPath()), UTF_8)))) {
			while (!sc.hasNext(END)) {
				if (sc.hasNext(NAME)) {
					sc.nextLine();
					cityName = sc.nextLine();
				}
				if (sc.hasNext(DESCRIPTION)) {
					sc.nextLine();
					while (!sc.hasNext(PATH)) {
						description = description + sc.nextLine();
					}
					sc.nextLine();
					image = sc.nextLine();
					sc.nextLine();
					countryName = sc.nextLine();
					City city = new City(cityName, description, image, new Country(countryName, null, null));
					cityList.add(city);
					description = EMPTY_STRING;
				}
				sc.nextLine();
			}
			return cityList;
		} catch (UnsupportedEncodingException e1) {
			throw new ServiceException(e1);
		} catch (IOException e2) {
			throw new ServiceException(e2);
		}
	}
	
	private List<Hotel> createHotelList(URL url) throws ServiceException {

		List<Hotel> hotelList = new ArrayList<>();
		try (Scanner sc = new Scanner(
				new BufferedReader(new InputStreamReader(new FileInputStream(url.getPath()), "UTF-8")))) {
			String hotelName = EMPTY_STRING;
			String description = EMPTY_STRING;
			while (!sc.hasNext(END)) {
				if (sc.hasNext(NAME)) {
					sc.nextLine();
					hotelName = sc.nextLine();
				}
				sc.nextLine();
				while (!sc.hasNext(PATH)) {
					description = description + sc.nextLine();
				}
				sc.nextLine();
				String image = sc.nextLine();
				sc.nextLine();
				int stars = Integer.parseInt(sc.nextLine());
				sc.nextLine();
				String cityName = sc.nextLine();
				Hotel hotel = new Hotel(hotelName, description, image, stars, new City(cityName, null, null, null));
				hotelList.add(hotel);
				description = EMPTY_STRING;
				sc.nextLine();
			}
			return hotelList;
		} catch (UnsupportedEncodingException e1) {
			throw new ServiceException(e1);
		} catch (IOException e2) {
			throw new ServiceException(e2);
		}
	}

	private List<Integer> createCitiesHasCategoriesList(URL url) throws ServiceException {
		List<Integer> cityCategoryList = new ArrayList<>();
		try (Scanner sc = new Scanner(
				new BufferedReader(new InputStreamReader(new FileInputStream(url.getPath()), "UTF-8")))) {
			while (!sc.hasNext(END)) {
				int city = Integer.parseInt(sc.nextLine());
				int category = Integer.parseInt(sc.nextLine());
				cityCategoryList.add(city);
				cityCategoryList.add(category);
				sc.nextLine();
			}
			return cityCategoryList;
		} catch (UnsupportedEncodingException e1) {
			throw new ServiceException(e1);
		} catch (IOException e2) {
			throw new ServiceException(e2);
		}
	}
}
