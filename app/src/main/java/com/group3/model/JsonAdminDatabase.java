package com.group3.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;

public class JsonAdminDatabase extends AJsonDatabase<List<Admin>> {
	public JsonAdminDatabase() {
		super("admin.json", new TypeToken<ArrayList<Admin>>() {}.getType());
	}
	// Extra method for LoginManager
	public Admin checkLogin(String username, String password) {
		List<Admin> admins = loadData();
		for (Admin admin : admins) {
			if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
				return admin;
			}
		}
		return null;
	}
	public boolean isUsernameExist(String username) {
		List<Admin> admins = loadData();
		for (Admin admin : admins) {
			if (admin.getUsername().equalsIgnoreCase(username)) {
				return true;
			}
		}
		return false;
	}
	@Override
	protected List<Admin> getDefaultValue() {
		return new ArrayList<>();
	}
}