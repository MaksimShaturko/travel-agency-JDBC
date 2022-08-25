package by.epam.shaturko.bean.tour;

import java.io.Serializable;

/**
 * The City class represents city entity.
 * 
 * @author Maksim Shaturko
 * @version 1.0
 */
public class City implements Serializable{
	private static final long serialVersionUID = -7082210654686632686L;
	
	private int id;	
	private String name;	
	private String description;	
	private String image;
	private Country country;

	/**
	 * Instantiates a new City
	 */
	public City() {
		super();
	}
	
	/**
	 * Instantiates a new City.
	 *
	 * @param id			a city id
	 * @param name  		a city name
	 * @param description 	a description of a city
	 * @param image			a path to an image of a city
	 * @param country  		a country of a city
	 */
	public City(int id, String name, String description, String image, Country country) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.image = image;
		this.country = country;
	}	
	
	public City(int id) {
		super();
		this.id = id;
	}

	public City(String name, String description, String image, Country country) {
		super();
		this.name = name;
		this.description = description;
		this.image = image;
		this.country = country;
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

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
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
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		City other = (City) obj;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
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
		return true;
	}
	
	@Override
	public String toString() {
		return "City [name=" + name + ", description=" + description + ", image=" + image + ", country=" + country
				+ "]";
	}	
	
}
