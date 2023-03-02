package com.example.commons;

import com.example.data.Owner;
import com.example.data.enums.Role;

@SuppressWarnings("serial")
public class Constants {
	public static final Owner OWNER_1 = new Owner(null, "ownerName1", "owner1@email.com", "password1", Role.ADMINISTRATOR, null, null);
	public static final Owner OWNER_2 = new Owner(null, "ownerName2", "owner2@email.com", "password2", Role.SIMPLE, null, null);
	public static final Owner OWNER_3 = new Owner(null, "ownerName3", "owner3@email.com", "password3", Role.ADMINISTRATOR, null, null);
	public static final Owner OWNER_4 = new Owner(null, "ownerName4", "owner4@email.com", "password4", Role.SIMPLE, null, null);
	public static final Owner OWNER_5 = new Owner(null, "ownerName5", "owner5@email.com", "password5", Role.ADMINISTRATOR, null, null);
	public static final Owner OWNER_6 = new Owner(null, "ownerName6", "owner6@email.com", "password6", Role.SIMPLE, null, null);
	public static final Owner OWNER_7 = new Owner(null, "ownerName7", "owner7@email.com", "password7", Role.ADMINISTRATOR, null, null);
	public static final Owner OWNER_8 = new Owner(null, "ownerName8", "owner8@email.com", "password8", Role.SIMPLE, null, null);
	public static final Owner OWNER_9 = new Owner(null, "ownerName9", "owner9@email.com", "password9", Role.ADMINISTRATOR, null, null);
	public static final Owner OWNER_10 = new Owner(null, "ownerName10", "owner10@email.com", "password10", Role.SIMPLE, null, null);
	public static final Owner OWNER_11 = new Owner(null, "ownerName11", "owner11@email.com", "password11", Role.ADMINISTRATOR, null, null);
	public static final Owner OWNER_12 = new Owner(null, "ownerName12", "owner12@email.com", "password12", Role.ADMINISTRATOR, null, null);
	public static final Owner OWNER_13 = new Owner(null, "ownerName13", "owner13@email.com", "password13", Role.ADMINISTRATOR, null, null);
	public static final Owner OWNER_14 = new Owner(null, "ownerName14", "owner14@email.com", "password14", Role.ADMINISTRATOR, null, null);
	public static final Owner OWNER_15 = new Owner(null, "ownerName15", "owner15@email.com", "password15", Role.ADMINISTRATOR, null, null);
	public static final Owner OWNER_16 = new Owner(null, "ownerName16", "owner16@email.com", "password16", Role.ADMINISTRATOR, null, null);
	public static final Owner OWNER_17 = new Owner(null, "ownerName17", "owner17@email.com", "password17", Role.ADMINISTRATOR, null, null);
	
	public static final Owner UPDATED_OWNER_11 = new Owner(null, "ownerName11up", "owner11up@email.com", "password11up", Role.SIMPLE, null, null);
	
	public static final Owner EMPTY_OWNER = new Owner();
	public static final Owner INVALID_OWNER = new Owner(null, null, null, null, null, null, null);
	
}