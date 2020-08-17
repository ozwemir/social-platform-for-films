package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import datahandler.DatabaseHandler;
import datahandler.TextHandler;
import model.Blog;
import model.Content;
import model.User;

@WebServlet("/MainController")
public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DatabaseHandler databaseHandler;
	@Resource(name = "jdbc/mydb")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		super.init();
		databaseHandler = new DatabaseHandler(dataSource);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String command = request.getParameter("command");
		// check if command is empty
		if (command == null) {
			command = "LOG-IN";
		}
		try {
			switch (command) {
			case "ADD-USER":
				addUser(request, response);
				break;
			case "LOG-IN":
				loginUser(request, response);
				break;
			case "ADD-CONTENT":
				addReview(request, response);
				break;
//			case "USER-PAGE":
//				userPage(request, response);
//				break;
			default:
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	private void userPage(HttpServletRequest request, HttpServletResponse response) {
//		int userId = Integer.parseInt(request.getParameter("userId"));
//
//		try {
//
//			HttpSession session = request.getSession();
//
//			Map<String, Content> contents = new LinkedHashMap<>();
//			contents = databaseHandler.getContent(userId);
//
//			session.setAttribute("CONTENT", contents);
//
//			RequestDispatcher dispatcher = request.getRequestDispatcher("profile.jsp");
//			dispatcher.forward(request, response);
//
//		} catch (ServletException | IOException | SQLException e) {
//			System.out.println("Some thing is wrong with  mapping");
//		}
//		// User data is here. need method for database and maybe modellling
//		// contents of the user
//
//	}

	private void addReview(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		/* Getting the review data from jsp form */
		String title = request.getParameter("title");
		String comment = request.getParameter("comment");
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");

		TextHandler textHandler = new TextHandler();

		/* create unique path for each created text */
		List<String> paths = databaseHandler.getPath(userId);
		String path = textHandler.createPath(userId, userName, paths);

		/* save the review to a text file */
		if (textHandler.createTxtFile(path, comment)) {
			Blog blog = new Blog(title, path);
			/* save the review title to database by user id */
			try {
				boolean isAdded = databaseHandler.addReview(blog, userId);
				System.out.println("is added " + isAdded);

				if (isAdded) {
					request.setAttribute("result", "added");
				} else {
					/* if something went wrong with database. Delete created text file */
					textHandler.deleteCreatedTextFile(path);
					request.setAttribute("result", "not added");
				}
				response.sendRedirect("ControllerPRG");

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Some thing is wrong with the TextHandler class");
			// There will be some warning codes here
		}
	}

	private void loginUser(HttpServletRequest request, HttpServletResponse response) {

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		try {
			User user = databaseHandler.checkLogin(email, password);
			String pageLink = "index.jsp";

			if (user != null) {
				HttpSession session = request.getSession();

				Map<String, Content> contents = new LinkedHashMap<>();
				contents = databaseHandler.getContent();

				session.setAttribute("THEUSER", user);
				session.setAttribute("CONTENT", contents);

				pageLink = "home.jsp";

			} else {
				String message = "Invalid email/password";
				request.setAttribute("message", message);
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher(pageLink);
			dispatcher.forward(request, response);

		} catch (ServletException | IOException | SQLException e) {
			System.out.println("Some thing is wrong with  mapping");
		}

	}

	private void addUser(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("userName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		User user = new User(userName, email, password);
		try {
			databaseHandler.addUser(user);
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
