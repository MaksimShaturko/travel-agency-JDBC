package by.epam.shaturko.controller.command;

import by.epam.shaturko.controller.command.impl.Authorization;
import by.epam.shaturko.controller.command.impl.CancelTour;
import by.epam.shaturko.controller.command.impl.ChangeAgencyDiscount;
import by.epam.shaturko.controller.command.impl.ChangeLocale;
import by.epam.shaturko.controller.command.impl.CreateSpecialOffer;
import by.epam.shaturko.controller.command.impl.ErrorCommand;
import by.epam.shaturko.controller.command.impl.FillingDB;
import by.epam.shaturko.controller.command.impl.GenerateTours;
import by.epam.shaturko.controller.command.impl.GetAllUsers;
import by.epam.shaturko.controller.command.impl.GetChosenTours;
import by.epam.shaturko.controller.command.impl.GetOrderedTours;
import by.epam.shaturko.controller.command.impl.GetVisitedTours;
import by.epam.shaturko.controller.command.impl.GoToCreatingSpecialOfferPage;
import by.epam.shaturko.controller.command.impl.GoToMainPage;
import by.epam.shaturko.controller.command.impl.GoToMessagePage;
import by.epam.shaturko.controller.command.impl.GoToRegistrationPage;
import by.epam.shaturko.controller.command.impl.GoToToursPage;
import by.epam.shaturko.controller.command.impl.GoToUsersPage;
import by.epam.shaturko.controller.command.impl.LogOut;
import by.epam.shaturko.controller.command.impl.OrderTour;
import by.epam.shaturko.controller.command.impl.Registration;
import by.epam.shaturko.controller.command.impl.SetTourAsVisited;
import by.epam.shaturko.controller.command.impl.ViewTour;
import by.epam.shaturko.controller.command.impl.WriteMessage;

/**
 * The CommandName enum represents command name.
 *
 * @author Maksim Shaturko
 * @version 1.0
 */
public enum CommandName {

	GO_TO_MAIN_PAGE(GoToMainPage.getInstance()), 
	GO_TO_REGISTRATION_PAGE(GoToRegistrationPage.getInstance()),
	REGISTRATION(Registration.getInstance()), 
	AUTHORIZATION(Authorization.getInstance()),
	CHANGE_LOCALE(ChangeLocale.getInstance()), 
	FILLING_DB(FillingDB.getInstance()),
	GENERATE_TOURS(GenerateTours.getInstance()), 
	GET_CHOSEN_TOURS(GetChosenTours.getInstance()),
	GO_TO_TOURS_PAGE(GoToToursPage.getInstance()),
	GO_TO_CREATING_SPECIAL_OFFER_PAGE(GoToCreatingSpecialOfferPage.getInstance()),
	CREATE_SPECIAL_OFFER(CreateSpecialOffer.getInstance()), 
	LOG_OUT(LogOut.getInstance()),
	GO_TO_USERS_PAGE(GoToUsersPage.getInstance()), 
	GET_ALL_USERS(GetAllUsers.getInstance()),
	CHANGE_AGENCY_DISCOUNT(ChangeAgencyDiscount.getInstance()), 
	ORDER_TOUR(OrderTour.getInstance()),
	GET_ORDERED_TOURS(GetOrderedTours.getInstance()), 
	VIEW_TOUR(ViewTour.getInstance()),
	WRITE_MESSAGE(WriteMessage.getInstance()), 
	CANCEL_TOUR(CancelTour.getInstance()),
	SET_TOUR_AS_VISITED(SetTourAsVisited.getInstance()), 
	GET_VISITED_TOURS(GetVisitedTours.getInstance()),
	GO_TO_MESSAGE_PAGE(GoToMessagePage.getInstance()),
	ERROR_COMMAND(ErrorCommand.getInstance());

	private final Command command;

	CommandName(Command command) {
		this.command = command;
	}

	public Command getCommand() {
		return command;
	}

}
