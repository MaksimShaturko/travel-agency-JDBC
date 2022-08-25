package by.epam.shaturko.bean.tour;

import java.io.Serializable;

/**
 * The SpecialOffer class represents special offer entity.
 * 
 * @author Maksim Shaturko
 * @version 1.0
 */
public class SpecialOffer implements Serializable {
	private static final long serialVersionUID = 8976390731100092097L;
	
	private int id;
	private String title;
	private String description;
	private int discount;

	/**
	 * Instantiates a new SpecialOffer
	 */
	public SpecialOffer() {
		super();
	}
	
	/**
	 * Instantiates a new SpecialOffer.
	 *
	 * @param id			a special offer id
	 * @param title  		a special offer name
	 * @param description 	a description of a special offer
	 * @param discount		an amount of a discount
	 */
	public SpecialOffer(int id, String title, String description, int discount) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.discount = discount;
	}

	public SpecialOffer(String title, String description, int discount) {
		super();
		this.title = title;
		this.description = description;
		this.discount = discount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + discount;
		result = prime * result + id;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		SpecialOffer other = (SpecialOffer) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (discount != other.discount)
			return false;
		if (id != other.id)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SpecialOffer [id=" + id + ", title=" + title + ", description=" + description + ", discount=" + discount
				+ "]";
	}

}
