package by.epam.shaturko.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.shaturko.controller.Constant;
import by.epam.shaturko.controller.PagePath;
import by.epam.shaturko.controller.SessionAttribute;
import by.epam.shaturko.controller.command.Command;

public class GoToUsersPage implements Command {
	private final static GoToUsersPage INSTANCE = new GoToUsersPage();

	private GoToUsersPage() {
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		session.setAttribute(SessionAttribute.REQUEST_URL, Constant.URL_TO_USERS_PAGE);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.USERS);
		requestDispatcher.forward(request, response);
	}

	public static GoToUsersPage getInstance() {
		return INSTANCE;
	}
}
