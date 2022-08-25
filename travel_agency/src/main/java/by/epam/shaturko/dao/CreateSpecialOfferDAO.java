package by.epam.shaturko.dao;

import java.sql.Connection;
import java.util.List;

import by.epam.shaturko.dao.exception.DAOException;
import by.epam.shaturko.entity.tour.City;
import by.epam.shaturko.entity.tour.Country;
import by.epam.shaturko.entity.tour.Hotel;
import by.epam.shaturko.entity.tour.SpecialOffer;
import by.epam.shaturko.entity.tour.Tour;

public interface CreateSpecialOfferDAO {
	
	void createSO(SpecialOffer so, ThreadLocal<Connection> threadLocal) throws DAOException;

	void createSOByCountries(SpecialOffer so, String[] countriesNames, String soTitle, ThreadLocal<Connection> threadLocal) throws DAOException;

	void createSOByCities(SpecialOffer so, String[] citiesNames, String soTitle, ThreadLocal<Connection> threadLocal) throws DAOException;
	
	void createSOByHotels(SpecialOffer so, String[] hotelsNames, String soTitle, ThreadLocal<Connection> threadLocal) throws DAOException;
	
	void createSOByTours(SpecialOffer so, List<Tour> tours, String soTitle, ThreadLocal<Connection> threadLocal) throws DAOException;
	
	List<SpecialOffer> getAllSO(ThreadLocal<Connection> threadLocal) throws DAOException;

}
