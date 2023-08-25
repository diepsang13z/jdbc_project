package com.jdieps.controller.admin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.jdieps.service.admin.PaginationService;

@WebServlet(urlPatterns = { "/admin-home" })
public class HomeController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final int NUMBER_ENTRIES_PER_PAGE = 5;

	private final String VIEW_PATH = "/views/admin/home.jsp";

	@Resource(name = "jdbc/jdbc_project1")
	private DataSource mDataSource;

	private PaginationService mPaginationService;

	private Map<String, ProcessingMethod> mCommandAction;

	@Override
	public void init() throws ServletException {
		super.init();

		try {
			mPaginationService = new PaginationService(mDataSource, NUMBER_ENTRIES_PER_PAGE, VIEW_PATH);
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
			processCreateRequest(req, resp);
			
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
	
	private void processCreateRequest(HttpServletRequest req, HttpServletResponse resp) {
		
	}

}
