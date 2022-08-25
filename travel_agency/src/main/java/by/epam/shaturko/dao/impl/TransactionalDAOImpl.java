package by.epam.shaturko.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import by.epam.shaturko.dao.TransactionalDAO;
import by.epam.shaturko.dao.connection_pool.ConnectionPool;
import by.epam.shaturko.dao.connection_pool.ConnectionPoolException;
import by.epam.shaturko.dao.exception.TransactionException;

public class TransactionalDAOImpl implements TransactionalDAO {
	private static final ThreadLocal<Connection> THREAD_LOCAL = new ThreadLocal<>();
	private static final TransactionalDAOImpl INSTANCE = new TransactionalDAOImpl();

	private TransactionalDAOImpl() {
	}

	@Override
	public ThreadLocal<Connection> startTransaction() throws TransactionException {
		ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
		Connection connection = null;
		try {
			connection = connectionPool.takeConnection();
		} catch (ConnectionPoolException e) {
			throw new TransactionException("Error when trying to take connection from Connection Pool",e);
		}
		THREAD_LOCAL.set(connection);

		return THREAD_LOCAL;
	}

	@Override
	public void commitTransaction() throws TransactionException {
		Connection connection = THREAD_LOCAL.get();
		try {
			connection.commit();
		} catch (SQLException e) {
			throw new TransactionException("Error while trying to commit connection", e);
		}
		ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
		try {
			connectionPool.releaseConnection(connection);
		} catch (SQLException e) {
			throw new TransactionException("Error while trying to commit connection", e);
		}
		THREAD_LOCAL.remove();
	}

	@Override
	public void rollbackTransaction() throws TransactionException {
		Connection connection = THREAD_LOCAL.get();
		try {
			connection.rollback();
		} catch (SQLException e) {
			throw new TransactionException("Error while trying to rollback connection", e);
		}
		ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
		try {
			connectionPool.releaseConnection(connection);
		} catch (SQLException e) {
			throw new TransactionException("Error while trying to commit connection", e);
		}
		THREAD_LOCAL.remove();
	}

	public static ThreadLocal getThreadLocal() {
		return THREAD_LOCAL;
	}

	public static TransactionalDAOImpl getTransaction() {
		return INSTANCE;
	}

}
