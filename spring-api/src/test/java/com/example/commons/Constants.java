package com.example.commons;

import com.example.data.entity.User;
import com.example.data.enums.Role;

public class Constants {
	
	public static final User USER_1 = new User(null, "userName1", "user1@email.com", "password1", Role.ADMINISTRATOR, null, null);
	public static final User USER_2 = new User(null, "userName2", "user2@email.com", "password2", Role.SIMPLE, null, null);
	public static final User USER_3 = new User(null, "userName3", "user3@email.com", "password3", Role.ADMINISTRATOR, null, null);
	public static final User USER_4 = new User(null, "userName4", "user4@email.com", "password4", Role.SIMPLE, null, null);
	public static final User USER_5 = new User(null, "userName5", "user5@email.com", "password5", Role.ADMINISTRATOR, null, null);
	public static final User USER_6 = new User(null, "userName6", "user6@email.com", "password6", Role.SIMPLE, null, null);
	public static final User USER_7 = new User(null, "userName7", "user7@email.com", "password7", Role.ADMINISTRATOR, null, null);
	public static final User USER_8 = new User(null, "userName8", "user8@email.com", "password8", Role.SIMPLE, null, null);
	public static final User USER_9 = new User(null, "userName9", "user9@email.com", "password9", Role.ADMINISTRATOR, null, null);
	public static final User USER_10 = new User(null, "userName10", "user10@email.com", "password10", Role.SIMPLE, null, null);
	public static final User USER_11 = new User(null, "userName11", "user11@email.com", "password11", Role.ADMINISTRATOR, null, null);
	public static final User USER_12 = new User(null, "userName12", "user12@email.com", "password12", Role.ADMINISTRATOR, null, null);
	public static final User USER_13 = new User(null, "userName13", "user13@email.com", "password13", Role.ADMINISTRATOR, null, null);
	public static final User USER_14 = new User(null, "userName14", "user14@email.com", "password14", Role.ADMINISTRATOR, null, null);
	public static final User USER_15 = new User(null, "userName15", "user15@email.com", "password15", Role.ADMINISTRATOR, null, null);
	public static final User USER_16 = new User(null, "userName16", "user16@email.com", "password16", Role.ADMINISTRATOR, null, null);
	public static final User USER_17 = new User(null, "userName17", "user17@email.com", "password17", Role.ADMINISTRATOR, null, null);
	
	public static final User UPDATED_USER_11 = new User(null, "userName11up", "user11up@email.com", "password11up", Role.SIMPLE, null, null);
	
	public static final User EMPTY_USER = new User();
	public static final User INVALID_USER = new User(null, null, null, null, null, null, null);
	
}