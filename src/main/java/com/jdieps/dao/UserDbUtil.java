package com.jdieps.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.jdieps.model.EStatus;
import com.jdieps.model.UserModel;

public class UserDbUtil extends DbUtil {

	private final String TABLE = "user";
	private final String ID = "id";
	private final String USERNAME = "username";
	private final String PASSWOED = "password";
	private final String FULLNAME = "fullname";
	private final String EMAIL = "email";
	private final String PHONE_NUMBER = "phoneNumber";
	private final String STATUS = "status";
	private final String ROLE_ID = "role_id";

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

			String query = String.format("select * " + "from %s " + "where %s=? and %s=?", TABLE, EMAIL, PASSWOED);

			preStmt = conn.prepareStatement(query);
			preStmt.setString(1, email);
			preStmt.setString(2, password);

			rs = preStmt.executeQuery();
			while (rs.next()) {
				long userId = rs.getLong("id");
				String userName = rs.getString("username");
				String userPassword = rs.getString("password");
				String userFullname = rs.getString("fullname");
				String userEmail = rs.getString("email");
				String userPhoneNumber = rs.getString("phoneNumber");

				int dbStatus = rs.getInt("status");
				EStatus userStatus = EStatus.findByValue(dbStatus);

				long roleId = rs.getLong("role_id");

				user = new UserModel(userId, userName, userPassword, userFullname, userEmail, userPhoneNumber,
						userStatus, roleId);
			}

		} finally {
			close(conn, preStmt, rs);
		}

		return user;
	}

	public UserModel getUserByUserName(String username) throws SQLException {
		UserModel user = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			String query = String.format("select * " + "from %s " + "where %s='%s'", TABLE, USERNAME, username);

			conn = super.mDataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				long userId = rs.getLong("id");
				String userName = rs.getString("username");
				String userPassword = rs.getString("password");
				String userFullname = rs.getString("fullname");
				String userEmail = rs.getString("email");
				String userPhoneNumber = rs.getString("phoneNumber");

				int dbStatus = rs.getInt("status");
				EStatus userStatus = EStatus.findByValue(dbStatus);

				long roleId = rs.getLong("role_id");

				user = new UserModel(userId, userName, userPassword, userFullname, userEmail, userPhoneNumber,
						userStatus, roleId);
			}

		} finally {
			close(conn, stmt, rs);
		}

		return user;
	}

	public UserModel getUserByEmail(String email) throws SQLException {
		UserModel user = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			String query = String.format("select * " + "from %s " + "where %s='%s'", TABLE, EMAIL, email);

			conn = super.mDataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				long userId = rs.getLong("id");
				String userName = rs.getString("username");
				String userPassword = rs.getString("password");
				String userFullname = rs.getString("fullname");
				String userEmail = rs.getString("email");
				String userPhoneNumber = rs.getString("phoneNumber");

				int dbStatus = rs.getInt("status");
				EStatus userStatus = EStatus.findByValue(dbStatus);

				long roleId = rs.getLong("role_id");

				user = new UserModel(userId, userName, userPassword, userFullname, userEmail, userPhoneNumber,
						userStatus, roleId);
			}

		} finally {
			close(conn, stmt, rs);
		}

		return user;
	}

	public void createUser(UserModel user) throws SQLException {
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;

		try {
			conn = super.mDataSource.getConnection();

			String query = String.format("insert into %s(%s, %s, %s, %s, %s, %s, %s) values(?, ?, ?, ?, ?, ?, ?)",
					TABLE, USERNAME, PASSWOED, FULLNAME, EMAIL, PHONE_NUMBER, STATUS, ROLE_ID);

			preStmt = conn.prepareStatement(query);

			int index = 1;
			preStmt.setString(index++, user.getUsername());
			preStmt.setString(index++, user.getPassword());
			preStmt.setString(index++, user.getFullname());
			preStmt.setString(index++, user.getEmail());
			preStmt.setString(index++, user.getPhoneNumber());
			preStmt.setInt(index++, user.getStatus().getValue());
			preStmt.setLong(index++, user.getRoleId());

			preStmt.execute();

		} finally {
			close(conn, preStmt, rs);
		}
	}

}
