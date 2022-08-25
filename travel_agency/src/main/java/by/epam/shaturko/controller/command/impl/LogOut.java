package by.epam.shaturko.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.shaturko.controller.Constant;
import by.epam.shaturko.controller.SessionAttribute;
import by.epam.shaturko.controller.command.Command;
import by.epam.shaturko.entity.user.UserRole;

public class LogOut implements Command {
	private final static LogOut INSTANCE = new LogOut();

	private LogOut() {
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		session.removeAttribute(SessionAttribute.AUTHORIZED);
		session.setAttribute(SessionAttribute.ROLE, UserRole.GUEST);
		session.removeAttribute(SessionAttribute.USER);
		session.invalidate();
		response.sendRedirect(Constant.URL_TO_MAIN_PAGE);
	}

	public static LogOut getInstance() {
		return INSTANCE;
	}

}
