package by.epam.shaturko.controller.command.impl;

import java.io.IOException;

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

public class ErrorCommand implements Command {
	private final static ErrorCommand INSTANCE = new ErrorCommand();
	private final static String ERROR_404 = "404";

	private ErrorCommand() {
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		String errorType = (String) session.getAttribute(SessionAttribute.ERROR_TYPE);
		RequestDispatcher requestDispatcher = null;
		if (errorType.equals(Constant.ERROR_500)) {
			requestDispatcher = request.getRequestDispatcher(PagePath.ERROR_500);
		} else {
			requestDispatcher = request.getRequestDispatcher(PagePath.ERROR_404);
		}		
		requestDispatcher.forward(request, response);
	}

	public static ErrorCommand getInstance() {
		return INSTANCE;
	}
}
