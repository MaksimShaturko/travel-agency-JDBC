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

public class GoToAllSOPage implements Command {
	private final static GoToAllSOPage INSTANCE = new GoToAllSOPage();

	private GoToAllSOPage() {
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		session.setAttribute(SessionAttribute.REQUEST_URL, Constant.URL_TO_ALL_SO_PAGE);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ALL_SO);
			requestDispatcher.forward(request, response);
	}
	
	public static GoToAllSOPage getInstance() {
		return INSTANCE;
	}

}
