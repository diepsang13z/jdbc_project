package com.jdieps.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.jdieps.constant.UserDbFieldConstant;
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

			String query = String.format("select * " + "from %s " + "where %s=? and %s=?", UserDbFieldConstant.TABLE,
					UserDbFieldConstant.EMAIL, UserDbFieldConstant.PASSWOED);

			preStmt = conn.prepareStatement(query);
			preStmt.setString(1, email);
			preStmt.setString(2, password);

			rs = preStmt.executeQuery();
			while (rs.next()) {
				long userId = rs.getLong(UserDbFieldConstant.ID);
				String userName = rs.getString(UserDbFieldConstant.USERNAME);
				String userPassword = rs.getString(UserDbFieldConstant.PASSWOED);
				String userFullname = rs.getString(UserDbFieldConstant.FULLNAME);
				String userEmail = rs.getString(UserDbFieldConstant.EMAIL);
				String userPhoneNumber = rs.getString(UserDbFieldConstant.PHONE_NUMBER);
				String userAdress = rs.getString(UserDbFieldConstant.ADDRESS);

				int dbStatus = rs.getInt(UserDbFieldConstant.STATUS);
				EStatus userStatus = EStatus.findByValue(dbStatus);

				long roleId = rs.getLong(UserDbFieldConstant.ROLE_ID);

				user = new UserModel(userId, userName, userPassword, userFullname, userEmail, userPhoneNumber,
						userAdress, userStatus, roleId);
			}

		} finally {
			close(conn, preStmt, rs);
		}

		return user;
	}

	public UserModel getUserById(long id) throws SQLException {
		UserModel user = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			String query = String.format("select * " + "from %s " + "where %s=%d", UserDbFieldConstant.TABLE,
					UserDbFieldConstant.ID, id);

			conn = super.mDataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				long userId = rs.getLong(UserDbFieldConstant.ID);
				String userName = rs.getString(UserDbFieldConstant.USERNAME);
				String userPassword = rs.getString(UserDbFieldConstant.PASSWOED);
				String userFullname = rs.getString(UserDbFieldConstant.FULLNAME);
				String userEmail = rs.getString(UserDbFieldConstant.EMAIL);
				String userPhoneNumber = rs.getString(UserDbFieldConstant.PHONE_NUMBER);
				String userAdress = rs.getString(UserDbFieldConstant.ADDRESS);

				int dbStatus = rs.getInt(UserDbFieldConstant.STATUS);
				EStatus userStatus = EStatus.findByValue(dbStatus);

				long roleId = rs.getLong(UserDbFieldConstant.ROLE_ID);

				user = new UserModel(userId, userName, userPassword, userFullname, userEmail, userPhoneNumber,
						userAdress, userStatus, roleId);
			}

		} finally {
			close(conn, stmt, rs);
		}

		return user;
	}

	public UserModel getUserByUserName(String username) throws SQLException {
		UserModel user = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			String query = String.format("select * " + "from %s " + "where %s='%s'", UserDbFieldConstant.TABLE,
					UserDbFieldConstant.USERNAME, username);

			conn = super.mDataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				long userId = rs.getLong(UserDbFieldConstant.ID);
				String userName = rs.getString(UserDbFieldConstant.USERNAME);
				String userPassword = rs.getString(UserDbFieldConstant.PASSWOED);
				String userFullname = rs.getString(UserDbFieldConstant.FULLNAME);
				String userEmail = rs.getString(UserDbFieldConstant.EMAIL);
				String userPhoneNumber = rs.getString(UserDbFieldConstant.PHONE_NUMBER);
				String userAdress = rs.getString(UserDbFieldConstant.ADDRESS);

				int dbStatus = rs.getInt(UserDbFieldConstant.STATUS);
				EStatus userStatus = EStatus.findByValue(dbStatus);

				long roleId = rs.getLong(UserDbFieldConstant.ROLE_ID);

				user = new UserModel(userId, userName, userPassword, userFullname, userEmail, userPhoneNumber,
						userAdress, userStatus, roleId);
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
			String query = String.format("select * " + "from %s " + "where %s='%s'", UserDbFieldConstant.TABLE,
					UserDbFieldConstant.EMAIL, email);

			conn = super.mDataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				long userId = rs.getLong(UserDbFieldConstant.ID);
				String userName = rs.getString(UserDbFieldConstant.USERNAME);
				String userPassword = rs.getString(UserDbFieldConstant.PASSWOED);
				String userFullname = rs.getString(UserDbFieldConstant.FULLNAME);
				String userEmail = rs.getString(UserDbFieldConstant.EMAIL);
				String userPhoneNumber = rs.getString(UserDbFieldConstant.PHONE_NUMBER);
				String userAdress = rs.getString(UserDbFieldConstant.ADDRESS);

				int dbStatus = rs.getInt(UserDbFieldConstant.STATUS);
				EStatus userStatus = EStatus.findByValue(dbStatus);

				long roleId = rs.getLong(UserDbFieldConstant.ROLE_ID);

				user = new UserModel(userId, userName, userPassword, userFullname, userEmail, userPhoneNumber,
						userAdress, userStatus, roleId);
			}

		} finally {
			close(conn, stmt, rs);
		}

		return user;
	}

	public void createUser(UserModel user) throws SQLException {
		Connection conn = null;
		PreparedStatement preStmt = null;

		try {
			conn = super.mDataSource.getConnection();

			String query = String.format("insert into %s(%s, %s, %s, %s, %s, %s, %s) values(?, ?, ?, ?, ?, ?, ?)",
					UserDbFieldConstant.TABLE, UserDbFieldConstant.USERNAME, UserDbFieldConstant.PASSWOED,
					UserDbFieldConstant.FULLNAME, UserDbFieldConstant.EMAIL, UserDbFieldConstant.PHONE_NUMBER,
					UserDbFieldConstant.STATUS, UserDbFieldConstant.ROLE_ID);

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
			close(conn, preStmt, null);
		}
	}

	public void updateUser(long userId, String newFullName, String newEmail, String newPhoneNumber, String newAddress,
			String newUserName, long newRoleId) throws SQLException {
		Connection conn = null;
		PreparedStatement preStmt = null;

		try {
			conn = super.mDataSource.getConnection();

			String query = String.format(
					"update %s " + "set %s=?, %s=?, %s=?, %s=?, %s=?, %s=? " + "where %s=" + userId,
					UserDbFieldConstant.TABLE, UserDbFieldConstant.FULLNAME, UserDbFieldConstant.EMAIL,
					UserDbFieldConstant.PHONE_NUMBER, UserDbFieldConstant.ADDRESS, UserDbFieldConstant.USERNAME,
					UserDbFieldConstant.ROLE_ID, UserDbFieldConstant.ID);

			preStmt = conn.prepareStatement(query);

			int index = 1;
			preStmt.setString(index++, newFullName);
			preStmt.setString(index++, newEmail);
			preStmt.setString(index++, newPhoneNumber);
			preStmt.setString(index++, newAddress);
			preStmt.setString(index++, newUserName);
			preStmt.setLong(index++, newRoleId);

			preStmt.execute();

		} finally {
			close(conn, preStmt, null);
		}
	}

	public void deleteUser(long userId) throws SQLException {
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = super.mDataSource.getConnection();

			String query = String.format("delete from %s where %s=%d", UserDbFieldConstant.TABLE,
					UserDbFieldConstant.ID, userId);

			stmt = conn.createStatement();
			stmt.execute(query);

		} finally {
			close(conn, stmt, null);
		}
	}

	public void lockUser(long userId) throws SQLException {
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = super.mDataSource.getConnection();

			int lockStatus = EStatus.LOCK.getValue();
			String query = String.format("update %s " + "set %s=" + lockStatus + " where %s=" + userId,
					UserDbFieldConstant.TABLE, UserDbFieldConstant.STATUS, UserDbFieldConstant.ID);

			stmt = conn.createStatement();
			stmt.execute(query);

		} finally {
			close(conn, stmt, null);
		}
	}

	public void activeUser(long userId) throws SQLException {
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = super.mDataSource.getConnection();

			int activeStatus = EStatus.ACTIVE.getValue();
			String query = String.format("update %s " + "set %s=" + activeStatus + " where %s=" + userId,
					UserDbFieldConstant.TABLE, UserDbFieldConstant.STATUS, UserDbFieldConstant.ID);

			stmt = conn.createStatement();
			stmt.execute(query);

		} finally {
			close(conn, stmt, null);
		}
	}

}
