package by.epam.shaturko.bean.user;

import java.io.Serializable;

/**
 * The Details class represents user details entity.
 * 
 * @author Maksim Shaturko
 * @version 1.0
 */
public class Details implements Serializable{
	private static final long serialVersionUID = 4454433836841026342L;

	private int id;	
	private String name;	
	private String surname;	
	private String phoneNumber;	
	private int numberOfVisitedTours;	
	private int loyalityDiscount;	
	private int agencyAdditionalDiscount;	
	private String imageAvatar;

	/**
	 * Instantiates a details of a new User.
	 */
	public Details() {
		super();
	}
	
	/**
	 * Instantiates a details of a new User.
	 *
	 * @param id	     				a details id
	 * @param name       				a name
	 * @param surname    				a surname
	 * @param phoneNumber 				a phone number	
	 * @param numberOfVisitedTours     	a number of visited tours
	 * @param loyalityDiscount	     	a loyality discount
	 * @param agencyAdditionalDiscount 	an agency additional discount
	 * @param imageAvatar				a path to avatar image
	 */
	public Details(int id, String name, String surname, String phoneNumber, int numberOfVisitedTours,
			int loyalityDiscount, int agencyAdditionalDiscount, String imageAvatar) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.phoneNumber = phoneNumber;
		this.numberOfVisitedTours = numberOfVisitedTours;
		this.loyalityDiscount = loyalityDiscount;
		this.agencyAdditionalDiscount = agencyAdditionalDiscount;
		this.imageAvatar = imageAvatar;
	}

	public Details(String name, String surname, String phoneNumber, String imageAvatar) {
		super();
		this.name = name;
		this.surname = surname;
		this.phoneNumber = phoneNumber;
		this.imageAvatar = imageAvatar;
	}

	public Details(int id, String name, String surName, String phoneNumber, String imageAvatar) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surName;
		this.phoneNumber = phoneNumber;
		this.imageAvatar = imageAvatar;
	}	
	
	public Details(String name, String surname, String phoneNumber, int numberOfVisitedTours,
			int loyalityDiscount, int agencyAdditionalDiscount, String imageAvatar) {
		super();
		this.name = name;
		this.surname = surname;
		this.phoneNumber = phoneNumber;
		this.numberOfVisitedTours = numberOfVisitedTours;
		this.loyalityDiscount = loyalityDiscount;
		this.agencyAdditionalDiscount = agencyAdditionalDiscount;
		this.imageAvatar = imageAvatar;
	}

	public int getId() {
		return id;
	}	

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surName) {
		this.surname = surName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getNumberOfVisitedTours() {
		return numberOfVisitedTours;
	}

	public void setNumberOfVisitedTours(int numberOfVisitedTours) {
		this.numberOfVisitedTours = numberOfVisitedTours;
	}

	public int getLoyalityDiscount() {
		return loyalityDiscount;
	}

	public void setLoyalityDiscount(int loyalityDiscount) {
		this.loyalityDiscount = loyalityDiscount;
	}

	public int getAgencyAdditionalDiscount() {
		return agencyAdditionalDiscount;
	}

	public void setAgencyAdditionalDiscount(int agencyAdditionalDiscount) {
		this.agencyAdditionalDiscount = agencyAdditionalDiscount;
	}

	public String getImageAvatar() {
		return imageAvatar;
	}

	public void setImageAvatar(String imageAvatar) {
		this.imageAvatar = imageAvatar;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + agencyAdditionalDiscount;
		result = prime * result + id;
		result = prime * result + ((imageAvatar == null) ? 0 : imageAvatar.hashCode());
		result = prime * result + loyalityDiscount;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + numberOfVisitedTours;
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
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
		Details other = (Details) obj;
		if (agencyAdditionalDiscount != other.agencyAdditionalDiscount)
			return false;
		if (id != other.id)
			return false;
		if (imageAvatar == null) {
			if (other.imageAvatar != null)
				return false;
		} else if (!imageAvatar.equals(other.imageAvatar))
			return false;
		if (loyalityDiscount != other.loyalityDiscount)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (numberOfVisitedTours != other.numberOfVisitedTours)
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Details [id=" + id + ", name=" + name + ", surname=" + surname + ", phoneNumber=" + phoneNumber
				+ ", numberOfVisitedTours=" + numberOfVisitedTours + ", loyalityDiscount=" + loyalityDiscount
				+ ", agencyAdditionalDiscount=" + agencyAdditionalDiscount + ", imageAvatar=" + imageAvatar + "]";
	}
}
