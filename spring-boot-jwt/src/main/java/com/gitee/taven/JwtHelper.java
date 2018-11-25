package com.gitee.taven;

import java.util.Date;

import com.gitee.taven.entity.Users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtHelper {

	private static final String SECRET = "SECRET";
	
	private static final Integer EXP = 7200000;
	
	public static final String USERNAME = "username";
	
	public static String createJWT(Users u) {
		JwtBuilder jwtBuilder = Jwts
								  .builder()
								  .setSubject(u.getId().toString())
								  .setExpiration(new Date(System.currentTimeMillis() + EXP))
								  .claim(USERNAME, u.getUsername())
								  .signWith(SignatureAlgorithm.HS256, SECRET);
		return jwtBuilder.compact();
	}
	
	public static Claims getClaims(String jwt) {
		return Jwts.parser().setSigningKey(SECRET)
                .parseClaimsJws(jwt).getBody();
	}
	
	public static boolean isExpToken(Claims c) {
		Date exp = c.getExpiration();
		Date current = new Date();
		return exp.compareTo(current) <= 0;
	}
	
}
