package by.epam.shaturko.dao.connection_pool;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class DBPropertiesManager {
	Properties properties = new Properties();
	FileInputStream fis = null;
	private final static String PROPERTIES_FILE = "db.properties";

	{
		try {

			URL url = DBPropertiesManager.class.getClassLoader().getResource(PROPERTIES_FILE);
			fis = new FileInputStream(url.getPath());
			properties.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private DBPropertiesManager() {
	}

	private static DBPropertiesManager instance = new DBPropertiesManager();

	public static DBPropertiesManager getInstance() {
		return instance;
	}

	public String getValue(String key) {
		return properties.getProperty(key);
	}

}
