package com.jdieps.service.admin;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.jdieps.constant.AdminAttrConstant;
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
		String usernameParam = req.getParameter("username");
		String passwordParam = req.getParameter("password");
		String fullnameParam = req.getParameter("fullname");
		String emailParam = req.getParameter("email");
		String phoneNumberParam = req.getParameter("phone_number");
		String addressParam = req.getParameter("address");
		String roleParam = req.getParameter("role");

		boolean isRequired = isCreateUserRequired(usernameParam, passwordParam, fullnameParam, emailParam, addressParam,
				roleParam);
		if (!isRequired) {
			ServletUtil.setSessionMessage(req, resp, NotificationMessageConstant.MISSING_DATA);
			return;
		}

		boolean isUserNameExsited = checkUserNameExsited(usernameParam);
		boolean isEmailExsited = checkEmailExsited(emailParam);
		if (isUserNameExsited || isEmailExsited) {
			ServletUtil.setSessionMessage(req, resp, NotificationMessageConstant.USER_EXISTED);
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

	public void updateUser(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
		String idParam = req.getParameter("id");
		String fullnameParam = req.getParameter("fullname");
		String emailParam = req.getParameter("email");
		String phoneNumberParam = req.getParameter("phone_number");
		String addressParam = req.getParameter("address");
		String usernameParam = req.getParameter("username");
		String roleParam = req.getParameter("role");

		boolean isRequired = isUpdateUserRequired(idParam, fullnameParam, emailParam, phoneNumberParam, addressParam,
				usernameParam, roleParam);
		if (!isRequired) {
			ServletUtil.setSessionMessage(req, resp, NotificationMessageConstant.MISSING_DATA);
			return;
		}

		long userId = Integer.parseInt(idParam);
		RoleModel userRole = mRoleDbUtil.getRoleByName(roleParam);
		long userRoleId = userRole.getId();

		try {
			mUserDbUtil.updateUser(userId, fullnameParam, emailParam, phoneNumberParam, addressParam, usernameParam,
					userRoleId);
			ServletUtil.setSessionMessage(req, resp, NotificationMessageConstant.SUCCESS);
		} catch (Exception e) {
			ServletUtil.setSessionMessage(req, resp, NotificationMessageConstant.NOT_SUCCESS);
		}

	}

	public void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
		String idParam = req.getParameter("userId");
		long userId = Integer.parseInt(idParam);
		try {
			mUserDbUtil.deleteUser(userId);
			ServletUtil.setSessionMessage(req, resp, NotificationMessageConstant.SUCCESS);
		} catch (Exception e) {
			ServletUtil.setSessionMessage(req, resp, NotificationMessageConstant.NOT_SUCCESS);
		}
	}

	public void lockUser(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
		String idParam = req.getParameter("userId");
		long userId = Integer.parseInt(idParam);
		try {
			mUserDbUtil.lockUser(userId);
			ServletUtil.setSessionMessage(req, resp, NotificationMessageConstant.SUCCESS);
		} catch (Exception e) {
			ServletUtil.setSessionMessage(req, resp, NotificationMessageConstant.NOT_SUCCESS);
		}
	}

	public void activeUser(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
		String idParam = req.getParameter("userId");
		long userId = Integer.parseInt(idParam);
		try {
			mUserDbUtil.activeUser(userId);
			ServletUtil.setSessionMessage(req, resp, NotificationMessageConstant.SUCCESS);
		} catch (Exception e) {
			ServletUtil.setSessionMessage(req, resp, NotificationMessageConstant.NOT_SUCCESS);
		}
	}
	

	public List<UserModel> searchUserByEmailOrPhoneNumber(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
		String searchContentParam = req.getParameter("search_content");
		return mUserDbUtil.searchUserByEmailOrPhoneNumber(searchContentParam);
	}

	// PRIVATE
	private boolean isCreateUserRequired(String username, String password, String fullname, String email,
			String address, String role) {
		return username != null && password != null && fullname != null && email != null && address != null
				&& role != null;
	}

	private boolean isUpdateUserRequired(String id, String fullname, String email, String phoneNumber, String address,
			String username, String role) {
		return id != null && fullname != null && email != null && phoneNumber != null && address != null
				&& username != null && role != null;
	}

	private boolean checkUserNameExsited(String username) throws SQLException {
		return mUserDbUtil.getUserByUserName(username) != null;
	}

	private boolean checkEmailExsited(String email) throws SQLException {
		return mUserDbUtil.getUserByEmail(email) != null;
	}

}
