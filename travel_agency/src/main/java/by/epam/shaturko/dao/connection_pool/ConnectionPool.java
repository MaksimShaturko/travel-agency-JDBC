package by.epam.shaturko.dao.connection_pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The ConnectionPool class represents connection pool.
 *
 * @author Maksim Shaturko
 * @version 1.0
 */
public class ConnectionPool {
	private static final Logger logger = LogManager.getLogger(ConnectionPool.class);
	private static final ConnectionPool instance = new ConnectionPool();

	private BlockingQueue<Connection> connectionQueue;
	private BlockingQueue<Connection> givenAwayConnectionQueue;

	private final String driverName;
	private final String url;
	private final String user;
	private final String password;
	private int poolSize;
	private boolean autoCommit = false;
	private boolean init = false;

	{
		DBPropertiesManager dbPropertiesManager = DBPropertiesManager.getInstance();
		DBParameter dbParameter = new DBParameter();
		this.driverName = dbPropertiesManager.getValue(dbParameter.DB_DRIVER);
		this.url = dbPropertiesManager.getValue(dbParameter.DB_URL);
		this.user = dbPropertiesManager.getValue(dbParameter.DB_USER);
		this.password = dbPropertiesManager.getValue(dbParameter.DB_PASSWORD);

		try {
			this.poolSize = Integer.parseInt(dbPropertiesManager.getValue(dbParameter.DB_POOLSIZE));
		} catch (NumberFormatException e) {
			poolSize = 5;
		}
	}

	/**
	 * Instance connection pool.
	 */
	private ConnectionPool() {
	}

	public void initPoolData() throws ClassNotFoundException, SQLException {
		if (!init) {
			Class.forName(driverName);
			connectionQueue = new ArrayBlockingQueue<>(poolSize);
			givenAwayConnectionQueue = new ArrayBlockingQueue<>(poolSize);

			for (int i = 0; i < poolSize; i++) {
				Connection connection = DriverManager.getConnection(url, user, password);
				connection.setAutoCommit(autoCommit);
				connectionQueue.add(connection);
			}
			init = true;
		}
	}

	public void dispose() {
		clearConnectionQueue();
	}

	private void clearConnectionQueue() {
		try {
			closeConnectionsQueue(givenAwayConnectionQueue);
			closeConnectionsQueue(connectionQueue);
		} catch (SQLException e) {
			logger.log(Level.ERROR, "Error closing the connection.", e);
		}
	}

	private void closeConnectionsQueue(BlockingQueue<Connection> queue) throws SQLException {
		Connection connection;
		while ((connection = queue.poll()) != null) {
			if (!connection.getAutoCommit()) {
				connection.commit();
			}
			connection.close();
		}
	}

	/**
	 * Gets connection.
	 *
	 * @return the connection
	 */
	public Connection takeConnection() throws ConnectionPoolException {
		Connection connection = null;
		try {
			connection = connectionQueue.take();
			givenAwayConnectionQueue.add(connection);
		} catch (InterruptedException e) {
			throw new ConnectionPoolException(e);
		}
		return connection;
	}

	/**
	 * Release connection.
	 *
	 * @param connection the connection
	 */
	public void releaseConnection(Connection connection) throws SQLException {
		if (connection.isClosed()) {
			throw new SQLException("Attempting to close closed connection.");
		}
		if (connection.isReadOnly()) {
			connection.setReadOnly(false);
		}
		if (!connection.getAutoCommit()) {
			connection.setAutoCommit(autoCommit);
		}
		if (!givenAwayConnectionQueue.remove(connection)) {
			throw new SQLException("Error deleting connection from the given away connections pool");
		}
		if (!connectionQueue.offer(connection)) {
			throw new SQLException("Error allocating connection in the pool.");
		}
	}

	public static ConnectionPool getConnectionPool() {
		return instance;
	}

}
