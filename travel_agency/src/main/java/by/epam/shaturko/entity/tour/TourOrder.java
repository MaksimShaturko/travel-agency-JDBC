package by.epam.shaturko.entity.tour;

import java.io.Serializable;
import java.time.LocalDateTime;

import by.epam.shaturko.entity.user.User;

/**
 * The TourOrder class represents TourOrder entity.
 * 
 * @author Maksim Shaturko
 * @version 1.0
 */
public class TourOrder implements Serializable {
	private static final long serialVersionUID = 2832589378040645495L;
	
	private int orderId;
	private User user;
	private Tour tour;
	private LocalDateTime confirmationDate;
	private String orderStatus;

	/**
	 * Instantiates a new TourOrder
	 */
	public TourOrder() {
		super();
	}

	/**
	 * Instantiates a new TourOrder.
	 *
	 * @param orderId			an order id
	 * @param user				a user
	 * @param tour 				a tour
	 * @param confirmationDate	a confirmation date
	 * @param orderStatus  		an order status
	 */
	public TourOrder(int orderId, User user, Tour tour, LocalDateTime confirmationDate, String orderStatus) {
		super();
		this.orderId = orderId;
		this.user = user;
		this.tour = tour;
		this.confirmationDate = confirmationDate;
		this.orderStatus = orderStatus;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public User getUser() {
		return user;
	}

	public void setUserId(User user) {
		this.user = user;
	}

	public Tour getTour() {
		return tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}

	public LocalDateTime getConfirmationDate() {
		return confirmationDate;
	}

	public void setConfirmationDate(LocalDateTime confirmationDate) {
		this.confirmationDate = confirmationDate;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((confirmationDate == null) ? 0 : confirmationDate.hashCode());
		result = prime * result + orderId;
		result = prime * result + ((orderStatus == null) ? 0 : orderStatus.hashCode());
		result = prime * result + ((tour == null) ? 0 : tour.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		TourOrder other = (TourOrder) obj;
		if (confirmationDate == null) {
			if (other.confirmationDate != null)
				return false;
		} else if (!confirmationDate.equals(other.confirmationDate))
			return false;
		if (orderId != other.orderId)
			return false;
		if (orderStatus == null) {
			if (other.orderStatus != null)
				return false;
		} else if (!orderStatus.equals(other.orderStatus))
			return false;
		if (tour == null) {
			if (other.tour != null)
				return false;
		} else if (!tour.equals(other.tour))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TourOrder [orderId=" + orderId + ", user=" + user + ", tour=" + tour + ", confirmationDate="
				+ confirmationDate + ", orderStatus=" + orderStatus + "]";
	}

}
