package app.leo.user.exceptions;

public class BadRequestException extends HttpException {
	public BadRequestException(String s) {
		super(s);
	}
}
