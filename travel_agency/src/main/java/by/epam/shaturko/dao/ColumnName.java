package by.epam.shaturko.dao;

public class ColumnName {
	public static final String DATABASE_NAME = "travel_agency";
	
	/**
	 * Constants for categories table
	 */
	public static final String CATEGORY_ID = "category_id";
	public static final String CATEGORY_NAME = "name";

	/**
	 * Constants for cities table
	 */
	public static final String CITY_ID = "city_id";
	public static final String CITY_NAME = "name";
	public static final String CITY_DESCRIPTION = "description";
	public static final String CITY_IMAGE_PATH = "image_path";
	public static final String CITY_COUNTRY_ID = "country_id";
	
	/**
	 * Constants for cities_has_categories table
	 */
	public static final String CITIES_HAS_CATEGORIES_CITY_ID = "city_id";
	public static final String CITIES_HAS_CATEGORIES_CATEGORY_ID = "category_id";
	
	/**
	 * Constants for countries table
	 */
	public static final String COUNTRIES_COUNTRY_ID = "country_id";
	public static final String COUNTRIES_NAME = "name";
	public static final String COUNTRIES_FLAG_IMAGE_PATH = "flag_image_path";
	public static final String COUNTRIES_DESCRIPTION = "description";
	
	/**
	 * Constants for hotels table
	 */
	public static final String HOTELS_HOTEL_ID = "hotel_id";
	public static final String HOTELS_NAME = "name";
	public static final String HOTELS_IMAGE_PATH = "image_path";
	public static final String HOTELS_DESCRIPTION = "description";
	public static final String HOTELS_STARS = "stars";
	public static final String HOTELS_CITY_ID = "city_id";
	
	/**
	 * Constants for messages table
	 */
	public static final String MESSAGES_MESSAGE_ID = "message_id";
	public static final String MESSAGES_TOUR_ID = "tour_id";
	public static final String MESSAGES_USER_ID = "user_id";
	public static final String MESSAGES_MESSAGE = "message";
	public static final String MESSAGES_MESSAGE_TIME = "message_time";
	
	/**
	 * Constants for special_offers table
	 */
	public static final String SPECIAL_OFFERS_SPECIAL_OFFER_ID = "special_offer_id";
	public static final String SPECIAL_OFFERS_TITLE = "title";
	public static final String SPECIAL_OFFERS_DESCRIPTION = "description";
	public static final String SPECIAL_OFFERS_DISCOUNT = "discount";
	
	/**
	 * Constants for tour_orders table
	 */
	public static final String TOUR_ORDERS_ORDER_ID = "order_id";
	public static final String TOUR_ORDERS_USER_ID = "user_id";
	public static final String TOUR_ORDERS_TOUR_ID = "tour_id";
	public static final String TOUR_ORDERS_CONFIRMATION_DATE = "confirmation_date";
	public static final String TOUR_ORDERS_ORDER_STATUS = "order_status";
	
	/**
	 * Constants for tours table
	 */
	public static final String TOURS_TOUR_ID = "tour_id";
	public static final String TOURS_PRICE_START = "price_start";
	public static final String TOURS_DATE_OF_DEPARTURE = "date_of_departure";
	public static final String TOURS_DATE_OF_RETURN = "date_of_return";
	public static final String TOURS_STATUS = "status";
	public static final String TOURS_TYPE_OF_FOOD = "type_of_food";
	public static final String TOURS_TYPE_OF_PLACEMENT = "type_of_placement";
	public static final String TOURS_TYPE_OF_ROOM = "type_of_room";
	public static final String TOURS_CATEGORY_ID = "category_id";
	public static final String TOURS_HOTEL_ID = "hotel_id";
	public static final String TOURS_SPECIAL_OFFERS_ID = "special_offers_id";
	
	/**
	 * Constants for users table
	 */
	public static final String USERS_USER_ID = "user_id";
	public static final String USERS_EMAIL = "email";
	public static final String USERS_LOGIN = "login";
	public static final String USERS_PASSWORD = "password";
	public static final String USERS_USER_STATUS = "user_status";
	public static final String USERS_USER_DETAILS = "user_details";
	public static final String USERS_USER_ROLE_ID = "user_role_id";
	
	/**
	 * Constants for users_details table
	 */
	public static final String USER_DETAILS_DETAILS_ID = "details_id";
	public static final String USER_DETAILS_NAME = "name";
	public static final String USER_DETAILS_SURNAME = "surname";
	public static final String USER_DETAILS_PHONE_NUMBER = "phone_number";	
	public static final String USER_DETAILS_NUMBER_OF_VISITED_TOURS = "number_of_visited_tours";
	public static final String USER_DETAILS_LOYALITY_DISCOUNT = "loyality_discount";
	public static final String USER_DETAILS_AGENCY_ADDITIONAL_DISCOUNT = "agency_additional_discount";
	public static final String USER_DETAILS_IMAGE_AVATAR_PATH = "image_avatar_path";
	
	/**
	 * Constants for users_roles table
	 */
	public static final String USER_ROLES_ROLE_ID = "role_id";
	public static final String USER_ROLES_ROLE_NAME = "role_name";
	
	/**
	 * Constants for visited_tours table
	 */
	public static final String VISITED_TOURS_USER_ID = "user_id";
	public static final String VISITED_TOURS_TOUR_ID = "tour_id";
	
	/**
	 * Constants for ratings table
	 */
	public static final String RATINGS_RATING_ID = "rating_id";
	public static final String RATINGS_USER_ID = "user_id";
	public static final String RATINGS_HOTEL_ID = "hotel_id";
	public static final String RATINGS_RATING = "rating";
	
	/**
	 * Constants for reviews table
	 */
	public static final String REVIEWS_REVIEW_ID = "review_id";
	public static final String REVIEWS_USER_ID = "user_id";
	public static final String REVIEWS_HOTEL_ID = "hotel_id";
	public static final String REVIEWS_REVIEW = "review";
	
}
