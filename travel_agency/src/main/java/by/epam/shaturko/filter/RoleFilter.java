package by.epam.shaturko.filter;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.shaturko.bean.user.User;
import by.epam.shaturko.bean.user.UserRole;
import by.epam.shaturko.controller.PagePath;
import by.epam.shaturko.controller.RequestParameter;
import by.epam.shaturko.controller.command.Command;
import by.epam.shaturko.controller.command.CommandName;
import by.epam.shaturko.controller.command.CommandProvider;
import by.epam.shaturko.controller.command.RoleAccess;
import by.epam.shaturko.controller.command.impl.ErrorCommand;

public class RoleFilter implements Filter{
	private static final Logger LOGGER = LogManager.getLogger();
	
	@Override
	public void init(FilterConfig config) {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession httpSession = httpRequest.getSession();
		String commandName = httpRequest.getParameter(RequestParameter.COMMAND_NAME);
		CommandProvider commandProvider = CommandProvider.getInstance();
		Optional<Command> commandOptional = commandProvider.getCommand(commandName);
		if (commandOptional.isPresent()) {
			Command command = commandOptional.get();
			if (command.getClass() != ErrorCommand.class) {
				User user = (User) httpSession.getAttribute(RequestParameter.USER);
				UserRole role = user != null ? user.getRole() : UserRole.GUEST;
				Set<CommandName> commands = null;
				switch (role) {
				case CLIENT:
					commands = RoleAccess.CLIENT.getAccessCommands();
					break;
				case ADMIN:
					commands = RoleAccess.ADMIN.getAccessCommands();
					break;
				case GUEST:
					commands = RoleAccess.GUEST.getAccessCommands();
					break;
				default:
					break;
				}
				if (commands != null && !commands.contains(CommandName.valueOf(commandName.toUpperCase()))) {
					LOGGER.log(Level.ERROR, "Role " + role + " has no access to command " + commandName);
					request.setAttribute(RequestParameter.ACCESS_ERROR_MESSAGE, true);
					request.getRequestDispatcher(PagePath.MESSAGE).forward(request, response);
					return;
				}
			}
		}
		chain.doFilter(request, response);
		
	}
	

	@Override
	public void destroy() {
	}

}
