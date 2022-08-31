package by.epam.shaturko.controller.command.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.shaturko.controller.Constant;
import by.epam.shaturko.controller.PagePath;
import by.epam.shaturko.controller.RequestParameter;
import by.epam.shaturko.controller.SessionAttribute;
import by.epam.shaturko.controller.command.Command;
import by.epam.shaturko.entity.tour.Tour;

public class GoToToursPage implements Command {
	private final static GoToToursPage INSTANCE = new GoToToursPage();
	private final static String YES = "YES";

	private GoToToursPage() {
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		session.setAttribute(SessionAttribute.REQUEST_URL, Constant.URL_TO_TOURS_PAGE);
		session.removeAttribute(SessionAttribute.VIEW_TOUR);
		int toursOnOnePage = (int) session.getAttribute(SessionAttribute.ON_ONE_PAGE);
		List<Tour> tours = (List<Tour>) session.getAttribute(SessionAttribute.TOURS_LIST);
		int pages;
		System.out.println("size = " + tours.size());
		System.out.println("toursOnOnePage = " + toursOnOnePage);
		if (tours.size() % toursOnOnePage == 0) {
			pages = tours.size() / toursOnOnePage;
		} else {
			pages = tours.size() / toursOnOnePage + 1;
		}
		int pageNumber;
		if (request.getParameter(RequestParameter.PAGE_NUMBER) != null) {
			pageNumber = Integer.parseInt(request.getParameter(RequestParameter.PAGE_NUMBER));
		} else {
			pageNumber = 1;
		}
		List<Tour> toursOnPage = new ArrayList<>();
		for (int i = pageNumber * toursOnOnePage - toursOnOnePage; i < pageNumber * toursOnOnePage; i++) {
			if (i > tours.size() - 1) {
				break;
			}
			toursOnPage.add(tours.get(i));
		}
		System.out.println(toursOnPage);
		System.out.println("pageNumber = " + pageNumber);
		System.out.println("pages = " + pages);
		if (pageNumber == 1 && toursOnOnePage < tours.size()) {
			request.setAttribute(RequestParameter.NEXT, YES);
		}
		if (pageNumber == pages && pageNumber != 1) {
			request.setAttribute(RequestParameter.PREVIOUS, YES);
		}
		if (pageNumber < pages && pageNumber > 1) {
			request.setAttribute(RequestParameter.PREVIOUS, YES);
			request.setAttribute(RequestParameter.NEXT, YES);
		}
		request.setAttribute(RequestParameter.TOURS_ON_PAGE, toursOnPage);
		request.setAttribute(RequestParameter.PAGE_NUMBER, pageNumber);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.TOURS);
		requestDispatcher.forward(request, response);
	}

	public static GoToToursPage getInstance() {
		return INSTANCE;
	}
}
