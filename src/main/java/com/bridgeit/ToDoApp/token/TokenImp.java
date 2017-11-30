package com.bridgeit.ToDoApp.token;

import java.util.Calendar;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author ThakurGulab
 * @this Class Implement of Token
 */
public class TokenImp implements IToken {
	String key = "createKey";
	String token = null;

	public String genratedToken(int id) {
		Calendar calendar = Calendar.getInstance();
		Date currentTime = calendar.getTime();
		calendar.add(Calendar.MINUTE, 4000);
		Date expireTime = calendar.getTime();
		token = Jwts.builder().setId(String.valueOf(id)).setIssuedAt(currentTime).setExpiration(expireTime)
				.signWith(SignatureAlgorithm.HS256, key).compact();
		return token;
	}

	public int varifyToken(String token) {

		try {
			Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
			return Integer.parseInt(claims.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

}
