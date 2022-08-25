package by.epam.shaturko.bean.tour;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * The Tour class represents tour entity.
 * 
 * @author Maksim Shaturko
 * @version 1.0
 */
public class Tour implements Serializable {
	private static final long serialVersionUID = 406198384431203054L;

	private int id;
	private double priceStart;
	private double realPrice;
	private LocalDate dateOfDeparture;
	private LocalDate dateOfReturn;
	private TourStatus status;
	private TypeOfFood food;
	private TypeOfPlacement placement;
	private TypeOfRoom room;
	private Hotel hotel;
	private Categories category;
	private SpecialOffer specialOffer;

	/**
	 * Instantiates a new Tour
	 */
	public Tour() {
		super();
	}
	
	/**
	 * Instantiates a new Tour.
	 *
	 * @param id				a tour id
	 * @param priceStart  		a start price without discounts
	 * @param dateOfDeparture 	a date of departure
	 * @param dateOfReturn		a date of return
	 * @param status  			a tour status
	 * @param food	     		a type of food in a hotel
	 * @param placement			a type of placement
	 * @param room				a type of room
	 * @param hotel				a name of a hotel
	 * @param category			a tour category
	 * @param specialOffer		a special offer of a tour
	 */
	public Tour(int id, double priceStart, LocalDate dateOfDeparture, LocalDate dateOfReturn, TourStatus status,
			TypeOfFood food, TypeOfPlacement placement, TypeOfRoom room, Hotel hotel, Categories category,
			SpecialOffer specialOffer) {
		super();
		this.id = id;
		this.priceStart = priceStart;
		this.dateOfDeparture = dateOfDeparture;
		this.dateOfReturn = dateOfReturn;
		this.status = status;
		this.food = food;
		this.placement = placement;
		this.room = room;
		this.hotel = hotel;
		this.category = category;
		this.specialOffer = specialOffer;
	}

	public Tour(double priceStart, LocalDate dateOfDeparture, LocalDate dateOfReturn, TourStatus status,
			TypeOfFood food, TypeOfPlacement placement, TypeOfRoom room, Hotel hotel, Categories category) {
		super();
		this.priceStart = priceStart;
		this.dateOfDeparture = dateOfDeparture;
		this.dateOfReturn = dateOfReturn;
		this.status = status;
		this.food = food;
		this.placement = placement;
		this.room = room;
		this.hotel = hotel;
		this.category = category;
	}

	public Tour(int id, double priceStart, LocalDate dateOfDeparture, LocalDate dateOfReturn, TourStatus status,
			TypeOfFood food, TypeOfPlacement placement, TypeOfRoom room, Hotel hotel, Categories category) {
		super();
		this.id = id;
		this.priceStart = priceStart;
		this.dateOfDeparture = dateOfDeparture;
		this.dateOfReturn = dateOfReturn;
		this.status = status;
		this.food = food;
		this.placement = placement;
		this.room = room;
		this.hotel = hotel;
		this.category = category;
	}

	public double getPriceStart() {
		return priceStart;
	}

	public void setPriceStart(double priceStart) {
		this.priceStart = priceStart;
	}

	public LocalDate getDateOfDeparture() {
		return dateOfDeparture;
	}

	public void setDateOfDeparture(LocalDate dateOfDeparture) {
		this.dateOfDeparture = dateOfDeparture;
	}

	public LocalDate getDateOfReturn() {
		return dateOfReturn;
	}

	public void setDateOfReturn(LocalDate dateOfReturn) {
		this.dateOfReturn = dateOfReturn;
	}

	public TourStatus getStatus() {
		return status;
	}

	public void setStatus(TourStatus status) {
		this.status = status;
	}

	public TypeOfFood getFood() {
		return food;
	}

	public void setFood(TypeOfFood food) {
		this.food = food;
	}

	public TypeOfPlacement getPlacement() {
		return placement;
	}

	public void setPlacement(TypeOfPlacement placement) {
		this.placement = placement;
	}

	public TypeOfRoom getRoom() {
		return room;
	}

	public void setRoom(TypeOfRoom room) {
		this.room = room;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Categories getCategory() {
		return category;
	}

	public void setCategory(Categories category) {
		this.category = category;
	}

	public SpecialOffer getSpecialOffer() {
		return specialOffer;
	}

	public void setSpecialOffer(SpecialOffer specialOffer) {
		this.specialOffer = specialOffer;
	}

	public double getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(double realPrice) {
		this.realPrice = realPrice;
	}	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((dateOfDeparture == null) ? 0 : dateOfDeparture.hashCode());
		result = prime * result + ((dateOfReturn == null) ? 0 : dateOfReturn.hashCode());
		result = prime * result + ((food == null) ? 0 : food.hashCode());
		result = prime * result + ((hotel == null) ? 0 : hotel.hashCode());
		result = prime * result + id;
		result = prime * result + ((placement == null) ? 0 : placement.hashCode());
		long temp;
		temp = Double.doubleToLongBits(priceStart);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(realPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((room == null) ? 0 : room.hashCode());
		result = prime * result + ((specialOffer == null) ? 0 : specialOffer.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tour other = (Tour) obj;
		if (category != other.category)
			return false;
		if (dateOfDeparture == null) {
			if (other.dateOfDeparture != null)
				return false;
		} else if (!dateOfDeparture.equals(other.dateOfDeparture))
			return false;
		if (dateOfReturn == null) {
			if (other.dateOfReturn != null)
				return false;
		} else if (!dateOfReturn.equals(other.dateOfReturn))
			return false;
		if (food != other.food)
			return false;
		if (hotel == null) {
			if (other.hotel != null)
				return false;
		} else if (!hotel.equals(other.hotel))
			return false;
		if (id != other.id)
			return false;
		if (placement != other.placement)
			return false;
		if (Double.doubleToLongBits(priceStart) != Double.doubleToLongBits(other.priceStart))
			return false;
		if (Double.doubleToLongBits(realPrice) != Double.doubleToLongBits(other.realPrice))
			return false;
		if (room != other.room)
			return false;
		if (specialOffer == null) {
			if (other.specialOffer != null)
				return false;
		} else if (!specialOffer.equals(other.specialOffer))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tour [id=" + id + ", priceStart=" + priceStart + ", realPrice=" + realPrice + ", dateOfDeparture="
				+ dateOfDeparture + ", dateOfReturn=" + dateOfReturn + ", status=" + status + ", food=" + food
				+ ", placement=" + placement + ", room=" + room + ", hotel=" + hotel + ", category=" + category
				+ ", specialOffer=" + specialOffer + "]";
	}	

}
