package by.epam.shaturko.controller.listener;

import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import by.epam.shaturko.dao.connection_pool.ConnectionPool;

public class ProjectContextListener implements ServletContextListener{
	
	public void contextInitialized(ServletContextEvent sce) {		
		ConnectionPool cp = ConnectionPool.getConnectionPool();		
		try {
			cp.initPoolData();
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
		} catch (SQLException e) {			
			e.printStackTrace();
		}		
		
	}	
}
