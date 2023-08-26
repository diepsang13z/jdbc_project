package com.jdieps.controller.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.jdieps.constant.NotificationMessageConstant;
import com.jdieps.dao.RoleDbUtil;
import com.jdieps.dao.UserDbUtil;
import com.jdieps.model.ERole;
import com.jdieps.model.EStatus;
import com.jdieps.model.RoleModel;
import com.jdieps.model.UserModel;
import com.jdieps.util.ServletUtil;

@WebServlet(urlPatterns = { "/login" })
public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final String ADMIN_PAGE = "/admin-home";
	private final String USER_PAGE = "/home";
	private final String LOGIN_PAGE = "/views/web/login.jsp";

	@Resource(name = "jdbc/jdbc_project")
	private DataSource mDataSource;

	private UserDbUtil mUserDbUtil;
	private RoleDbUtil mRoleDbUtil;

	@Override
	public void init() throws ServletException {
		super.init();

		try {
			mUserDbUtil = new UserDbUtil(mDataSource);
			mRoleDbUtil = new RoleDbUtil(mDataSource);
		} catch (Exception e) {
			throw new ServletException();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		processLogin(req, resp);
	}

	protected void processLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String emailParam = req.getParameter("email");
		String passwordParam = req.getParameter("password");

		boolean isRequired = isRequired(emailParam, passwordParam);
		if (!isRequired) {
			ServletUtil.forwardWithMessage(req, resp, LOGIN_PAGE, NotificationMessageConstant.MISSING_DATA);
		}

		try {
			UserModel user = mUserDbUtil.getUserByEmailAndPass(emailParam, passwordParam);

			if (user == null) {
				ServletUtil.forwardWithMessage(req, resp, LOGIN_PAGE, NotificationMessageConstant.USER_NOT_FOUND);
			}

			int userStatus = user.getStatus().getValue();
			int lockStatus = EStatus.LOCK.getValue();
			if (userStatus == lockStatus) {
				ServletUtil.forwardWithMessage(req, resp, LOGIN_PAGE, NotificationMessageConstant.LOCKED_ACCOUNT);
			}

			long roleID = user.getRoleId();
			RoleModel role = mRoleDbUtil.getRoleById(roleID);
			String roleName = role.getName().toUpperCase();

			if (roleName.equals(ERole.ADMIN.name())) {
				String path = req.getContextPath() + ADMIN_PAGE;
				resp.sendRedirect(path);
			} else if (roleName.equals(ERole.USER.name())) {
				String path = req.getContextPath() + USER_PAGE;
				resp.sendRedirect(path);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// PRIVATE
	private boolean isRequired(String emailParam, String passwordParam) {
		return emailParam != null && passwordParam != null;
	}

}
