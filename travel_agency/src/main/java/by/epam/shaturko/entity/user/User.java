package by.epam.shaturko.entity.user;

import java.io.Serializable;

/**
 * The User class represents user entity.
 * 
 * @author Maksim Shaturko
 * @version 1.0
 */
public class User implements Serializable {
	private static final long serialVersionUID = -7865710402054559129L;

	private int id;
	private String email;
	private String login;
	private String password;
	private String userStatus;
	private Details details;
	private UserRole role;
	
	/**
	 * Instantiates a new User.
	 */
	public User() {		
	}
	
	/**
	 * Instantiates a new User.
	 *
	 * @param id			a user id
	 * @param login  		a login
	 * @param password 		a password
	 * @param userStatus	a user status
	 * @param details  		a details
	 * @param role	     	a user role
	 */
	public User(int id, String email, String login, String password, String userStatus, Details details, UserRole role) {
		super();
		this.id = id;
		this.email = email;
		this.login = login;
		this.password = password;
		this.userStatus = userStatus;
		this.details = details;
		this.role = role;
	}
	
	public User(int id) {
		this.id = id;
	}

	public User(String email, String login, String password) {
		this.email = email;
		this.login = login;
		this.password = password;
		this.role = UserRole.CLIENT;
	}

	public User(String login, String password, UserRole role) {
		this.login = login;
		this.password = password;
		this.role = role;
	}

	public User(String login, Details details) {
		super();
		this.login = login;
		this.details = details;
	}

	public User(int id, String login, Details details) {
		super();
		this.id = id;
		this.login = login;
		this.details = details;
	}
	
	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public Details getDetails() {
		return details;
	}

	public void setDetails(Details details) {
		this.details = details;
	}	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((details == null) ? 0 : details.hashCode());
		result = prime * result + id;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((userStatus == null) ? 0 : userStatus.hashCode());
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
		User other = (User) obj;
		if (details == null) {
			if (other.details != null)
				return false;
		} else if (!details.equals(other.details))
			return false;
		if (id != other.id)
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (role != other.role)
			return false;
		if (userStatus == null) {
			if (other.userStatus != null)
				return false;
		} else if (!userStatus.equals(other.userStatus))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id = " + id + ", login=" + login + ", password=" + password + ", role=" + role + ", details="
				+ details + "]";
	}

}
