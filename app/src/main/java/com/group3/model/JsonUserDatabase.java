package com.group3.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;

public class JsonUserDatabase extends AJsonDatabase<List<User>> {

    public JsonUserDatabase() {
        super("users.json", new TypeToken<ArrayList<User>>() {}.getType());
    }

    @Override
    protected List<User> getDefaultValue() {
        return new ArrayList<>();
    }
	// Supporter method for Login and Register functions
	public User checkLogin(String username, String password) {
		List<User> users = loadData();
		for (User user : users) {
			if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
				return user;
			}
		}
		return null;
	}

	// For register manager controller
	public boolean isUsernameExist(String username) {
		List<User> users = loadData();
		for (User user : users) {
			if (user.getUsername().equalsIgnoreCase(username)) {
				return true;
			}
		}
		return false;
	}

	public boolean addUser(User newUser) {
		List<User> users = loadData();

		int newId = 1;
		if (!users.isEmpty()) {
			newId = users.get(users.size() - 1).getUserID() + 1;
		}
		newUser.setUserID(newId);
		// Save to users.json file
		users.add(newUser);
		return saveData(users);
	}

	// Extra method for set Goal in case user want to change their goal
	// That will update all content in user.json
	public boolean updateUser(User updatedUser) {
		List<User> users = loadData();
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUserID() == updatedUser.getUserID()) {
				users.set(i, updatedUser);
				return saveData(users);
			}
		}
		return false;
	}
}