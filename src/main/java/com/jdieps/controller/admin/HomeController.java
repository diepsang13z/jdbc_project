package com.jdieps.controller.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.jdieps.service.admin.PaginationService;
import com.jdieps.service.admin.UserManagementService;

@WebServlet(urlPatterns = { "/admin-home" })
public class HomeController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final int NUMBER_ENTRIES_PER_PAGE = 5;

	private final String VIEW_PATH = "/views/admin/home.jsp";

	@Resource(name = "jdbc/jdbc_project1")
	private DataSource mDataSource;

	private PaginationService mPaginationService;
	private UserManagementService mUserManagementService;

	private Map<String, ProcessingMethod> mCommandAction;

	@Override
	public void init() throws ServletException {
		super.init();

		try {
			mPaginationService = new PaginationService(mDataSource, NUMBER_ENTRIES_PER_PAGE, VIEW_PATH);
			mUserManagementService = new UserManagementService(mDataSource);
		} catch (SQLException e) {
			throw new ServletException("Error initializing PaginationService", e);
		}

		setCommandAction();

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		actionCommand(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		actionCommand(req, resp);
	}

	// PRIVATE
	private interface ProcessingMethod {
		void method(HttpServletRequest req, HttpServletResponse resp);
	}

	private void setCommandAction() {
		mCommandAction = new HashMap<>();

		mCommandAction.put("LIST", (req, resp) -> {
			try {
				mPaginationService.listUser(req, resp);
			} catch (ServletException | IOException | SQLException e) {
				throw new RuntimeException("ERROR: listUser method in Pagination Service!", e);
			}
		});

		mCommandAction.put("CHANGE_PAGE", (req, resp) -> {
			try {
				mPaginationService.changePage(req, resp);
			} catch (ServletException | IOException | SQLException e) {
				throw new RuntimeException("ERROR: listUser method in Pagination Service!", e);
			}
		});

		mCommandAction.put("CREATE", (req, resp) -> {
			String usernameParam = req.getParameter("username");
			String passwordParam = req.getParameter("password");
			String fullnameParam = req.getParameter("fullname");
			String emailParam = req.getParameter("email");
			String phoneNumberParam = req.getParameter("phone_number");
			String addressParam = req.getParameter("address");
			String roleParam = req.getParameter("role");

			String message = null;

			try {
				message = mUserManagementService.addUser(usernameParam, passwordParam, fullnameParam, emailParam,
						phoneNumberParam, addressParam, roleParam);
			} catch (SQLException e) {
				throw new RuntimeException("ERROR: addUser method in UserManagementService!", e);
			}

			HttpSession session = req.getSession();
			session.setAttribute("message", message);
			try {
				mPaginationService.listUser(req, resp);
			} catch (ServletException | IOException | SQLException e) {
				throw new RuntimeException("ERROR: listUser method in Pagination Service!", e);
			}
		});

	}

	// PRIVATE
	private void actionCommand(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String command = req.getParameter("command");

			if (command == null) {
				command = "LIST";
			}

			mCommandAction.get(command).method(req, resp);

		} catch (Exception e) {
			throw new ServletException("ERROR in actionCommand method", e);
		}
	}

}
