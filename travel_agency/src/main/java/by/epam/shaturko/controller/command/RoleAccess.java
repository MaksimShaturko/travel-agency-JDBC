package by.epam.shaturko.controller.command;

import java.util.EnumSet;
import java.util.Set;
/**
 * The RoleAccess enum represents role access.
 *
 * @author Maksim Shaturko
 * @version 1.0
 */
public enum RoleAccess {
	
	/**
     * Guest role access.
     */
	GUEST(EnumSet.of(CommandName.AUTHORIZATION,
			CommandName.CHANGE_LOCALE,
			CommandName.GET_CHOSEN_TOURS,
			CommandName.GO_TO_MAIN_PAGE,
			CommandName.GO_TO_REGISTRATION_PAGE,
			CommandName.GO_TO_TOURS_PAGE,
			CommandName.REGISTRATION,
			CommandName.GO_TO_MESSAGE_PAGE)),
	
	/**
     * Client role access.
     */
	CLIENT(EnumSet.of(CommandName.CANCEL_TOUR,
			CommandName.CHANGE_LOCALE,
			CommandName.GET_CHOSEN_TOURS,
			CommandName.GET_ORDERED_TOURS,
			CommandName.GET_VISITED_TOURS,
			CommandName.GO_TO_MAIN_PAGE,
			CommandName.GO_TO_TOURS_PAGE,
			CommandName.LOG_OUT,
			CommandName.ORDER_TOUR,
			CommandName.VIEW_TOUR,
			CommandName.WRITE_MESSAGE,
			CommandName.GO_TO_MESSAGE_PAGE,
			CommandName.GET_ALL_SO,
			CommandName.GO_TO_ALL_SO_PAGE,
			CommandName.GET_SO_TOURS)),
	
	/**
     * Admin role access.
     */
	ADMIN(EnumSet.of(CommandName.CHANGE_AGENCY_DISCOUNT,
			CommandName.CHANGE_LOCALE,
			CommandName.CREATE_SPECIAL_OFFER,
			CommandName.FILLING_DB,
			CommandName.GENERATE_TOURS,
			CommandName.GET_ALL_USERS,
			CommandName.GET_CHOSEN_TOURS,
			CommandName.GET_ORDERED_TOURS,
			CommandName.GO_TO_CREATING_SPECIAL_OFFER_PAGE,
			CommandName.GO_TO_MAIN_PAGE,
			CommandName.GO_TO_TOURS_PAGE,
			CommandName.GO_TO_USERS_PAGE,
			CommandName.LOG_OUT,
			CommandName.SET_TOUR_AS_VISITED,
			CommandName.VIEW_TOUR,
			CommandName.WRITE_MESSAGE,
			CommandName.GO_TO_MESSAGE_PAGE,
			CommandName.GET_ALL_SO,
			CommandName.GO_TO_ALL_SO_PAGE,
			CommandName.GET_SO_TOURS));	
	
	private Set<CommandName> accessCommands;
	
	RoleAccess(Set<CommandName> accessCommands){
		this.accessCommands = accessCommands;
	}
	
	public Set<CommandName> getAccessCommands() {
        return this.accessCommands;
    }
}
