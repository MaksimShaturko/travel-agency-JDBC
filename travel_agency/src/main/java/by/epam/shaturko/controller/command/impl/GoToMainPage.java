package by.epam.shaturko.controller.command.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.shaturko.controller.Constant;
import by.epam.shaturko.controller.PagePath;
import by.epam.shaturko.controller.SessionAttribute;
import by.epam.shaturko.controller.command.Command;
import by.epam.shaturko.entity.tour.Categories;
import by.epam.shaturko.entity.tour.Tour;
import by.epam.shaturko.entity.tour.TypeOfFood;
import by.epam.shaturko.entity.tour.TypeOfPlacement;
import by.epam.shaturko.entity.tour.TypeOfRoom;
import by.epam.shaturko.entity.user.User;
import by.epam.shaturko.service.ServiceGettingData;
import by.epam.shaturko.service.ServiceProvider;
import by.epam.shaturko.service.exception.ServiceException;

public class GoToMainPage implements Command {
	private final static GoToMainPage INSTANCE = new GoToMainPage();
	private final static Logger logger = LogManager.getLogger();
	private final static String RU = "ru";
	private final static String BUNDLE = "localization.local";
	private final static String KEY = "main_page.any";
	private final static String KEY_2 = "main_page.any_2";

	private GoToMainPage() {
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		List<Integer> listOfDurations = new ArrayList<>();
		for (int i = 7; i < 31; i++) {
			listOfDurations.add(i);
		}
		Map<String, String> numbers = new HashMap<>();
		for (int i = 1; i < 5; i++) {
			numbers.put(Integer.toString(i), TypeOfPlacement.values()[i - 1].toString());
		}
		HttpSession session = request.getSession();
		String localeStr;
		if(session.getAttribute(SessionAttribute.LOCALE) != null) {
			localeStr = (String) session.getAttribute(SessionAttribute.LOCALE);			
		} else {
			localeStr = RU;
		}
		Locale locale = new Locale(localeStr);		
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, locale);
		numbers.put(bundle.getString(KEY), null);
		ServiceProvider provider = ServiceProvider.getInstance();
		ServiceGettingData serviceGettingData = provider.getServiceGettingData();
		try {
			List<String> listOfCountriesNames = serviceGettingData.getListOfCountriesNames();
			listOfCountriesNames.add(bundle.getString(KEY_2));
			session.setAttribute(SessionAttribute.REQUEST_URL, Constant.URL_TO_MAIN_PAGE);
			session.setAttribute(SessionAttribute.DURATIONS, listOfDurations);
			session.setAttribute(SessionAttribute.FOOD, TypeOfFood.values());
			session.setAttribute(SessionAttribute.PLACEMENTS, TypeOfPlacement.values());
			session.setAttribute(SessionAttribute.ROOMS, TypeOfRoom.values());
			session.setAttribute(SessionAttribute.CATEGORIES, Categories.values());
			session.setAttribute(SessionAttribute.COUNTRIES, listOfCountriesNames);
			session.setAttribute(SessionAttribute.NUMBERS, numbers);

			User user = (User) session.getAttribute(SessionAttribute.USER);
			if (user != null) {
				List<Tour> allTours = serviceGettingData.getAllSOTours(user);
				List<Tour> tours = new ArrayList<>();
				Random random = new Random();
				for (int i = 0; i < 5; i++) {
					tours.add(allTours.get(random.nextInt(allTours.size())));
				}
				session.setAttribute(SessionAttribute.TOURS_LIST, tours);
			}
			session.removeAttribute(SessionAttribute.TOURS_REQUEST);
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Error while getting list of countries names", e);
			session.setAttribute(SessionAttribute.ERROR_TYPE, Constant.ERROR_500);
			session.setAttribute(SessionAttribute.ERROR_MESSAGE, e);
			response.sendRedirect(Constant.ERROR_COMMAND);
		}
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.MAIN);
		requestDispatcher.forward(request, response);
	}

	public static GoToMainPage getInstance() {
		return INSTANCE;
	}
}
