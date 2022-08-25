package by.epam.shaturko.service.impl;

import java.sql.Connection;
import java.util.Optional;

import by.epam.shaturko.dao.AuthorizationDAO;
import by.epam.shaturko.dao.DAOProvider;
import by.epam.shaturko.dao.SaveUserDAO;
import by.epam.shaturko.dao.TransactionalDAO;
import by.epam.shaturko.dao.exception.DAOException;
import by.epam.shaturko.dao.exception.TransactionException;
import by.epam.shaturko.entity.user.User;
import by.epam.shaturko.service.ServiceAuthorization;
import by.epam.shaturko.service.exception.ServiceException;

public class ServiceAuthorizationImpl implements ServiceAuthorization {
	private final DAOProvider PROVIDER = DAOProvider.getInstance();
	private final TransactionalDAO TRANSACTION = PROVIDER.getTransactionalDAO();
	private final AuthorizationDAO AUTHORIZATION_DAO = PROVIDER.getAuthorizationDAO();

	@Override
	public Optional<User> logIn(String login, String password) throws ServiceException {
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			Optional<User> optionalUser = AUTHORIZATION_DAO.logIn(login, password, threadLocal);
			TRANSACTION.commitTransaction();
			return optionalUser;
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
