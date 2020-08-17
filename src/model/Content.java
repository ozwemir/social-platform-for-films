package model;

public class Content {
	private int userId; // I may not need this. keeping it for now
	private final String userName;
	private final String header;
	private final String path; // this is gonna be unique for map
	private final String review;

	public Content(int userId, String userName, String header, String path, String review) {
		this.userId = userId;
		this.userName = userName;
		this.header = header;
		this.path = path;
		this.review = review;
	}

	public int getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public String getHeader() {
		return header;
	}

	public String getPath() {
		return path;
	}

	public String getReview() {
		return review;
	}

}
