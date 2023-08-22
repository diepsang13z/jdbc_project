package com.jdieps.service.admin;

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
import com.jdieps.dao.PaginationDbUtil;
import com.jdieps.dao.RoleDbUtil;
import com.jdieps.model.RoleModel;
import com.jdieps.model.UserModel;

public class PaginationService {

	private PaginationDbUtil mPaginationDbUtil;
	
	private int mPageControl;
	private String mView;

	public PaginationService(DataSource dataSource, int numberEntriesPerPage, String view) throws SQLException {
		mPaginationDbUtil = new PaginationDbUtil(dataSource, numberEntriesPerPage);
		mPageControl = 1;
		mView = view;
	}

	public void listUser(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {
		int totalPage = mPaginationDbUtil.getTotalPage();
		List<UserModel> entries = mPaginationDbUtil.getEntriesPerPage(mPageControl);
		
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
}
