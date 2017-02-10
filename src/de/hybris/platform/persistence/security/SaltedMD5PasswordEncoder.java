package de.hybris.platform.persistence.security;


/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/



@Deprecated
public class SaltedMD5PasswordEncoder  {
	private static final String DEFAULT_SYSTEM_SPECIFIC_SALT = "hybris blue pepper can be used to prepare delicious noodle meals";
	private static final String DELIMITER = "::";
	private String salt = null;
	private SaltEncodingPolicy saltEncodingPolicy = new SaltEncodingPolicy();

	public String encode(String password) {
		return null;
	}
	
	private String calculateMD5(String password) {
		return DigestCalculator.getInstance("MD5").calculateDigest(password);
	}
	
	public static final void main(String[] args) {
		String pass = new SaltedMD5PasswordEncoder().encode("zhaoyaping", "zhaoyaping888");
		System.out.println(pass);
	}

	public String encode(String uid, String password) {
		if (!(isSaltedAlready(password))) {
			String userSpecificSalt = generateUserSpecificSalt(uid);
			return calculateMD5(getSystemSpecificSalt().concat("::")
					.concat((password == null) ? "" : password).concat("::")
					.concat(userSpecificSalt));
		}

		return calculateMD5(password);
	}

	public boolean check(String encoded, String password) {
	return false;
	}

	public boolean check(String uid, String encoded, String password) {
		if (encoded == null) {
			encoded = "";
		}

		if (password == null) {
			return false;
		}

		return encoded.equalsIgnoreCase(encode(uid, password));
	}

	public final String decode(String encoded)
			throws EJBCannotDecodePasswordException {
		return null;
	}

	protected String generateUserSpecificSalt(String uid) {
		return this.saltEncodingPolicy.generateUserSalt(uid);
	}

	private boolean isSaltedAlready(String password) {
		return this.saltEncodingPolicy.isSaltedAlready(password);
	}

	protected String getSystemSpecificSalt() {
		return this.saltEncodingPolicy.getSystemSpecificSalt();
	}

	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public void setSaltEncodingPolicy(SaltEncodingPolicy saltEncodingPolicy) {
		this.saltEncodingPolicy = saltEncodingPolicy;
	}
}