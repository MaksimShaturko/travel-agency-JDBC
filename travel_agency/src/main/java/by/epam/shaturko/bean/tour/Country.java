package by.epam.shaturko.bean.tour;

import java.io.Serializable;

/**
 * The Country class represents country entity.
 * 
 * @author Maksim Shaturko
 * @version 1.0
 */
public class Country implements Serializable {
	private static final long serialVersionUID = 3067334395120457410L;
	
	private int id;
	private String name;
	private String description;
	private String image;

	/**
	 * Instantiates a new Country
	 */
	public Country() {
		super();
	}

	/**
	 * Instantiates a new Country.
	 *
	 * @param id			a country id
	 * @param name  		a country name
	 * @param description 	a description of a country
	 * @param pathToImage	a path to an image of a country
	 */
	public Country(int id, String name, String description, String pathToImage) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.image = pathToImage;
	}

	public Country(String name, String description, String pathToImage) {
		super();
		this.name = name;
		this.description = description;
		this.image = pathToImage;
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
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((image == null) ? 0 : image.hashCode());
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
		Country other = (Country) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Country [name=" + name + ", description=" + description + ", pathToImage=" + image + ", visa="
				+ "]";
	}
}
