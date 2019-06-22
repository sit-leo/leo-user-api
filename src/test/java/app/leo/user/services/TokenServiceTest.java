package app.leo.user.services;

import org.junit.Assert;
import org.junit.Test;

import app.leo.user.DTO.Token;
import app.leo.user.models.User;

public class TokenServiceTest {

	@Test
	public void testGeneratedTokenIsUsable() {
		TokenService tokenService = new TokenService();
		tokenService.setSecret("secret");
		tokenService.setExpires("9999");

		User user = new User();
		user.setUsername("keerati");
		user.setPassword("1234");
		user.setRole("applicant");

		Token token = tokenService.generateTokenByUser(user);
		String username = tokenService.getUsernameFromToken("Bearer " + token.getToken());

		Assert.assertEquals(user.getUsername(), username);
	}
}