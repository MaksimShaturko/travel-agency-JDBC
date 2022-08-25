package by.epam.shaturko.bean.user;

public enum UserRole {
	
	ADMIN(1), CLIENT(2), GUEST(3);
	
	private int id;
	
	UserRole(int id){
		this.id = id;
	}
	
	public int getRoleId() {		
		return this.id;
	}	
}
