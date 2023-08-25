package com.jdieps.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.jdieps.model.EStatus;
import com.jdieps.model.UserModel;

public class PaginationDbUtil extends DbUtil {

	private int mNumberEntriesPerPage;

	public PaginationDbUtil(DataSource dataSource, int numberEntriesPerPage) throws SQLException {
		super(dataSource);
		mNumberEntriesPerPage = numberEntriesPerPage;
	}

	public int getTotalPage() throws SQLException {
		int countPage = 0;
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;

		try {
			conn = mDataSource.getConnection();
			String query = "select count(*) from user";

			preStmt = conn.prepareStatement(query);
			rs = preStmt.executeQuery();

			while (rs.next()) {
				int total = rs.getInt(1);
				countPage = (total % mNumberEntriesPerPage != 0) ? (total / mNumberEntriesPerPage) + 1
						: (total / mNumberEntriesPerPage);
			}

		} finally {
			close(conn, preStmt, rs);
		}

		return countPage;
	}

	public List<UserModel> getEntriesPerPage(int pageNumber) throws SQLException {
		List<UserModel> entries = new ArrayList<>();
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;

		try {
			conn = mDataSource.getConnection();
			String query = "select * from user " + "order by id " + "limit ?, ?";

			final int offsetValue = (pageNumber - 1) * mNumberEntriesPerPage;
			final int limmitValue = mNumberEntriesPerPage;

			preStmt = conn.prepareStatement(query);
			preStmt.setInt(1, offsetValue);
			preStmt.setInt(2, limmitValue);

			rs = preStmt.executeQuery();

			while (rs.next()) {
				long userId = rs.getLong("id");
				String userName = rs.getString("username");
				String userPwd = rs.getString("password");
				String userFullname = rs.getString("fullname");
				String userEmail = rs.getString("email");
				String userPhoneNumber = rs.getString("phoneNumber");

				int dbStatus = rs.getInt("status");
				EStatus userStatus = EStatus.findByValue(dbStatus);

				long roleId = rs.getLong("role_id");

				UserModel user = new UserModel(userId, userName, userPwd, userFullname, userEmail, userPhoneNumber,
						userStatus, roleId);

				entries.add(user);
			}

		} finally {
			close(conn, preStmt, rs);
		}

		return entries;
	}

}
