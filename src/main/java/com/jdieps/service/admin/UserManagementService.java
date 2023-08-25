package com.jdieps.service.admin;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.jdieps.constant.NotificationMessageConstant;
import com.jdieps.dao.RoleDbUtil;
import com.jdieps.dao.UserDbUtil;
import com.jdieps.model.EStatus;
import com.jdieps.model.RoleModel;
import com.jdieps.model.UserModel;
import com.jdieps.util.ServletUtil;

public class UserManagementService {

	private UserDbUtil mUserDbUtil;
	private RoleDbUtil mRoleDbUtil;

	public UserManagementService(DataSource dataSource) {
		mUserDbUtil = new UserDbUtil(dataSource);
		mRoleDbUtil = new RoleDbUtil(dataSource);
	}

	public void addUser(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
		HttpSession session = req.getSession();

		String usernameParam = req.getParameter("username");
		String passwordParam = req.getParameter("password");
		String fullnameParam = req.getParameter("fullname");
		String emailParam = req.getParameter("email");
		String phoneNumberParam = req.getParameter("phone_number");
		String addressParam = req.getParameter("address");
		String roleParam = req.getParameter("role");

		boolean isUserNameExsited = checkUserNameExsited(usernameParam);
		boolean isEmailExsited = checkEmailExsited(emailParam);
		if (isUserNameExsited || isEmailExsited) {
			session.setAttribute("message", NotificationMessageConstant.USER_EXISTED);
			return;
		}

		EStatus userStatus = EStatus.ACTIVE;
		RoleModel userRole = mRoleDbUtil.getRoleByName(roleParam);
		long userRoleId = userRole.getId();

		UserModel newUser = new UserModel(usernameParam, passwordParam, fullnameParam, emailParam, phoneNumberParam,
				addressParam, userStatus, userRoleId);

		try {
			mUserDbUtil.createUser(newUser);
			ServletUtil.setSessionMessage(req, resp, NotificationMessageConstant.SUCCESS);
		} catch (Exception e) {
			ServletUtil.setSessionMessage(req, resp, NotificationMessageConstant.NOT_SUCCESS);
		}

	}

	public void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
		String idParam = req.getParameter("userId");
		try {
			mUserDbUtil.deleteUserById(idParam);
			ServletUtil.setSessionMessage(req, resp, NotificationMessageConstant.SUCCESS);
		} catch (Exception e) {
			ServletUtil.setSessionMessage(req, resp, NotificationMessageConstant.NOT_SUCCESS);
		}
	}

	// PRIVATE
	private boolean checkUserNameExsited(String username) throws SQLException {
		return mUserDbUtil.getUserByUserName(username) != null;
	}

	private boolean checkEmailExsited(String email) throws SQLException {
		return mUserDbUtil.getUserByEmail(email) != null;
	}

}
