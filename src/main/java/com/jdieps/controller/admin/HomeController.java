package com.jdieps.controller.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.jdieps.model.UserModel;
import com.jdieps.service.admin.management.UserManagementService;
import com.jdieps.service.admin.pagination.UserPaginationService;

@WebServlet(urlPatterns = { "/admin-home" })
public class HomeController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final int NUMBER_ENTRIES_PER_PAGE = 5;

	private final String VIEW_PATH = "/views/admin/home.jsp";

	@Resource(name = "jdbc/jdbc_project")
	private DataSource mDataSource;

	private UserPaginationService mUserPaginationService;
	private UserManagementService mUserManagementService;

	private Map<String, ProcessingMethod> mCommandAction;

	@Override
	public void init() throws ServletException {
		super.init();

		try {
			mUserPaginationService = new UserPaginationService(mDataSource, NUMBER_ENTRIES_PER_PAGE, VIEW_PATH);
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
				mUserPaginationService.listUser(req, resp);
			} catch (ServletException | IOException | SQLException e) {
				throw new RuntimeException("ERROR: listUser method in PaginationService!", e);
			}
		});

		mCommandAction.put("CHANGE_PAGE", (req, resp) -> {
			try {
				mUserPaginationService.changePage(req, resp);
			} catch (ServletException | IOException | SQLException e) {
				throw new RuntimeException("ERROR: listUser method in PaginationService!", e);
			}
		});

		mCommandAction.put("CREATE", (req, resp) -> {
			try {
				mUserManagementService.addUser(req, resp);
			} catch (SQLException e) {
				throw new RuntimeException("ERROR: addUser method in UserManagementService!", e);
			}
			mCommandAction.get("LIST").method(req, resp);
		});

		mCommandAction.put("DELETE", (req, resp) -> {
			try {
				mUserManagementService.deleteUser(req, resp);
			} catch (SQLException e) {
				throw new RuntimeException("ERROR: deleteUser method in UserManagementService!", e);
			}
			mCommandAction.get("LIST").method(req, resp);
		});

		mCommandAction.put("UPDATE", (req, resp) -> {
			try {
				mUserManagementService.updateUser(req, resp);
			} catch (SQLException e) {
				throw new RuntimeException("ERROR: updateUser method in UserManagementService!", e);
			}
			mCommandAction.get("LIST").method(req, resp);
		});

		mCommandAction.put("LOCK", (req, resp) -> {
			try {
				mUserManagementService.lockUser(req, resp);
			} catch (SQLException e) {
				throw new RuntimeException("ERROR: lockUser method in UserManagementService!", e);
			}
			mCommandAction.get("LIST").method(req, resp);
		});

		mCommandAction.put("ACTIVE", (req, resp) -> {
			try {
				mUserManagementService.activeUser(req, resp);
			} catch (SQLException e) {
				throw new RuntimeException("ERROR: activeUser method in UserManagementService!", e);
			}
			mCommandAction.get("LIST").method(req, resp);
		});

		mCommandAction.put("SEARCH", (req, resp) -> {
			List<UserModel> userList = null;

			try {
				userList = mUserManagementService.searchUserByEmailOrPhoneNumber(req, resp);
			} catch (SQLException e) {
				throw new RuntimeException("ERROR: searchUserByEmailOrPhoneNumber method in UserManagementService!", e);
			}

			try {
				mUserPaginationService.listUser(req, resp, userList);
			} catch (ServletException | IOException | SQLException e) {
				throw new RuntimeException("ERROR: listUser method in PaginationService!", e);
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
