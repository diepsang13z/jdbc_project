package com.jdieps.controller.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.jdieps.constant.ErrorMessageConstant;
import com.jdieps.dao.RoleDbUtil;
import com.jdieps.dao.UserDbUtil;
import com.jdieps.model.ERole;
import com.jdieps.model.RoleModel;
import com.jdieps.model.UserModel;

@WebServlet(urlPatterns = { "/login" })
public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final String ADMIN_PAGE = "/admin-home";
	private final String USER_PAGE = "/home";
	private final String LOGIN_PAGE = "/views/web/login.jsp";

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
		processLogin(req, resp);
	}

	protected void processLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String emailParam = req.getParameter("email");
		String passwordParam = req.getParameter("password");

		try {
			UserModel user = mUserDbUtil.getUserByEmailAndPass(emailParam, passwordParam);

			if (user != null) {
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

			} else {
				HttpSession session = req.getSession();
				session.setAttribute("message", ErrorMessageConstant.USER_NOT_FOUND);
				RequestDispatcher rd = req.getRequestDispatcher(LOGIN_PAGE);
				rd.forward(req, resp);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
