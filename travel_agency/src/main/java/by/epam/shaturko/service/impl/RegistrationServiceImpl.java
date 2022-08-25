package by.epam.shaturko.service.impl;

import java.sql.Connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.shaturko.bean.user.Details;
import by.epam.shaturko.bean.user.User;
import by.epam.shaturko.dao.DAOProvider;
import by.epam.shaturko.dao.SaveUserDAO;
import by.epam.shaturko.dao.TransactionalDAO;
import by.epam.shaturko.dao.exception.DAOException;
import by.epam.shaturko.dao.exception.TransactionException;
import by.epam.shaturko.service.RegistrationService;
import by.epam.shaturko.service.exception.ServiceException;

/**
 * The RegistrationServiceImp class represents RegistrationService implementation.
 *
 * @author Maksim Shaturko
 * @version 1.0
 */
public class RegistrationServiceImpl implements RegistrationService {
	private final DAOProvider PROVIDER = DAOProvider.getInstance();
	private final TransactionalDAO TRANSACTION = PROVIDER.getTransactionalDAO();
	private final SaveUserDAO SAVE_USER_DAO = PROVIDER.getSaveUserDAO();

	@Override
	public boolean registerUser(User user, Details details) throws ServiceException {
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			if (SAVE_USER_DAO.saveUser(user, details, threadLocal)) {
				TRANSACTION.commitTransaction();
				return true;
			} else {
				TRANSACTION.rollbackTransaction();
				return false;
			}
		} catch (TransactionException e) {			
			throw new ServiceException(e);
		} catch( DAOException e) {
			try {
				TRANSACTION.rollbackTransaction();
			} catch (TransactionException e1) {
				throw new ServiceException(e1);
			}
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean checkLogin(String login) throws ServiceException {
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			boolean checkResult = SAVE_USER_DAO.checkLogin(login, threadLocal);
			TRANSACTION.commitTransaction();
			return checkResult;
		} catch (TransactionException | DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean checkEmail(String email) throws ServiceException {
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			boolean checkResult = SAVE_USER_DAO.checkEmail(email, threadLocal);
			TRANSACTION.commitTransaction();
			return checkResult;
		} catch (TransactionException | DAOException e) {
			throw new ServiceException(e);
		}		
	}
}
