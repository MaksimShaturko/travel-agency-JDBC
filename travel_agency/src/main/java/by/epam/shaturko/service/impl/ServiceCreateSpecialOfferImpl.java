package by.epam.shaturko.service.impl;

import java.sql.Connection;
import java.util.List;

import by.epam.shaturko.bean.tour.SpecialOffer;
import by.epam.shaturko.bean.tour.Tour;
import by.epam.shaturko.dao.CreateSpecialOfferDAO;
import by.epam.shaturko.dao.DAOProvider;
import by.epam.shaturko.dao.TransactionalDAO;
import by.epam.shaturko.dao.exception.DAOException;
import by.epam.shaturko.dao.exception.TransactionException;
import by.epam.shaturko.service.ServiceCreateSpecialOffer;
import by.epam.shaturko.service.exception.ServiceException;

public class ServiceCreateSpecialOfferImpl implements ServiceCreateSpecialOffer {
	private final DAOProvider PROVIDER = DAOProvider.getInstance();
	private final TransactionalDAO TRANSACTION = PROVIDER.getTransactionalDAO();
	private final CreateSpecialOfferDAO CREATE_SO = PROVIDER.getCreateSpecialOfferDAO();

	@Override
	public void createSO(SpecialOffer so) throws ServiceException {
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			CREATE_SO.createSO(so, threadLocal);
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
	public void createSOByCountries(SpecialOffer so, String[] countriesNames, String soTitle) throws ServiceException {
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			CREATE_SO.createSOByCountries(so, countriesNames, soTitle, threadLocal);
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
	public void createSOByCities(SpecialOffer so, String[] citiesNames, String soTitle) throws ServiceException {
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			CREATE_SO.createSOByCities(so, citiesNames, soTitle, threadLocal);
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
	public void createSOByHotels(SpecialOffer so, String[] hotelsNames, String soTitle) throws ServiceException {
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			CREATE_SO.createSOByHotels(so, hotelsNames, soTitle, threadLocal);
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
	public void createSOByTours(SpecialOffer so, List<Tour> tours, String soTitle) throws ServiceException {
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			CREATE_SO.createSOByTours(so, tours, soTitle, threadLocal);
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
	public List<SpecialOffer> getAllSO() throws ServiceException {
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			List<SpecialOffer> offersList = CREATE_SO.getAllSO(threadLocal);
			TRANSACTION.commitTransaction();
			return offersList;
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
