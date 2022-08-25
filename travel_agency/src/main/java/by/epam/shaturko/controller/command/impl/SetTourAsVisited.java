package by.epam.shaturko.controller.command.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.shaturko.controller.Constant;
import by.epam.shaturko.controller.RequestParameter;
import by.epam.shaturko.controller.SessionAttribute;
import by.epam.shaturko.controller.command.Command;
import by.epam.shaturko.entity.tour.Tour;
import by.epam.shaturko.entity.tour.TourOrder;
import by.epam.shaturko.entity.user.User;
import by.epam.shaturko.service.ServiceChangingData;
import by.epam.shaturko.service.ServiceGettingData;
import by.epam.shaturko.service.ServiceProvider;
import by.epam.shaturko.service.exception.ServiceException;

public class SetTourAsVisited implements Command {
	private final static SetTourAsVisited INSTANCE = new SetTourAsVisited();
	private final static Logger logger = LogManager.getLogger();

	private SetTourAsVisited() {
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		List<Tour> listOfTours = (List<Tour>) session.getAttribute(SessionAttribute.TOURS_LIST);
		ServiceProvider provider = ServiceProvider.getInstance();
		String requestUrl = Constant.URL_TO_TOURS_PAGE;
		int tourId = Integer.parseInt(request.getParameter(RequestParameter.TOUR_ID));
		Tour tour = null;
		for (Tour tourVar : listOfTours) {
			if (tourVar.getId() == tourId) {
				tour = tourVar;
				break;
			}
		}
		ServiceGettingData getData = provider.getServiceGettingData();
		try {
			Optional<TourOrder> order = getData.getOrderByTourId(tour);
			ServiceChangingData changeData = provider.getServiceChangingData();
			changeData.setTourAsVisited(order.get());
			User user = (User) session.getAttribute(SessionAttribute.USER);
			ServiceGettingData serviceGettingData = provider.getServiceGettingData();
			List<Tour> toursList = serviceGettingData.getListOfOrderedTours(user);
			session.setAttribute(SessionAttribute.TOURS_LIST, toursList);
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Error while setting a tour as visited", e);
			session.setAttribute(SessionAttribute.ERROR_TYPE, Constant.ERROR_500);
			session.setAttribute(SessionAttribute.ERROR_MESSAGE, e);
			requestUrl = Constant.ERROR_COMMAND;
		}		
		response.sendRedirect(requestUrl);
	}

	public static SetTourAsVisited getInstance() {
		return INSTANCE;
	}
}
