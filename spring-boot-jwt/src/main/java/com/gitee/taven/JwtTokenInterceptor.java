package com.gitee.taven;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.gitee.taven.entity.Users;
import com.gitee.taven.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureException;

public class JwtTokenInterceptor implements HandlerInterceptor {

	@Autowired private UserService userService;
	
	public final static Logger LOGGER = LoggerFactory.getLogger(JwtTokenInterceptor.class);
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
		
		LOGGER.info("Execute JWT interceptor");
		
		if (request.getRequestURI().contains("/user/login"))
			return true;
		
		final String authHeader = request.getHeader("Auth-Token");
		
		try {
			
			if (authHeader == null || authHeader.trim() == "")
			    throw new SignatureException("not found Auth-Token.");

			Claims claims = JwtHelper.getClaims(authHeader);
			String username = claims.get(JwtHelper.USERNAME, String.class);
			Users u = userService.getUserByUsername(username);
			String token = u.getToken();
			
			if (token == null || token.trim() == "")
				throw new SignatureException("invalid token, please login again");
			
			if (!token.equals(authHeader))
				throw new SignatureException("invalid token");
			
			if (JwtHelper.isExpToken(claims))
				throw new SignatureException("the token expired");
			
		} catch (SignatureException e) {
			LOGGER.info(e.getMessage());
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			PrintWriter pw = response.getWriter();
			pw.write(e.getMessage());
			pw.close();
			return false;
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			PrintWriter pw = response.getWriter();
			pw.write(e.getMessage());
			pw.close();
			return false;
		} 
		
		return true;
	}
	
	@Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
	
}
