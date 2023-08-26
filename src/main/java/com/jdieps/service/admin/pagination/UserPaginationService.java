package com.jdieps.service.admin.pagination;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.jdieps.constant.AdminAttrConstant;
import com.jdieps.dao.UserDbUtil;
import com.jdieps.model.UserModel;

public class UserPaginationService {

	private UserDbUtil mUserDbUtil;

	private int mNumberEntriesPerPage;
	private int mPageControl;
	private String mView;

	public UserPaginationService(DataSource dataSource, int numberEntriesPerPage, String view) throws SQLException {
		mUserDbUtil = new UserDbUtil(dataSource);
		mNumberEntriesPerPage = numberEntriesPerPage;
		mPageControl = 1;
		mView = view;
	}

	public void listUser(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {
		int totalPage = getTotalPage();
		List<UserModel> entries = getEntriesPerPage(mPageControl);

		HttpSession session = req.getSession();
		session.setAttribute(AdminAttrConstant.TOTAL_PAGE, totalPage);
		session.setAttribute(AdminAttrConstant.CURRENT_PAGE, mPageControl);
		session.setAttribute(AdminAttrConstant.ENTRIES_DATA, entries);

		RequestDispatcher rd = req.getRequestDispatcher(mView);
		rd.forward(req, resp);
	}

	public void listUser(HttpServletRequest req, HttpServletResponse resp, List<UserModel> entries)
			throws ServletException, IOException, SQLException {
		int totalPage = getTotalPage();
		mPageControl = 1;

		HttpSession session = req.getSession();
		session.setAttribute(AdminAttrConstant.TOTAL_PAGE, totalPage);
		session.setAttribute(AdminAttrConstant.CURRENT_PAGE, mPageControl);
		session.setAttribute(AdminAttrConstant.ENTRIES_DATA, entries);

		RequestDispatcher rd = req.getRequestDispatcher(mView);
		rd.forward(req, resp);
	}

	public void changePage(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {
		int page = Integer.parseInt(req.getParameter("page"));
		mPageControl = page;
		listUser(req, resp);
	}

	// PRIVATE
	private int getTotalPage() throws SQLException {
		int countUser = mUserDbUtil.getCountUser();
		int countPage = (countUser % mNumberEntriesPerPage != 0) ? (countUser / mNumberEntriesPerPage) + 1
				: (countUser / mNumberEntriesPerPage);
		return countPage;
	}

	private List<UserModel> getEntriesPerPage(int pageNumber) throws SQLException {
		final int limitValue = mNumberEntriesPerPage;
		final int offsetValue = (pageNumber - 1) * mNumberEntriesPerPage;
		List<UserModel> userList = mUserDbUtil.getUserWithLimmitOffset(limitValue, offsetValue);
		return userList;
	}

}
