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

public class UserDbUtil extends DbUtil {

	public UserDbUtil(DataSource dataSource) {
		super(dataSource);
	}

	public List<UserModel> getUserList() {
		List<UserModel> userList = new ArrayList<>();
		return userList;
	}

	public UserModel getUserByEmailAndPass(String email, String password) throws SQLException {
		UserModel user = null;
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;

		try {
			conn = super.mDataSource.getConnection();

			String query = "select * " + "from user " + "where email=? and password=?";

			preStmt = conn.prepareStatement(query);
			preStmt.setString(1, email);
			preStmt.setString(2, password);

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

				user = new UserModel(userId, userName, userPwd, userFullname, userEmail, userPhoneNumber, userStatus,
						roleId);
			}

		} finally {
			close(conn, preStmt, rs);
		}

		return user;
	}

}
