package com.jdieps.util;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServletUtil {
	public static final String MESSAGE_ATTR = "message";

	public static void forwardWithMessage(HttpServletRequest req, HttpServletResponse resp, String path,
			String message) throws ServletException, IOException {
		HttpSession session = req.getSession();
		session.setAttribute(MESSAGE_ATTR, message);
		RequestDispatcher rd = req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}
}
