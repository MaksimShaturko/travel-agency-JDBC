package by.epam.shaturko.entity;

import java.time.LocalDateTime;

import by.epam.shaturko.entity.tour.Tour;
import by.epam.shaturko.entity.user.User;

public class Message {
	
	private int id;
	
	private User user;
	
	private Tour tour;
	
	private String text;
	
	private LocalDateTime dateTime;

	public Message() {
		super();
	}
	
	public Message(User user, Tour tour, String text, LocalDateTime dateTime) {
		super();
		this.user = user;
		this.tour = tour;
		this.text = text;
		this.dateTime = dateTime;
	}

	public Message(int id, User user, Tour tour, String text, LocalDateTime dateTime) {
		super();
		this.id = id;
		this.user = user;
		this.tour = tour;
		this.text = text;
		this.dateTime = dateTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Tour getTour() {
		return tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", user=" + user + ", tour=" + tour + ", text=" + text + ", dateTime=" + dateTime
				+ "]\n";
	}
	
	
	

}
