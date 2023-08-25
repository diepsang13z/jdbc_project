package com.jdieps.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.jdieps.model.RoleModel;

public class RoleDbUtil extends DbUtil {

	private final String TABLE = "role";
	private final String ID = "id";
	private final String ROLE = "role";

	public RoleDbUtil(DataSource dataSource) {
		super(dataSource);
	}

	public List<RoleModel> getRoleList() {
		List<RoleModel> roleList = new ArrayList<>();
		return roleList;
	}

	public RoleModel getRoleById(long id) throws SQLException {
		RoleModel role = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = super.mDataSource.getConnection();

			String query = String.format("select * " + "from %s " + "where %s=%d", TABLE, ID, id);

			stmt = conn.createStatement();

			rs = stmt.executeQuery(query);
			while (rs.next()) {
				long roleId = rs.getLong("id");
				String roleName = rs.getString("role");
				role = new RoleModel(roleId, roleName);
			}

		} finally {
			close(conn, stmt, rs);
		}

		return role;
	}

	public RoleModel getRoleByName(String name) throws SQLException {
		RoleModel role = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = super.mDataSource.getConnection();

			String query = String.format("select * " + "from %s " + "where %s='%s'", TABLE, ROLE, name);

			stmt = conn.createStatement();

			rs = stmt.executeQuery(query);
			while (rs.next()) {
				long roleId = rs.getLong("id");
				String roleName = rs.getString("role");
				role = new RoleModel(roleId, roleName);
			}

		} finally {
			close(conn, stmt, rs);
		}

		return role;
	}

}
