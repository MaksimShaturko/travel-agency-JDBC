package by.epam.shaturko.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.shaturko.controller.RequestParameter;
import by.epam.shaturko.controller.SessionAttribute;
import by.epam.shaturko.controller.command.Command;

public class ChangeLocale implements Command {
	private final static ChangeLocale INSTANCE = new ChangeLocale();
	
	private ChangeLocale() {}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		String requestUrl = (String) session.getAttribute(SessionAttribute.REQUEST_URL);
		session.setAttribute(SessionAttribute.LOCALE, request.getParameter(RequestParameter.LANGUAGE));
		response.sendRedirect(requestUrl);
	}

	public static ChangeLocale getInstance() {
		return INSTANCE;
	}

}
