package model;

public class Blog {
	private String blogId;
	private String blogPath;
	private String blogTitle;

	public Blog(String blogId, String blogTitle, String blogPath) {
		this.blogId = blogId;
		this.blogTitle = blogTitle;
		this.blogPath = blogPath;
	}

	public Blog(String blogTitle, String blogPath) {
		this.blogTitle = blogTitle;
		this.blogPath = blogPath;
	}

	public String getBlogId() {
		return blogId;
	}

	public String getBlogTitle() {
		return blogTitle;
	}

	public String getBlogPath() {
		return blogPath;
	}
}
