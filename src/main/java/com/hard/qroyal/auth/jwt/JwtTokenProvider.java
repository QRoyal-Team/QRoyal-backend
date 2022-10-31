package com.hard.qroyal.auth.jwt;

import com.hard.qroyal.auth.MyUserDetails;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtTokenProvider {

	private final String JWT_SECRET = "QROYAL-SECRET";

	private final long JWT_EXPIRATION = 604800000L;

	public String generateToken(MyUserDetails userDetails) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
		Map<String, Object> claims = new HashMap<>();
		claims.put("name", userDetails.getUser().getName());
		claims.put("username", userDetails.getUsername());
		claims.put("role", userDetails.getAuthorities());
		return Jwts.builder().setSubject(Long.toString(userDetails.getUser().getId())).addClaims(claims)
				.setIssuedAt(now).setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, JWT_SECRET)
				.compact();
	}

	public String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	public Long getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
			return true;
		} catch (MalformedJwtException ex) {
			log.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			log.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			log.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			log.error("JWT claims string is empty.");
		}
		return false;
	}
}
