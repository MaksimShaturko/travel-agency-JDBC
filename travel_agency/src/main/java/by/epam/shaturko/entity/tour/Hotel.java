package by.epam.shaturko.entity.tour;

import java.io.Serializable;

/**
 * The Hotel class represents hotel entity.
 * 
 * @author Maksim Shaturko
 * @version 1.0
 */
public class Hotel implements Serializable {
	private static final long serialVersionUID = -27106013833005361L;
	
	private int id;
	private String name;
	private String description;
	private String image;
	private int stars;
	private City city;

	/**
	 * Instantiates a new Hotel
	 */
	public Hotel() {
		super();
	}

	/**
	 * Instantiates a new Hotel.
	 *
	 * @param id			a hotel id
	 * @param name  		a hotel name
	 * @param description 	a description of a hotel
	 * @param image			a path to an image of a hotel
	 * @param stars  		a number of hotel stars
	 * @param city			a city where a hotel is placed
	 */
	public Hotel(int id, String name, String description, String image, int stars, City city) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.image = image;
		this.stars = stars;
		this.city = city;
	}

	public Hotel(String name, String description, String image, int stars, City city) {
		super();
		this.name = name;
		this.description = description;
		this.image = image;
		this.stars = stars;
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + stars;
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
		Hotel other = (Hotel) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (stars != other.stars)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Hotel [id=" + id + ", name=" + name + ", description=" + description + ", image=" + image + ", stars="
				+ stars + ", city=" + city + "]";
	}


}
