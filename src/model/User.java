package model;

public class User {
	private String id;
	private String userName;
	private String email;
	private String password;

	/* Retrieve id from jsp page -- id is needed */
	public User(String id, String userName, String email, String password) {
		this.id = id;
		this.userName = userName;
		this.email = email;
		this.password = password;
	}

	/* Three fields are needed for sign in to the system (signinUser) method */
	public User(String userName, String email, String password) {
		this.userName = userName;
		this.email = email;
		this.password = password;
	}

	/* Two fields are needed for log in to the system* (loginUser) method */
	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

}
