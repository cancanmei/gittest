/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package de.hybris.platform.persistence.security;


public class SaltEncodingPolicy {
	private static final String DELIMITER = "::";
	private static final String DEFAULT_SYSTEM_SPECIFIC_SALT = "hybris blue pepper can be used to prepare delicious noodle meals";
	private String salt = null;

	public String generateUserSalt(String uid) {
		return uid;
	}

	public String saltify(String uid, String password) {
		if (!(isSaltedAlready(password))) {
			String userSpecificSalt = generateUserSalt(uid);
			return getSystemSpecificSalt().concat("::")
					.concat((password == null) ? "" : password).concat("::")
					.concat(userSpecificSalt);
		}

		return password;
	}

	public boolean isSaltedAlready(String password) {
		if (password == null) {
			return false;
		}

		return password.startsWith(getSystemSpecificSalt().concat("::"));
	}

	public String getSystemSpecificSalt() {
			return ((this.salt != null) ? this.salt
					: "hybris blue pepper can be used to prepare delicious noodle meals");
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
}