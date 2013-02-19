package co.okmercury.security

class UserAlreadyExistsException extends Exception {
	UserAlreadyExistsException(String email) {
		super("User with email ${email} already exists")
	}
}
