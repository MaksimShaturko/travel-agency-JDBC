package by.epam.shaturko.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.shaturko.controller.PagePath;
import by.epam.shaturko.controller.RequestParameter;
import by.epam.shaturko.controller.SessionAttribute;
import by.epam.shaturko.controller.command.Command;

public class GoToMessagePage implements Command{
	private final static GoToMessagePage INSTANCE = new GoToMessagePage();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		String messageType = (String) session.getAttribute(SessionAttribute.MESSAGE_PAGE_INFO);
		request.setAttribute(messageType, true);
		RequestDispatcher dispatcher = request.getRequestDispatcher(PagePath.MESSAGE);
		dispatcher.forward(request, response);		
	}
	
	public static GoToMessagePage getInstance() {
		return INSTANCE;
	}
}
