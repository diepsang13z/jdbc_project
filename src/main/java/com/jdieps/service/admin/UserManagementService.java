package com.jdieps.service.admin;

import java.sql.SQLException;

import javax.sql.DataSource;

import com.jdieps.constant.NotificationMessageConstant;
import com.jdieps.dao.RoleDbUtil;
import com.jdieps.dao.UserDbUtil;
import com.jdieps.model.EStatus;
import com.jdieps.model.RoleModel;
import com.jdieps.model.UserModel;
import com.jdieps.util.ServletUtil;

public class UserManagementService {

	private UserDbUtil mUserDbUtil;
	private RoleDbUtil mRoleDbUtil;

	public UserManagementService(DataSource dataSource) {
		mUserDbUtil = new UserDbUtil(dataSource);
		mRoleDbUtil = new RoleDbUtil(dataSource);
	}

	public String addUser(String username, String password, String fullname, String email, String phoneNumber,
			String address, String role) throws SQLException {
		
		boolean isUserNameExsited = checkUserNameExsited(username);
		boolean isEmailExsited = checkEmailExsited(email);
		if (isUserNameExsited || isEmailExsited) {
			return NotificationMessageConstant.USER_EXISTED;
		}
		
		EStatus userStatus = EStatus.ACTIVE;
		RoleModel userRole = mRoleDbUtil.getRoleByName(role);
		long userRoleId = userRole.getId();
		
		UserModel newUser = new UserModel(username, password, fullname, email, phoneNumber, address, userStatus, userRoleId);
		
		try {			
			mUserDbUtil.createUser(newUser);
			return NotificationMessageConstant.SUCCESS;
		} catch (Exception e) {
			return NotificationMessageConstant.NOT_SUCCESS;
		}
		
	}
	
	// PRIVATE
	private boolean checkUserNameExsited(String username) throws SQLException {
		return mUserDbUtil.getUserByUserName(username) != null;
	}

	private boolean checkEmailExsited(String email) throws SQLException {
		return mUserDbUtil.getUserByEmail(email) != null;
	}

}
