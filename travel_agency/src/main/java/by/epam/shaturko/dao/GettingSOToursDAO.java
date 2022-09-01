package by.epam.shaturko.dao;

import java.sql.Connection;
import java.util.List;

import by.epam.shaturko.dao.exception.DAOException;
import by.epam.shaturko.entity.tour.SpecialOffer;
import by.epam.shaturko.entity.tour.Tour;

public interface GettingSOToursDAO {
	
	public List<Tour> getToursBySOId(ThreadLocal<Connection> threadLocal, SpecialOffer id) throws DAOException;
	
	public List<Tour> getAllSOTours(ThreadLocal<Connection> threadLocal);

}
