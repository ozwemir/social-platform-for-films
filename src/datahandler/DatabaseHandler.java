package datahandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import model.Blog;
import model.Content;
import model.User;

public class DatabaseHandler {

	final private DataSource dataSource;

	public DatabaseHandler(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void addUser(User user) throws Exception { // return nothing... do some validations
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = dataSource.getConnection();

			final String query = "INSERT INTO user(user_name, user_email, user_password) values(?,?,?)";

			statement = connection.prepareStatement(query);
			statement.setString(1, user.getUserName());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getPassword());

			statement.execute();

		} finally {
			close(connection, statement, null);

		}
	}

	public User checkLogin(String email, String password) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {

			connection = dataSource.getConnection();
			final String query = "SELECT * FROM user WHERE user_email = ? and user_password = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, email);
			statement.setString(2, password);

			resultSet = statement.executeQuery();

			User user = null; // if the user is not found then return null
			if (resultSet.next()) {
				String userName = resultSet.getString("user_name"); // need this data for personalisation
				String userId = resultSet.getString("user_id");
				user = new User(userId, userName, email, password);

			}
			return user;
		} finally {
			close(connection, statement, resultSet);
		}
	}

	public boolean addReview(Blog blog, String userId) throws SQLException { // return something for validation
		Connection connection = null;
		PreparedStatement statement = null;
		boolean flag = false; // needed some flag to delete created text file
		try {
			connection = dataSource.getConnection();
			final String query = "INSERT INTO blog(blog_title, blog_path, user_user_id) values(?,?,?)";
			statement = connection.prepareStatement(query);
			statement.setString(1, blog.getBlogTitle());
			statement.setString(2, blog.getBlogPath());
			statement.setString(3, userId);

			if (statement.execute()) {
				flag = true;
			}

		} catch (Exception e) {
			System.out.println("catch blok"); // app did not break yay
		} finally {
			close(connection, statement, null);
		}
		return flag;
	}

	public Map<String, Content> getContent() throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		Map<String, Content> contents = new LinkedHashMap<>();

		try {
			connection = dataSource.getConnection();
			final String query = "select user.user_name, blog.blog_title,blog.blog_path,blog.user_user_id from user join blog on user.user_id=blog.user_user_id";
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();

			TextHandler th = new TextHandler(); // this object is for get the user's review

			while (resultSet.next()) {
				int userId = resultSet.getInt("user_user_id");
				String userName = resultSet.getString("user_name");
				String blogTitle = resultSet.getString("blog_title");
				String blogPath = resultSet.getString("blog_path"); // path value is going to be key for the content Map

				// Read the review
				String review = th.getTxtFile(blogPath);
				if (review == null) {
					review = "There is no content";
				}
				contents.put(blogPath, new Content(userId, userName, blogTitle, blogPath, review));
			}
			return contents;
		} finally {
			close(connection, statement, null);
		}
	}

	/* OverLoading thing */
//	public Map<String, Content> getContent(int userId) throws SQLException {
//		Connection connection = null;
//		PreparedStatement statement = null;
//		ResultSet resultSet = null;
//
//		Map<String, Content> contents = new LinkedHashMap<>();
//
//		try {
//			connection = dataSource.getConnection();
//			final String query = "select user.user_name, blog.blog_title,blog.blog_path,blog.user_user_id"
//					+ " from user join blog on user.user_id=blog.user_user_id where user_id=?";
//			statement = connection.prepareStatement(query);
//			statement.setInt(1, userId);
//			resultSet = statement.executeQuery();
//
//			TextHandler th = new TextHandler(); // this object is for get the user's review
//
//			while (resultSet.next()) {
//				// int userId = resultSet.getInt("user_user_id");
//				String userName = resultSet.getString("user_name");
//				String blogTitle = resultSet.getString("blog_title");
//				String blogPath = resultSet.getString("blog_path"); // path value is going to be key for the content Map
//
//				// Read the review
//				String review = th.getTxtFile(blogPath);
//				if (review == null) {
//					review = "There is no content";
//				}
//				contents.put(blogPath, new Content(userName, blogTitle, blogPath, review));
//			}
//			return contents;
//		} finally {
//			close(connection, statement, null);
//		}
//	}
//
	public List<String> getPath(String userId) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		List<String> paths = new ArrayList<>();

		try {
			connection = dataSource.getConnection();
			final String query = "select blog.blog_path" + " from user join blog" + " on user.user_id=blog.user_user_id"
					+ " where user_id=?";
			statement = connection.prepareStatement(query);
			statement.setString(1, userId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String path = resultSet.getString("blog_path");
				paths.add(path);
			}
			return paths;
		} finally {
			close(connection, statement, null);
		}
	}

	private void close(Connection connection, PreparedStatement statement, ResultSet resultSet) {
		try {
			if (connection != null) {
				connection.close();
			}
			if (statement != null) {
				statement.close();
			}

			if (resultSet != null) {
				resultSet.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}