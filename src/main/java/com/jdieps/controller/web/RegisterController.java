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

@WebServlet(urlPatterns = { "/register" })
public class RegisterController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final String REGISTER_PAGE = "/views/web/register.jsp";

	@Resource(name = "jdbc/jdbc_project1")
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
		try {
			processRegister(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	protected void processRegister(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {
		String emailParam = req.getParameter("email");
		String fullnameParam = req.getParameter("fullname");
		String usernameParam = req.getParameter("username");
		String passwordParam = req.getParameter("password");
		String confirmPasswordParam = req.getParameter("confirm_password");
		String phoneNumberParam = req.getParameter("phone_number");

		boolean isRequired = isRequired(emailParam, fullnameParam, usernameParam, passwordParam, confirmPasswordParam,
				phoneNumberParam);
		if (!isRequired) {
			ServletUtil.forwardWithMessage(req, resp, REGISTER_PAGE, NotificationMessageConstant.MISSING_DATA);
		}

		boolean isMatch = checkConfirmPassword(passwordParam, confirmPasswordParam);
		if (!isMatch) {
			ServletUtil.forwardWithMessage(req, resp, REGISTER_PAGE, NotificationMessageConstant.CONFIMN_PASSWORD_NOT_MATCH);
		}

		boolean isUserNameExsited = checkUserNameExsited(usernameParam);
		boolean isEmailExsited = checkEmailExsited(usernameParam);
		if (isUserNameExsited || isEmailExsited) {
			ServletUtil.forwardWithMessage(req, resp, REGISTER_PAGE, NotificationMessageConstant.USER_EXISTED);
		}

		String userAddress = null;

		EStatus userStatus = EStatus.ACTIVE;

		String roleName = ERole.USER.name();
		RoleModel role = mRoleDbUtil.getRoleByName(roleName);
		long userRoleId = role.getId();

		UserModel newUser = new UserModel(usernameParam, passwordParam, fullnameParam, emailParam, phoneNumberParam,
				userAddress, userStatus, userRoleId);

		try {
			mUserDbUtil.createUser(newUser);
			ServletUtil.forwardWithMessage(req, resp, REGISTER_PAGE, NotificationMessageConstant.SUCCESS);
		} catch (Exception e) {
			ServletUtil.forwardWithMessage(req, resp, REGISTER_PAGE, NotificationMessageConstant.NOT_SUCCESS);
		}

	}

	// PRIVATE
	private boolean isRequired(String email, String fullname, String username, String password, String confirmPassword,
			String phoneNumber) {
		return email != null && fullname != null && username != null && password != null && confirmPassword != null
				&& phoneNumber != null;
	}

	private boolean checkConfirmPassword(String passwordParam, String confirmPasswordParam) {
		return passwordParam.equals(confirmPasswordParam);
	}

	private boolean checkUserNameExsited(String username) throws SQLException {
		return mUserDbUtil.getUserByUserName(username) != null;
	}

	private boolean checkEmailExsited(String email) throws SQLException {
		return mUserDbUtil.getUserByEmail(email) != null;
	}
}
