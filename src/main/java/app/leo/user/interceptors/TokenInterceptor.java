package app.leo.user.interceptors;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.HandlerInterceptor;

import app.leo.user.DTO.UserDTO;
import app.leo.user.exceptions.BadRequestException;
import app.leo.user.models.Token;
import app.leo.user.services.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@ComponentScan
public class TokenInterceptor implements HandlerInterceptor {

	@Value("${jwt.secret}")
	private String SECRET;

	@Autowired
	private TokenService tokenService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws BadRequestException    {
		if (this.isOptionMethod(request)) {
			return true;
		}
		String token = getToken(request);
		UserDTO user = this.getUserFromToken(token);
		request.setAttribute("user", user);
		request.setAttribute("token", token);
		return true;
	}

	private boolean isValidToken (String token){
		if (token == null) {
			return false;
		} else if (!token.startsWith("Bearer") ||token.length() < 7) {
			return false;
		}
		return !isExpires(token);
	}

	private boolean isExpires(String token){
		String username = getUsernameFromToken(token);
		String tokenFormat = getTokenFormat(token);
		Token tokenByUsernameAndToken = tokenService.getTokenByUsernameAndToken(username, tokenFormat);
		if (tokenByUsernameAndToken == null) {
			return true;
		}
		return new Date(System.currentTimeMillis()).after(tokenByUsernameAndToken.getExpiresTime());
	}

	private String getToken(HttpServletRequest httpServletRequest) throws BadRequestException {
		String token = httpServletRequest.getHeader("Authorization");
		if (!this.isValidToken(token)) {
			throw new BadRequestException("Invalid authorization provided.");
		}
		return token;
	}

	private UserDTO getUserFromToken(String token) {
		String tokenFormat = getTokenFormat(token);

		Jws<Claims> claims = Jwts.parser()
				.setSigningKey(this.SECRET)
				.parseClaimsJws(tokenFormat);

		long userId = Long.parseLong((String) claims.getBody().get("userId"));
		String role = (String) claims.getBody().get("role");
		long profileId = Long.parseLong((String) claims.getBody().get("profileId"));
		return new UserDTO(userId, role, profileId);
	}

	private boolean isOptionMethod(HttpServletRequest request) {
		return "OPTIONS".equalsIgnoreCase(request.getMethod());
	}

	private String getUsernameFromToken(String token){
		String tokenFormat = getTokenFormat(token);
		Jws<Claims> claims = Jwts.parser().setSigningKey(this.SECRET).parseClaimsJws(tokenFormat);
		return (String) claims.getBody().get("sub");
	}

	private String getTokenFormat(String token) {
		return token.substring(7);
	}
}